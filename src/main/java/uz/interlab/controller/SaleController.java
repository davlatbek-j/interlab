package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.sale.Sale;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.SaleDetailsDTO;
import uz.interlab.payload.SaleMainDTO;
import uz.interlab.service.SaleService;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/sale")
public class SaleController
{
    private final SaleService saleService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Sale>> create(
            @RequestParam(value = "json") String json,
            @RequestPart(value = "main-photo-uz") MultipartFile mainPhotoUz,
            @RequestPart(value = "main-photo-ru") MultipartFile mainPhotoRu,
            @RequestPart(value = "background-photo") MultipartFile backgroundPhoto,
            @RequestPart(value = "icon", required = false) MultipartFile icon)
    {
        return saleService.create(json, mainPhotoUz, mainPhotoRu, backgroundPhoto, icon);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<SaleMainDTO>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return saleService.getById(id, lang);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<SaleMainDTO>> getById(
            @PathVariable String slug,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return saleService.getBySlug(slug, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<SaleMainDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang,
            @RequestParam(value = "main", defaultValue = "all", required = false) String main,
            @RequestParam(value = "active", defaultValue = "all", required = false) String active)
    {
        return saleService.getAll(lang, main, active);
    }

    @GetMapping("/get-full-data/{slug}")
    public ResponseEntity<ApiResponse<Sale>> getFullData(@PathVariable String slug)
    {
        return saleService.getBySlug(slug);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Sale>> update(
            @PathVariable Long id,
            @RequestParam(value = "json", required = false) String newJson,
            @RequestPart(value = "main-photo-uz", required = false) MultipartFile newMainPhotoUz,
            @RequestPart(value = "main-photo-ru", required = false) MultipartFile newMainPhotoRu,
            @RequestPart(value = "background-photo", required = false) MultipartFile newBackgroundPhoto,
            @RequestPart(value = "icon", required = false) MultipartFile newIcon)
    {
        return saleService.update(id, newJson, newMainPhotoUz, newMainPhotoRu, newBackgroundPhoto, newIcon);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id)
    {
        return saleService.delete(id);
    }


    @GetMapping("/details/{slug}")
    public ResponseEntity<ApiResponse<SaleDetailsDTO>> getDetailsBySlug(
            @PathVariable String slug,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return saleService.getDetailsBySlug(slug, lang);
    }
}
