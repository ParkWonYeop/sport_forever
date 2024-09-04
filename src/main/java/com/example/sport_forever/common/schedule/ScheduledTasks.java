package com.example.sport_forever.common.schedule;

import com.example.sport_forever.common.entity.*;
import com.example.sport_forever.common.repository.BanRepository;
import com.example.sport_forever.common.repository.FacilityRepository;
import com.example.sport_forever.common.repository.RentalRepository;
import com.example.sport_forever.common.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class ScheduledTasks {
    private final ReservationRepository reservationRepository;
    private final RentalRepository rentalRepository;
    private final BanRepository banRepository;
    private final FacilityRepository facilityRepository;

    @Transactional
    @Scheduled(cron = "0 1 9-22 * * ?")
    public void deleteReservation() {
        List<ReservationEntity> reservationEntityList = reservationRepository.findAll();
        List<RentalEntity> rentalEntityList = rentalRepository.findAll();

        LocalDateTime now = LocalDateTime.now();

        for (ReservationEntity reservationEntity : reservationEntityList) {
            if (now.isAfter(reservationEntity.getEndTime())) {
                if(!reservationEntity.getActivate()) {
                    banUser(reservationEntity.getUserEntity(), now);
                }
                reservationRepository.delete(reservationEntity);
                FacilityEntity facility = reservationEntity.getFacilityEntity();
                facility.setReservationAble(true);
                facilityRepository.save(facility);
            }
        }

        for (RentalEntity rentalEntity : rentalEntityList) {
            if (now.isAfter(rentalEntity.getEndTime())) {
                if(!rentalEntity.getActivate()) {
                    banUser(rentalEntity.getUserEntity(), now);
                    rentalRepository.delete(rentalEntity);
                }
            }
        }

        log.info("ScheduledTaks : 삭제완료");
    }

    public void banUser(UserEntity userEntity, LocalDateTime now) {
        Optional<BanEntity> optionalBanEntity = banRepository.findByUserEntity(userEntity);
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
}
