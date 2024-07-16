package uz.interlab.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.instruction.Instruction;
import uz.interlab.entity.instruction.InstructionHead;
import uz.interlab.entity.instruction.InstructionMainTitle;
import uz.interlab.entity.instruction.Recommendation;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.instruction.*;
import uz.interlab.respository.InstructionMainTitleRepository;
import uz.interlab.respository.InstructionRepository;
import uz.interlab.service.instruction.InstructionHeadService;
import uz.interlab.util.SlugUtil;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class InstructionService
{
    private final RecommendationService recService;
    private final InstructionHeadService headService;
    private final InstructionRepository instructionRepo;
    private final ObjectMapper jsonMapper;
    private final PhotoService photoService;
    private final InstructionMainTitleRepository mainTitleRepo;

    //Instruction
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

    /*public ResponseEntity<ApiResponse<List<InstructionDetailsDTO>>> findDetailsAll(String lang)
    {
        ApiResponse<List<InstructionDetailsDTO>> response = new ApiResponse<>();
        List<Instruction> all = instructionRepo.findAll();
        response.setData(new ArrayList<>());
        all.forEach(instruction -> response.getData().add(new InstructionDetailsDTO(instruction, lang)));
        response.setMessage("Found " + all.size() + " Instruction(s)");
        return ResponseEntity.status(200).body(response);
    }*/

    // Instruction Recommendation
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

    // Instruction Head
    public ResponseEntity<ApiResponse<InstructionHead>> createHead(String json, MultipartFile icon)
    {
        return headService.createOrUpdate(json, icon);
    }

    public ResponseEntity<ApiResponse<InstructionHeadDTO>> getHead(String lang)
    {
        return headService.get(lang);
    }

    public ResponseEntity<ApiResponse<InstructionHead>> updateHead(String json, MultipartFile icon)
    {
        return headService.update(json, icon);
    }

    public ResponseEntity<ApiResponse<?>> deleteHead()
    {
        return headService.delete();
    }

    // Instruction main title
    public ResponseEntity<ApiResponse<InstructionMainTitle>> createMainTitle(InstructionMainTitle mainTitle)
    {
        ApiResponse<InstructionMainTitle> response = new ApiResponse<>();
        List<InstructionMainTitle> all = mainTitleRepo.findAll();
        if (all.isEmpty())
        {
            response.setMessage("Created");
            response.setData(mainTitleRepo.save(mainTitle));
            return ResponseEntity.status(200).body(response);
        }
        mainTitle.setId(all.get(0).getId());
        response.setMessage("Updated");
        response.setData(mainTitleRepo.save(mainTitle));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<InstructionMainTitleDTO>> getMainTitle(String lang)
    {
        ApiResponse<InstructionMainTitleDTO> response = new ApiResponse<>();
        List<InstructionMainTitle> all = mainTitleRepo.findAll();
        if (all.isEmpty())
        {
            response.setMessage("Main title is null");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.setMessage("Found");
        response.setData(new InstructionMainTitleDTO(all.get(0), lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteMainTitle()
    {
        ApiResponse<?> response = new ApiResponse<>();
        if (mainTitleRepo.findAll().isEmpty())
        {
            response.setMessage("Main title is already deleted or not created yet");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        mainTitleRepo.deleteAll();
        response.setMessage("Deleted");
        return ResponseEntity.status(200).body(response);
    }

}