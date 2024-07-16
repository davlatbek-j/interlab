package uz.interlab.controller.doctor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.doctor.Doctor;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.doctor.DoctorDTO;
import uz.interlab.payload.doctor.DoctorDetailsDTO;
import uz.interlab.service.doctor.DoctorService;

import java.util.List;

@RequiredArgsConstructor

@Controller
@RequestMapping("/doctor")
public class DoctorController
{
    private final DoctorService doctorService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Doctor>> createDoctor(
            @RequestParam(value = "json") String doctor,
            @RequestPart(value = "photo") MultipartFile photo)
    {
        return doctorService.create(doctor, photo);
    }

    @GetMapping("/get/{id}")
    ResponseEntity<ApiResponse<DoctorDTO>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return doctorService.findById(id, lang);
    }

    @GetMapping("/{slug}")
    ResponseEntity<ApiResponse<DoctorDTO>> getBySlug(
            @PathVariable String slug,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return doctorService.findBySlug(slug, lang);
    }

    @GetMapping("/details/{slug}")
    ResponseEntity<ApiResponse<DoctorDetailsDTO>> detailsDoctor(
            @RequestHeader(value = "Accept-Language") String lang,
            @PathVariable String slug)
    {
        return doctorService.getDetails(slug,lang);
    }

    @GetMapping("/get-all")
    ResponseEntity<ApiResponse<List<DoctorDTO>>> getAll(
            @RequestParam(value = "main", defaultValue = "all", required = false) String main,
            @RequestParam(value = "active", defaultValue = "all", required = false) String active,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return doctorService.findAll(lang, main, active);
    }

    @GetMapping("/get-full-data/{slug}")
    ResponseEntity<ApiResponse<Doctor>> getFullData(@PathVariable String slug)
    {
        return doctorService.findBySlug(slug);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<ApiResponse<Doctor>> updateDoctor(
            @PathVariable Long id,
            @RequestParam(value = "json", required = false) String json,
            @RequestPart(value = "photo", required = false) MultipartFile photo)
    {
        return doctorService.update(id, json, photo);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ApiResponse<?>> deleteDoctor(@PathVariable Long id)
    {
        return doctorService.deleteById(id);
    }
}
