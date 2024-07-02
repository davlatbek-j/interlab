package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.ServiceDetails;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.ServiceDetailsDTO;
import uz.interlab.service.DetailsService;

@RequiredArgsConstructor

@Controller
@RequestMapping("/service-details")
public class ServiceDetailsController
{
    private final DetailsService detailsService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ServiceDetails>> createServiceDetails(
            @RequestParam(value = "service-id") Long serviceId,
            @RequestParam(value = "json") String jsonDetails,
            @RequestPart(value = "photo") MultipartFile photo)
    {
        return detailsService.createDetails(serviceId, jsonDetails, photo);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<ServiceDetailsDTO>> getServiceDetails(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return detailsService.findById(id,lang);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<ServiceDetails>> findById(@PathVariable Long id)
    {
        return detailsService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ServiceDetails>> update(
            @PathVariable Long id,
            @RequestParam(value = "json") String newJson,
            @RequestPart(value = "photo") MultipartFile newPhoto)
    {
        return detailsService.update(id,newJson,newPhoto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id)
    {
        return detailsService.deleteById(id);
    }

}
