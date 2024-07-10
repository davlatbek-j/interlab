package uz.interlab.service.legal;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.interlab.entity.legal.LegalPageBody;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.legal.LegalPageBodyDTO;
import uz.interlab.respository.LegalPageBodyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LegalPageBodyService {

    private final LegalPageBodyRepository legalPageBodyRepository;

    public ResponseEntity<ApiResponse<LegalPageBody>> create(LegalPageBody legalPageBody) {
        ApiResponse<LegalPageBody> response = new ApiResponse<>();
        legalPageBody.setId(null);
        LegalPageBody save = legalPageBodyRepository.save(legalPageBody);
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<LegalPageBodyDTO>> findById(Long id, String lang) {
        ApiResponse<LegalPageBodyDTO> response = new ApiResponse<>();
        Optional<LegalPageBody> optionalLegalPageBody = legalPageBodyRepository.findById(id);
        if (optionalLegalPageBody.isEmpty()) {
            response.setMessage("LegalPageBody not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        LegalPageBody legalPageBody = optionalLegalPageBody.get();
        response.setMessage("Found");
        response.setData(new LegalPageBodyDTO(legalPageBody, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<LegalPageBodyDTO>>> findAll(String lang) {
        ApiResponse<List<LegalPageBodyDTO>> response = new ApiResponse<>();
        List<LegalPageBody> all = legalPageBodyRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(legalPageBody -> response.getData().add(new LegalPageBodyDTO(legalPageBody, lang)));
        response.setMessage("Found " + all.size() + " legalPageBody");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<LegalPageBody>> findById(Long id) {
        ApiResponse<LegalPageBody> response = new ApiResponse<>();
        Optional<LegalPageBody> optionalLegalPageBody = legalPageBodyRepository.findById(id);
        if (optionalLegalPageBody.isEmpty()) {
            response.setMessage("LegalPageBody not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        LegalPageBody legalPageBody = optionalLegalPageBody.get();
        response.setMessage("Found");
        response.setData(legalPageBody);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<LegalPageBody>> update(Long id, LegalPageBody legalPageBody) {
        ApiResponse<LegalPageBody> response = new ApiResponse<>();
        Optional<LegalPageBody> optionalLegalPageBody = legalPageBodyRepository.findById(id);
        if (optionalLegalPageBody.isEmpty()) {
            response.setMessage("LegalPageBody not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        LegalPageBody oldLegalPageBody = optionalLegalPageBody.get();
        oldLegalPageBody.setTitleUz(legalPageBody.getTitleUz());
        oldLegalPageBody.setTitleRu(legalPageBody.getTitleRu());
        oldLegalPageBody.setDescriptionUz(legalPageBody.getDescriptionUz());
        oldLegalPageBody.setDescriptionRu(legalPageBody.getDescriptionRu());
        LegalPageBody save = legalPageBodyRepository.save(oldLegalPageBody);
        response.setData(save);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (legalPageBodyRepository.findById(id).isEmpty()) {
            response.setMessage("LegalPageBody not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        legalPageBodyRepository.deleteById(id);
        response.setMessage("Successfully deleted");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        Optional<LegalPageBody> optionalLegalPageBody = legalPageBodyRepository.findById(id);
        if (optionalLegalPageBody.isEmpty()) {
            response.setMessage("LegalPageBody not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        LegalPageBody legalPageBody = optionalLegalPageBody.get();
        boolean active = !legalPageBody.isActive();
        legalPageBodyRepository.changeActive(id, active);
        response.setMessage("Successfully changed! LegalPageBody active: " + active);
        return ResponseEntity.status(200).body(response);
    }

}
