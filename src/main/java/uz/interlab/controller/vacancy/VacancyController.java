package uz.interlab.controller.vacancy;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.interlab.entity.vacancy.Vacancy;
import uz.interlab.entity.vacancy.VacancyDetails;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.vacancy.VacancyDTO;
import uz.interlab.payload.vacancy.VacancyDetailsDTO;
import uz.interlab.service.vacancy.VacancyService;

import java.util.List;

@RequiredArgsConstructor

@Controller
@RequestMapping("/vacancy")
public class VacancyController
{
    private final VacancyService vacancyService;

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
