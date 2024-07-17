package uz.interlab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.interlab.entity.instruction.Recommendation;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.instruction.RecommendationDTO;
import uz.interlab.respository.RecommendationRepository;

import java.util.List;

@RequiredArgsConstructor

@Service
public class RecommendationService
{
    private final RecommendationRepository recRepo;

    public ResponseEntity<ApiResponse<Recommendation>> create(Recommendation recommendation)
    {
        ApiResponse<Recommendation> response = new ApiResponse<>();
        // Check if an entity already exists
        List<Recommendation> existingRecommendations = recRepo.findAll();
        if (!existingRecommendations.isEmpty())
        {
            // Update the existing entity
            Recommendation existingRecommendation = existingRecommendations.get(0);
            existingRecommendation.setNameUz(recommendation.getNameUz());
            existingRecommendation.setNameRu(recommendation.getNameRu());
            existingRecommendation.setValueUz(recommendation.getValueUz());
            existingRecommendation.setValueRu(recommendation.getValueRu());
            Recommendation updated = recRepo.save(existingRecommendation);
            response.setMessage("Updated");
            response.setData(updated);
        } else
        {
            Recommendation save = recRepo.save(recommendation);
            response.setMessage("Created");
            response.setData(save);
        }
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<RecommendationDTO>> get(String lang)
    {
        ApiResponse<RecommendationDTO> response = new ApiResponse<>();
        if (recRepo.findAll().isEmpty())
        {
            response.setMessage("Recommendation is null");
            return ResponseEntity.status(404).body(response);
        }
        Recommendation rec = recRepo.findAll().get(0);
        response.setData(new RecommendationDTO(rec, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> delete()
    {
        ApiResponse<?> response = new ApiResponse<>();
        if (recRepo.findAll().isEmpty())
        {
            response.setMessage("Recommendation is null");
            return ResponseEntity.status(404).body(response);
        }
        recRepo.deleteAll();
        response.setMessage("Deleted");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Recommendation>> getFullData()
    {
        ApiResponse<Recommendation> response = new ApiResponse<>();
        List<Recommendation> all = recRepo.findAll();
        if (all.isEmpty())
        {
            response.setMessage("Recommendation is null");
            return ResponseEntity.status(404).body(response);
        }
        response.setMessage("Found");
        response.setData(all.get(0));
        return ResponseEntity.status(200).body(response);
    }
}
