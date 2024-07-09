package uz.interlab.controller.instruction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.interlab.entity.instruction.InstructionDetails;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.instruction.InstructionDetailsDTO;
import uz.interlab.service.instruction.InstructionDetailsService;

@RequiredArgsConstructor

@Controller
@RequestMapping("instruction-details")
public class InstructionDetailsController
{
    private final InstructionDetailsService detailsService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<InstructionDetails>> create(
            @RequestParam("instruction-id") Long instructionId,
            @RequestBody InstructionDetails instructionDetails)
    {
        return detailsService.create(instructionId, instructionDetails);
    }

    @GetMapping("{slug}")
    public ResponseEntity<ApiResponse<InstructionDetailsDTO>> getBySlug(
            @PathVariable String slug,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return detailsService.findBySlug(slug, lang);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<InstructionDetails>> update(
            @RequestParam("instruction-id") Long instructionId,
            @RequestBody InstructionDetails instructionDetails)
    {
        return detailsService.update(instructionId,instructionDetails);
    }
}
