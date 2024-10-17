package com.example.sport_forever.facility;

import com.example.sport_forever.common.entity.FacilityEntity;
import com.example.sport_forever.facility.dto.EndDto;
import com.example.sport_forever.facility.dto.ReservationDto;
import com.example.sport_forever.facility.dto.ReservationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Facility", description = "운동 시설 관련 기능")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/facility")
public class FacilityController {
    private final FacilityService facilityService;

    @Operation(
            operationId = "운동 시설 예약",
            summary = "운동 시설을 예약합니다.",
            description = "운동 시설을 예약합니다."
    )
    @PostMapping("/reservation")
    @ResponseStatus(HttpStatus.CREATED)
    public void reservationFacility(@RequestBody @Valid ReservationDto reservationDto) {
        facilityService.reservationFacility(reservationDto);
    }

    @Operation(
            operationId = "운동 시설 이용 종료",
            summary = "운동 시설을 이용 종료합니다.",
            description = "운동 시설을 이용 종료합니다."
    )
    @DeleteMapping("/reservation")
    public void endFacility(@RequestBody @Valid EndDto endDto) {
        facilityService.endFacility(endDto);
    }

    @Operation(
            operationId = "운동 시설 리스트 조회",
            summary = "운동 시설 리스트를 조회합니다.",
            description = "운동 시설 리스트를 조회합니다."
    )
    @GetMapping
    public List<FacilityEntity> getFacilityList() {
        return facilityService.getFacilities();
    }

    @Operation(
            operationId = "운동 시설 예약 조회",
            summary = "운동 시설을 예약했는지 조회하고, 예약 정보를 반환합니다.",
            description = "운동 시설을 예약했는지 조회하고, 예약 정보를 반환합니다."
    )
    @GetMapping("/reservation")
    public ReservationResponseDto getReservation() {
        return facilityService.getReservation();
    }

    @Operation(
            operationId = "예약 활성화",
            summary = "예약을 활성화 합니다.",
            description = "예약을 활성화 합니다."
    )
    @PutMapping("/reservation")
    public void activeReservation(@RequestParam Long reservationId) {
        facilityService.activeReservation(reservationId);
    }
}
