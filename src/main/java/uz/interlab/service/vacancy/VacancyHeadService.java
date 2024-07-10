package uz.interlab.service.vacancy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.Photo;
import uz.interlab.entity.vacancy.VacancyHead;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.vacancy.VacancyHeadDTO;
import uz.interlab.respository.VacancyHeadRepository;
import uz.interlab.service.PhotoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VacancyHeadService {

    private final VacancyHeadRepository vacancyHeadRepository;

    private final ObjectMapper objectMapper;

    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<VacancyHead>> create(String strVacancyHead, MultipartFile photoFile) {
        ApiResponse<VacancyHead> response = new ApiResponse<>();
        try {
            VacancyHead vacancyHead = objectMapper.readValue(strVacancyHead, VacancyHead.class);
            Photo photo = photoService.save(photoFile);
            vacancyHead.setPhotoUrl(photo.getHttpUrl());
            VacancyHead save = vacancyHeadRepository.save(vacancyHead);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<VacancyHeadDTO>> findById(Long id, String lang) {
        ApiResponse<VacancyHeadDTO> response = new ApiResponse<>();
        Optional<VacancyHead> optionalVacancyHead = vacancyHeadRepository.findById(id);
        if (optionalVacancyHead.isEmpty()) {
            response.setMessage("VacancyHead not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        VacancyHead vacancyHead = optionalVacancyHead.get();
        response.setData(new VacancyHeadDTO(vacancyHead, lang));
        response.setMessage("Found");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<VacancyHeadDTO>>> findAll(String lang) {
        ApiResponse<List<VacancyHeadDTO>> response = new ApiResponse<>();
        List<VacancyHead> all = vacancyHeadRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(vacancyHead -> response.getData().add(new VacancyHeadDTO(vacancyHead, lang)));
        response.setMessage("Found " + all.size() + " vacancyHead");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<VacancyHead>> findById(Long id) {
        ApiResponse<VacancyHead> response = new ApiResponse<>();
        Optional<VacancyHead> optionalVacancyHead = vacancyHeadRepository.findById(id);
        if (optionalVacancyHead.isEmpty()) {
            response.setMessage("VacancyHead not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        VacancyHead vacancyHead = optionalVacancyHead.get();
        response.setMessage("Found");
        response.setData(vacancyHead);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<VacancyHead>> update(Long id, String newJson, MultipartFile newPhoto) {
        ApiResponse<VacancyHead> response = new ApiResponse<>();
        if (vacancyHeadRepository.findById(id).isEmpty()) {
            response.setMessage("VacancyHead not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        String oldPhotoUrl = vacancyHeadRepository.findPhotoUrlById(id);
        VacancyHead newVacancyHead = new VacancyHead();
        try {
            if (newJson != null) {
                newVacancyHead = objectMapper.readValue(newJson, VacancyHead.class);
                if (newPhoto == null || !(newPhoto.getSize() > 0)) {
                    newVacancyHead.setPhotoUrl(oldPhotoUrl);
                }
                newVacancyHead.setId(id);
            } else {
                newVacancyHead = vacancyHeadRepository.findById(id).get();
            }
            if (newPhoto != null && newPhoto.getSize() > 0) {
                Photo photo = photoService.save(newPhoto);
                newVacancyHead.setPhotoUrl(photo.getHttpUrl());
            }
            VacancyHead save = vacancyHeadRepository.save(newVacancyHead);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(401).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (vacancyHeadRepository.findById(id).isEmpty()) {
            response.setMessage("VacancyHead not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        vacancyHeadRepository.deleteById(id);
        response.setMessage("Successfully deleted");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        Optional<VacancyHead> optionalVacancyHead = vacancyHeadRepository.findById(id);
        if (optionalVacancyHead.isEmpty()) {
            response.setMessage("VacancyHead not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        VacancyHead vacancyHead = optionalVacancyHead.get();
        boolean active = !vacancyHead.isActive();
        vacancyHeadRepository.changeActive(id, active);
        response.setMessage("Successfully changed! VacancyHead active: " + active);
        return ResponseEntity.status(200).body(response);

    }

}
