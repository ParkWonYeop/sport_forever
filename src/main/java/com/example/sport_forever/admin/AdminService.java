package com.example.sport_forever.admin;

import com.example.sport_forever.admin.dto.ActivateDto;
import com.example.sport_forever.admin.dto.FacilityDto;
import com.example.sport_forever.admin.dto.WareDto;
import com.example.sport_forever.common.controller.SportException;
import com.example.sport_forever.common.controller.constant.CommunalResponse;
import com.example.sport_forever.common.entity.*;
import com.example.sport_forever.common.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final WareRepository wareRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;
    private final BanRepository banRepository;
    private final FacilityRepository facilityRepository;

    @Transactional
    public void addWare(WareDto wareDto) {
        if(wareRepository.findByName(wareDto.name()).isPresent()) {
            throw new SportException(CommunalResponse.WARE_ALREADY_ADD);
        }

        WareEntity wareEntity = new WareEntity(wareDto.category(), wareDto.name(), wareDto.maxCount());

        wareRepository.save(wareEntity);
    }

    @Transactional
    public void deleteBanedUser(String phoneNumber) {
        Optional<UserEntity> userEntityOptional = userRepository.findUserEntityByPhoneNumber(phoneNumber);
        if(userEntityOptional.isEmpty()) {
            throw new SportException(CommunalResponse.USER_NOT_FOUND);
        }

        UserEntity userEntity = userEntityOptional.get();

        Optional<BanEntity> banEntityOptional = banRepository.findByUserEntity(userEntity);

        if(banEntityOptional.isEmpty()) {
            throw new SportException(CommunalResponse.BAN_USER_NOT_FOUND);
        }

        banRepository.delete(banEntityOptional.get());
    }

    @Transactional
    public void activateRental(ActivateDto activateDto) {
        Optional<UserEntity> userEntityOptional = userRepository.findUserEntityByPhoneNumber(activateDto.phoneNumber());

        if(userEntityOptional.isEmpty()) {
            throw new SportException(CommunalResponse.USER_NOT_FOUND);
        }

        UserEntity userEntity = userEntityOptional.get();

        Optional<WareEntity> wareEntityOptional = wareRepository.findByName(activateDto.wareName());

        if(wareEntityOptional.isEmpty()) {
            throw new SportException(CommunalResponse.WARE_NOT_FOUND);
        }

        WareEntity wareEntity = wareEntityOptional.get();

        Optional<RentalEntity> rentalEntityOptional = rentalRepository.findByUserEntityAndWareEntity(userEntity, wareEntity);

        if(rentalEntityOptional.isEmpty()) {
            throw new SportException(CommunalResponse.RENTAL_NOT_FOUND);
        }

        RentalEntity rentalEntity = rentalEntityOptional.get();

        rentalEntity.setActivate(true);

        rentalRepository.save(rentalEntity);
    }

    @Transactional
    public void banUser(String phoneNumber) {
        Optional<UserEntity> userEntityOptional = userRepository.findUserEntityByPhoneNumber(phoneNumber);
        if(userEntityOptional.isEmpty()) {
            throw new SportException(CommunalResponse.USER_NOT_FOUND);
        }
        UserEntity userEntity = userEntityOptional.get();

        Optional<BanEntity> optionalBanEntity = banRepository.findByUserEntity(userEntity);

        LocalDateTime now = LocalDateTime.now();

        if(optionalBanEntity.isEmpty()) {
            BanEntity banEntity = new BanEntity(userEntity);

            banRepository.save(banEntity);
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

    @Transactional
    public void addFacility(FacilityDto facilityDto) {
        if(facilityRepository.findByName(facilityDto.name()).isPresent()) {
            throw new SportException(CommunalResponse.FACILITY_ALREADY_ADD);
        }

        FacilityEntity facilityEntity = new FacilityEntity(facilityDto.name());

        facilityRepository.save(facilityEntity);
    }
}
