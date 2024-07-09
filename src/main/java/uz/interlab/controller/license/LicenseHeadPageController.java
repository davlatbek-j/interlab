package uz.interlab.controller.license;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.interlab.entity.license.LicensePageHead;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.license.LicensePageHeadDTO;
import uz.interlab.service.license.LicensePageHeadService;

import java.util.List;

@RestController
@RequestMapping("/license-head-page")
@RequiredArgsConstructor
public class LicenseHeadPageController {

    private final LicensePageHeadService licensePageHeadService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<LicensePageHead>> createLicenseHeadPage(
            @RequestBody LicensePageHead licensePageHead
    ) {
        return licensePageHeadService.create(licensePageHead);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<LicensePageHeadDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang) {
        return licensePageHeadService.findLicenseHeadPage(lang);
    }

    @GetMapping("/get-full-data")
    public ResponseEntity<ApiResponse<List<LicensePageHead>>> getFullData() {
        return licensePageHeadService.findLicenseHeadPage();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<LicensePageHead>> updateLicenseHeadPage(
            @PathVariable Long id,
            @RequestBody LicensePageHead licensePageHead
    ) {
        return licensePageHeadService.update(id, licensePageHead);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteLicense(@PathVariable Long id) {
        return licensePageHeadService.deleteById(id);
    }

    @PutMapping("/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActive(@PathVariable Long id) {
        return licensePageHeadService.changeActive(id);
    }

}
