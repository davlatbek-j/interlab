package uz.interlab.service.instruction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.interlab.entity.instruction.InstructionDetails;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.instruction.InstructionDetailsDTO;
import uz.interlab.respository.InstructionRepository;
import uz.interlab.respository.instruction.InstructionDetailsRepository;

@RequiredArgsConstructor
@Service
public class InstructionDetailsService
{
    private final InstructionDetailsRepository detailsRepo;
    private final InstructionRepository instructionRepo;

    public ResponseEntity<ApiResponse<InstructionDetails>> create(Long instructionId, InstructionDetails instructionDetails)
    {
        ApiResponse<InstructionDetails> response = new ApiResponse<>();

        if (!instructionRepo.existsById(instructionId))
        {
            response.setMessage("Instruction not found by id " + instructionId);
            return ResponseEntity.status(404).body(response);
        }
        instructionDetails.setId(null);
        instructionDetails.setInstruction(instructionRepo.findById(instructionId).get());
        InstructionDetails saved = detailsRepo.save(instructionDetails);
        instructionRepo.updateDetailsId(saved.getId(), instructionId);
        response.setMessage("Created");
        response.setData(saved);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<InstructionDetailsDTO>> findBySlug(String slug, String lang)
    {
        ApiResponse<InstructionDetailsDTO> response = new ApiResponse<>();
        if (!instructionRepo.existsBySlug(slug))
        {
            response.setMessage("Instruction not found by slug: " + slug);
            return ResponseEntity.status(404).body(response);
        }

        Long detailsId = instructionRepo.findDetailsIdBySlug(slug);
        if (detailsId == null || !detailsRepo.existsById(detailsId))
        {
            response.setMessage("Instruction details is null: ");
            return ResponseEntity.status(200).body(response);
        }
        InstructionDetails instructionDetails = detailsRepo.findById(detailsId).get();
        response.setMessage("Found");
        response.setData(new InstructionDetailsDTO(instructionDetails, lang));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<InstructionDetails>> update(Long instructionId, InstructionDetails instructionDetails)
    {
        ApiResponse<InstructionDetails> response = new ApiResponse<>();
        if (!instructionRepo.existsById(instructionId))
        {
            response.setMessage("Instruction not found by id: " + instructionId);
            return ResponseEntity.status(404).body(response);
        }

        Long detailsId = instructionRepo.findDetailsId(instructionId);
        if (detailsId == null || !detailsRepo.existsById(detailsId))
        {
            response.setMessage("Instruction details is null: ");
            return ResponseEntity.status(200).body(response);
        }
        instructionDetails.setId(detailsId);
        instructionDetails.setInstruction(instructionRepo.findById(instructionId).get());
        InstructionDetails saved = detailsRepo.save(instructionDetails);
        response.setMessage("Updated");
        response.setData(saved);
        return ResponseEntity.ok(response);
    }
}
