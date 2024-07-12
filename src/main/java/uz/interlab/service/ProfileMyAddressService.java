package uz.interlab.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.interlab.entity.ProfileMyAddress;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.ProfileMyAddressDTO;
import uz.interlab.respository.ProfileMyAddressRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileMyAddressService {

    private final ProfileMyAddressRepository profileMyAddressRepository;

    public ResponseEntity<ApiResponse<ProfileMyAddress>> create(ProfileMyAddress profileMyAddress) {
        ApiResponse<ProfileMyAddress> response = new ApiResponse<>();
        ProfileMyAddress save = profileMyAddressRepository.save(profileMyAddress);
        response.setData(save);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<ProfileMyAddress>> findById(Long id) {
        ApiResponse<ProfileMyAddress> response = new ApiResponse<>();
        Optional<ProfileMyAddress> optionalProfileMyAddress = profileMyAddressRepository.findById(id);
        if (optionalProfileMyAddress.isEmpty()) {
            response.setMessage("ProfileMyAddress not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        ProfileMyAddress profileMyAddress = optionalProfileMyAddress.get();
        response.setMessage("Found");
        response.setData(profileMyAddress);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<ProfileMyAddressDTO>> findById(Long id, String lang) {
        ApiResponse<ProfileMyAddressDTO> response = new ApiResponse<>();
        Optional<ProfileMyAddress> optionalProfileMyAddress = profileMyAddressRepository.findById(id);
        if (optionalProfileMyAddress.isEmpty()) {
            response.setMessage("ProfileMyAddress not found id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        ProfileMyAddress profileMyAddress = optionalProfileMyAddress.get();
        response.setMessage("Found");
        response.setData(new ProfileMyAddressDTO(profileMyAddress, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<ProfileMyAddressDTO>>> findAll(String lang) {
        ApiResponse<List<ProfileMyAddressDTO>> response = new ApiResponse<>();
        List<ProfileMyAddress> all = profileMyAddressRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(profileMyAddress -> response.getData().add(new ProfileMyAddressDTO(profileMyAddress, lang)));
        response.setMessage("Found " + all.size() + " ProfileMyAddress");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<ProfileMyAddress>> update(Long id, ProfileMyAddress profileMyAddress) {
        ApiResponse<ProfileMyAddress> response = new ApiResponse<>();
        Optional<ProfileMyAddress> optionalProfileMyAddress = profileMyAddressRepository.findById(id);
        if (optionalProfileMyAddress.isEmpty()) {
            response.setMessage("ProfileMyAddress not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        ProfileMyAddress oldProfileMyAddress = optionalProfileMyAddress.get();
        oldProfileMyAddress.setTitleUz(profileMyAddress.getTitleUz());
        oldProfileMyAddress.setTitleRu(profileMyAddress.getTitleRu());
        oldProfileMyAddress.setAddressInfoUz(profileMyAddress.getAddressInfoUz());
        oldProfileMyAddress.setAddressInfoRu(profileMyAddress.getAddressInfoRu());

        ProfileMyAddress save = profileMyAddressRepository.save(oldProfileMyAddress);
        response.setData(save);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (profileMyAddressRepository.findById(id).isEmpty()) {
            response.setMessage("ProfileMyAddress not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        profileMyAddressRepository.deleteById(id);
        response.setMessage("Successfully deleted!");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        Optional<ProfileMyAddress> optionalProfileMyAddress = profileMyAddressRepository.findById(id);
        if (optionalProfileMyAddress.isEmpty()) {
            response.setMessage("ProfileMyAddress not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        ProfileMyAddress profileMyAddress = optionalProfileMyAddress.get();
        boolean active = !profileMyAddress.isActive();
        profileMyAddressRepository.changeActive(id, active);
        response.setMessage("Successfully changed. ProfileMyAddress active: " + active);
        return ResponseEntity.status(200).body(response);
    }
}
