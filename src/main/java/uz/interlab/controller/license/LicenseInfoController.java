package uz.interlab.controller.license;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.interlab.entity.Achievement;
import uz.interlab.entity.license.LicenseInfo;
import uz.interlab.payload.AchievementDTO;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.license.LicenseInfoDTO;
import uz.interlab.service.license.LicenseInfoService;

import java.util.List;

@RestController
@RequestMapping("/license_info")
@RequiredArgsConstructor
public class LicenseInfoController {

    private final LicenseInfoService licenseInfoService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<LicenseInfo>> createLicenseInfo(
            @RequestBody LicenseInfo licenseInfo
    ) {
        return licenseInfoService.create(licenseInfo);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<LicenseInfoDTO>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return licenseInfoService.findById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<LicenseInfoDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return licenseInfoService.findALl(lang);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<LicenseInfo>> getFullData(@PathVariable Long id) {
        return licenseInfoService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<LicenseInfo>> updateLicenseInfo(
            @PathVariable Long id,
            @RequestBody LicenseInfo licenseInfo
    ) {
        return licenseInfoService.update(id, licenseInfo);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteLicenseInfo(@PathVariable Long id) {
        return licenseInfoService.deleteById(id);
    }

    @PutMapping("/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActive(@PathVariable Long id) {
        return licenseInfoService.changeActive(id);
    }

}
