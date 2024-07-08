package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.Sale;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.SaleDTO;
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
            @RequestPart(value = "icon", required = false) MultipartFile icon)
    {
        return saleService.create(json, icon);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<SaleDTO>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return saleService.getById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<SaleDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang,
            @RequestParam(value = "main", defaultValue = "all", required = false) String main,
            @RequestParam(value = "active", defaultValue = "all", required = false) String active)
    {
        return saleService.getAll(lang, main, active);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<Sale>> getFullData(@PathVariable Long id)
    {
        return saleService.getById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Sale>> update(
            @PathVariable Long id,
            @RequestParam(value = "json", required = false) String newJson,
            @RequestPart(value = "icon", required = false) MultipartFile icon)
    {
        return saleService.update(id, newJson, icon);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id)
    {
        return saleService.delete(id);
    }
}
