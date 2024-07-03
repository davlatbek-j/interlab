package uz.interlab.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.Newness;
import uz.interlab.entity.Photo;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.NewnessDTO;
import uz.interlab.respository.NewNessRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NewnessService {

    private final NewNessRepository newNessRepository;

    private final ObjectMapper objectMapper;

    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<Newness>> create(String strNewness, MultipartFile photoFile) {
        ApiResponse<Newness> response = new ApiResponse<>();
        try {
            Newness newness = objectMapper.readValue(strNewness, Newness.class);
            newness.setId(null);

            Photo photo = photoService.save(photoFile);

            newness.setPhotoUrl(photo.getHttpUrl());
            Newness save = newNessRepository.save(newness);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<ApiResponse<NewnessDTO>> findById(Long id, String lang) {
        ApiResponse<NewnessDTO> response = new ApiResponse<>();
        if (newNessRepository.findById(id).isEmpty()) {
            response.setMessage("Newness not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Newness newness = newNessRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(new NewnessDTO(newness, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<NewnessDTO>>> findAll(String lang) {
        ApiResponse<List<NewnessDTO>> response = new ApiResponse<>();
        List<Newness> all = newNessRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(newness -> response.getData().add(new NewnessDTO(newness, lang)));
        response.setMessage("Found " + all.size() + " new(s)");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Newness>> findById(Long id) {
        ApiResponse<Newness> response = new ApiResponse<>();
        if (newNessRepository.findById(id).isEmpty()) {
            response.setMessage("New not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Newness newness = newNessRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(newness);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Newness>> update(Long id, String newJson, MultipartFile newPhoto) {
        ApiResponse<Newness> response = new ApiResponse<>();
        if (newNessRepository.findById(id).isEmpty()) {
            response.setMessage("Entrance not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        String oldPhotoUrl = newNessRepository.findPhotoUrlById(id);
        Newness newness = new Newness();
        try {
            if (newJson != null) {
                newness = objectMapper.readValue(newJson, Newness.class);
                if (newPhoto == null || !(newPhoto.getSize() > 0)) {
                    newness.setPhotoUrl(oldPhotoUrl);
                }
                newness.setId(id);
            } else {
                newness = newNessRepository.findById(id).get();
            }
            if (newPhoto != null && newPhoto.getSize() > 0) {
                Photo photo = photoService.save(newPhoto);
                newness.setPhotoUrl(photo.getHttpUrl());
            }
            Newness save = newNessRepository.save(newness);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(401).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (newNessRepository.findById(id).isEmpty()) {
            response.setMessage("Newness not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        response.setMessage("Successfully deleted");
        newNessRepository.deleteById(id);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (newNessRepository.findById(id).isEmpty()) {
            response.setMessage("Entrance not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Newness newness = newNessRepository.findById(id).get();
        boolean active = !newness.isActive();
        newNessRepository.changeActive(id, active);
        response.setMessage("Successfully changed! Newness active: " + active);
        return ResponseEntity.status(200).body(response);
    }

}
