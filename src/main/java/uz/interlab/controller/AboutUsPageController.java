package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.aboutUs.AboutUs;
import uz.interlab.entity.aboutUs.AboutUsPage;
import uz.interlab.entity.blog.Blog;
import uz.interlab.payload.AboutUsDTO;
import uz.interlab.payload.AboutUsPageDTO;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.BlogDTO;
import uz.interlab.service.AboutUsPageService;

import java.util.List;

@RestController
@RequestMapping("/about-us-page")
@RequiredArgsConstructor
public class AboutUsPageController {

    private final AboutUsPageService aboutUsPageService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<AboutUsPage>> createAboutUsPage(
            @RequestParam(value = "json") String aboutUsPage,
            @RequestPart(value = "gallery") List<MultipartFile> gallery
    ) {
        return aboutUsPageService.create(aboutUsPage, gallery);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<AboutUsPageDTO>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return aboutUsPageService.findById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<AboutUsPageDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return aboutUsPageService.findAll(lang);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<AboutUsPage>> getFullData(@PathVariable Long id) {
        return aboutUsPageService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<AboutUsPage>> updateAboutUsPage(
            @PathVariable Long id,
            @RequestParam(value = "json", required = false) String json,
            @RequestPart(value = "gallery", required = false) List<MultipartFile> gallery
    ) {
        return aboutUsPageService.update(id, json, gallery);
    }

    @PutMapping("/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActive(@PathVariable Long id) {
        return aboutUsPageService.changeActive(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteAboutUs(@PathVariable Long id) {
        return aboutUsPageService.deleteById(id);
    }

}
