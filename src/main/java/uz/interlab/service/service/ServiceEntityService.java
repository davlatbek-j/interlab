package uz.interlab.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.service.Service;
import uz.interlab.entity.service.ServiceHead;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.service.ServiceDTO;
import uz.interlab.payload.service.ServiceDetailsDTO;
import uz.interlab.payload.service.ServiceHeadDTO;
import uz.interlab.respository.ServiceHeadRepository;
import uz.interlab.respository.ServiceRepository;
import uz.interlab.service.PhotoService;
import uz.interlab.util.SlugUtil;

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
    private final ServiceHeadRepository headRepo;

    public ResponseEntity<ApiResponse<Service>> create(String json, MultipartFile photo)
    {
        ApiResponse<Service> response = new ApiResponse<>();
        try
        {
            Service service = jsonMapper.readValue(json, Service.class);
            service.setId(null);
            service.setIconUrl(photoService.save(photo).getHttpUrl());
            Service saved = serviceRepo.save(service);
            String slug = saved.getId() + "-" + SlugUtil.makeSlug(service.getNameUz());
            serviceRepo.updateSlug(slug, saved.getId());
            saved.setSlug(slug);
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
        String slug = serviceRepo.findSlugById(id);
        Service newService = new Service();

        try
        {
            if (newJson != null)
            {
                newService = jsonMapper.readValue(newJson, Service.class);
                if (newPhoto == null || !(newPhoto.getSize() > 0))
                    newService.setIconUrl(oldIconUrl);
                newService.setId(id);
                newService.setSlug(slug);
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

    public ResponseEntity<ApiResponse<ServiceDTO>> findBySlug(String slug, String lang)
    {
        ApiResponse<ServiceDTO> response = new ApiResponse<>();

        if (!serviceRepo.existsBySlug(slug))
        {
            response.setMessage("Service not found by slug:" + slug);
            return ResponseEntity.status(404).body(response);
        }
        Service bySlug = serviceRepo.findBySlug(slug);
        response.setMessage("Found");
        response.setData(new ServiceDTO(bySlug, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Service>> findBySlug(String slug)
    {
        ApiResponse<Service> response = new ApiResponse<>();

        if (!serviceRepo.existsBySlug(slug))
        {
            response.setMessage("Service not found by slug:" + slug);
            return ResponseEntity.status(404).body(response);
        }
        Service bySlug = serviceRepo.findBySlug(slug);
        response.setMessage("Found");
        response.setData(bySlug);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<ServiceDetailsDTO>> findDetails(String slug, String lang)
    {
        ApiResponse<ServiceDetailsDTO> response = new ApiResponse<>();
        if (!serviceRepo.existsBySlug(slug))
        {
            response.setMessage("Service not found by slug:" + slug);
            return ResponseEntity.status(404).body(response);
        }
        Service bySlug = serviceRepo.findBySlug(slug);
        response.setMessage("Found");
        response.setData(new ServiceDetailsDTO(bySlug, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<ServiceHead>> createHead(String json, List<MultipartFile> gallery)
    {
        ApiResponse<ServiceHead> response = new ApiResponse<>();

        try
        {
            ServiceHead serviceHead = jsonMapper.readValue(json, ServiceHead.class);
            serviceHead.setGallery(new ArrayList<>());
            gallery.forEach(i -> serviceHead.getGallery().add(photoService.save(i).getHttpUrl()));
            response.setMessage("Created");

            if (headRepo.findAll().isEmpty())
                response.setData(headRepo.save(serviceHead));
            else
            {
                serviceHead.setId(headRepo.findAll().get(0).getId());
                response.setData(headRepo.save(serviceHead));
            }

            return ResponseEntity.status(200).body(response);
        } catch (JsonProcessingException e)
        {
            response.setMessage("Error parsing json" + e.getMessage());
            return ResponseEntity.status(400).body(response);
        }

    }

    public ResponseEntity<ApiResponse<ServiceHeadDTO>> getHead(String lang)
    {
        ApiResponse<ServiceHeadDTO> response = new ApiResponse<>();
        if (headRepo.findAll().isEmpty())
        {
            response.setMessage("Head is null");
            return ResponseEntity.status(404).body(response);
        }
        ServiceHead serviceHead = headRepo.findAll().get(0);
        response.setMessage("Found");
        response.setData(new ServiceHeadDTO(serviceHead, lang));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<ServiceHead>> updateHead(String json, List<MultipartFile> gallery)
    {
        ApiResponse<ServiceHead> response = new ApiResponse<>();
        List<ServiceHead> all = headRepo.findAll();
        if (all.isEmpty())
        {
            response.setMessage("Head is null");
            return ResponseEntity.status(404).body(response);
        }
        ServiceHead serviceHead = all.get(0);
        Long id = serviceHead.getId();
        List<String> oldGallery = serviceHead.getGallery();
        if (json != null)
        {
            try
            {
                serviceHead = jsonMapper.readValue(json, ServiceHead.class);
                serviceHead.setId(id);
                serviceHead.setGallery(oldGallery);
            } catch (JsonProcessingException e)
            {
                response.setMessage("Error parsing json" + e.getMessage());
                return ResponseEntity.status(400).body(response);
            }
        }

        if (gallery != null)
        {
            List<String> galleryUrls = new ArrayList<>();
            gallery.forEach(i -> galleryUrls.add(photoService.save(i).getHttpUrl()));
            serviceHead.setGallery(galleryUrls);
        }
        response.setMessage("Updated");
        response.setData(headRepo.save(serviceHead));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteHead()
    {
        ApiResponse<?> response = new ApiResponse<>();
        List<ServiceHead> all = headRepo.findAll();
        if (all.isEmpty())
        {
            response.setMessage("Head is null");
            return ResponseEntity.status(404).body(response);
        }
        headRepo.deleteById(all.get(0).getId());
        response.setMessage("Deleted");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<ServiceHead>> getFullDataHead()
    {
        ApiResponse<ServiceHead> response = new ApiResponse<>();
        List<ServiceHead> all = headRepo.findAll();
        if (all.isEmpty())
        {
            response.setMessage("Head is null");
            return ResponseEntity.status(404).body(response);
        }
        response.setMessage("Found");
        response.setData(all.get(0));
        return ResponseEntity.status(200).body(response);
    }
}
