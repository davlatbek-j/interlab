package uz.interlab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.ResultsPage;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.ResultsPageDTO;
import uz.interlab.respository.ResultsPageRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RequiredArgsConstructor

@Service
@Transactional
public class ResultsPageService
{
    private final PhotoService photoService;

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${server.base-url}")
    private String baseUrl;

    private final ResultsPageRepository resultsPageRepository;

    public ResponseEntity<ApiResponse<ResultsPage>> create(ResultsPage resultsPage)
    {
        ApiResponse<ResultsPage> response = new ApiResponse<>();
        resultsPage.setId(1L);
        response.setMessage("Created");
        response.setData(resultsPageRepository.save(resultsPage));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<ApiResponse<ResultsPage>> uploadFile(MultipartFile video, MultipartFile introPhoto)
    {
        ApiResponse<ResultsPage> response = new ApiResponse<>();
        if (video.isEmpty())
        {
            response.setMessage("Please select a file to upload");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (!"video/mp4".equals(video.getContentType()))
        {
            response.setMessage("Only MP4 videos are allowed");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try
        {
            // Video
            String fileName = StringUtils.cleanPath(video.getOriginalFilename());
            Path targetLocation = Paths.get(uploadPath).resolve(fileName);

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists())
            {
                uploadDir.mkdirs();
            }

            Files.copy(video.getInputStream(), targetLocation);

            resultsPageRepository.updateVideoUrl(baseUrl + "/video/" + fileName, 1L);
            resultsPageRepository.updateIntroUrl(photoService.save(introPhoto).getHttpUrl(), 1L);

            response.setMessage("Successfully uploaded");
            response.setData(resultsPageRepository.findById(1L).get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException ex)
        {
            response.setMessage("Could not upload the file: " + ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse<ResultsPageDTO>> getPage(String lang)
    {
        ApiResponse<ResultsPageDTO> response = new ApiResponse<>();
        Optional<ResultsPage> byId = resultsPageRepository.findById(1L);
        if (byId.isPresent())
        {
            response.setMessage("Found");
            response.setData(new ResultsPageDTO(byId.get(), lang));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setMessage("Not found or not created yet");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<ApiResponse<?>> delete()
    {
        ApiResponse<?> response = new ApiResponse<>();
        if (!resultsPageRepository.existsById(1L))
        {
            response.setMessage("Not found or already deleted");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        resultsPageRepository.deleteById(1L);
        response.setMessage("Deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
