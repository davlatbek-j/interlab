package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.interlab.entity.analysis.AnalysisDetails;
import uz.interlab.payload.AnalysisDetailsDTO;
import uz.interlab.payload.ApiResponse;
import uz.interlab.service.AnalysisDetailsService;

import java.util.List;

@RestController
@RequestMapping("/analysis-detail")
@RequiredArgsConstructor
public class AnalysisDetailsController {

    private final AnalysisDetailsService analysisDetailsService;

    @PostMapping("/create/{analysisOptId}")
    public ResponseEntity<ApiResponse<AnalysisDetails>> createAnalysisDetails(
            @PathVariable Long analysisOptId,
            @RequestBody AnalysisDetails analysisDetails
    ) {
        return analysisDetailsService.create(analysisDetails, analysisOptId);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<AnalysisDetailsDTO>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return analysisDetailsService.findById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<AnalysisDetailsDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return analysisDetailsService.findAll(lang);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<AnalysisDetails>> getFullData(@PathVariable Long id) {
        return analysisDetailsService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<AnalysisDetails>> updateAnalysisDetails(
            @PathVariable Long id,
            @RequestBody AnalysisDetails analysisDetails
    ) {
        return analysisDetailsService.update(id, analysisDetails);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteAnalysisDetails(@PathVariable Long id) {
        return analysisDetailsService.deleteById(id);
    }

    @PutMapping("/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActive(@PathVariable Long id) {
        return analysisDetailsService.changeActive(id);
    }

}
