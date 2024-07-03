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

import java.util.Optional;

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
            serviceDetails.setPhotoUrl(photoService.save(photo).getHttpUrl());

            ServiceDetails save = detailsRepo.save(serviceDetails);

            serviceRepo.updateDetailsId(serviceId, save.getId());

            response.setMessage("Created");
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
            response.setMessage("Error on parsing json :" + e.getMessage());
            response.setData(null);
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

        Optional<ServiceDetails> serviceDetails = detailsRepo.findById(detailsId);
        if (serviceDetails.isEmpty())
        {
            response.setMessage("Service details is null");
            return ResponseEntity.status(200).body(response);
        }

        response.setMessage("Found");
        serviceDetails.ifPresent(details -> response.setData(new ServiceDetailsDTO(details, lang)));

        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<ServiceDetails>> update(Long id, String newJson, MultipartFile newPhoto)
    {
        ApiResponse<ServiceDetails> response = new ApiResponse<>();
        if (!detailsRepo.existsById(id))
        {
            response.setMessage("Service not found by id:" + id);
            return ResponseEntity.status(404).body(response);
        }

        String oldPhotoUrl = detailsRepo.findById(id).get().getPhotoUrl();
        ServiceDetails newDetails = new ServiceDetails();

        try
        {
            if (newJson != null)
            {
                newDetails = jsonMapper.readValue(newJson, ServiceDetails.class);
                if (newPhoto == null || !(newPhoto.getSize() > 0))
                    newDetails.setPhotoUrl(oldPhotoUrl);
                newDetails.setId(id);
            } else
                newDetails = detailsRepo.findById(id).get();

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

    public ResponseEntity<ApiResponse<?>> deleteById(Long id)
    {
        ApiResponse<?> response = new ApiResponse<>();
        if (!detailsRepo.existsById(id))
        {
            response.setMessage("Service not found by id:" + id);
            return ResponseEntity.status(404).body(response);
        }
        detailsRepo.deleteById(id);
        response.setMessage("Deleted successfully");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<ServiceDetails>> findByServiceId(Long id)
    {
        ApiResponse<ServiceDetails> response = new ApiResponse<>();
        if (!detailsRepo.existsById(id))
        {
            response.setMessage("Service not found by id:" + id);
            return ResponseEntity.status(404).body(response);
        }
        ServiceDetails serviceDetails = detailsRepo.findById(id).get();
        response.setMessage("Found");
        response.setData(serviceDetails);
        return ResponseEntity.status(200).body(response);
    }
}
