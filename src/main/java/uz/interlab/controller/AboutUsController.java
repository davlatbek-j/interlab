package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.AboutUs;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.AboutUsDTO;
import uz.interlab.service.AboutUsService;

import java.util.List;

@RestController
@RequestMapping("/about-us")
@RequiredArgsConstructor
public class AboutUsController {

    private final AboutUsService aboutUsService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<AboutUs>> createAboutUs(
            @RequestParam(value = "json") String about,
            @RequestPart(value = "photo") MultipartFile photo
    ) {
        return aboutUsService.create(about, photo);
    }

    @GetMapping("/get-about-us")
    public ResponseEntity<ApiResponse<AboutUsDTO>> getAboutUs(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return aboutUsService.getAboutUs(lang);
    }

    @GetMapping("/get-about-us-full-data")
    public ResponseEntity<ApiResponse<AboutUs>> getAboutUsFullData() {
        return aboutUsService.getAboutUs();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<AboutUs>> updateAboutUs(
            @PathVariable Long id,
            @RequestParam(value = "json", required = false) String json,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {
        return aboutUsService.update(id, json, photo);
    }

    @PutMapping("/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActive(@PathVariable Long id) {
        return aboutUsService.changeActive(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteAboutUs(@PathVariable Long id) {
        return aboutUsService.deleteById(id);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<AboutUsDTO>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return aboutUsService.findById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<AboutUsDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return aboutUsService.findAll(lang);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<AboutUs>> getFullData(@PathVariable Long id) {
        return aboutUsService.findById(id);
    }


}
