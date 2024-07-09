package uz.interlab.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.interlab.entity.Achievement;
import uz.interlab.payload.AchievementDTO;
import uz.interlab.payload.ApiResponse;
import uz.interlab.respository.AchievementRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;

    public ResponseEntity<ApiResponse<Achievement>> create(Achievement achievement) {
        ApiResponse<Achievement> response = new ApiResponse<>();
        achievement.setId(null);
        Achievement save = achievementRepository.save(achievement);
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<AchievementDTO>> findById(Long id, String lang) {
        ApiResponse<AchievementDTO> response = new ApiResponse<>();
        Optional<Achievement> optionalAchievement = achievementRepository.findById(id);
        if (optionalAchievement.isEmpty()) {
            response.setMessage("Achievement not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Achievement achievement = optionalAchievement.get();
        response.setMessage("Found");
        response.setData(new AchievementDTO(achievement, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<AchievementDTO>>> findAll(String lang) {
        ApiResponse<List<AchievementDTO>> response = new ApiResponse<>();
        List<Achievement> all = achievementRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(achievement -> response.getData().add(new AchievementDTO(achievement, lang)));
        response.setMessage("Found " + all.size() + " achievement(s)");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Achievement>> findById(Long id){
        ApiResponse<Achievement> response=new ApiResponse<>();
        Optional<Achievement> optionalAchievement = achievementRepository.findById(id);
        if (optionalAchievement.isEmpty()){
            response.setMessage("Achievement not found by id: "+id);
            return ResponseEntity.status(404).body(response);
        }
        Achievement achievement = optionalAchievement.get();
        response.setMessage("Found");
        response.setData(achievement);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Achievement>> update(Long id, Achievement achievement){
        ApiResponse<Achievement> response=new ApiResponse<>();
        Optional<Achievement> optionalAchievement = achievementRepository.findById(id);
        if (optionalAchievement.isEmpty()){
            response.setMessage("Achievement not found by id: "+id);
            return ResponseEntity.status(404).body(response);
        }
        Achievement oldAchievement = optionalAchievement.get();
        oldAchievement.setTitleUz(achievement.getTitleUz());
        oldAchievement.setTitleRu(achievement.getTitleRu());
        oldAchievement.setDescriptionUz(achievement.getDescriptionUz());
        oldAchievement.setDescriptionRu(achievement.getDescriptionRu());
        Achievement save = achievementRepository.save(oldAchievement);
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id){
        ApiResponse<?> response=new ApiResponse<>();
        if (achievementRepository.findById(id).isEmpty()){
            response.setMessage("Achievement not found by id: "+id);
            return ResponseEntity.status(404).body(response);
        }
        achievementRepository.deleteById(id);
        response.setMessage("Successfully deleted");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id){
        ApiResponse<?> response=new ApiResponse<>();
        Optional<Achievement> optionalAchievement = achievementRepository.findById(id);
        if (optionalAchievement.isEmpty()){
            response.setMessage("Achievement not found by id: "+id);
            return ResponseEntity.status(404).body(response);
        }
        Achievement achievement = optionalAchievement.get();
        boolean active=!achievement.isActive();
        achievementRepository.changeActive(id,active);
        response.setMessage("Successfully changed! Achievement active: "+active);
        return ResponseEntity.status(200).body(response);
    }

}
