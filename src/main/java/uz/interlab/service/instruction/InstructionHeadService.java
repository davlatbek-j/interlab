package uz.interlab.service.instruction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.instruction.InstructionHead;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.instruction.InstructionHeadDTO;
import uz.interlab.respository.InstructionHeadRepository;
import uz.interlab.respository.InstructionRepository;
import uz.interlab.service.PhotoService;

import java.util.List;

@RequiredArgsConstructor

@Transactional
@Service
public class InstructionHeadService
{

    private final InstructionHeadRepository headRepo;
    private final ObjectMapper jsonMapper;
    private final PhotoService photoService;
    private final InstructionRepository instructionRepo;

    public ResponseEntity<ApiResponse<InstructionHead>> createOrUpdate(String json, MultipartFile icon)
    {
        ApiResponse<InstructionHead> response = new ApiResponse<>();
        try
        {
            InstructionHead instructionHead = jsonMapper.readValue(json, InstructionHead.class);
            if (icon != null && !icon.isEmpty())
            {
                instructionHead.setIconUrl(photoService.save(icon).getHttpUrl());
            }

            List<InstructionHead> all = headRepo.findAll();
            if (all.isEmpty())
            {
                InstructionHead saved = headRepo.save(instructionHead);
                response.setMessage("Created");
                response.setData(saved);
            } else
            {
                InstructionHead existing = all.get(0);
                Long id = existing.getId();
                updateInstructionHead(existing, instructionHead);
                existing.setId(id);
                InstructionHead saved = headRepo.save(existing);
                response.setMessage("Updated");
                response.setData(saved);
            }
            return ResponseEntity.status(200).body(response);
        } catch (JsonProcessingException e)
        {
            response.setMessage("Error parsing json: " + e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    private void updateInstructionHead(InstructionHead existing, InstructionHead updated)
    {
        if (updated.getTitleUz() != null) existing.setTitleUz(updated.getTitleUz());
        if (updated.getTitleRu() != null) existing.setTitleRu(updated.getTitleRu());
        if (updated.getDescriptionUz() != null) existing.setDescriptionUz(updated.getDescriptionUz());
        if (updated.getDescriptionRu() != null) existing.setDescriptionRu(updated.getDescriptionRu());
        if (updated.getColourCode() != null) existing.setColourCode(updated.getColourCode());
        if (updated.getIconUrl() != null) existing.setIconUrl(updated.getIconUrl());
    }

    public ResponseEntity<ApiResponse<InstructionHeadDTO>> get(String lang)
    {
        ApiResponse<InstructionHeadDTO> response = new ApiResponse<>();
        List<InstructionHead> all = headRepo.findAll();
        if (all.isEmpty())
        {
            response.setMessage("Instruction head is null");
            return ResponseEntity.status(404).body(response);
        }

        response.setData(new InstructionHeadDTO(all.get(0), lang));
        response.setMessage("Found");

        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<InstructionHead>> update(String json, MultipartFile icon)
    {
        ApiResponse<InstructionHead> response = new ApiResponse<>();
        try
        {
            InstructionHead updatedInstructionHead = jsonMapper.readValue(json, InstructionHead.class);
            List<InstructionHead> all = headRepo.findAll();
            if (all.isEmpty())
            {
                response.setMessage("No InstructionHead found to update");
                return ResponseEntity.status(404).body(response);
            }
            InstructionHead existing = all.get(0);
            if (icon != null && !icon.isEmpty())
            {
                updatedInstructionHead.setIconUrl(photoService.save(icon).getHttpUrl());
            }
            updateInstructionHead(existing, updatedInstructionHead);
            InstructionHead saved = headRepo.save(existing);
            response.setMessage("Updated");
            response.setData(saved);
            return ResponseEntity.status(200).body(response);
        } catch (JsonProcessingException e)
        {
            response.setMessage("Error parsing json: " + e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> delete()
    {
        ApiResponse<?> response = new ApiResponse<>();
        List<InstructionHead> all = headRepo.findAll();
        if (all.isEmpty())
        {
            response.setMessage("No Instruction Head found or not Created yet to delete");
            return ResponseEntity.status(404).body(response);
        }
        headRepo.deleteAll();
        response.setMessage("Deleted");
        return ResponseEntity.status(200).body(response);
    }
}
