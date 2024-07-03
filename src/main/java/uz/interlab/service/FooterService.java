package uz.interlab.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.footer.Footer;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.FooterDTO;
import uz.interlab.respository.FooterRepository;

@RequiredArgsConstructor

@Service
public class FooterService
{
    private final FooterRepository footerRepo;
    private final ObjectMapper jsonMapper;
    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<Footer>> create(String json, MultipartFile logo, MultipartFile creatorLogo)
    {
        ApiResponse<Footer> response = new ApiResponse<>();
        try
        {
            Footer footer = jsonMapper.readValue(json, Footer.class);
            footer.setId(null);
            footer.setLogoUrl(photoService.save(logo).getHttpUrl());
            footer.getCreator().setLogoUrl(photoService.save(creatorLogo).getHttpUrl());
            footerRepo.save(footer);
            response.setData(footer);
            response.setMessage("Created");
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
            response.setMessage("Error on parsing json" + e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<FooterDTO>> findById(Long id, String lang)
    {
        ApiResponse<FooterDTO> response = new ApiResponse<>();
        if (!footerRepo.existsById(id))
        {
            response.setMessage("Footer not found by id " + id);
            return ResponseEntity.status(404).body(response);
        }
        Footer entity = footerRepo.findById(id).get();
        response.setMessage("Found");
        response.setData(new FooterDTO(entity, lang));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<Footer>> update(Long id, String newJson, MultipartFile newLogo, MultipartFile newCreatorLogo)
    {
        ApiResponse<Footer> response = new ApiResponse<>();
        if (!footerRepo.existsById(id))
        {
            response.setMessage("Footer not found by id " + id);
            return ResponseEntity.status(404).body(response);
        }

        Footer footer = footerRepo.findById(id).get();
        String oldLogoUrl = footer.getLogoUrl();
        String oldCreatorLogoUrl = footer.getCreator().getLogoUrl();

        Footer newFooter = new Footer();
        try
        {
            if (newJson != null)
            {
                newFooter = jsonMapper.readValue(newJson, Footer.class);
                newFooter.setId(null);
                if (newLogo == null || newLogo.isEmpty())
                {
                    newFooter.setLogoUrl(oldLogoUrl);
                }
                if (newCreatorLogo == null || newCreatorLogo.isEmpty())
                {
                    newFooter.getCreator().setLogoUrl(oldCreatorLogoUrl);
                }
            } else
                newFooter = footer;

            if (newLogo != null && !newLogo.isEmpty())
            {
                newFooter.setLogoUrl(photoService.save(newLogo).getHttpUrl());
            }
            if (newCreatorLogo != null && !newCreatorLogo.isEmpty())
            {
                newFooter.setLogoUrl(photoService.save(newCreatorLogo).getHttpUrl());
            }
            response.setData(footerRepo.save(newFooter));
            response.setMessage("Updated");
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
            response.setMessage("Error on parsing json" + e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> delete(Long id)
    {
        ApiResponse<Footer> response = new ApiResponse<>();
        if (!footerRepo.existsById(id))
        {
            response.setMessage("Footer not found by id " + id);
            return ResponseEntity.status(404).body(response);
        }

        footerRepo.deleteById(id);
        response.setMessage("Deleted");
        return ResponseEntity.ok(response);
    }
}
