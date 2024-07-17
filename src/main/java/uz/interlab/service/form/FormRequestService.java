package uz.interlab.service.form;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.interlab.entity.form.FormRequest;
import uz.interlab.payload.ApiResponse;
import uz.interlab.respository.FormRequestRepository;

import java.util.List;

@RequiredArgsConstructor

@Service
public class FormRequestService
{
    private final FormRequestRepository requestRepository;

    public ResponseEntity<ApiResponse<?>> create(FormRequest formRequest)
    {
        ApiResponse<?> response = new ApiResponse<>();
        requestRepository.save(formRequest);
        response.setMessage("Saved");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<List<FormRequest>>> getAll()
    {
        ApiResponse<List<FormRequest>> response = new ApiResponse<>();
        response.setData(requestRepository.findAll());
        return ResponseEntity.ok(response);
    }
}
