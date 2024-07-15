package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.interlab.entity.PhoneTool;
import uz.interlab.payload.ApiResponse;
import uz.interlab.service.PhoneToolService;

@RestController
@RequestMapping("/phone-tool")
@RequiredArgsConstructor
public class PhoneToolController {

    private final PhoneToolService phoneToolService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PhoneTool>> create(@RequestBody PhoneTool phoneTool) {
        return phoneToolService.create(phoneTool);
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<PhoneTool>> find() {
        return phoneToolService.find();
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<PhoneTool>> update(@RequestBody PhoneTool phoneTool) {
        return phoneToolService.update(phoneTool);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<?>> delete() {
        return phoneToolService.delete();
    }

}
