package uz.interlab.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.interlab.entity.Address;
import uz.interlab.payload.AddressDTO;
import uz.interlab.payload.ApiResponse;
import uz.interlab.respository.AddressRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    private final ObjectMapper objectMapper;

    public ResponseEntity<ApiResponse<Address>> create(String strAddress){
        ApiResponse<Address> response = new ApiResponse<>();

        try {
            Address address=objectMapper.readValue(strAddress,Address.class);
            address.setId(null);

            Address save=addressRepository.save(address);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<ApiResponse<AddressDTO>> findById(Long id, String lang) {
        ApiResponse<AddressDTO> response = new ApiResponse<>();
        if (addressRepository.findById(id).isEmpty()) {
            response.setMessage("Address not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Address address = addressRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(new AddressDTO(address, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<AddressDTO>>> findAll(String lang) {
        ApiResponse<List<AddressDTO>> response = new ApiResponse<>();
        List<Address> all = addressRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(address -> response.getData().add(new AddressDTO(address, lang)));
        response.setMessage("Found " + all.size() + " address");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Address>> findById(Long id) {
        ApiResponse<Address> response = new ApiResponse<>();
        if (addressRepository.findById(id).isEmpty()) {
            response.setMessage("Address not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Address address = addressRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(address);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Address>> update(Long id, String newJson) {
        ApiResponse<Address> response = new ApiResponse<>();
        if (addressRepository.findById(id).isEmpty()) {
            response.setMessage("Address not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }

        Address address=new Address();
        try {
            if (newJson != null) {
                address = objectMapper.readValue(newJson, Address.class);
                address.setId(id);
            } else {
                address =addressRepository.findById(id).get();
            }

            Address save = addressRepository.save(address);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(401).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (addressRepository.findById(id).isEmpty()) {
            response.setMessage("Address not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        response.setMessage("Successfully deleted");
        addressRepository.deleteById(id);
        return ResponseEntity.status(200).body(response);
    }
}
