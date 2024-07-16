package uz.interlab.controller.instruction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.instruction.Instruction;
import uz.interlab.entity.instruction.InstructionHead;
import uz.interlab.entity.instruction.InstructionMainTitle;
import uz.interlab.entity.instruction.Recommendation;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.instruction.*;
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
            @RequestBody Instruction instruction)
    {
        return instructionService.create(instruction);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<InstructionMainDTO>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return instructionService.findById(id, lang);
    }

    @GetMapping("{slug}")
    public ResponseEntity<ApiResponse<InstructionMainDTO>> getBySlug(
            @PathVariable String slug,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return instructionService.findBySlug(slug, lang);
    }

    @GetMapping("/details/{slug}")
    public ResponseEntity<ApiResponse<InstructionDetailsDTO>> getDetails(
            @PathVariable String slug,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return instructionService.findDetailsBySlug(slug, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<InstructionMainDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang,
            @RequestParam(value = "main", required = false, defaultValue = "all") String main,
            @RequestParam(value = "active", required = false, defaultValue = "all") String active)
    {
        return instructionService.findAll(lang, main, active);
    }

    /*@GetMapping("/details/get-all")
    public ResponseEntity<ApiResponse<List<InstructionDetailsDTO>>> getDetailsAll(
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return instructionService.findDetailsAll(lang);
    }*/

    @GetMapping("/get-full-data/{slug}")
    public ResponseEntity<ApiResponse<Instruction>> getFullData(@PathVariable String slug)
    {
        return instructionService.findBySlug(slug);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Instruction>> updateNewness(
            @PathVariable Long id,
            @RequestBody Instruction instruction)
    {
        return instructionService.update(id, instruction);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id)
    {
        return instructionService.deleteById(id);
    }

    @PostMapping("/main-title/create")
    public ResponseEntity<ApiResponse<InstructionMainTitle>> createMainTitle(
            @RequestBody InstructionMainTitle mainTitle)
    {
        return instructionService.createMainTitle(mainTitle);
    }

    @GetMapping("/main-title")
    public ResponseEntity<ApiResponse<InstructionMainTitleDTO>> getMainTitle(
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return instructionService.getMainTitle(lang);
    }

    @PutMapping("/main-title/update")
    public ResponseEntity<ApiResponse<InstructionMainTitle>> updateMainTitle(
            @RequestBody InstructionMainTitle newMainTitle)
    {
        return instructionService.createMainTitle(newMainTitle);
    }

    @DeleteMapping("/main-title/delete")
    public ResponseEntity<ApiResponse<?>> deleteMainTitle()
    {
        return instructionService.deleteMainTitle();
    }


    @PutMapping("/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActive(@PathVariable Long id)
    {
        return instructionService.changeActive(id);
    }

    @PostMapping("/recommendation/create")
    public ResponseEntity<ApiResponse<Recommendation>> createRecommendation(
            @RequestBody Recommendation recommendation)
    {
        return instructionService.createRecommendation(recommendation);
    }

    @GetMapping("/recommendation/get")
    public ResponseEntity<ApiResponse<RecommendationDTO>> getRecommendation(
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return instructionService.getRecommendation(lang);
    }

    @PutMapping("/recommendation/update")
    public ResponseEntity<ApiResponse<Recommendation>> updateRecommendation(
            @RequestBody Recommendation recommendation)
    {
        return instructionService.createRecommendation(recommendation);
    }

    @DeleteMapping("/recommendation/delete")
    public ResponseEntity<ApiResponse<?>> deleteRecommendation()
    {
        return instructionService.deleteRecommendation();
    }

    @PostMapping("/head/create")
    public ResponseEntity<ApiResponse<InstructionHead>> create(
            @RequestParam(value = "json") String json,
            @RequestPart(value = "icon") MultipartFile icon)
    {
        return instructionService.createHead(json, icon);
    }

    @GetMapping("/head")
    public ResponseEntity<ApiResponse<InstructionHeadDTO>> getHead(
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return instructionService.getHead(lang);
    }

    @PutMapping("/head/update")
    public ResponseEntity<ApiResponse<InstructionHead>> update(
            @RequestParam(value = "json", required = false) String json,
            @RequestPart(value = "icon", required = false) MultipartFile icon)
    {
        return instructionService.updateHead(json, icon);
    }

    @DeleteMapping("/head/delete")
    public ResponseEntity<ApiResponse<?>> deleteHead()
    {
        return instructionService.deleteHead();
    }

}
