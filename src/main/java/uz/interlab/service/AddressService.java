package uz.interlab.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.interlab.entity.Address;
import uz.interlab.payload.AddressDTO;
import uz.interlab.payload.ApiResponse;
import uz.interlab.respository.AddressRepository;
import uz.interlab.util.SlugUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService
{

    private final AddressRepository addressRepository;

    private final ObjectMapper objectMapper;

    public ResponseEntity<ApiResponse<Address>> create(String strAddress)
    {
        ApiResponse<Address> response = new ApiResponse<>();

        try
        {
            Address address = objectMapper.readValue(strAddress, Address.class);
            address.setId(null);

            Address save = addressRepository.save(address);
            String slug = save.getId() + "-" + SlugUtil.makeSlug(address.getNameUz());
            addressRepository.updateSlug(slug, save.getId());
            save.setSlug(slug);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<ApiResponse<AddressDTO>> findById(Long id, String lang)
    {
        ApiResponse<AddressDTO> response = new ApiResponse<>();
        if (addressRepository.findById(id).isEmpty())
        {
            response.setMessage("Address not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Address address = addressRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(new AddressDTO(address, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<AddressDTO>>> findAll(String lang, String active, String main)
    {
        ApiResponse<List<AddressDTO>> response = new ApiResponse<>();
        List<Address> all = new ArrayList<>();
        response.setData(new ArrayList<>());
        int size = 0;
        switch (main.toLowerCase())
        {
            case "all":
            {
                if (active.equalsIgnoreCase("all"))
                {
                    all = addressRepository.findAll();
                    all.forEach(i -> response.getData().add(new AddressDTO(i, lang)));
                    size = all.size();
                } else if (active.equalsIgnoreCase("true") || active.equalsIgnoreCase("false"))
                {
                    all = addressRepository.findAllByActive(Boolean.parseBoolean(active));
                    all.forEach(i -> response.getData().add(new AddressDTO(i, lang)));
                    size = all.size();
                } else
                    throw new IllegalArgumentException("'active' should be either 'all' or 'true' or 'false'");
                break;
            }
            case "true":
            case "false":
            {
                all = addressRepository.findAllByMainAndActive(Boolean.parseBoolean(main), Boolean.parseBoolean(active));
                all.forEach(i -> response.getData().add(new AddressDTO(i, lang)));
                size = all.size();
                break;
            }
            default:
                throw new IllegalArgumentException("'main' should be either 'all' or 'true' or 'false'");
        }

        response.setMessage("Found " + size + " Address(es)");

        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Address>> findById(Long id)
    {
        ApiResponse<Address> response = new ApiResponse<>();
        if (addressRepository.findById(id).isEmpty())
        {
            response.setMessage("Address not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Address address = addressRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(address);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Address>> update(Long id, String newJson)
    {
        ApiResponse<Address> response = new ApiResponse<>();
        if (addressRepository.findById(id).isEmpty())
        {
            response.setMessage("Address not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }

        Address address = new Address();
        String slug = addressRepository.findSlugById(id);
        try
        {
            if (newJson != null)
            {
                address = objectMapper.readValue(newJson, Address.class);
                address.setId(id);
                address.setSlug(slug);
            } else
            {
                address = addressRepository.findById(id).get();
            }

            Address save = addressRepository.save(address);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e)
        {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(401).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id)
    {
        ApiResponse<?> response = new ApiResponse<>();
        if (addressRepository.findById(id).isEmpty())
        {
            response.setMessage("Address not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        response.setMessage("Successfully deleted");
        addressRepository.deleteById(id);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<AddressDTO>> findBySlug(String slug, String lang)
    {
        ApiResponse<AddressDTO> response = new ApiResponse<>();
        Optional<Address> optionalAddress = addressRepository.findBySlug(slug);
        if (optionalAddress.isEmpty())
        {
            response.setMessage("Address not found by slug: " + slug);
            return ResponseEntity.status(404).body(response);
        }
        Address address = optionalAddress.get();
        response.setMessage("Found");
        response.setData(new AddressDTO(address, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Address>> findBySlug(String slug)
    {
        ApiResponse<Address> response = new ApiResponse<>();
        Optional<Address> optionalAddress = addressRepository.findBySlug(slug);
        if (optionalAddress.isEmpty())
        {
            response.setMessage("Address not found by slug: " + slug);
            return ResponseEntity.status(404).body(response);
        }
        Address address = optionalAddress.get();
        response.setMessage("Found");
        response.setData(address);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id)
    {
        ApiResponse<?> response = new ApiResponse<>();
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isEmpty())
        {
            response.setMessage("Address not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Address address = optionalAddress.get();
        boolean active = !address.isActive();
        addressRepository.changeActive(id, active);
        response.setMessage("Successfully changed! Address active: " + active);
        return ResponseEntity.status(200).body(response);
    }
}
