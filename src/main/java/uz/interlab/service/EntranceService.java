package uz.interlab.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.Doctor;
import uz.interlab.entity.Entrance;
import uz.interlab.entity.Photo;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.DoctorDTO;
import uz.interlab.payload.EntranceDTO;
import uz.interlab.respository.EntranceRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EntranceService {

    private final EntranceRepository entranceRepository;

    private final ObjectMapper objectMapper;

    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<Entrance>> create(String strEntrance, MultipartFile photoFile) {
        ApiResponse<Entrance> response = new ApiResponse<>();
        try {
            Entrance entrance = objectMapper.readValue(strEntrance, Entrance.class);
            entrance.setId(null);

            Photo photo = photoService.save(photoFile);

            entrance.setPhotoUrl(photo.getHttpUrl());
            Entrance save = entranceRepository.save(entrance);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<EntranceDTO>> findById(Long id, String lang) {
        ApiResponse<EntranceDTO> response = new ApiResponse<>();
        if (entranceRepository.findById(id).isEmpty()) {
            response.setMessage("Entrance not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Entrance entrance = entranceRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(new EntranceDTO(entrance, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<EntranceDTO>>> findAll(String lang) {
        ApiResponse<List<EntranceDTO>> response = new ApiResponse<>();
        List<Entrance> all = entranceRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(entrance -> response.getData().add(new EntranceDTO(entrance, lang)));
        response.setMessage("Found " + all.size() + " entrance(s)");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Entrance>> findById(Long id) {
        ApiResponse<Entrance> response = new ApiResponse<>();
        if (entranceRepository.findById(id).isEmpty()) {
            response.setMessage("Entrance not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Entrance entrance = entranceRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(entrance);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Entrance>> update(Long id, String newJson, MultipartFile newPhoto) {
        ApiResponse<Entrance> response = new ApiResponse<>();
        if (entranceRepository.findById(id).isEmpty()) {
            response.setMessage("Entrance not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        String oldPhotoUrl = entranceRepository.findPhotoUrlById(id);
        Entrance newEntrance = new Entrance();

        try {
            if (newJson != null) {
                newEntrance = objectMapper.readValue(newJson, Entrance.class);
                if (newPhoto == null || !(newPhoto.getSize() > 0)) {
                    newEntrance.setPhotoUrl(oldPhotoUrl);
                }
                newEntrance.setId(id);
            } else {
                newEntrance = entranceRepository.findById(id).get();
            }
            if (newPhoto != null && newPhoto.getSize() > 0) {
                Photo photo = photoService.save(newPhoto);
                newEntrance.setPhotoUrl(photo.getHttpUrl());
            }
            Entrance save = entranceRepository.save(newEntrance);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(401).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (entranceRepository.findById(id).isEmpty()) {
            response.setMessage("Entrance not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        response.setMessage("Successfully deleted");
        entranceRepository.deleteById(id);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (entranceRepository.findById(id).isEmpty()) {
            response.setMessage("Entrance not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Entrance entrance = entranceRepository.findById(id).get();
        boolean active = !entrance.isActive();
        entranceRepository.changeActive(id, active);
        response.setMessage("Successfully changed! Entrance active: " + active);
        return ResponseEntity.status(200).body(response);
    }

}
