package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.Banner;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.BannerDTO;
import uz.interlab.service.BannerService;

import java.util.List;

@RestController
@RequestMapping("/banner")
@RequiredArgsConstructor
public class BannerController {

    private final BannerService bannerService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Banner>> createBanner(
            @RequestParam(value = "json") String banner,
            @RequestPart(value = "photo") MultipartFile photo
    ) {
        return bannerService.create(banner, photo);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<BannerDTO>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return bannerService.findById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<BannerDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return bannerService.findAll(lang);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<Banner>> getFullData(@PathVariable Long id) {
        return bannerService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Banner>> updateBanner(
            @PathVariable Long id,
            @RequestParam(value = "json", required = false) String json,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {
        return bannerService.update(id, json, photo);
    }

    @PutMapping("/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActive(@PathVariable Long id) {
        return bannerService.changeActive(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteBanner(@PathVariable Long id) {
        return bannerService.deleteById(id);
    }

}
