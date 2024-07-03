package uz.interlab.controller.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.service.Service;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.service.ServiceDTO;
import uz.interlab.service.ServiceEntityService;

import java.util.List;

@RequiredArgsConstructor

@Controller
@RequestMapping("/service")
public class ServiceController
{

    private final ServiceEntityService entityService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Service>> createService(
            @RequestParam("json") String json,
            @RequestPart("icon") MultipartFile photo)
    {
        return entityService.create(json, photo);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<ServiceDTO>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return entityService.findById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<ServiceDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return entityService.findAll(lang);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<Service>> getFullData(@PathVariable Long id)
    {
        return entityService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Service>> update(
            @PathVariable Long id,
            @RequestParam(value = "json", required = false) String newJson,
            @RequestPart(value = "icon", required = false) MultipartFile newPhoto)
    {
        return entityService.update(id, newJson, newPhoto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id)
    {
        return entityService.deleteById(id);
    }


}
