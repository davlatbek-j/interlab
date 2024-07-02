package uz.interlab.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.Service;
import uz.interlab.payload.ApiResponse;
import uz.interlab.respository.ServiceRepository;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class ServiceEntityService
{
    private final ServiceRepository serviceRepo;
    private final ObjectMapper jsonMapper;

    public ResponseEntity<ApiResponse<Service>> create(String json, MultipartFile photo)
    {
        ApiResponse<Service> apiResponse = new ApiResponse<>();
        try
        {
            Service service = jsonMapper.readValue(json, Service.class);
            service.setId(null);
        } catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
        return null;
    }
}
