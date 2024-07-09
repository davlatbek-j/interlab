package uz.interlab.controller.instruction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.interlab.entity.instruction.Instruction;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.instruction.InstructionDto;
import uz.interlab.service.InstructionService;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/instruction")
public class InstructionController
{

    private final InstructionService instructionService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Instruction>> createInstruction(
            @RequestBody Instruction instruction
    )
    {
        return instructionService.create(instruction);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<InstructionDto>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return instructionService.findById(id, lang);
    }

    @GetMapping("{slug}")
    public ResponseEntity<ApiResponse<InstructionDto>> getBySlug(
            @PathVariable String  slug,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return instructionService.findBySlug(slug,lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<InstructionDto>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang
    )
    {
        return instructionService.findAll(lang);
    }

    @GetMapping("/get-full-data/{slug}")
    public ResponseEntity<ApiResponse<Instruction>> getFullData(@PathVariable String  slug)
    {
        return instructionService.findBySlug(slug);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Instruction>> updateNewness(
            @PathVariable Long id,
            @RequestBody Instruction instruction
    )
    {
        return instructionService.update(id, instruction);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteNewness(@PathVariable Long id)
    {
        return instructionService.deleteById(id);
    }

    @PutMapping("/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActive(@PathVariable Long id)
    {
        return instructionService.changeActive(id);
    }

}
