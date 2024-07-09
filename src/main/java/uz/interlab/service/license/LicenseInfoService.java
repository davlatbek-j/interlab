package uz.interlab.service.license;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.interlab.entity.license.LicenseInfo;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.license.LicenseInfoDTO;
import uz.interlab.respository.LicenseInfoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LicenseInfoService {

    private final LicenseInfoRepository licenseInfoRepository;

    public ResponseEntity<ApiResponse<LicenseInfo>> create(LicenseInfo licenseInfo) {
        ApiResponse<LicenseInfo> response = new ApiResponse<>();
        licenseInfo.setId(null);
        LicenseInfo save = licenseInfoRepository.save(licenseInfo);
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<LicenseInfoDTO>> findById(Long id, String lang) {
        ApiResponse<LicenseInfoDTO> response = new ApiResponse<>();
        Optional<LicenseInfo> optionalLicenseInfo = licenseInfoRepository.findById(id);
        if (optionalLicenseInfo.isEmpty()) {
            response.setMessage("LicenseInfo not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        LicenseInfo licenseInfo = optionalLicenseInfo.get();
        response.setMessage("Found");
        response.setData(new LicenseInfoDTO(licenseInfo, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<LicenseInfoDTO>>> findALl(String lang) {
        ApiResponse<List<LicenseInfoDTO>> response = new ApiResponse<>();
        List<LicenseInfo> all = licenseInfoRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(licenseInfo -> response.getData().add(new LicenseInfoDTO(licenseInfo, lang)));
        response.setMessage("Found " + all.size() + " licenseInfo(s)");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<LicenseInfo>> findById(Long id) {
        ApiResponse<LicenseInfo> response = new ApiResponse<>();
        Optional<LicenseInfo> optionalLicenseInfo = licenseInfoRepository.findById(id);
        if (optionalLicenseInfo.isEmpty()) {
            response.setMessage("LicenseInfo not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        LicenseInfo licenseInfo = optionalLicenseInfo.get();
        response.setMessage("Found");
        response.setData(licenseInfo);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<LicenseInfo>> update(Long id, LicenseInfo licenseInfo) {
        ApiResponse<LicenseInfo> response = new ApiResponse<>();
        Optional<LicenseInfo> optionalLicenseInfo = licenseInfoRepository.findById(id);
        if (optionalLicenseInfo.isEmpty()) {
            response.setMessage("LicenseInfo not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        LicenseInfo oldLicenseInfo = optionalLicenseInfo.get();
        oldLicenseInfo.setTitleUz(licenseInfo.getTitleUz());
        oldLicenseInfo.setTitleRu(licenseInfo.getTitleRu());
        oldLicenseInfo.setBodyUz(licenseInfo.getBodyUz());
        oldLicenseInfo.setBodyRu(licenseInfo.getBodyRu());
        LicenseInfo save = licenseInfoRepository.save(oldLicenseInfo);
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (licenseInfoRepository.findById(id).isEmpty()) {
            response.setMessage("LicenseInfo not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        licenseInfoRepository.deleteById(id);
        response.setMessage("Successfully deleted!");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        Optional<LicenseInfo> optionalLicenseInfo = licenseInfoRepository.findById(id);
        if (optionalLicenseInfo.isEmpty()) {
            response.setMessage("LicenseInfo not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        LicenseInfo licenseInfo = optionalLicenseInfo.get();
        boolean active = !licenseInfo.isActive();
        licenseInfoRepository.changeActive(id, active);
        response.setMessage("Successfully changed! LicenseInfo active: " + active);
        return ResponseEntity.status(200).body(response);
    }

}
