package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.interlab.entity.Achievement;
import uz.interlab.payload.AchievementDTO;
import uz.interlab.payload.ApiResponse;
import uz.interlab.service.AchievementService;

import java.util.List;

@RestController
@RequestMapping("/achievement")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Achievement>> createAchievement(
            @RequestBody Achievement achievement
    ) {
        return achievementService.create(achievement);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<AchievementDTO>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return achievementService.findById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<AchievementDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return achievementService.findAll(lang);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<Achievement>> getFullData(@PathVariable Long id) {
        return achievementService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Achievement>> updateAchievement(
            @PathVariable Long id,
            @RequestBody Achievement achievement
    ) {
        return achievementService.update(id, achievement);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteAchievement(@PathVariable Long id) {
        return achievementService.deleteById(id);
    }

    @PutMapping("/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActive(@PathVariable Long id) {
        return achievementService.changeActive(id);
    }

}
