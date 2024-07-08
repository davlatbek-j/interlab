package uz.interlab.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.interlab.entity.analysis.Analysis;
import uz.interlab.entity.analysis.AnalysisOption;
import uz.interlab.payload.AnalysisDTO;
import uz.interlab.payload.AnalysisOptionDTO;
import uz.interlab.payload.ApiResponse;
import uz.interlab.respository.AnalysisOptionRepository;
import uz.interlab.respository.AnalysisRepository;
import uz.interlab.util.SlugUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AnalysisService {

    private final AnalysisRepository analysisRepository;

    private final AnalysisOptionRepository analysisOptionRepository;

    public ResponseEntity<ApiResponse<Analysis>> create(Analysis analysis) {
        ApiResponse<Analysis> response = new ApiResponse<>();
        analysis.setId(null);

        Analysis save = analysisRepository.save(analysis);
        List<AnalysisOption> analysisOptions = save.getAnalysisOptions();
        if (analysisOptions!=null){
            for (AnalysisOption option : analysisOptions) {
                String slug = option.getId()+"-"+SlugUtil.makeSlug(option.getTitleUz());
                option.setSlug(slug);
            }
        }
        save.setAnalysisOptions(analysisOptions);
        save=analysisRepository.save(save);
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<AnalysisDTO>> findById(Long id, String lang) {
        ApiResponse<AnalysisDTO> response = new ApiResponse<>();
        if (analysisRepository.findById(id).isEmpty()) {
            response.setMessage("Analysis not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Analysis analysis = analysisRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(new AnalysisDTO(analysis, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<AnalysisDTO>>> findAll(String lang) {
        ApiResponse<List<AnalysisDTO>> response = new ApiResponse<>();
        List<Analysis> all = analysisRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(analysis -> response.getData().add(new AnalysisDTO(analysis, lang)));
        response.setMessage("Found " + all.size() + " analysis");
        return ResponseEntity.status(200).body(response);
    }
    public ResponseEntity<ApiResponse<Analysis>> findById(Long id) {
        ApiResponse<Analysis> response = new ApiResponse<>();
        if (analysisRepository.findById(id).isEmpty()) {
            response.setMessage("Analysis not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Analysis analysis = analysisRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(analysis);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Analysis>> update(Long id, Analysis updateAnalysis) {
        ApiResponse<Analysis> response = new ApiResponse<>();
        if (analysisRepository.findById(id).isEmpty()) {
            response.setMessage("Analysis not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Analysis analysis = analysisRepository.findById(id).get();
        analysis.setId(id);
        analysis.setTitleUz(updateAnalysis.getTitleUz());
        analysis.setTitleRu(updateAnalysis.getTitleRu());
        analysis.setCategory(updateAnalysis.getCategory());
        analysis.setAnalysisOptions(updateAnalysis.getAnalysisOptions());
        Analysis save = analysisRepository.save(analysis);
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (analysisRepository.findById(id).isEmpty()) {
            response.setMessage("Analysis not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        analysisRepository.deleteById(id);
        response.setMessage("Successfully deleted");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (analysisRepository.findById(id).isEmpty()) {
            response.setMessage("Analysis not found id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Analysis analysis = analysisRepository.findById(id).get();
        boolean active = !analysis.isActive();
        analysisRepository.changeActive(id, active);
        response.setMessage("Successfully changed! Analysis active: " + active);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changePopular(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (analysisOptionRepository.findById(id).isEmpty()) {
            response.setMessage("Analysis option not found id: " + id);
            ResponseEntity.status(404).body(response);
        }
        AnalysisOption analysisOption = analysisOptionRepository.findById(id).get();
        boolean popular = !analysisOption.isPopular();
        analysisOptionRepository.changePopular(id, popular);
        response.setMessage("Successfully changed! Analysis option popular: " + popular);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<AnalysisOptionDTO>>> getPopularAnalysisOption(String lang) {
        ApiResponse<List<AnalysisOptionDTO>> response = new ApiResponse<>();
        List<AnalysisOption> list = analysisOptionRepository.findAll().stream()
                .filter(AnalysisOption::isPopular).toList();
        response.setData(new ArrayList<>());
        list.forEach(analysisOption -> response.getData().add(new AnalysisOptionDTO(analysisOption, lang)));
        response.setMessage("Found " + list.size() + " popular analysis");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<AnalysisOptionDTO>> findBySlug(String slug, String lang){
        ApiResponse<AnalysisOptionDTO> response=new ApiResponse<>();
        Optional<AnalysisOption> optionalAnalysisOption = analysisOptionRepository.findBySlug(slug);
        if (optionalAnalysisOption.isEmpty()) {
            response.setMessage("AnalysisOption not found by slug: "+slug);
            return ResponseEntity.status(404).body(response);
        }
        AnalysisOption analysisOption = optionalAnalysisOption.get();
        response.setMessage("Found");
        response.setData(new AnalysisOptionDTO(analysisOption,lang));
        return ResponseEntity.status(200).body(response);
    }


}
