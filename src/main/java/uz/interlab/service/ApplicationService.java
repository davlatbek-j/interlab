package uz.interlab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.interlab.entity.Application;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.ApplicationCreateDTO;
import uz.interlab.respository.ApplicationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ResponseEntity<ApiResponse<Application>> create(ApplicationCreateDTO appCreateDTO) {
        ApiResponse<Application> response = new ApiResponse<>();
        Application application = Application.map(appCreateDTO);
        application.setId(null);
        Application save = applicationRepository.save(application);
        response.setData(save);
        return ResponseEntity.status(201).body(response);

    }

    public ResponseEntity<ApiResponse<Application>> findById(Long id) {
        ApiResponse<Application> response = new ApiResponse<>();
        if (applicationRepository.findById(id).isEmpty()) {
            response.setMessage("Application not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Application application = applicationRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(application);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<Application>>> findAll() {
        ApiResponse<List<Application>> response = new ApiResponse<>();
        List<Application> all = applicationRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(application -> response.getData().add(application));
        response.setMessage("Found " + all.size() + " application(s)");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Application>> update(Long id, ApplicationCreateDTO createDTO) {
        ApiResponse<Application> response = new ApiResponse<>();
        if (applicationRepository.findById(id).isEmpty()) {
            response.setMessage("Application not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Application application = applicationRepository.findById(id).get();
        application.setFullName(createDTO.getFullName());
        application.setPhoneNumber(createDTO.getPhoneNumber());
        application.setServiceName(createDTO.getServiceName());
        application.setComment(createDTO.getComment());
        Application save = applicationRepository.save(application);
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (applicationRepository.findById(id).isEmpty()) {
            response.setMessage("Applicatiob not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        applicationRepository.deleteById(id);
        response.setMessage("Successfully deleted");
        return ResponseEntity.status(200).body(response);
    }

}
