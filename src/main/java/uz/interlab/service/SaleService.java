package uz.interlab.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.Photo;
import uz.interlab.entity.Sale;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.SaleDTO;
import uz.interlab.respository.SaleRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@Service
public class SaleService
{
    private final ObjectMapper jsonMapper;
    private final PhotoService photoService;
    private final SaleRepository saleRepo;

    public ResponseEntity<ApiResponse<Sale>> create(String json, MultipartFile icon)
    {
        ApiResponse<Sale> response = new ApiResponse<>();
        try
        {
            Sale sale = jsonMapper.readValue(json, Sale.class);
            if (icon != null)
                sale.setPhotoUrl(photoService.save(icon).getHttpUrl());
            Sale save = saleRepo.save(sale);
            response.setData(save);
            response.setMessage("Created");
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e)
        {
            response.setMessage("Error while parsing json");
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<SaleDTO>> getById(Long id, String lang)
    {
        ApiResponse<SaleDTO> response = new ApiResponse<>();
        if (!saleRepo.existsById(id))
        {
            response.setMessage("Not Found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Sale sale = saleRepo.findById(id).get();
        response.setMessage(lang);
        response.setData(new SaleDTO(sale, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<SaleDTO>>> getAll(String lang, String main, String active)
    {
        ApiResponse<List<SaleDTO>> response = new ApiResponse<>();
        response.setData(new ArrayList<>());
        List<Sale> all = new ArrayList<>();
        switch (main.toLowerCase())
        {
            case "all":
            {
                if (active.equalsIgnoreCase("all"))
                    all = saleRepo.findAll();
                else if (active.equalsIgnoreCase("true") || active.equalsIgnoreCase("false"))
                    all = saleRepo.findByActive(Boolean.parseBoolean(active));
                else
                    throw new IllegalArgumentException("'active' should be either 'all' or 'true' or 'false'");

                all.forEach(i -> response.getData().add(new SaleDTO(i, lang)));
                response.setMessage("Found " + all.size() + " sale(s)");
                break;
            }
            case "true":
            case "false":
            {
                if (active.equalsIgnoreCase("all"))
                    all = saleRepo.findByMain(Boolean.parseBoolean(main));
                else if (active.equalsIgnoreCase("true") || active.equalsIgnoreCase("false"))
                    all = saleRepo.findByMainAndActive(Boolean.parseBoolean(main), Boolean.parseBoolean(active));
                else
                    throw new IllegalArgumentException("'active' should be either 'all' or 'true' or 'false'");

                all.forEach(i -> response.getData().add(new SaleDTO(i, lang)));
                response.setMessage("Found " + response.getData().size() + " sale(s)");
                break;
            }
            default:
                throw new IllegalArgumentException("'main' should be either 'all' or 'true' or 'false'");
        }
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<ApiResponse<?>> delete(Long id)
    {
        ApiResponse<?> response = new ApiResponse<>();
        if (!saleRepo.existsById(id))
        {
            response.setMessage("Not Found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        saleRepo.deleteById(id);
        response.setMessage("Deleted");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Sale>> getById(Long id)
    {
        ApiResponse<Sale> response = new ApiResponse<>();
        if (!saleRepo.existsById(id))
        {
            response.setMessage("Not Found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Sale sale = saleRepo.findById(id).get();
        response.setMessage("Found");
        response.setData(sale);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Sale>> update(Long id, String newJson, MultipartFile newIcon)
    {
        ApiResponse<Sale> response = new ApiResponse<>();
        if (!saleRepo.existsById(id))
        {
            response.setMessage("Not Found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        String oldPhotoUrl = saleRepo.findPhotoUrlById(id);
        Sale newSale = new Sale();
        try
        {
            if (newJson != null)
            {
                newSale = jsonMapper.readValue(newJson, Sale.class);
                newSale.setId(id);
            } else
                newSale = saleRepo.findById(id).get();

            if (newIcon != null && newIcon.getSize() > 0)
            {
                Photo photo = photoService.save(newIcon);
                newSale.setPhotoUrl(photo.getHttpUrl());
            } else
                newSale.setPhotoUrl(oldPhotoUrl);

            Sale save = saleRepo.save(newSale);
            response.setMessage("Updated");
            response.setData(save);
            return ResponseEntity.status(200).body(response);
        } catch (JsonProcessingException e)
        {
            response.setMessage("Error on parsing json: " + e.getMessage());
            return ResponseEntity.status(400).body(response);
        }

    }
}
