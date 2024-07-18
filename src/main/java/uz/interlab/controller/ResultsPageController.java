package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.ResultsPage;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.ResultsPageDTO;
import uz.interlab.service.ResultsPageService;

@RequiredArgsConstructor

@Controller
@RequestMapping("/results")
public class ResultsPageController
{
    private final ResultsPageService resultsPageService;

    @PostMapping
    public ResponseEntity<ApiResponse<ResultsPage>> create(
            @RequestBody ResultsPage resultsPage)
    {
        return resultsPageService.create(resultsPage);
    }

    @PostMapping("/upload-video")
    public ResponseEntity<ApiResponse<ResultsPage>> handleFileUpload(
            @RequestParam("video") MultipartFile video,
            @RequestParam("intro-photo") MultipartFile introPhoto)
    {
        return resultsPageService.uploadFile(video, introPhoto);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ResultsPageDTO>> get(
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return resultsPageService.getPage(lang);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<?>> delete()
    {
        return resultsPageService.delete();
    }
}
