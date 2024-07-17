package uz.interlab.service.doctor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.doctor.DoctorHead;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.doctor.DoctorHeadDTO;
import uz.interlab.respository.DoctorHeadRepository;
import uz.interlab.service.PhotoService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DoctorHeadService
{
    private final DoctorHeadRepository headRepo;
    private final ObjectMapper jsonMapper;
    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<DoctorHead>> create(String json, MultipartFile photo)
    {
        ApiResponse<DoctorHead> response = new ApiResponse<>();
        try
        {
            DoctorHead newDoctorHead = json != null ? jsonMapper.readValue(json, DoctorHead.class) : new DoctorHead();
            if (photo != null && !photo.isEmpty())
            {
                newDoctorHead.setPhotoUrl(photoService.save(photo).getHttpUrl());
            }

            List<DoctorHead> all = headRepo.findAll();
            if (all.isEmpty())
            {
                DoctorHead saved = headRepo.save(newDoctorHead);
                response.setMessage("Created");
                response.setData(saved);
            } else
            {
                DoctorHead existing = all.get(0);
                updateDoctorHead(existing, newDoctorHead);
                DoctorHead saved = headRepo.save(existing);
                response.setMessage("Updated");
                response.setData(saved);
            }
            return ResponseEntity.status(200).body(response);
        } catch (JsonProcessingException e)
        {
            response.setMessage("Error parsing json: " + e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<DoctorHead>> update(String json, MultipartFile photo)
    {
        ApiResponse<DoctorHead> response = new ApiResponse<>();
        try
        {
            DoctorHead updatedDoctorHead = json != null ? jsonMapper.readValue(json, DoctorHead.class) : new DoctorHead();
            List<DoctorHead> all = headRepo.findAll();
            if (all.isEmpty())
            {
                response.setMessage("No DoctorHead found to update");
                return ResponseEntity.status(404).body(response);
            }
            DoctorHead existing = all.get(0);
            if (photo != null && !photo.isEmpty())
            {
                updatedDoctorHead.setPhotoUrl(photoService.save(photo).getHttpUrl());
            }
            Long id = existing.getId();
            updateDoctorHead(existing, updatedDoctorHead);
            existing.setId(id);
            DoctorHead saved = headRepo.save(existing);
            response.setMessage("Updated");
            response.setData(saved);
            return ResponseEntity.status(200).body(response);
        } catch (JsonProcessingException e)
        {
            response.setMessage("Error parsing json: " + e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<DoctorHeadDTO>> get(String lang)
    {
        ApiResponse<DoctorHeadDTO> response = new ApiResponse<>();
        List<DoctorHead> all = headRepo.findAll();
        if (all.isEmpty())
        {
            response.setMessage("Doctor Head is null");
            return ResponseEntity.status(404).body(response);
        }
        response.setMessage("Found");
        response.setData(new DoctorHeadDTO(all.get(0), lang));
        return ResponseEntity.status(200).body(response);
    }

    private void updateDoctorHead(DoctorHead existing, DoctorHead updated)
    {
        if (updated.getTitleUz() != null) existing.setTitleUz(updated.getTitleUz());
        if (updated.getTitleRu() != null) existing.setTitleRu(updated.getTitleRu());
        if (updated.getSubTitleUz() != null) existing.setSubTitleUz(updated.getSubTitleUz());
        if (updated.getSubTitleRu() != null) existing.setSubTitleRu(updated.getSubTitleRu());
        if (updated.getDescriptionUz() != null) existing.setDescriptionUz(updated.getDescriptionUz());
        if (updated.getDescriptionRu() != null) existing.setDescriptionRu(updated.getDescriptionRu());
        if (updated.getPhotoUrl() != null) existing.setPhotoUrl(updated.getPhotoUrl());
        if (updated.getColourCode() != null) existing.setColourCode(updated.getColourCode());
    }

    public ResponseEntity<ApiResponse<?>> delete()
    {
        ApiResponse<?> response = new ApiResponse<>();
        List<DoctorHead> all = headRepo.findAll();
        if (all.isEmpty())
        {
            response.setMessage("No DoctorHead found to delete or not created yet");
            return ResponseEntity.status(404).body(response);
        }
        headRepo.deleteById(all.get(0).getId());
        response.setMessage("Deleted");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<DoctorHead>> getFullData()
    {
        ApiResponse<DoctorHead> response = new ApiResponse<>();
        List<DoctorHead> all = headRepo.findAll();
        if (all.isEmpty())
        {
            response.setMessage("DoctorHead is null");
            return ResponseEntity.status(404).body(response);
        }
        response.setMessage("Found");
        response.setData(all.get(0));
        return ResponseEntity.status(200).body(response);
    }
}
