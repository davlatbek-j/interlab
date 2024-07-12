package uz.interlab.service.license;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.interlab.entity.license.LicensePageHead;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.license.LicensePageHeadDTO;
import uz.interlab.respository.LicensePageHeadRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LicensePageHeadService {

    private final LicensePageHeadRepository licensePageHeadRepository;

    public ResponseEntity<ApiResponse<LicensePageHead>> create(LicensePageHead licensePageHead) {
        ApiResponse<LicensePageHead> response = new ApiResponse<>();
        Optional<LicensePageHead> firstLicensePageHead = licensePageHeadRepository.findAll().stream().findFirst();
        if (firstLicensePageHead.isPresent()) {
            LicensePageHead madeLicensePageHead = firstLicensePageHead.get();
            madeLicensePageHead.setTitleUz(licensePageHead.getTitleUz());
            madeLicensePageHead.setTitleRu(licensePageHead.getTitleRu());
            madeLicensePageHead.setSubtitleUz(licensePageHead.getSubtitleUz());
            madeLicensePageHead.setSubtitleRu(licensePageHead.getSubtitleRu());
            madeLicensePageHead.setDescriptionUz(licensePageHead.getDescriptionUz());
            madeLicensePageHead.setDescriptionRu(licensePageHead.getDescriptionRu());
            LicensePageHead save = licensePageHeadRepository.save(madeLicensePageHead);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        }
        licensePageHead.setId(null);
        LicensePageHead save = licensePageHeadRepository.save(licensePageHead);
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<List<LicensePageHeadDTO>>> findLicenseHeadPage(String lang) {
        ApiResponse<List<LicensePageHeadDTO>> response = new ApiResponse<>();
        List<LicensePageHead> all = licensePageHeadRepository.findAll();
        if (all.isEmpty()) {
            response.setMessage("Not found LicenseHeadPage");
            return ResponseEntity.status(404).body(response);
        }
        response.setData(new ArrayList<>());
        all.forEach(licensePageHead -> response.getData().add(new LicensePageHeadDTO(licensePageHead, lang)));
        response.setMessage("Found");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<LicensePageHead>>> findLicenseHeadPage() {
        ApiResponse<List<LicensePageHead>> response = new ApiResponse<>();
        List<LicensePageHead> all = licensePageHeadRepository.findAll();
        if (all.isEmpty()) {
            response.setMessage("Not found LicenseHeadPage");
            return ResponseEntity.status(404).body(response);
        }
        response.setData(all);
        response.setMessage("Found");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<LicensePageHead>> update(Long id, LicensePageHead licensePageHead) {
        ApiResponse<LicensePageHead> response = new ApiResponse<>();
        Optional<LicensePageHead> optionalLicensePageHead = licensePageHeadRepository.findById(id);
        if (optionalLicensePageHead.isEmpty()) {
            response.setMessage("LicenseHeadPage not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        LicensePageHead oldLicenseHeadPage = optionalLicensePageHead.get();
        oldLicenseHeadPage.setTitleUz(licensePageHead.getTitleUz());
        oldLicenseHeadPage.setTitleRu(licensePageHead.getTitleRu());
        oldLicenseHeadPage.setSubtitleUz(licensePageHead.getSubtitleUz());
        oldLicenseHeadPage.setSubtitleRu(licensePageHead.getSubtitleRu());
        oldLicenseHeadPage.setDescriptionUz(licensePageHead.getDescriptionUz());
        oldLicenseHeadPage.setDescriptionRu(licensePageHead.getDescriptionRu());
        LicensePageHead save = licensePageHeadRepository.save(oldLicenseHeadPage);
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (licensePageHeadRepository.findById(id).isEmpty()) {
            response.setMessage("LicenseHeadPage not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        licensePageHeadRepository.deleteById(id);
        response.setMessage("Successfully deleted");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        Optional<LicensePageHead> optionalLicensePageHead = licensePageHeadRepository.findById(id);
        if (optionalLicensePageHead.isEmpty()) {
            response.setMessage("LicenseHeadPage not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        LicensePageHead licensePageHead = optionalLicensePageHead.get();
        boolean active = !licensePageHead.isActive();
        licensePageHeadRepository.changeActive(id, active);
        response.setMessage("Successfully changed! LicenseHeadPage active: " + active);
        return ResponseEntity.status(200).body(response);
    }

}
