package com.example.sport_forever.facility;

import com.example.sport_forever.common.controller.SportException;
import com.example.sport_forever.common.controller.constant.CommunalResponse;
import com.example.sport_forever.common.entity.BanEntity;
import com.example.sport_forever.common.entity.FacilityEntity;
import com.example.sport_forever.common.entity.ReservationEntity;
import com.example.sport_forever.common.entity.UserEntity;
import com.example.sport_forever.common.repository.BanRepository;
import com.example.sport_forever.common.repository.FacilityRepository;
import com.example.sport_forever.common.repository.ReservationRepository;
import com.example.sport_forever.common.repository.UserRepository;
import com.example.sport_forever.facility.dto.EndDto;
import com.example.sport_forever.facility.dto.ReservationDto;
import com.example.sport_forever.facility.dto.ReservationResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.sport_forever.common.utils.SecurityUtil.getCurrentMemberId;

@Service
@Slf4j
@RequiredArgsConstructor
public class FacilityService {
    private final FacilityRepository facilityRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final BanRepository banRepository;

    @Transactional
    public void reservationFacility(ReservationDto reservationDto) {
        Optional<UserEntity> optionalUserEntity = userRepository.findUserEntityByPhoneNumber(getCurrentMemberId());

        if(optionalUserEntity.isEmpty()) {
            throw new SportException(CommunalResponse.USER_NOT_FOUND);
        }

        UserEntity userEntity = optionalUserEntity.get();

        Optional<BanEntity> banEntityOptional = banRepository.findByUserEntity(userEntity);

        if(banEntityOptional.isPresent()) {
            BanEntity banEntity = banEntityOptional.get();
            if(banEntity.getBanTime().isAfter(LocalDateTime.now())) {
                throw new SportException(CommunalResponse.BANED_USER);
            }
        }

        if(reservationRepository.findByUserEntity(userEntity).isPresent()) {
            throw new SportException(CommunalResponse.ALREADY_RESERVATION_USER);
        }

        Optional<FacilityEntity> optionalFacilityEntity = facilityRepository.findByName(reservationDto.facilityName());

        if (optionalFacilityEntity.isEmpty()) {
            throw new SportException(CommunalResponse.FACILITY_NOT_FOUND);
        }

        FacilityEntity facilityEntity = optionalFacilityEntity.get();

        if(!facilityEntity.getReservationAble()) {
            throw new SportException(CommunalResponse.RESERVATION_NOT_ABLE);
        }

        if(reservationRepository.findByFacilityEntity(facilityEntity).isPresent()) {
            throw new SportException(CommunalResponse.ALREADY_RESERVATION_FACILITY);
        }

        facilityEntity.setReservationAble(false);
        ReservationEntity reservationEntity = new ReservationEntity(facilityEntity, userEntity, reservationDto.reservationTime(),reservationDto.endTime());

        facilityRepository.save(facilityEntity);
        reservationRepository.save(reservationEntity);
    }

    @Transactional
    public void endFacility(EndDto endDto) {
        Optional<UserEntity> optionalUserEntity = userRepository.findUserEntityByPhoneNumber(getCurrentMemberId());
        if(optionalUserEntity.isEmpty()) {
            throw new SportException(CommunalResponse.USER_NOT_FOUND);
        }
        UserEntity userEntity = optionalUserEntity.get();

        Optional<FacilityEntity> optionalFacilityEntity = facilityRepository.findByName(endDto.facilityName());
        if(optionalFacilityEntity.isEmpty()) {
            throw new SportException(CommunalResponse.FACILITY_NOT_FOUND);
        }

        FacilityEntity facilityEntity = optionalFacilityEntity.get();

        Optional<ReservationEntity> optionalReservationEntity = reservationRepository.findByFacilityEntity(facilityEntity);

        if(optionalReservationEntity.isEmpty()) {
            throw new SportException(CommunalResponse.RESERVATION_NOT_FOUND);
        }

        ReservationEntity reservationEntity = optionalReservationEntity.get();

        if(!reservationEntity.getUserEntity().equals(userEntity)) {
            throw new SportException(CommunalResponse.RESERVATION_USER_NOT_CORRECT);
        }

        LocalDateTime now = LocalDateTime.now();

        if(reservationEntity.getEndTime().plusMinutes(30).isAfter(now)) {
            Optional<BanEntity> optionalBanEntity = banRepository.findByUserEntity(userEntity);
            if(optionalBanEntity.isEmpty()) {
                BanEntity banEntity = new BanEntity();
                banEntity.setUserEntity(userEntity);
            } else {
                BanEntity banEntity = optionalBanEntity.get();
                if(banEntity.getBanCount() == 1) {
                    banEntity.setBanCount(2);
                    banEntity.setBanTime(now.plusDays(7));
                } else if(banEntity.getBanCount() == 2) {
                    banEntity.setBanCount(3);
                    banEntity.setBanTime(now.plusYears(100));
                }
                banRepository.save(banEntity);
            }
        }

        facilityEntity.setReservationAble(true);
        facilityRepository.save(facilityEntity);

        reservationRepository.delete(reservationEntity);
    }

    public List<FacilityEntity> getFacilities() {
        return facilityRepository.findAll();
    }

    public ReservationResponseEntity getReservation() {
        Optional<UserEntity> optionalUserEntity = userRepository.findUserEntityByPhoneNumber(getCurrentMemberId());
        if(optionalUserEntity.isEmpty()) {
            throw new SportException(CommunalResponse.USER_NOT_FOUND);
        }
        UserEntity userEntity = optionalUserEntity.get();

        Optional<ReservationEntity> optionalReservationEntity = reservationRepository.findByUserEntity(userEntity);

        if(optionalReservationEntity.isEmpty()) {
            throw new SportException(CommunalResponse.RESERVATION_NOT_FOUND);
        }

        return new ReservationResponseEntity(
                optionalReservationEntity.get().getFacilityEntity().getName(),
                optionalReservationEntity.get().getActivate(),
                optionalReservationEntity.get().getReservationTime(),
                optionalReservationEntity.get().getEndTime()
        );
    }
}
