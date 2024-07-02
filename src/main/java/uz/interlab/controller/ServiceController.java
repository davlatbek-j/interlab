package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.Service;
import uz.interlab.payload.ApiResponse;
import uz.interlab.service.ServiceEntityService;

@RequiredArgsConstructor

@Controller
@RequestMapping("/service")
public class ServiceController
{

    private final ServiceEntityService entityService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Service>> createService(
            @RequestParam("json") String json,
            @RequestPart("photo") MultipartFile photo)
    {
        return entityService.create(json, photo);
    }
}
