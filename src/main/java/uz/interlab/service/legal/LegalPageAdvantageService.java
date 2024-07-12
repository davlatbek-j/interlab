package uz.interlab.service.legal;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.interlab.entity.legal.LegalPageAdvantage;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.legal.LegalPageAdvantageDTO;
import uz.interlab.respository.LegalPageAdvantageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LegalPageAdvantageService {

    private final LegalPageAdvantageRepository legalPageAdvantageRepository;

    public ResponseEntity<ApiResponse<LegalPageAdvantage>> create(LegalPageAdvantage legalPageAdvantage) {
        ApiResponse<LegalPageAdvantage> response = new ApiResponse<>();
        legalPageAdvantage.setId(null);
        LegalPageAdvantage save = legalPageAdvantageRepository.save(legalPageAdvantage);
        response.setData(legalPageAdvantage);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<LegalPageAdvantageDTO>> findById(Long id, String lang) {
        ApiResponse<LegalPageAdvantageDTO> response = new ApiResponse<>();
        Optional<LegalPageAdvantage> optionalLegalPageAdvantage = legalPageAdvantageRepository.findById(id);
        if (optionalLegalPageAdvantage.isEmpty()) {
            response.setMessage("LegalPageAdvantage not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        LegalPageAdvantage legalPageAdvantage = optionalLegalPageAdvantage.get();
        response.setData(new LegalPageAdvantageDTO(legalPageAdvantage, lang));
        response.setMessage("Found");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<LegalPageAdvantageDTO>>> findAll(String lang) {
        ApiResponse<List<LegalPageAdvantageDTO>> response = new ApiResponse<>();
        List<LegalPageAdvantage> all = legalPageAdvantageRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(legalPageAdvantage -> response.getData().add(new LegalPageAdvantageDTO(legalPageAdvantage, lang)));
        response.setMessage("Found " + all.size() + " LegalPageAdvantage");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<LegalPageAdvantage>> findById(Long id) {
        ApiResponse<LegalPageAdvantage> response = new ApiResponse<>();
        Optional<LegalPageAdvantage> optionalLegalPageAdvantage = legalPageAdvantageRepository.findById(id);
        if (optionalLegalPageAdvantage.isEmpty()) {
            response.setMessage("LegalPageAdvantage not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        LegalPageAdvantage legalPageAdvantage = optionalLegalPageAdvantage.get();
        response.setData(legalPageAdvantage);
        response.setMessage("Found");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<LegalPageAdvantage>> update(Long id, LegalPageAdvantage legalPageAdvantage) {
        ApiResponse<LegalPageAdvantage> response = new ApiResponse<>();
        Optional<LegalPageAdvantage> optionalLegalPageAdvantage = legalPageAdvantageRepository.findById(id);
        if (optionalLegalPageAdvantage.isEmpty()) {
            response.setMessage("LegalPageAdvantage not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        LegalPageAdvantage oldLegalPageAdvantage = optionalLegalPageAdvantage.get();
        oldLegalPageAdvantage.setBodyUz(legalPageAdvantage.getBodyUz());
        oldLegalPageAdvantage.setBodyRu(legalPageAdvantage.getBodyRu());
        LegalPageAdvantage save = legalPageAdvantageRepository.save(oldLegalPageAdvantage);
        response.setData(save);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (legalPageAdvantageRepository.findById(id).isEmpty()) {
            response.setMessage("LegalPageAdvantage not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        legalPageAdvantageRepository.deleteById(id);
        response.setMessage("Successfully deleted");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        Optional<LegalPageAdvantage> optionalLegalPageAdvantage = legalPageAdvantageRepository.findById(id);
        if (optionalLegalPageAdvantage.isEmpty()) {
            response.setMessage("LegalPageAdvantage not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        LegalPageAdvantage legalPageAdvantage = optionalLegalPageAdvantage.get();
        boolean active = !legalPageAdvantage.isActive();
        legalPageAdvantageRepository.changeActive(id, active);
        response.setMessage("Successfully changed! LegalPageAdvantage active: " + active);
        return ResponseEntity.status(200).body(response);
    }

}
