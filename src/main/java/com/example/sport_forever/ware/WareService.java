package com.example.sport_forever.ware;

import com.example.sport_forever.common.controller.SportException;
import com.example.sport_forever.common.controller.constant.CommunalResponse;
import com.example.sport_forever.common.entity.BanEntity;
import com.example.sport_forever.common.entity.RentalEntity;
import com.example.sport_forever.common.entity.UserEntity;
import com.example.sport_forever.common.entity.WareEntity;
import com.example.sport_forever.common.enums.WareEnum;
import com.example.sport_forever.common.repository.BanRepository;
import com.example.sport_forever.common.repository.RentalRepository;
import com.example.sport_forever.common.repository.UserRepository;
import com.example.sport_forever.common.repository.WareRepository;
import com.example.sport_forever.ware.dto.RentalDto;
import com.example.sport_forever.ware.dto.RentalResponseDto;
import com.example.sport_forever.ware.dto.ReturnDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.sport_forever.common.utils.SecurityUtil.getCurrentMemberId;

@Service
@Slf4j
@RequiredArgsConstructor
public class WareService {
    private final WareRepository wareRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;
    private final BanRepository banRepository;

    @Transactional
    public void rentalWare(RentalDto rentalDto) {
        Optional<WareEntity> optionalWareEntity = wareRepository.findByName(rentalDto.wareName());

        if (optionalWareEntity.isEmpty()) {
            throw new SportException(CommunalResponse.WARE_NOT_FOUND);
        }

        WareEntity wareEntity = optionalWareEntity.get();

        if (wareEntity.getCurrentCount() == 0) {
            throw new SportException(CommunalResponse.WARE_COUNT_ZERO);
        }

        Optional<UserEntity> optionalUserEntity = userRepository.findUserEntityByPhoneNumber(getCurrentMemberId());

        if (optionalUserEntity.isEmpty()) {
            throw new SportException(CommunalResponse.USER_NOT_FOUND);
        }

        UserEntity userEntity = optionalUserEntity.get();

        Optional<BanEntity> optionalBanEntity = banRepository.findByUserEntity(userEntity);

        if (optionalBanEntity.isPresent()) {
            BanEntity banEntity = optionalBanEntity.get();

            if (banEntity.getBanTime().isAfter(LocalDateTime.now())) {
                throw new SportException(CommunalResponse.BANED_USER);
            }
        }

        Optional<RentalEntity> optionalRentalEntity = rentalRepository.findByUserEntityAndWareEntity(userEntity, wareEntity);

        if (optionalRentalEntity.isPresent()) {
            WareEnum wareCategory = optionalRentalEntity.get().getWareEntity().getCategory();
            if (wareCategory == wareEntity.getCategory()) {
                throw new SportException(CommunalResponse.ALREADY_RENTAL);
            }
        }

        RentalEntity rentalEntity = new RentalEntity(wareEntity, userEntity, rentalDto.rentalTime(), rentalDto.endTime());

        wareEntity.setCurrentCount(wareEntity.getCurrentCount() - 1);
        wareRepository.save(wareEntity);

        rentalRepository.save(rentalEntity);
    }

    @Transactional(readOnly = true)
    public List<RentalResponseDto> getRental() {
        Optional<UserEntity> optionalUserEntity = userRepository.findUserEntityByPhoneNumber(getCurrentMemberId());

        if (optionalUserEntity.isEmpty()) {
            throw new SportException(CommunalResponse.USER_NOT_FOUND);
        }

        UserEntity userEntity = optionalUserEntity.get();

        List<RentalEntity> rentalEntityList = rentalRepository.findByUserEntity(userEntity);

        List<RentalResponseDto> rentalResponseDtoList = new ArrayList<>();

        for (RentalEntity rentalEntity : rentalEntityList) {
            rentalResponseDtoList.add(new RentalResponseDto(rentalEntity.getWareEntity().getName(), rentalEntity.getRentalTime(), rentalEntity.getEndTime()));
        }

        return rentalResponseDtoList;
    }

    @Transactional
    public void returnWare(ReturnDto returnDto) {
        Optional<UserEntity> optionalUserEntity = userRepository.findUserEntityByPhoneNumber(getCurrentMemberId());

        if (optionalUserEntity.isEmpty()) {
            throw new SportException(CommunalResponse.USER_NOT_FOUND);
        }

        UserEntity userEntity = optionalUserEntity.get();

        Optional<WareEntity> optionalWareEntity = wareRepository.findByName(returnDto.wareName());

        if (optionalWareEntity.isEmpty()) {
            throw new SportException(CommunalResponse.WARE_NOT_FOUND);
        }

        WareEntity wareEntity = optionalWareEntity.get();

        Optional<RentalEntity> optionalRentalEntity = rentalRepository.findByUserEntityAndWareEntity(userEntity, wareEntity);

        if (optionalRentalEntity.isEmpty()) {
            throw new SportException(CommunalResponse.RENTAL_NOT_FOUND);
        }

        RentalEntity rentalEntity = optionalRentalEntity.get();

        LocalDateTime now = LocalDateTime.now();

        if (rentalEntity.getEndTime().plusMinutes(30).isBefore(now)) {
            Optional<BanEntity> optionalBanEntity = banRepository.findByUserEntity(userEntity);
            if (optionalBanEntity.isEmpty()) {
                BanEntity banEntity = new BanEntity(userEntity);
                banRepository.save(banEntity);
            } else {
                BanEntity banEntity = optionalBanEntity.get();
                if (banEntity.getBanCount() == 1) {
                    banEntity.setBanCount(2);
                    banEntity.setBanTime(now.plusDays(7));
                } else if (banEntity.getBanCount() == 2) {
                    banEntity.setBanCount(3);
                    banEntity.setBanTime(now.plusYears(100));
                }
                banRepository.save(banEntity);
            }
        }

        wareEntity.setCurrentCount(wareEntity.getCurrentCount() + 1);
        wareRepository.save(wareEntity);

        rentalRepository.delete(rentalEntity);
    }

    @Transactional(readOnly = true)
    public List<WareEntity> getWareList() {
        return wareRepository.findAll();
    }
}
