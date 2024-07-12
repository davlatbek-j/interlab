package uz.interlab.controller.legal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.legal.LegalPageAdvantage;
import uz.interlab.entity.legal.LegalPageBody;
import uz.interlab.entity.legal.LegalPageHead;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.legal.LegalPageAdvantageDTO;
import uz.interlab.payload.legal.LegalPageBodyDTO;
import uz.interlab.payload.legal.LegalPageHeadDTO;
import uz.interlab.service.legal.LegalPageAdvantageService;
import uz.interlab.service.legal.LegalPageBodyService;
import uz.interlab.service.legal.LegalPageHeadService;

import java.util.List;

@RestController
@RequestMapping("/legal")
@RequiredArgsConstructor
public class LegalController {

    private final LegalPageHeadService legalPageHeadService;

    private final LegalPageBodyService legalPageBodyService;

    private final LegalPageAdvantageService legalPageAdvantageService;

    @PostMapping("/head/create")
    public ResponseEntity<ApiResponse<LegalPageHead>> createLegalPageHead(
            @RequestParam(value = "json") String legalPageHead,
            @RequestPart(value = "photo") MultipartFile photo
    ) {
        return legalPageHeadService.create(legalPageHead, photo);
    }

    @GetMapping("/head/get/{id}")
    public ResponseEntity<ApiResponse<LegalPageHeadDTO>> getByIdLegalPageHead(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return legalPageHeadService.findById(id, lang);
    }

    @GetMapping("/head/get-all")
    public ResponseEntity<ApiResponse<List<LegalPageHeadDTO>>> getAllLegalPageHead(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return legalPageHeadService.findAll(lang);
    }

    @GetMapping("/head/get-full-data/{id}")
    public ResponseEntity<ApiResponse<LegalPageHead>> getFullDataLegalPageHead(@PathVariable Long id) {
        return legalPageHeadService.findById(id);
    }

    @PutMapping("/head/update/{id}")
    public ResponseEntity<ApiResponse<LegalPageHead>> updateLegalPageHead(
            @PathVariable Long id,
            @RequestParam(value = "json", required = false) String json,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {
        return legalPageHeadService.update(id, json, photo);
    }

    @PutMapping("/head/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActiveLegalPageHead(@PathVariable Long id) {
        return legalPageHeadService.changeActive(id);
    }

    @DeleteMapping("/head/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteLegalPageHead(@PathVariable Long id) {
        return legalPageHeadService.deleteById(id);
    }

    @PostMapping("/body/create")
    public ResponseEntity<ApiResponse<LegalPageBody>> createLegalPageBody(
            @RequestBody LegalPageBody legalPageBody
    ) {
        return legalPageBodyService.create(legalPageBody);
    }

    @GetMapping("/body/get/{id}")
    public ResponseEntity<ApiResponse<LegalPageBodyDTO>> getByIdLegalPageBody(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return legalPageBodyService.findById(id, lang);
    }

    @GetMapping("/body/get-all")
    public ResponseEntity<ApiResponse<List<LegalPageBodyDTO>>> getAllLegalPageBody(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return legalPageBodyService.findAll(lang);
    }

    @GetMapping("/body/get-full-data/{id}")
    public ResponseEntity<ApiResponse<LegalPageBody>> getFullDataLegalPageBody(@PathVariable Long id) {
        return legalPageBodyService.findById(id);
    }

    @PutMapping("/body/update/{id}")
    public ResponseEntity<ApiResponse<LegalPageBody>> updateLegalPageBody(
            @PathVariable Long id,
            @RequestBody LegalPageBody legalPageBody
    ) {
        return legalPageBodyService.update(id, legalPageBody);
    }

    @DeleteMapping("/body/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteLegalPageBody(@PathVariable Long id){
        return legalPageBodyService.deleteById(id);
    }

    @PutMapping("/body/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActiveLegalPageBody(@PathVariable Long id){
        return legalPageBodyService.changeActive(id);
    }

    @PostMapping("/advantage/create")
    public ResponseEntity<ApiResponse<LegalPageAdvantage>> createLegalPageAdvantage(
            @RequestBody LegalPageAdvantage legalPageAdvantage
    ) {
        return legalPageAdvantageService.create(legalPageAdvantage);
    }

    @GetMapping("/advantage/get/{id}")
    public ResponseEntity<ApiResponse<LegalPageAdvantageDTO>> getByIdLegalPageAdvantage(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return legalPageAdvantageService.findById(id, lang);
    }

    @GetMapping("/advantage/get-all")
    public ResponseEntity<ApiResponse<List<LegalPageAdvantageDTO>>> getAllLegalPageAdvantage(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return legalPageAdvantageService.findAll(lang);
    }

    @GetMapping("/advantage/get-full-data/{id}")
    public ResponseEntity<ApiResponse<LegalPageAdvantage>> getFullDataLegalPageAdvantage(@PathVariable Long id) {
        return legalPageAdvantageService.findById(id);
    }

    @PutMapping("/advantage/update/{id}")
    public ResponseEntity<ApiResponse<LegalPageAdvantage>> updateLegalPageAdvantage(
            @PathVariable Long id,
            @RequestBody LegalPageAdvantage legalPageAdvantage
    ) {
        return legalPageAdvantageService.update(id, legalPageAdvantage);
    }

    @PutMapping("/advantage/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActiveLegalPageAdvantage(@PathVariable Long id) {
        return legalPageAdvantageService.changeActive(id);
    }

    @DeleteMapping("/advantage/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteLegalPageAdvantage(@PathVariable Long id) {
        return legalPageAdvantageService.deleteById(id);
    }

}
