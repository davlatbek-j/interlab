package uz.interlab.controller.license;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.license.LicenseInfo;
import uz.interlab.entity.license.LicensePageHead;
import uz.interlab.entity.license.LicensePhoto;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.license.LicenseInfoDTO;
import uz.interlab.payload.license.LicensePageHeadDTO;
import uz.interlab.payload.license.LicensePhotoDTO;
import uz.interlab.service.license.LicenseInfoService;
import uz.interlab.service.license.LicensePageHeadService;
import uz.interlab.service.license.LicensePhotoService;

import java.util.List;

@RestController
@RequestMapping("/license")
@RequiredArgsConstructor
public class LicenseController {

    private final LicensePageHeadService licensePageHeadService;

    private final LicenseInfoService licenseInfoService;

    private final LicensePhotoService licensePhotoService;

    @PostMapping("/head/create")
    public ResponseEntity<ApiResponse<LicensePageHead>> createLicenseHead(
            @RequestBody LicensePageHead licensePageHead
    ) {
        return licensePageHeadService.create(licensePageHead);
    }

    @GetMapping("/head/get-all")
    public ResponseEntity<ApiResponse<List<LicensePageHeadDTO>>> getAllLicenseHead(
            @RequestHeader(value = "Accept-Language") String lang) {
        return licensePageHeadService.findLicenseHeadPage(lang);
    }

    @GetMapping("/head/get-full-data")
    public ResponseEntity<ApiResponse<List<LicensePageHead>>> getFullDataLicenseHead() {
        return licensePageHeadService.findLicenseHeadPage();
    }

    @PutMapping("/head/update/{id}")
    public ResponseEntity<ApiResponse<LicensePageHead>> updateLicenseHead(
            @PathVariable Long id,
            @RequestBody LicensePageHead licensePageHead
    ) {
        return licensePageHeadService.update(id, licensePageHead);
    }

    @DeleteMapping("/head/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteLicenseHead(@PathVariable Long id) {
        return licensePageHeadService.deleteById(id);
    }

    @PutMapping("/head/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActiveLicenseHead(@PathVariable Long id) {
        return licensePageHeadService.changeActive(id);
    }

    @PostMapping("/info/create")
    public ResponseEntity<ApiResponse<LicenseInfo>> createLicenseInfo(
            @RequestBody LicenseInfo licenseInfo
    ) {
        return licenseInfoService.create(licenseInfo);
    }

    @GetMapping("/info/get/{id}")
    public ResponseEntity<ApiResponse<LicenseInfoDTO>> getByIdLicenseInfo(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return licenseInfoService.findById(id, lang);
    }

    @GetMapping("/info/get-all")
    public ResponseEntity<ApiResponse<List<LicenseInfoDTO>>> getAllLicenseInfo(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return licenseInfoService.findALl(lang);
    }

    @GetMapping("/info/get-full-data/{id}")
    public ResponseEntity<ApiResponse<LicenseInfo>> getFullDataLicenseInfo(@PathVariable Long id) {
        return licenseInfoService.findById(id);
    }

    @PutMapping("/info/update/{id}")
    public ResponseEntity<ApiResponse<LicenseInfo>> updateLicenseInfo(
            @PathVariable Long id,
            @RequestBody LicenseInfo licenseInfo
    ) {
        return licenseInfoService.update(id, licenseInfo);
    }

    @DeleteMapping("/info/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteLicenseInfo(@PathVariable Long id) {
        return licenseInfoService.deleteById(id);
    }

    @PutMapping("/info/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActiveLicenseInfo(@PathVariable Long id) {
        return licenseInfoService.changeActive(id);
    }

    @PostMapping("/photo/create")
    public ResponseEntity<ApiResponse<LicensePhoto>> createLicensePhoto(
            @RequestParam(value = "json") String licensePhoto,
            @RequestPart(value = "photo") MultipartFile photo
    ) {
        return licensePhotoService.create(licensePhoto, photo);
    }

    @GetMapping("/photo/get/{id}")
    public ResponseEntity<ApiResponse<LicensePhotoDTO>> getByIdLicensePhoto(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return licensePhotoService.findById(id, lang);
    }

    @GetMapping("/photo/get-all")
    public ResponseEntity<ApiResponse<List<LicensePhotoDTO>>> getAllLicensePhoto(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return licensePhotoService.findAll(lang);
    }

    @GetMapping("/photo/get-full-data/{id}")
    public ResponseEntity<ApiResponse<LicensePhoto>> getFullDataLicensePhoto(@PathVariable Long id) {
        return licensePhotoService.findById(id);
    }

    @PutMapping("/photo/update/{id}")
    public ResponseEntity<ApiResponse<LicensePhoto>> updateLicensePhoto(
            @PathVariable Long id,
            @RequestParam(value = "json", required = false) String json,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {
        return licensePhotoService.update(id, json, photo);
    }

    @PutMapping("/photo/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActiveLicensePhoto(@PathVariable Long id) {
        return licensePhotoService.changeActive(id);
    }

    @DeleteMapping("/photo/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteLicensePhoto(@PathVariable Long id) {
        return licensePhotoService.deleteById(id);
    }

}
