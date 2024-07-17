package uz.interlab.controller.form;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.interlab.entity.form.FormRequest;
import uz.interlab.payload.ApiResponse;
import uz.interlab.service.form.FormRequestService;

import java.util.List;

@RequiredArgsConstructor

@Controller
@RequestMapping("/form-request")
public class FormRequestController
{
    private final FormRequestService requestService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> mainPage(@RequestBody FormRequest formRequest)
    {
        return requestService.create(formRequest);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FormRequest>>> getAllFormRequests()
    {
        return requestService.getAll();
    }
}
