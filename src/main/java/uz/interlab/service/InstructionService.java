package uz.interlab.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.instruction.Instruction;
import uz.interlab.entity.instruction.InstructionHead;
import uz.interlab.entity.instruction.Recommendation;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.instruction.InstructionDetailsDTO;
import uz.interlab.payload.instruction.InstructionHeadDTO;
import uz.interlab.payload.instruction.InstructionMainDTO;
import uz.interlab.payload.instruction.RecommendationDTO;
import uz.interlab.respository.InstructionHeadRepository;
import uz.interlab.respository.InstructionRepository;
import uz.interlab.util.SlugUtil;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class InstructionService
{
    private final RecommendationService recService;
    private final InstructionRepository instructionRepo;
    private final ObjectMapper jsonMapper;
    private final PhotoService photoService;
    private final InstructionHeadRepository headRepo;

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

    public ResponseEntity<ApiResponse<InstructionMainDTO>> findById(Long id, String lang)
    {
        ApiResponse<InstructionMainDTO> response = new ApiResponse<>();
        if (instructionRepo.findById(id).isEmpty())
        {
            response.setMessage("Instruction not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Instruction instruction = instructionRepo.findById(id).get();
        response.setMessage("Found");
        response.setData(new InstructionMainDTO(instruction, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<InstructionMainDTO>>> findAll(String lang, String main, String active)
    {
        ApiResponse<List<InstructionMainDTO>> response = new ApiResponse<>();
        List<Instruction> all = new ArrayList<>();
        response.setData(new ArrayList<>());
        int size = 0;
        switch (main.toLowerCase())
        {
            case "all":
            {
                if (active.equalsIgnoreCase("all"))
                {
                    all = instructionRepo.findAll();
                    all.forEach(instruction -> response.getData().add(new InstructionMainDTO(instruction, lang)));
                    size = all.size();
                } else if (active.equalsIgnoreCase("true") || active.equalsIgnoreCase("false"))
                {
                    all = instructionRepo.findAllByActive(Boolean.parseBoolean(active));
                    all.forEach(instruction -> response.getData().add(new InstructionMainDTO(instruction, lang)));
                    size = all.size();
                } else
                    throw new IllegalArgumentException("'active' should be either 'all' or 'true' or 'false'");
                break;
            }
            case "true":
            case "false":
            {
                all = instructionRepo.findAllByMainAndActive(Boolean.parseBoolean(main), Boolean.parseBoolean(active));
                all.forEach(instruction -> response.getData().add(new InstructionMainDTO(instruction, lang)));
                size = all.size();
                break;
            }
            default:
                throw new IllegalArgumentException("'main' should be either 'all' or 'true' or 'false'");
        }

        response.setMessage("Found " + size + " Instruction(s)");
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

    public ResponseEntity<ApiResponse<Instruction>> update(Long id, Instruction newInstruction)
    {
        ApiResponse<Instruction> response = new ApiResponse<>();
        if (instructionRepo.findById(id).isEmpty())
        {
            response.setMessage("Instruction not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        String slug = id + "-" + SlugUtil.makeSlug(newInstruction.getNameUz());
        newInstruction.setId(id);
        newInstruction.setSlug(slug);
        Instruction save = instructionRepo.save(newInstruction);
        response.setMessage("Updated");
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

    public ResponseEntity<ApiResponse<InstructionMainDTO>> findBySlug(String slug, String lang)
    {
        ApiResponse<InstructionMainDTO> response = new ApiResponse<>();
        if (!instructionRepo.existsBySlug(slug))
        {
            response.setMessage("Instruction not found by slug: " + slug);
            return ResponseEntity.status(404).body(response);
        }
        Instruction bySlug = instructionRepo.findBySlug(slug);
        InstructionMainDTO dto = new InstructionMainDTO(bySlug, lang);
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

    public ResponseEntity<ApiResponse<InstructionDetailsDTO>> findDetailsBySlug(String slug, String lang)
    {
        ApiResponse<InstructionDetailsDTO> response = new ApiResponse<>();
        if (!instructionRepo.existsBySlug(slug))
        {
            response.setMessage("Instruction not found by slug: " + slug);
            return ResponseEntity.status(404).body(response);
        }
        Instruction bySlug = instructionRepo.findBySlug(slug);
        response.setMessage("Found");
        response.setData(new InstructionDetailsDTO(bySlug, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<InstructionDetailsDTO>>> findDetailsAll(String lang)
    {
        ApiResponse<List<InstructionDetailsDTO>> response = new ApiResponse<>();
        List<Instruction> all = instructionRepo.findAll();
        response.setData(new ArrayList<>());
        all.forEach(instruction -> response.getData().add(new InstructionDetailsDTO(instruction, lang)));
        response.setMessage("Found " + all.size() + " Instruction(s)");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Recommendation>> createRecommendation(Recommendation recommendation)
    {
        return recService.create(recommendation);
    }

    public ResponseEntity<ApiResponse<RecommendationDTO>> getRecommendation(String lang)
    {
        return recService.get(lang);
    }

    public ResponseEntity<ApiResponse<?>> deleteRecommendation()
    {
        return recService.delete();
    }

    public ResponseEntity<ApiResponse<InstructionHead>> createHead(String json, MultipartFile icon)
    {
        ApiResponse<InstructionHead> response = new ApiResponse<>();
        try
        {
            InstructionHead instructionHead = jsonMapper.readValue(json, InstructionHead.class);
            instructionHead.setIconUrl(photoService.save(icon).getHttpUrl());
            InstructionHead saved = headRepo.save(instructionHead);
            response.setMessage("Created");
            response.setData(saved);
            return ResponseEntity.status(200).body(response);
        } catch (JsonProcessingException e)
        {
            response.setMessage("Error parsing json" + e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<List<InstructionHeadDTO>>> getAllHead(String lang)
    {
        ApiResponse<List<InstructionHeadDTO>> response = new ApiResponse<>();
        List<InstructionHead> all = headRepo.findAll();
        all.forEach(i -> response.getData().add(new InstructionHeadDTO(i, lang)));
        response.setMessage("Found " + all.size() + " Instruction(s)");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<InstructionHead>> updateHead(Long id, String json, MultipartFile icon)
    {
        ApiResponse<InstructionHead> response = new ApiResponse<>();
        if (!instructionRepo.existsById(id))
        {
            response.setMessage("Instruction not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }

        InstructionHead head = new InstructionHead();
        String oldIcon = headRepo.findIconUrlById(id);

        try
        {
            if (json != null)
            {
                head = jsonMapper.readValue(json, InstructionHead.class);
                head.setIconUrl(oldIcon);
                head.setId(id);
            } else
                head = headRepo.findById(id).get();

            if (icon != null && !icon.isEmpty())
                head.setIconUrl(photoService.save(icon).getHttpUrl());
            else
                head.setIconUrl(oldIcon);
            response.setMessage("Updated");
            response.setData(head);
            return ResponseEntity.status(200).body(response);
        } catch (JsonProcessingException e)
        {
            response.setMessage("Error parsing json" + e.getMessage());
            return ResponseEntity.status(400).body(response);
        }

    }

    public ResponseEntity<ApiResponse<?>> deleteHead(Long id)
    {
        ApiResponse<InstructionHead> response = new ApiResponse<>();
        if (!instructionRepo.existsById(id))
        {
            response.setMessage("Instruction not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        headRepo.deleteById(id);
        response.setMessage("Deleted");
        return ResponseEntity.status(200).body(response);
    }
}