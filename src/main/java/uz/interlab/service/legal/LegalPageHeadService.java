package uz.interlab.service.legal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.Photo;
import uz.interlab.entity.legal.LegalPageHead;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.legal.LegalPageHeadDTO;
import uz.interlab.respository.LegalPageHeadRepository;
import uz.interlab.service.PhotoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LegalPageHeadService {

    private final LegalPageHeadRepository legalPageHeadRepository;

    private final PhotoService photoService;

    private final ObjectMapper objectMapper;

    public ResponseEntity<ApiResponse<LegalPageHead>> create(String strLegalPageHead, MultipartFile photoFile) {
        ApiResponse<LegalPageHead> response = new ApiResponse<>();
        try {
            LegalPageHead legalPageHead = objectMapper.readValue(strLegalPageHead, LegalPageHead.class);
            legalPageHead.setId(null);

            Photo photo = photoService.save(photoFile);

            legalPageHead.setPhotoUrl(photo.getHttpUrl());
            LegalPageHead save = legalPageHeadRepository.save(legalPageHead);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<LegalPageHeadDTO>> findById(Long id, String lang) {
        ApiResponse<LegalPageHeadDTO> response = new ApiResponse<>();
        Optional<LegalPageHead> optionalLegalPageHead = legalPageHeadRepository.findById(id);
        if (optionalLegalPageHead.isEmpty()) {
            response.setMessage("LegalPageHead not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        LegalPageHead legalPageHead = optionalLegalPageHead.get();
        response.setMessage("Found");
        response.setData(new LegalPageHeadDTO(legalPageHead, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<LegalPageHeadDTO>>> findAll(String lang) {
        ApiResponse<List<LegalPageHeadDTO>> response = new ApiResponse<>();
        List<LegalPageHead> all = legalPageHeadRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(legalPageHead -> response.getData().add(new LegalPageHeadDTO(legalPageHead, lang)));
        response.setMessage("Found " + all.size() + " legalPageHead(s)");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<LegalPageHead>> findById(Long id) {
        ApiResponse<LegalPageHead> response = new ApiResponse<>();
        Optional<LegalPageHead> optionalLegalPageHead = legalPageHeadRepository.findById(id);
        if (optionalLegalPageHead.isEmpty()) {
            response.setMessage("LegalPageHead not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        LegalPageHead legalPageHead = optionalLegalPageHead.get();
        response.setMessage("Found");
        response.setData(legalPageHead);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<LegalPageHead>> update(Long id, String newJson, MultipartFile newPhoto) {
        ApiResponse<LegalPageHead> response = new ApiResponse<>();
        if (legalPageHeadRepository.findById(id).isEmpty()) {
            response.setMessage("LegalPageHead not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        String oldPhotoUrl = legalPageHeadRepository.findPhotoUrlById(id);
        LegalPageHead newLegalPageHead = new LegalPageHead();

        try {
            if (newJson != null) {
                newLegalPageHead = objectMapper.readValue(newJson, LegalPageHead.class);
                if (newPhoto == null || !(newPhoto.getSize() > 0)) {
                    newLegalPageHead.setPhotoUrl(oldPhotoUrl);
                }
                newLegalPageHead.setId(id);
            } else {
                newLegalPageHead = legalPageHeadRepository.findById(id).get();
            }
            if (newPhoto != null && newPhoto.getSize() > 0) {
                Photo photo = photoService.save(newPhoto);
                newLegalPageHead.setPhotoUrl(photo.getHttpUrl());
            }
            LegalPageHead save = legalPageHeadRepository.save(newLegalPageHead);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(401).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (legalPageHeadRepository.findById(id).isEmpty()) {
            response.setMessage("LegalPageHead not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        response.setMessage("Successfully deleted!");
        legalPageHeadRepository.deleteById(id);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        Optional<LegalPageHead> optionalLegalPageHead = legalPageHeadRepository.findById(id);
        if (optionalLegalPageHead.isEmpty()) {
            response.setMessage("LegalPageHead not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        LegalPageHead legalPageHead = optionalLegalPageHead.get();
        boolean active = !legalPageHead.isActive();
        legalPageHeadRepository.changeActive(id, active);
        response.setMessage("Successfully changed! LegalPageHead active: " + active);
        return ResponseEntity.status(200).body(response);
    }

}
