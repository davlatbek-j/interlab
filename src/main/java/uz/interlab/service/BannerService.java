package uz.interlab.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.Banner;
import uz.interlab.entity.Photo;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.BannerDTO;
import uz.interlab.respository.BannerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    private final ObjectMapper objectMapper;

    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<Banner>> create(String strBanner, MultipartFile photoFile) {
        ApiResponse<Banner> response = new ApiResponse<>();
        try {
            Banner banner = objectMapper.readValue(strBanner, Banner.class);
            banner.setId(null);

            Photo photo = photoService.save(photoFile);

            banner.setPhotoUrl(photo.getHttpUrl());
            Banner save = bannerRepository.save(banner);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<BannerDTO>> findById(Long id, String lang) {
        ApiResponse<BannerDTO> response = new ApiResponse<>();
        if (bannerRepository.findById(id).isEmpty()) {
            response.setMessage("Banner not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Banner banner = bannerRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(new BannerDTO(banner, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<BannerDTO>>> findAll(String lang) {
        ApiResponse<List<BannerDTO>> response = new ApiResponse<>();
        List<Banner> all = bannerRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(banner -> response.getData().add(new BannerDTO(banner, lang)));
        response.setMessage("Found " + all.size() + " banner(s)");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Banner>> findById(Long id) {
        ApiResponse<Banner> response = new ApiResponse<>();
        if (bannerRepository.findById(id).isEmpty()) {
            response.setMessage("Banner not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Banner banner = bannerRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(banner);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Banner>> update(Long id, String newJson, MultipartFile newPhoto) {
        ApiResponse<Banner> response = new ApiResponse<>();
        if (bannerRepository.findById(id).isEmpty()) {
            response.setMessage("Banner not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        String oldPhotoUrl = bannerRepository.findPhotoUrlById(id);
        Banner newBanner = new Banner();

        try {
            if (newJson != null) {
                newBanner = objectMapper.readValue(newJson, Banner.class);
                if (newPhoto == null || !(newPhoto.getSize() > 0)) {
                    newBanner.setPhotoUrl(oldPhotoUrl);
                }
                newBanner.setId(id);
            } else {
                newBanner = bannerRepository.findById(id).get();
            }

            if (newPhoto != null && newPhoto.getSize() > 0) {
                Photo photo = photoService.save(newPhoto);
                newBanner.setPhotoUrl(photo.getHttpUrl());
            }
            Banner save = bannerRepository.save(newBanner);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(401).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (bannerRepository.findById(id).isEmpty()) {
            response.setMessage("Banner not found by id: " + id);
            return ResponseEntity.status(401).body(response);
        }
        bannerRepository.deleteById(id);
        response.setMessage("Successfully deleted");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (bannerRepository.findById(id).isEmpty()) {
            response.setMessage("Banner not found by id: " + id);
            return ResponseEntity.status(401).body(response);
        }
        Banner banner = bannerRepository.findById(id).get();
        boolean active = !banner.isActive();
        bannerRepository.changeActive(id, active);
        response.setMessage("Successfully changed! Banner active: " + active);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<BannerDTO>> getBanner(String lang) {
        ApiResponse<BannerDTO> response = new ApiResponse<>();
        Optional<Banner> optionalBanner = bannerRepository.findTopByOrderByIdDesc();
        if (optionalBanner.isEmpty()) {
            response.setMessage("No banner found");
            return ResponseEntity.status(401).body(response);
        }
        Banner banner = optionalBanner.get();
        response.setData(new BannerDTO(banner, lang));
        response.setMessage("Found");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Banner>> getBanner() {
        ApiResponse<Banner> response = new ApiResponse<>();
        Optional<Banner> optionalBanner = bannerRepository.findTopByOrderByIdDesc();
        if (optionalBanner.isEmpty()) {
            response.setMessage("No banner found");
            return ResponseEntity.status(401).body(response);
        }
        Banner banner = optionalBanner.get();
        response.setData(banner);
        response.setMessage("Found");
        return ResponseEntity.status(200).body(response);
    }

}
