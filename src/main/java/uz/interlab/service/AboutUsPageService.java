package uz.interlab.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.aboutUs.AboutUsPage;
import uz.interlab.payload.AboutUsPageDTO;
import uz.interlab.payload.ApiResponse;
import uz.interlab.respository.AboutUsPageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AboutUsPageService {

    private final AboutUsPageRepository aboutUsPageRepository;

    private final ObjectMapper objectMapper;

    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<AboutUsPage>> create(String strAboutUsPage, List<MultipartFile> photoFiles) {
        ApiResponse<AboutUsPage> response = new ApiResponse<>();
        try {
            AboutUsPage aboutUsPage = objectMapper.readValue(strAboutUsPage, AboutUsPage.class);
            aboutUsPage.setId(null);
            aboutUsPage.setPhotoUrls(new ArrayList<>());
            for (MultipartFile multipartFile : photoFiles) {
                aboutUsPage.getPhotoUrls().add(photoService.save(multipartFile).getHttpUrl());
            }
            AboutUsPage save = aboutUsPageRepository.save(aboutUsPage);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<AboutUsPageDTO>> findById(Long id, String lang) {
        ApiResponse<AboutUsPageDTO> response = new ApiResponse<>();
        Optional<AboutUsPage> optionalAboutUsPage = aboutUsPageRepository.findById(id);
        if (optionalAboutUsPage.isEmpty()) {
            response.setMessage("About Us Page not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        AboutUsPage aboutUsPage = optionalAboutUsPage.get();
        response.setMessage("Found");
        response.setData(new AboutUsPageDTO(aboutUsPage, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<AboutUsPageDTO>>> findAll(String lang) {
        ApiResponse<List<AboutUsPageDTO>> response = new ApiResponse<>();
        List<AboutUsPage> all = aboutUsPageRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(aboutUsPage -> response.getData().add(new AboutUsPageDTO(aboutUsPage, lang)));
        response.setMessage("Found " + all.size() + " about us");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<AboutUsPage>> findById(Long id) {
        ApiResponse<AboutUsPage> response = new ApiResponse<>();
        Optional<AboutUsPage> optionalAboutUsPage = aboutUsPageRepository.findById(id);
        if (optionalAboutUsPage.isEmpty()) {
            response.setMessage("About Us page not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        AboutUsPage aboutUsPage = optionalAboutUsPage.get();
        response.setMessage("Found");
        response.setData(aboutUsPage);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<AboutUsPage>> update(Long id, String newJson, List<MultipartFile> newPhotoFiles) {
        ApiResponse<AboutUsPage> response = new ApiResponse<>();
        Optional<AboutUsPage> optionalAboutUsPage = aboutUsPageRepository.findById(id);
        if (optionalAboutUsPage.isEmpty()) {
            response.setMessage("AboutUsPage not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        List<String> oldPhotoUrls = optionalAboutUsPage.get().getPhotoUrls();
        AboutUsPage newAboutUsPage = new AboutUsPage();

        try {
            if (newJson != null) {
                newAboutUsPage = objectMapper.readValue(newJson, AboutUsPage.class);
                if (newPhotoFiles == null || newPhotoFiles.isEmpty()) {
                    newAboutUsPage.setPhotoUrls(oldPhotoUrls);
                }
                newAboutUsPage.setId(id);
            } else {
                newAboutUsPage = aboutUsPageRepository.findById(id).get();
            }
            if (newPhotoFiles != null && !newPhotoFiles.isEmpty()) {
                newAboutUsPage.setPhotoUrls(new ArrayList<>());
                for (MultipartFile multipartFile : newPhotoFiles) {
                    newAboutUsPage.getPhotoUrls().add(photoService.save(multipartFile).getHttpUrl());
                }
            }
            AboutUsPage save = aboutUsPageRepository.save(newAboutUsPage);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(401).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (aboutUsPageRepository.findById(id).isEmpty()) {
            response.setMessage("About Us Page not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        aboutUsPageRepository.deleteById(id);
        response.setMessage("Successfully deleted!");
        aboutUsPageRepository.deleteById(id);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        Optional<AboutUsPage> optionalAboutUsPage = aboutUsPageRepository.findById(id);
        if (optionalAboutUsPage.isEmpty()) {
            response.setMessage("About Us Page not fond by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        AboutUsPage aboutUsPage = optionalAboutUsPage.get();
        boolean active = !aboutUsPage.isActive();
        aboutUsPageRepository.changeActive(id, active);
        response.setMessage("Successfully changed! AboutUsPage active: " + active);
        return ResponseEntity.status(200).body(response);
    }

}
