package uz.interlab.service.license;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.Photo;
import uz.interlab.entity.license.LicensePhoto;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.license.LicensePhotoDTO;
import uz.interlab.respository.LicensePhotoRepository;
import uz.interlab.service.PhotoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LicensePhotoService {

    private final LicensePhotoRepository licensePhotoRepository;

    private final PhotoService photoService;

    private final ObjectMapper objectMapper;

    public ResponseEntity<ApiResponse<LicensePhoto>> create(String strLicensePhoto, MultipartFile photoFile) {
        ApiResponse<LicensePhoto> response = new ApiResponse<>();
        try {
            LicensePhoto licensePhoto = objectMapper.readValue(strLicensePhoto, LicensePhoto.class);
            licensePhoto.setId(null);

            Photo photo = photoService.save(photoFile);

            licensePhoto.setPhotoUrl(photo.getHttpUrl());
            LicensePhoto save = licensePhotoRepository.save(licensePhoto);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<LicensePhotoDTO>> findById(Long id, String lang) {
        ApiResponse<LicensePhotoDTO> response = new ApiResponse<>();
        Optional<LicensePhoto> optionalLicensePhoto = licensePhotoRepository.findById(id);
        if (optionalLicensePhoto.isEmpty()) {
            response.setMessage("LicensePhoto not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        LicensePhoto licensePhoto = optionalLicensePhoto.get();
        response.setMessage("Found");
        response.setData(new LicensePhotoDTO(licensePhoto, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<LicensePhotoDTO>>> findAll(String lang) {
        ApiResponse<List<LicensePhotoDTO>> response = new ApiResponse<>();
        List<LicensePhoto> all = licensePhotoRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(licensePhoto -> response.getData().add(new LicensePhotoDTO(licensePhoto, lang)));
        response.setMessage("Found " + all.size() + " license(s)");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<LicensePhoto>> findById(Long id) {
        ApiResponse<LicensePhoto> response = new ApiResponse<>();
        Optional<LicensePhoto> optionalLicensePhoto = licensePhotoRepository.findById(id);
        if (optionalLicensePhoto.isEmpty()) {
            response.setMessage("LicensePhoto not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        LicensePhoto licensePhoto = optionalLicensePhoto.get();
        response.setMessage("Found");
        response.setData(licensePhoto);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<LicensePhoto>> update(Long id, String newJson, MultipartFile newPhoto) {
        ApiResponse<LicensePhoto> response = new ApiResponse<>();
        if (licensePhotoRepository.findById(id).isEmpty()) {
            response.setMessage("LicensePhoto not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        String oldPhotoUrl = licensePhotoRepository.findPhotoUrlById(id);
        LicensePhoto newLicensePhoto = new LicensePhoto();

        try {
            if (newJson != null) {
                newLicensePhoto = objectMapper.readValue(newJson, LicensePhoto.class);
                if (newPhoto == null || !(newPhoto.getSize() > 0)) {
                    newLicensePhoto.setPhotoUrl(oldPhotoUrl);
                }
                newLicensePhoto.setId(id);
            } else {
                newLicensePhoto = licensePhotoRepository.findById(id).get();
            }
            if (newPhoto != null && newPhoto.getSize() > 0) {
                Photo photo = photoService.save(newPhoto);
                newLicensePhoto.setPhotoUrl(photo.getHttpUrl());
            }
            LicensePhoto save = licensePhotoRepository.save(newLicensePhoto);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(401).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (licensePhotoRepository.findById(id).isEmpty()) {
            response.setMessage("LicensePhoto not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        licensePhotoRepository.deleteById(id);
        response.setMessage("Successfully deleted!");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        Optional<LicensePhoto> optionalLicensePhoto = licensePhotoRepository.findById(id);
        if (optionalLicensePhoto.isEmpty()) {
            response.setMessage("LicensePhoto not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        LicensePhoto licensePhoto = optionalLicensePhoto.get();
        boolean active = !licensePhoto.isActive();
        licensePhotoRepository.changeActive(id, active);
        response.setMessage("Successfully changed! LicensePhoto active: " + active);
        return ResponseEntity.status(200).body(response);
    }

}
