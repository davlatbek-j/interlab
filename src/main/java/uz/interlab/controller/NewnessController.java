package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.Newness;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.NewnessDTO;
import uz.interlab.service.NewnessService;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewnessController {

    private final NewnessService newnessService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Newness>> createNewness(
            @RequestParam(value = "json") String newness,
            @RequestPart(value = "photo") MultipartFile photo
    ) {
        return newnessService.create(newness, photo);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<NewnessDTO>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return newnessService.findById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<NewnessDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return newnessService.findAll(lang);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<Newness>> getFullData(@PathVariable Long id) {
        return newnessService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Newness>> updateNewness(
            @PathVariable Long id,
            @RequestParam(value = "json", required = false) String json,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {
        return newnessService.update(id, json, photo);
    }

    @PutMapping("/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActive(@PathVariable Long id) {
        return newnessService.changeActive(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteNewness(@PathVariable Long id) {
        return newnessService.deleteById(id);
    }

}
