package uz.interlab.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.aboutUs.AboutUs;
import uz.interlab.entity.Photo;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.AboutUsDTO;
import uz.interlab.respository.AboutUsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AboutUsService {

    private final AboutUsRepository aboutUsRepository;

    private final ObjectMapper objectMapper;

    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<AboutUs>> create(String strAboutUs, MultipartFile photoFile) {
        ApiResponse<AboutUs> response = new ApiResponse<>();
        try {
            AboutUs aboutUs = objectMapper.readValue(strAboutUs, AboutUs.class);
            aboutUs.setId(null);

            Photo photo = photoService.save(photoFile);

            aboutUs.setPhotoUrl(photo.getHttpUrl());
            AboutUs save = aboutUsRepository.save(aboutUs);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<AboutUsDTO>> findById(Long id, String lang) {
        ApiResponse<AboutUsDTO> response = new ApiResponse<>();
        if (aboutUsRepository.findById(id).isEmpty()) {
            response.setMessage("AboutUs not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        AboutUs aboutUs = aboutUsRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(new AboutUsDTO(aboutUs, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<AboutUsDTO>>> findAll(String lang) {
        ApiResponse<List<AboutUsDTO>> response = new ApiResponse<>();
        List<AboutUs> all = aboutUsRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(aboutUs -> response.getData().add(new AboutUsDTO(aboutUs, lang)));
        response.setMessage("Found " + all.size() + " aboutUs(s)");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<AboutUs>> findById(Long id) {
        ApiResponse<AboutUs> response = new ApiResponse<>();
        if (aboutUsRepository.findById(id).isEmpty()) {
            response.setMessage("AboutUs not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        AboutUs aboutUs = aboutUsRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(aboutUs);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<AboutUs>> update(Long id, String newJson, MultipartFile newPhoto) {
        ApiResponse<AboutUs> response = new ApiResponse<>();
        if (aboutUsRepository.findById(id).isEmpty()) {
            response.setMessage("AboutUs not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        String oldPhotoUrl = aboutUsRepository.findPhotoUrlById(id);
        AboutUs newAboutUs = new AboutUs();

        try {
            if (newJson != null) {
                newAboutUs = objectMapper.readValue(newJson, AboutUs.class);
                if (newPhoto == null || !(newPhoto.getSize() > 0)) {
                    newAboutUs.setPhotoUrl(oldPhotoUrl);
                }
                newAboutUs.setId(id);
            } else {
                newAboutUs = aboutUsRepository.findById(id).get();
            }

            if (newPhoto != null && newPhoto.getSize() > 0) {
                Photo photo = photoService.save(newPhoto);
                newAboutUs.setPhotoUrl(photo.getHttpUrl());
            }
            AboutUs save = aboutUsRepository.save(newAboutUs);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(401).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (aboutUsRepository.findById(id).isEmpty()) {
            response.setMessage("AboutUs not found by id: " + id);
            return ResponseEntity.status(401).body(response);
        }
        aboutUsRepository.deleteById(id);
        response.setMessage("Successfully deleted");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (aboutUsRepository.findById(id).isEmpty()) {
            response.setMessage("AboutUs not found by id: " + id);
            return ResponseEntity.status(401).body(response);
        }
        AboutUs aboutUs = aboutUsRepository.findById(id).get();
        boolean active = !aboutUs.isActive();
        aboutUsRepository.changeActive(id, active);
        response.setMessage("Successfully changed! AboutUs active: " + active);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<AboutUsDTO>> getAboutUs(String lang) {
        ApiResponse<AboutUsDTO> response = new ApiResponse<>();
        Optional<AboutUs> optionalAboutUs = aboutUsRepository.findTopByOrderByIdDesc();
        if (optionalAboutUs.isEmpty()) {
            response.setMessage("No aboutUs found");
            return ResponseEntity.status(401).body(response);
        }
        AboutUs aboutUs = optionalAboutUs.get();
        response.setData(new AboutUsDTO(aboutUs, lang));
        response.setMessage("Found");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<AboutUs>> getAboutUs() {
        ApiResponse<AboutUs> response = new ApiResponse<>();
        Optional<AboutUs> optionalAboutUs = aboutUsRepository.findTopByOrderByIdDesc();
        if (optionalAboutUs.isEmpty()) {
            response.setMessage("No aboutUs found");
            return ResponseEntity.status(401).body(response);
        }
        AboutUs aboutUs = optionalAboutUs.get();
        response.setData(aboutUs);
        response.setMessage("Found");
        return ResponseEntity.status(200).body(response);
    }

}
