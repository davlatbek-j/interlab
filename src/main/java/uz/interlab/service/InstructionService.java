package uz.interlab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.interlab.entity.instruction.Instruction;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.instruction.InstructionDto;
import uz.interlab.respository.InstructionRepository;
import uz.interlab.util.SlugUtil;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class InstructionService
{


    private final InstructionRepository instructionRepo;

    public ResponseEntity<ApiResponse<Instruction>> create(Instruction instruction)
    {
        ApiResponse<Instruction> response = new ApiResponse<>();
        instruction.setId(null);
        Instruction save = instructionRepo.save(instruction);
        String slug = save.getId() + "-" + SlugUtil.makeSlug(save.getNameUz());
        save.setSlug(slug);
        response.setData(save);
        response.setMessage("Created");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<InstructionDto>> findById(Long id, String lang)
    {
        ApiResponse<InstructionDto> response = new ApiResponse<>();
        if (instructionRepo.findById(id).isEmpty())
        {
            response.setMessage("Instruction not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Instruction instruction = instructionRepo.findById(id).get();
        response.setMessage("Found");
        response.setData(new InstructionDto(instruction, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<InstructionDto>>> findAll(String lang)
    {
        ApiResponse<List<InstructionDto>> response = new ApiResponse<>();
        List<Instruction> all = instructionRepo.findAll();
        response.setData(new ArrayList<>());
        all.forEach(instruction -> response.getData().add(new InstructionDto(instruction, lang)));
        response.setMessage("Found " + all.size() + " Instruction(s)");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Instruction>> findById(Long id)
    {
        ApiResponse<Instruction> response = new ApiResponse<>();
        if (instructionRepo.findById(id).isEmpty())
        {
            response.setMessage("Instruction not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Instruction instruction = instructionRepo.findById(id).get();
        response.setMessage("Found");
        response.setData(instruction);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Instruction>> update(Long id, Instruction updateInstruction)
    {
        ApiResponse<Instruction> response = new ApiResponse<>();
        if (instructionRepo.findById(id).isEmpty())
        {
            response.setMessage("Instruction not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Instruction instruction = instructionRepo.findById(id).get();
        instruction.setId(id);

        String slug = id + "-" + SlugUtil.makeSlug(updateInstruction.getNameUz());
        instruction.setSlug(slug);
        instruction.setNameUz(updateInstruction.getNameUz());
        instruction.setNameRu(updateInstruction.getNameRu());
        Instruction save = instructionRepo.save(instruction);
        response.setData(save);
        return ResponseEntity.status(201).body(response);

    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id)
    {
        ApiResponse<?> response = new ApiResponse<>();
        if (instructionRepo.findById(id).isEmpty())
        {
            response.setMessage("Instruction not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        response.setMessage("Successfully deleted");
        instructionRepo.deleteById(id);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id)
    {
        ApiResponse<?> response = new ApiResponse<>();
        if (instructionRepo.findById(id).isEmpty())
        {
            response.setMessage("Instruction not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Instruction instruction = instructionRepo.findById(id).get();
        boolean active = !instruction.isActive();
        instructionRepo.changeActive(id, active);
        response.setMessage("Successfully changed! Instruction active: " + active);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<InstructionDto>> findBySlug(String slug, String lang)
    {
        ApiResponse<InstructionDto> response = new ApiResponse<>();
        if (!instructionRepo.existsBySlug(slug))
        {
            response.setMessage("Instruction not found by slug: " + slug);
            return ResponseEntity.status(404).body(response);
        }
        Instruction bySlug = instructionRepo.findBySlug(slug);
        InstructionDto dto = new InstructionDto(bySlug, lang);
        response.setData(dto);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Instruction>> findBySlug(String slug)
    {
        ApiResponse<Instruction> response = new ApiResponse<>();
        if (!instructionRepo.existsBySlug(slug))
        {
            response.setMessage("Instruction not found by slug: " + slug);
            return ResponseEntity.status(404).body(response);
        }
        Instruction bySlug = instructionRepo.findBySlug(slug);
        response.setMessage("Found");
        response.setData(bySlug);
        return ResponseEntity.status(200).body(response);
    }
}