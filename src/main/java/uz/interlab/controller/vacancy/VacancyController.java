package uz.interlab.controller.vacancy;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.vacancy.VacancyBody;
import uz.interlab.entity.vacancy.VacancyHead;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.vacancy.VacancyBodyDTO;
import uz.interlab.payload.vacancy.VacancyHeadDTO;
import uz.interlab.service.vacancy.VacancyBodyService;
import uz.interlab.service.vacancy.VacancyHeadService;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/vacancy")
public class VacancyController {

    private final VacancyHeadService vacancyHeadService;

    private final VacancyBodyService vacancyBodyService;

    @PostMapping("/head/create")
    public ResponseEntity<ApiResponse<VacancyHead>> createVacancyHead(
            @RequestParam(value = "json") String vacancyHead,
            @RequestPart(value = "photo") MultipartFile photo
    ) {
        return vacancyHeadService.create(vacancyHead, photo);
    }

    @GetMapping("/head/get/{id}")
    public ResponseEntity<ApiResponse<VacancyHeadDTO>> getByIdVacancyHead(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return vacancyHeadService.findById(id, lang);
    }

    @GetMapping("/head/get-all")
    public ResponseEntity<ApiResponse<List<VacancyHeadDTO>>> getAllVacancyHead(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return vacancyHeadService.findAll(lang);
    }

    @GetMapping("/head/get-full-data/{id}")
    public ResponseEntity<ApiResponse<VacancyHead>> getFullDataVacancyHead(@PathVariable Long id) {
        return vacancyHeadService.findById(id);
    }

    @PutMapping("/head/update/{id}")
    public ResponseEntity<ApiResponse<VacancyHead>> updateVacancyHead(
            @PathVariable Long id,
            @RequestParam(value = "json", required = false) String json,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {
        return vacancyHeadService.update(id, json, photo);
    }

    @PutMapping("/head/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActiveVacancyHead(@PathVariable Long id) {
        return vacancyHeadService.changeActive(id);
    }

    @DeleteMapping("/head/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteVacancyHead(@PathVariable Long id) {
        return vacancyHeadService.deleteById(id);
    }

    //body

    @PostMapping("/body/create")
    public ResponseEntity<ApiResponse<VacancyBody>> createVacancyBody(
            @RequestParam(value = "json") String vacancyBody,
            @RequestPart(value = "photo") MultipartFile photo
    ) {
        return vacancyBodyService.create(vacancyBody, photo);
    }

    @GetMapping("/body/get/{id}")
    public ResponseEntity<ApiResponse<VacancyBodyDTO>> getByIdVacancyBody(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return vacancyBodyService.findById(id, lang);
    }

    @GetMapping("/body/get-all")
    public ResponseEntity<ApiResponse<List<VacancyBodyDTO>>> getAllVacancyBody(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return vacancyBodyService.findAll(lang);
    }

    @GetMapping("/body/get-full-data/{id}")
    public ResponseEntity<ApiResponse<VacancyBody>> getFullDataVacancyBody(@PathVariable Long id) {
        return vacancyBodyService.findById(id);
    }

    @PutMapping("/body/update/{id}")
    public ResponseEntity<ApiResponse<VacancyBody>> updateVacancyBody(
            @PathVariable Long id,
            @RequestParam(value = "json", required = false) String json,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {
        return vacancyBodyService.update(id, json, photo);
    }

    @PutMapping("/body/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActiveVacancyBody(@PathVariable Long id) {
        return vacancyBodyService.changeActive(id);
    }

    @DeleteMapping("/body/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteVacancyBody(@PathVariable Long id) {
        return vacancyBodyService.deleteById(id);
    }

}