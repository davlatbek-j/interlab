package uz.interlab.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.sale.Sale;
import uz.interlab.entity.sale.SaleDetails;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.SaleDetailsDTO;
import uz.interlab.payload.SaleMainDTO;
import uz.interlab.respository.SaleDetailsRepository;
import uz.interlab.respository.SaleRepository;
import uz.interlab.util.SlugUtil;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@Service
public class SaleService
{
    private final ObjectMapper jsonMapper;
    private final PhotoService photoService;
    private final SaleRepository saleRepo;
    private final SaleDetailsRepository detailsRepo;

    public ResponseEntity<ApiResponse<Sale>> create(String json, MultipartFile mainPhotoUz, MultipartFile mainPhotoRu, MultipartFile backgroundPhoto, MultipartFile icon)
    {
        ApiResponse<Sale> response = new ApiResponse<>();
        try
        {
            Sale sale = saleRepo.save(new Sale());
            Long saleId = sale.getId();
            sale = jsonMapper.readValue(json, Sale.class);
            sale.setId(saleId);
            sale.setSlug(saleId + "-" + SlugUtil.makeSlug(sale.getNameUz()));
            sale.getDetails().setSlug(sale.getSlug());
            sale.setMainPhotoUz(photoService.save(mainPhotoUz).getHttpUrl());
            sale.setMainPhotoRu(photoService.save(mainPhotoRu).getHttpUrl());
            sale.getDetails().setBackgroundPhoto(photoService.save(backgroundPhoto).getHttpUrl());
            if (sale != null && !icon.isEmpty())
            {
                sale.getDetails().setIcon(photoService.save(icon).getHttpUrl());
            }

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

    public ResponseEntity<ApiResponse<SaleMainDTO>> getById(Long id, String lang)
    {
        ApiResponse<SaleMainDTO> response = new ApiResponse<>();
        if (!saleRepo.existsById(id))
        {
            response.setMessage("Not Found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Sale sale = saleRepo.findById(id).get();
        response.setMessage(lang);
        response.setData(new SaleMainDTO(sale, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<SaleMainDTO>>> getAll(String lang, String main, String active)
    {
        ApiResponse<List<SaleMainDTO>> response = new ApiResponse<>();
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

                all.forEach(i -> response.getData().add(new SaleMainDTO(i, lang)));
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

                all.forEach(i -> response.getData().add(new SaleMainDTO(i, lang)));
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

    public ResponseEntity<ApiResponse<Sale>> update(Long id, String newJson, MultipartFile newMainPhotoUz, MultipartFile newMainPhotoRu, MultipartFile newBackgroundPhoto, MultipartFile newIcon)
    {
        ApiResponse<Sale> response = new ApiResponse<>();
        if (!saleRepo.existsById(id))
        {
            response.setMessage("Not Found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }

        Sale newSale = saleRepo.findById(id).get();
        String oldMainPhotoUz = newSale.getMainPhotoUz();
        String oldMainPhotoRu = newSale.getMainPhotoRu();
        String oldBackgroundPhoto = newSale.getDetails().getBackgroundPhoto();
        String oldIcon = newSale.getDetails().getIcon();
        try
        {
            if (newJson != null)
            {
                newSale = jsonMapper.readValue(newJson, Sale.class);
                newSale.setId(id);
            }

            if (newMainPhotoUz != null && newMainPhotoUz.getSize() > 0)
                newSale.setMainPhotoUz(photoService.save(newMainPhotoUz).getHttpUrl());
            else
                newSale.setMainPhotoUz(oldMainPhotoUz);

            if (newMainPhotoRu != null && newMainPhotoRu.getSize() > 0)
                newSale.setMainPhotoRu(photoService.save(newMainPhotoRu).getHttpUrl());
            else
                newSale.setMainPhotoRu(oldMainPhotoRu);

            if (newBackgroundPhoto != null && newBackgroundPhoto.getSize() > 0)
                newSale.getDetails().setBackgroundPhoto(photoService.save(newBackgroundPhoto).getHttpUrl());
            else
                newSale.getDetails().setBackgroundPhoto(oldBackgroundPhoto);

            if (newIcon != null && newIcon.getSize() > 0)
                newSale.getDetails().setIcon(photoService.save(newIcon).getHttpUrl());
            else
                newSale.getDetails().setIcon(oldIcon);

            newSale.setSlug(id + "-" + SlugUtil.makeSlug(newSale.getNameUz()));
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

    public ResponseEntity<ApiResponse<SaleMainDTO>> getBySlug(String slug, String lang)
    {
        ApiResponse<SaleMainDTO> response = new ApiResponse<>();
        if (!saleRepo.existsBySlug(slug))
        {
            response.setMessage("Not Found by slug: " + slug);
            return ResponseEntity.status(404).body(response);
        }
        Sale bySlug = saleRepo.findBySlug(slug);
        response.setMessage("Found");
        response.setData(new SaleMainDTO(bySlug, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Sale>> getBySlug(String slug)
    {
        ApiResponse<Sale> response = new ApiResponse<>();
        if (!saleRepo.existsBySlug(slug))
        {
            response.setMessage("Not Found by slug: " + slug);
            return ResponseEntity.status(404).body(response);
        }
        Sale bySlug = saleRepo.findBySlug(slug);
        System.err.println("bySlug.getDetails() = " + bySlug.getDetails());
        response.setMessage("Found");
        response.setData(bySlug);
        return ResponseEntity.status(200).body(response);
    }


    public ResponseEntity<ApiResponse<SaleDetailsDTO>> getDetailsBySlug(String slug, String lang)
    {
        ApiResponse<SaleDetailsDTO> response = new ApiResponse<>();
        if (!detailsRepo.existsBySlug(slug))
        {
            response.setMessage("Sale Details not found by slug: " + slug);
            return ResponseEntity.status(404).body(response);
        }
        Sale bySlug = saleRepo.findBySlug(slug);
        if (bySlug == null)
        {
            response.setMessage("Sale Details is null");
            return ResponseEntity.status(404).body(response);
        }
        SaleDetails details = bySlug.getDetails();
        response.setMessage("Found");
        response.setData(new SaleDetailsDTO(bySlug, lang));
        return ResponseEntity.ok(response);
    }
}
