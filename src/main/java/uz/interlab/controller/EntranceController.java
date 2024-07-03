package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.Entrance;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.EntranceDTO;
import uz.interlab.service.EntranceService;

import java.util.List;

@RestController
@RequestMapping("/entrance")
@RequiredArgsConstructor
public class EntranceController {

    private final EntranceService entranceService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Entrance>> createEntrance(
            @RequestParam(value = "json") String entrance,
            @RequestPart(value = "photo") MultipartFile photo
    ) {
        return entranceService.create(entrance, photo);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<EntranceDTO>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return entranceService.findById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<EntranceDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return entranceService.findAll(lang);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<Entrance>> getFullData(@PathVariable Long id) {
        return entranceService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Entrance>> updateEntrance(
            @PathVariable Long id,
            @RequestParam(value = "json", required = false) String json,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {
        return entranceService.update(id, json, photo);
    }

    @PutMapping("/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActive(@PathVariable Long id) {
        return entranceService.changeActive(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteEntrance(@PathVariable Long id) {
        return entranceService.deleteById(id);
    }

}
