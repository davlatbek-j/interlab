package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.interlab.entity.Application;
import uz.interlab.payload.ApiResponse;
import uz.interlab.service.ApplicationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Application>> createApplication(
            @RequestBody Application application
    ) {
        return applicationService.create(application);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<Application>> getById(
            @PathVariable Long id
    ) {
        return applicationService.findById(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<Application>>> getAll() {
        return applicationService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteApplication(@PathVariable Long id) {
        return applicationService.deleteById(id);
    }

}
