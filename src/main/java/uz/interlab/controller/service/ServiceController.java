package uz.interlab.controller.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.service.Service;
import uz.interlab.entity.service.ServiceHead;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.service.ServiceDTO;
import uz.interlab.payload.service.ServiceDetailsDTO;
import uz.interlab.payload.service.ServiceHeadDTO;
import uz.interlab.service.service.ServiceEntityService;

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

    @GetMapping("{slug}")
    public ResponseEntity<ApiResponse<ServiceDTO>> getBySlug(
            @PathVariable String slug,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return entityService.findBySlug(slug, lang);
    }

    @GetMapping("/details/{slug}")
    public ResponseEntity<ApiResponse<ServiceDetailsDTO>> getDetails(
            @PathVariable String slug,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return entityService.findDetails(slug, lang);
    }


    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<ServiceDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return entityService.findAll(lang);
    }

    @GetMapping("/get-full-data/{slug}")
    public ResponseEntity<ApiResponse<Service>> getFullData(@PathVariable String slug)
    {
        return entityService.findBySlug(slug);
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

    // Service HEAD
    @PostMapping("/head/create")
    public ResponseEntity<ApiResponse<ServiceHead>> createHead(
            @RequestParam(value = "json") String json,
            @RequestPart(value = "gallery") List<MultipartFile> gallery)
    {
        return entityService.createHead(json, gallery);
    }

    @GetMapping("/head")
    public ResponseEntity<ApiResponse<ServiceHeadDTO>> getHead(
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return entityService.getHead(lang);
    }

    @GetMapping("/head/full-data")
    public ResponseEntity<ApiResponse<ServiceHead>> getHeadFullData()
    {
        return entityService.getFullDataHead();
    }

    @PutMapping("/head/update")
    public ResponseEntity<ApiResponse<ServiceHead>> updateHead(
            @RequestParam(value = "json", required = false) String json,
            @RequestPart(value = "gallery", required = false) List<MultipartFile> gallery)
    {
        return entityService.updateHead(json, gallery);
    }

    @DeleteMapping("/head/delete")
    public ResponseEntity<ApiResponse<?>> deleteHead()
    {
        return entityService.deleteHead();
    }

}
