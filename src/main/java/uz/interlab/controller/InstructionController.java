package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.interlab.entity.Doctor;
import uz.interlab.entity.Instruction;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.DoctorDTO;
import uz.interlab.payload.InstructionDto;
import uz.interlab.service.InstructionService;

import java.util.List;

@RequiredArgsConstructor

@Controller
@RequestMapping("/instruction")
public class InstructionController {

    private final InstructionService instructionService;
/*
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Doctor>> createDoctor(@RequestParam String name,@RequestParam ){
        return
    }*/

    @GetMapping("/get/{id}")
    ResponseEntity<ApiResponse<InstructionDto>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return instructionService.getById(id,lang);
    }

    @GetMapping("/get-all")
    ResponseEntity<ApiResponse<List<InstructionDto>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return instructionService.findAll(lang);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ApiResponse<?>> deleteInstruction(@PathVariable Long id)
    {
        return instructionService.deleteById(id);
    }
}
