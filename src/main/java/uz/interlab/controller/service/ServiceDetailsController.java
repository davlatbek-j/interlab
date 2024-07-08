package uz.interlab.controller.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.service.ServiceDetails;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.service.ServiceDetailsDTO;
import uz.interlab.service.service.DetailsService;

@RequiredArgsConstructor

@Controller
@RequestMapping("/service-details")
public class ServiceDetailsController
{
    private final DetailsService detailsService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ServiceDetails>> createServiceDetails(
            @RequestParam(value = "service-id") Long serviceId,
            @RequestParam(value = "json") String json,
            @RequestPart(value = "photo", required = false) MultipartFile photo)
    {
        return detailsService.createDetails(serviceId, json, photo);
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<ServiceDetailsDTO>> getServiceDetails(
            @RequestParam(name = "service-id") Long serviceId,
            @RequestParam(value = "lang") String lang)
    {
        return detailsService.findByServiceId(serviceId, lang);
    }

    @GetMapping("/get-full-data/{service-id}")
    public ResponseEntity<ApiResponse<ServiceDetails>> findById(
            @PathVariable(value = "service-id") Long serviceId)
    {
        return detailsService.findByServiceId(serviceId);
    }

    @PutMapping("/update/{service-id}")
    public ResponseEntity<ApiResponse<ServiceDetails>> update(
            @PathVariable(value = "service-id") Long serviceId,
            @RequestParam(value = "json", required = false) String newJson,
            @RequestPart(value = "photo", required = false) MultipartFile newPhoto)
    {
        return detailsService.update(serviceId, newJson, newPhoto);
    }

    @DeleteMapping("/delete/{service-id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable(value = "service-id") Long serviceId)
    {
        return detailsService.delete(serviceId);
    }

}
