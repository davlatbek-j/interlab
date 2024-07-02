package uz.interlab.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.Service;
import uz.interlab.entity.ServiceDetails;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.ServiceDTO;
import uz.interlab.respository.ServiceDetailsRepository;
import uz.interlab.respository.ServiceRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
@Transactional
public class ServiceEntityService
{
    private final ServiceRepository serviceRepo;
    private final ObjectMapper jsonMapper;
    private final PhotoService photoService;
    private final ServiceDetailsRepository serviceDetailRepo;

    public ResponseEntity<ApiResponse<Service>> create(String json, MultipartFile photo)
    {
        ApiResponse<Service> response = new ApiResponse<>();
        try
        {
            Service service = jsonMapper.readValue(json, Service.class);
            service.setId(null);
            service.setIconUrl(photoService.save(photo).getHttpUrl());
            serviceRepo.save(service);
            response.setData(service);
            response.setMessage("Created");
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
            response.setMessage("Error parsing json");
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<ServiceDTO>> findById(Long id, String lang)
    {
        ApiResponse<ServiceDTO> response = new ApiResponse<>();

        if (!serviceRepo.existsById(id))
        {
            response.setMessage("Service not found by id:" + id);
            return ResponseEntity.status(404).body(response);
        }
        Service service = serviceRepo.findById(id).get();
        response.setMessage("Found");
        response.setData(new ServiceDTO(service, lang));
        return ResponseEntity.status(200).body(response);
    }


    public ResponseEntity<ApiResponse<List<ServiceDTO>>> findAll(String lang)
    {
        ApiResponse<List<ServiceDTO>> response = new ApiResponse<>();
        List<Service> services = serviceRepo.findAll();
        response.setMessage("Found " + services.size() + " service(s)");
        response.setData(new ArrayList<>());
        services.forEach(i -> response.getData().add(new ServiceDTO(i, lang)));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Service>> findById(Long id)
    {
        ApiResponse<Service> response = new ApiResponse<>();

        if (!serviceRepo.existsById(id))
        {
            response.setMessage("Service not found by id:" + id);
            return ResponseEntity.status(404).body(response);
        }
        response.setMessage("Found");
        response.setData(serviceRepo.findById(id).get());
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Service>> update(Long id, String newJson, MultipartFile newPhoto)
    {
        ApiResponse<Service> response = new ApiResponse<>();
        if (!serviceRepo.existsById(id))
        {
            response.setMessage("Service not found by id:" + id);
            return ResponseEntity.status(404).body(response);
        }
        String oldIconUrl = serviceRepo.findById(id).get().getIconUrl();
        Service newService = new Service();

        try
        {
            if (newJson != null)
            {
                newService = jsonMapper.readValue(newJson, Service.class);
                if (newPhoto == null || !(newPhoto.getSize() > 0))
                    newService.setIconUrl(oldIconUrl);
                newService.setId(id);
            } else
                newService = serviceRepo.findById(id).get();

            if (newPhoto != null && newPhoto.getSize() > 0)
            {
                newService.setIconUrl(photoService.save(newPhoto).getHttpUrl());
            }
            Service save = serviceRepo.save(newService);
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
        ApiResponse<Service> response = new ApiResponse<>();
        if (!serviceRepo.existsById(id))
        {
            response.setMessage("Service not found by id:" + id);
            return ResponseEntity.status(404).body(response);
        }
        serviceRepo.deleteById(id);
        response.setMessage("Deleted successfully");
        return ResponseEntity.status(200).body(response);
    }

}
