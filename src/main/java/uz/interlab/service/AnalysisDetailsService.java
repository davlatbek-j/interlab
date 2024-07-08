package uz.interlab.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.interlab.entity.analysis.AnalysisDetails;
import uz.interlab.entity.analysis.AnalysisOption;
import uz.interlab.payload.AnalysisDetailsDTO;
import uz.interlab.payload.ApiResponse;
import uz.interlab.respository.AnalysisDetailsRepository;
import uz.interlab.respository.AnalysisOptionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AnalysisDetailsService {

    private final AnalysisDetailsRepository analysisDetailsRepository;

    private final AnalysisOptionRepository analysisOptionRepository;

    public ResponseEntity<ApiResponse<AnalysisDetails>> create(AnalysisDetails analysisDetails, Long analysisOptId) {
        ApiResponse<AnalysisDetails> response = new ApiResponse<>();
        analysisDetails.setId(null);
        Optional<AnalysisOption> optionalAnalysisOption = analysisOptionRepository.findById(analysisOptId);
        if (optionalAnalysisOption.isEmpty()) {
            response.setMessage("AnalysisOption not found by: " + analysisOptId);
            return ResponseEntity.status(404).body(response);
        }
        AnalysisOption analysisOption = optionalAnalysisOption.get();
        analysisDetails.setAnalysisOption(analysisOption);
        AnalysisDetails save = analysisDetailsRepository.save(analysisDetails);
        response.setData(save);
        return ResponseEntity.status(201).body(response);

    }

    public ResponseEntity<ApiResponse<AnalysisDetailsDTO>> findById(Long id, String lang) {
        ApiResponse<AnalysisDetailsDTO> response = new ApiResponse<>();
        Optional<AnalysisDetails> optionalAnalysisDetails = analysisDetailsRepository.findById(id);
        if (optionalAnalysisDetails.isEmpty()) {
            response.setMessage("AnalysisDetails not found by: " + id);
            return ResponseEntity.status(404).body(response);
        }
        AnalysisDetails analysisDetails = optionalAnalysisDetails.get();
        response.setMessage("Found");
        response.setData(new AnalysisDetailsDTO(analysisDetails, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<AnalysisDetailsDTO>>> findAll(String lang) {
        ApiResponse<List<AnalysisDetailsDTO>> response = new ApiResponse<>();
        List<AnalysisDetails> all = analysisDetailsRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(analysisDetails -> response.getData().add(new AnalysisDetailsDTO(analysisDetails, lang)));
        response.setMessage("Found " + all.size() + " details");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<AnalysisDetails>> findById(Long id) {
        ApiResponse<AnalysisDetails> response = new ApiResponse<>();
        Optional<AnalysisDetails> optionalAnalysisDetails = analysisDetailsRepository.findById(id);
        if (optionalAnalysisDetails.isEmpty()) {
            response.setMessage("AnalysisDetails not found by: " + id);
            return ResponseEntity.status(404).body(response);
        }
        AnalysisDetails analysisDetails = optionalAnalysisDetails.get();
        response.setMessage("Found");
        response.setData(analysisDetails);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<AnalysisDetails>> update(Long id, AnalysisDetails updateAnalysisDetails) {
        ApiResponse<AnalysisDetails> response = new ApiResponse<>();
        Optional<AnalysisDetails> optionalAnalysisDetails = analysisDetailsRepository.findById(id);
        if (optionalAnalysisDetails.isEmpty()) {
            response.setMessage("AnalysisDetails not found by: " + id);
            return ResponseEntity.status(404).body(response);
        }
        AnalysisDetails analysisDetails = optionalAnalysisDetails.get();
        analysisDetails.setAnalysisOption(analysisDetails.getAnalysisOption());
        analysisDetails.setBodyUz(updateAnalysisDetails.getBodyUz());
        analysisDetails.setBodyRu(updateAnalysisDetails.getBodyRu());
        AnalysisDetails save = analysisDetailsRepository.save(analysisDetails);
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        Optional<AnalysisDetails> optionalAnalysisDetails = analysisDetailsRepository.findById(id);
        if (analysisDetailsRepository.findById(id).isEmpty()) {
            response.setMessage("AnalysisDetails not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        AnalysisDetails analysisDetails = optionalAnalysisDetails.get();
        analysisDetails.setAnalysisOption(null);
        analysisDetailsRepository.save(analysisDetails);
        analysisDetailsRepository.deleteById(id);
        response.setMessage("Successfully deleted");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        Optional<AnalysisDetails> optionalAnalysisDetails = analysisDetailsRepository.findById(id);
        if (optionalAnalysisDetails.isEmpty()) {
            response.setMessage("AnalysisDetails not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        AnalysisDetails analysisDetails = optionalAnalysisDetails.get();
        boolean active = !analysisDetails.isActive();
        analysisDetailsRepository.changeActive(id, active);
        response.setMessage("Successfully changed! AnalysisDetails active: " + active);
        return ResponseEntity.status(200).body(response);
    }

}
