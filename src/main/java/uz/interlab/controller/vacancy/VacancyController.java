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
import uz.interlab.entity.vacancy.Vacancy;
import uz.interlab.payload.vacancy.VacancyDTO;
import uz.interlab.payload.vacancy.VacancyDetailsDTO;
import uz.interlab.service.vacancy.VacancyService;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/vacancy")
public class VacancyController {

    private final VacancyHeadService vacancyHeadService;

    private final VacancyBodyService vacancyBodyService;
  
    private final VacancyService vacancyService;

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

    // VACANCY ENTITY
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Vacancy>> create(@RequestBody Vacancy vacancy)
    {
        return vacancyService.create(vacancy);
    }

    @GetMapping("{slug}")
    public ResponseEntity<ApiResponse<VacancyDTO>> get(
            @PathVariable String slug,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return vacancyService.getBySlug(slug, lang);
    }

    @GetMapping("/details/{slug}")
    public ResponseEntity<ApiResponse<VacancyDetailsDTO>> getDetails(
            @PathVariable String slug,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return vacancyService.getDetails(slug, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<VacancyDTO>>> getAll(
            @RequestParam(value = "main", required = false, defaultValue = "all") String main,
            @RequestParam(value = "active", required = false, defaultValue = "all") String active,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return vacancyService.getAll(lang, main, active);
    }

    @GetMapping("/get-full-data/{slug}")
    public ResponseEntity<ApiResponse<Vacancy>> getFullData(@PathVariable String slug)
    {
        return vacancyService.getFullData(slug);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Vacancy>> update(
            @PathVariable Long id,
            @RequestBody Vacancy vacancy)
    {
        return vacancyService.update(id, vacancy);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id)
    {
        return vacancyService.delete(id);
    }
  
}
