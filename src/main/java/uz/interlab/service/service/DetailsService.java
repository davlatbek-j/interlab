package uz.interlab.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.service.ServiceDetails;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.service.ServiceDetailsDTO;
import uz.interlab.respository.DetailsRepository;
import uz.interlab.respository.ServiceRepository;
import uz.interlab.service.PhotoService;

@RequiredArgsConstructor

@Service
@Transactional
public class DetailsService
{
    private final DetailsRepository detailsRepo;
    private final ServiceRepository serviceRepo;
    private final ObjectMapper jsonMapper;
    private final PhotoService photoService;


    public ResponseEntity<ApiResponse<ServiceDetails>> createDetails(Long serviceId, String jsonDetails, MultipartFile photo)
    {
        ApiResponse<ServiceDetails> response = new ApiResponse<>();
        if (!serviceRepo.existsById(serviceId))
        {
            response.setMessage("Service not found by id:" + serviceId);
            return ResponseEntity.status(404).body(response);
        }
        try
        {
            ServiceDetails serviceDetails = jsonMapper.readValue(jsonDetails, ServiceDetails.class);
            serviceDetails.setService(serviceRepo.findById(serviceId).get());
            if (photo != null && !photo.isEmpty())
                serviceDetails.setPhotoUrl(photoService.save(photo).getHttpUrl());

            ServiceDetails saved = detailsRepo.save(serviceDetails);
            serviceRepo.updateDetailsId(serviceId, saved.getId());
            response.setData(saved);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e)
        {
            response.setMessage("Error parsing json " + e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<ServiceDetailsDTO>> findByServiceId(Long serviceId, String lang)
    {
        ApiResponse<ServiceDetailsDTO> response = new ApiResponse<>();
        if (!serviceRepo.existsById(serviceId))
        {
            response.setMessage("Service not found by id:" + serviceId);
            return ResponseEntity.status(404).body(response);
        }
        Long detailsId = serviceRepo.findDetailsIdByServiceId(serviceId);

        if (detailsId == null || !detailsRepo.existsById(detailsId))
        {
            response.setMessage("Service details is null");
            return ResponseEntity.status(200).body(response);
        }

        ServiceDetails serviceDetails = detailsRepo.findById(detailsId).get();

        response.setMessage("Found");
        response.setData(new ServiceDetailsDTO(serviceDetails, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<ServiceDetails>> update(Long serviceId, String newJson, MultipartFile
            newPhoto)
    {
        ApiResponse<ServiceDetails> response = new ApiResponse<>();
        if (!detailsRepo.existsById(serviceId))
        {
            response.setMessage("Service not found by id:" + serviceId);
            return ResponseEntity.status(404).body(response);
        }
        Long detailsId = serviceRepo.findDetailsIdByServiceId(serviceId);
        String oldPhotoUrl = detailsRepo.findById(detailsId).get().getPhotoUrl();
        ServiceDetails newDetails = new ServiceDetails();

        try
        {
            if (newJson != null)
            {
                newDetails = jsonMapper.readValue(newJson, ServiceDetails.class);
                if (newPhoto == null || !(newPhoto.getSize() > 0))
                    newDetails.setPhotoUrl(oldPhotoUrl);
                newDetails.setId(detailsId);
            } else
                newDetails = detailsRepo.findById(detailsId).get();

            if (newPhoto != null && newPhoto.getSize() > 0)
            {
                newDetails.setPhotoUrl(photoService.save(newPhoto).getHttpUrl());
            }
            ServiceDetails save = detailsRepo.save(newDetails);
            response.setMessage("Updated");
            response.setData(save);
            return ResponseEntity.status(200).body(response);
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
            response.setMessage("Error parsing json" + e.getMessage());
            return ResponseEntity.status(400).body(response);
        }

    }

    public ResponseEntity<ApiResponse<?>> delete(Long serviceId)
    {
        ApiResponse<?> response = new ApiResponse<>();
        Long detailsId = serviceRepo.findDetailsIdByServiceId(serviceId);
        if (!detailsRepo.existsById(detailsId))
        {
            response.setMessage("Service not found by id:" + serviceId);
            return ResponseEntity.status(404).body(response);
        }
        serviceRepo.updateDetailsId(serviceId, null);
        detailsRepo.deleteById(detailsId);
        response.setMessage("Deleted successfully");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<ServiceDetails>> findByServiceId(Long serviceId)
    {
        ApiResponse<ServiceDetails> response = new ApiResponse<>();
        Long detailsId = serviceRepo.findDetailsIdByServiceId(serviceId);
        if (detailsId == null)
        {
            response.setMessage("Service details is null");
            return ResponseEntity.status(200).body(response);
        }

        if (!detailsRepo.existsById(detailsId))
        {
            response.setMessage("Service details not found by id:" + serviceId);
            return ResponseEntity.status(404).body(response);
        }
        ServiceDetails serviceDetails = detailsRepo.findById(serviceId).get();
        response.setMessage("Found");
        response.setData(serviceDetails);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<ServiceDetailsDTO>> findByServiceSlug(String serviceSlug, String lang)
    {
        ApiResponse<ServiceDetailsDTO> response = new ApiResponse<>();
        if (!serviceRepo.existsBySlug(serviceSlug))
        {
            response.setMessage("Service not found by slug: " + serviceSlug);
            return ResponseEntity.status(404).body(response);
        }
        Long detailsId = serviceRepo.findDetailsIdByServiceSlug(serviceSlug);
        if (detailsId == null)
        {
            response.setMessage("Service details is null");
            return ResponseEntity.status(200).body(response);
        }
        ServiceDetails serviceDetails = detailsRepo.findById(detailsId).get();
        response.setMessage("Found");
        response.setData(new ServiceDetailsDTO(serviceDetails, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<ServiceDetails>> findByServiceSlug(String slug)
    {
        ApiResponse<ServiceDetails> response = new ApiResponse<>();
        if (!serviceRepo.existsBySlug(slug))
        {
            response.setMessage("Service not found by slug: " + slug);
            return ResponseEntity.status(404).body(response);
        }
        Long detailsId = serviceRepo.findDetailsIdByServiceSlug(slug);
        if (detailsId == null)
        {
            response.setMessage("Service details is null");
            return ResponseEntity.status(200).body(response);
        }
        ServiceDetails serviceDetails = detailsRepo.findById(detailsId).get();
        response.setMessage("Found");
        response.setData(serviceDetails);
        return ResponseEntity.status(200).body(response);
    }
}
