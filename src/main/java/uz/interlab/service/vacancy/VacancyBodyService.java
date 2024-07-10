package uz.interlab.service.vacancy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.Photo;
import uz.interlab.entity.vacancy.VacancyBody;
import uz.interlab.entity.vacancy.VacancyHead;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.vacancy.VacancyBodyDTO;
import uz.interlab.payload.vacancy.VacancyHeadDTO;
import uz.interlab.respository.VacancyBodyRepository;
import uz.interlab.service.PhotoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VacancyBodyService {

    private final VacancyBodyRepository vacancyBodyRepository;

    private final ObjectMapper objectMapper;

    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<VacancyBody>> create(String strVacancyBody, MultipartFile photoFile) {
        ApiResponse<VacancyBody> response = new ApiResponse<>();
        try {
            VacancyBody vacancyBody = objectMapper.readValue(strVacancyBody, VacancyBody.class);
            Photo photo = photoService.save(photoFile);
            vacancyBody.setIconUrl(photo.getHttpUrl());
            VacancyBody save = vacancyBodyRepository.save(vacancyBody);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<VacancyBodyDTO>> findById(Long id, String lang) {
        ApiResponse<VacancyBodyDTO> response = new ApiResponse<>();
        Optional<VacancyBody> optionalVacancyBody = vacancyBodyRepository.findById(id);
        if (optionalVacancyBody.isEmpty()) {
            response.setMessage("VacancyBody not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        VacancyBody vacancyBody = optionalVacancyBody.get();
        response.setData(new VacancyBodyDTO(vacancyBody, lang));
        response.setMessage("Found");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<VacancyBodyDTO>>> findAll(String lang) {
        ApiResponse<List<VacancyBodyDTO>> response = new ApiResponse<>();
        List<VacancyBody> all = vacancyBodyRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(vacancyBody -> response.getData().add(new VacancyBodyDTO(vacancyBody, lang)));
        response.setMessage("Found " + all.size() + " vacancyHead");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<VacancyBody>> findById(Long id) {
        ApiResponse<VacancyBody> response = new ApiResponse<>();
        Optional<VacancyBody> optionalVacancyBody = vacancyBodyRepository.findById(id);
        if (optionalVacancyBody.isEmpty()) {
            response.setMessage("VacancyBody not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        VacancyBody vacancyBody = optionalVacancyBody.get();
        response.setMessage("Found");
        response.setData(vacancyBody);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<VacancyBody>> update(Long id, String newJson, MultipartFile newPhoto) {
        ApiResponse<VacancyBody> response = new ApiResponse<>();
        if (vacancyBodyRepository.findById(id).isEmpty()) {
            response.setMessage("Vacancy not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        String oldPhotoUrl = vacancyBodyRepository.findPhotoUrlById(id);
        VacancyBody newVacancyBody = new VacancyBody();
        try {
            if (newJson != null) {
                newVacancyBody = objectMapper.readValue(newJson, VacancyBody.class);
                if (newPhoto == null || !(newPhoto.getSize() > 0)) {
                    newVacancyBody.setIconUrl(oldPhotoUrl);
                }
                newVacancyBody.setId(id);
            } else {
                newVacancyBody = vacancyBodyRepository.findById(id).get();
            }
            if (newPhoto != null && newPhoto.getSize() > 0) {
                Photo photo = photoService.save(newPhoto);
                newVacancyBody.setIconUrl(photo.getHttpUrl());
            }
            VacancyBody save = vacancyBodyRepository.save(newVacancyBody);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(401).body(response);
        }
    }


    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (vacancyBodyRepository.findById(id).isEmpty()) {
            response.setMessage("VacancyBody not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        vacancyBodyRepository.deleteById(id);
        response.setMessage("Successfully deleted");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        Optional<VacancyBody> optionalVacancyBody = vacancyBodyRepository.findById(id);
        if (optionalVacancyBody.isEmpty()) {
            response.setMessage("VacancyBody not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        VacancyBody vacancyBody = optionalVacancyBody.get();
        boolean active = !vacancyBody.isActive();
        vacancyBodyRepository.changeActive(id, active);
        response.setMessage("Successfully changed! VacancyBody active: " + active);
        return ResponseEntity.status(200).body(response);
    }

}
