package uz.interlab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.interlab.entity.PhoneTool;
import uz.interlab.entity.license.LicensePageHead;
import uz.interlab.payload.ApiResponse;
import uz.interlab.respository.PhoneToolRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhoneToolService {

    private final PhoneToolRepository phoneToolRepository;

    public ResponseEntity<ApiResponse<PhoneTool>> create(PhoneTool phoneTool) {
        ApiResponse<PhoneTool> response = new ApiResponse<>();
        Optional<PhoneTool> firstPhoneTool = phoneToolRepository.findAll().stream().findFirst();
        if (firstPhoneTool.isPresent()) {
            PhoneTool currentPhoneTool = firstPhoneTool.get();
            currentPhoneTool.setPhoneNumber(phoneTool.getPhoneNumber());
            PhoneTool save = phoneToolRepository.save(currentPhoneTool);
            response.setData(save);
            return ResponseEntity.status(200).body(response);
        }
        PhoneTool save = phoneToolRepository.save(phoneTool);
        response.setData(save);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<PhoneTool>> find() {
        ApiResponse<PhoneTool> response = new ApiResponse<>();
        Optional<PhoneTool> optionalPhoneTool = phoneToolRepository.findAll().stream().findFirst();
        if (optionalPhoneTool.isEmpty()) {
            response.setMessage("Phone tool not found");
            return ResponseEntity.status(404).body(response);
        }
        PhoneTool phoneTool = optionalPhoneTool.get();
        response.setMessage("Found");
        response.setData(phoneTool);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<PhoneTool>> update(PhoneTool phoneTool) {
        ApiResponse<PhoneTool> response = new ApiResponse<>();
        Optional<PhoneTool> optionalPhoneTool = phoneToolRepository.findAll().stream().findFirst();
        if (optionalPhoneTool.isEmpty()) {
            response.setMessage("Phone tool not found");
            return ResponseEntity.status(404).body(response);
        }
        PhoneTool oldPhoneTool = optionalPhoneTool.get();
        oldPhoneTool.setPhoneNumber(phoneTool.getPhoneNumber());
        PhoneTool save = phoneToolRepository.save(oldPhoneTool);
        response.setData(save);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> delete() {
        ApiResponse<PhoneTool> response = new ApiResponse<>();
        Optional<PhoneTool> optionalPhoneTool = phoneToolRepository.findAll().stream().findFirst();
        if (optionalPhoneTool.isEmpty()) {
            response.setMessage("Phone tool not found");
            return ResponseEntity.status(404).body(response);
        }
        Long id = optionalPhoneTool.get().getId();
        phoneToolRepository.deleteById(id);
        response.setMessage("Successfully deleted");
        return ResponseEntity.status(200).body(response);
    }


}
