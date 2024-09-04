package com.example.sport_forever.admin;

import com.example.sport_forever.admin.dto.ActivateDto;
import com.example.sport_forever.admin.dto.FacilityDto;
import com.example.sport_forever.admin.dto.WareDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name="Admin", description = "어드민 관련 기능(어드민 권한을 가진 계정 필요)")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Operation(
            operationId = "운동 물품 추가",
            summary = "운동 물품을 추가합니다.",
            description = "운동 물품을 추가합니다."
    )
    @PostMapping("/ware")
    @ResponseStatus(HttpStatus.CREATED)
    public void addWare(@RequestBody @Valid WareDto wareDto) {
        adminService.addWare(wareDto);
    }

    @Operation(
            operationId = "밴 삭제",
            summary = "밴 유저를 해제합니다.",
            description = "밴 유저를 해제합니다."
    )
    @DeleteMapping("/ban")
    public void deleteBanedUser(@RequestParam String phoneNumber) {
        adminService.deleteBanedUser(phoneNumber);
    }

    @Operation(
            operationId = "밴 유저",
            summary = "유저를 밴합니다.",
            description = "유저를 밴합니다. 1차 1일, 2차 7일, 3차 100년"
    )
    @PostMapping("/ban")
    public void banUser(@RequestParam String phoneNumber) {
        adminService.banUser(phoneNumber);
    }

    @Operation(
            operationId = "운동 물품 대여 활성화",
            summary = "운동 물품 대여를 활성화 합니다.",
            description = "운동 물품 대여가 실제로 대여 되었을 경우 활성화합니다."
    )
    @PutMapping("/rental")
    public void activateRental(@RequestBody @Valid ActivateDto activateDto) {
        adminService.activateRental(activateDto);
    }

    @Operation(
            operationId = "운동 시설 추가",
            summary = "운동 시설을 추가합니다.",
            description = "운동 시설을 추가합니다."
    )
    @PostMapping("/facility")
    public void addFacility(@RequestBody @Valid FacilityDto facilityDto) {
        adminService.addFacility(facilityDto);
    }
}
