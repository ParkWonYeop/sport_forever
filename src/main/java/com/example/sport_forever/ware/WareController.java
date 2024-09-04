package com.example.sport_forever.ware;

import com.example.sport_forever.common.entity.WareEntity;
import com.example.sport_forever.ware.dto.RentalDto;
import com.example.sport_forever.ware.dto.RentalResponseDto;
import com.example.sport_forever.ware.dto.ReturnDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Rental Ware", description = "물품 대여 관련 기능")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/ware")
public class WareController {
    private final WareService rentalWareService;

    @Operation(
            operationId = "물품 대여",
            summary = "물품을 대여합니다.",
            description = "물품명, 예약 시간을 받아 예약합니다."
    )
    @PostMapping("/rental")
    @ResponseStatus(HttpStatus.CREATED)
    public void rentalWare(@RequestBody @Valid RentalDto rentalDto) {
        rentalWareService.rentalWare(rentalDto);
    }

    @Operation(
            operationId = "물품 대여 조회",
            summary = "물품을 대여했는지 조회하고, 대여 정보를 반환합니다.",
            description = "현재 유저가 어떤 물품을 대여했는지 반환합니다."
    )
    @GetMapping("/rental")
    public List<RentalResponseDto> getRental() {
        return rentalWareService.getRental();
    }

    @Operation(
            operationId = "물품 반납",
            summary = "물품을 반납합니다.",
            description = "현재 유저가 대여한 물품을 반납합니다."
    )
    @DeleteMapping("/rental")
    public void returnWare(@RequestBody @Valid ReturnDto returnDto) {
        rentalWareService.returnWare(returnDto);
    }

    @Operation(
            operationId = "물품 리스트 조회",
            summary = "물품 리스트를 조회합니다.",
            description = "물품 리스트를 조회합니다."
    )
    @GetMapping()
    public List<WareEntity> getWareList() {
        return rentalWareService.getWareList();
    }
}
