package uz.interlab.controller.license;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.license.LicensePhoto;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.license.LicensePhotoDTO;
import uz.interlab.service.license.LicensePhotoService;

import java.util.List;

@RestController
@RequestMapping("/license_photo")
@RequiredArgsConstructor
public class LicensePhotoController {

    private final LicensePhotoService licensePhotoService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<LicensePhoto>> createLicensePhoto(
            @RequestParam(value = "json") String licensePhoto,
            @RequestPart(value = "photo") MultipartFile photo
    ) {
        return licensePhotoService.create(licensePhoto, photo);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<LicensePhotoDTO>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return licensePhotoService.findById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<LicensePhotoDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return licensePhotoService.findAll(lang);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<LicensePhoto>> getFullData(@PathVariable Long id) {
        return licensePhotoService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<LicensePhoto>> updateLicensePhoto(
            @PathVariable Long id,
            @RequestParam(value = "json", required = false) String json,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {
        return licensePhotoService.update(id, json, photo);
    }

    @PutMapping("/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActive(@PathVariable Long id) {
        return licensePhotoService.changeActive(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteLicensePhoto(@PathVariable Long id) {
        return licensePhotoService.deleteById(id);
    }

}
