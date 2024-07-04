package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.interlab.entity.Instruction;
import uz.interlab.entity.menuBanner.MenuBanner;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.InstructionDto;
import uz.interlab.payload.MenuBannerDTO;
import uz.interlab.service.MenuBannerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu-banner")
public class MenuBannerController {

    private final MenuBannerService menuBannerService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<MenuBanner>> createMenuBanner(
            @RequestBody MenuBanner menuBanner
    ){
        return menuBannerService.create(menuBanner);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<MenuBannerDTO>>getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ){
        return menuBannerService.findById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<MenuBannerDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang
    ){
        return menuBannerService.findAll(lang);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<MenuBanner>> getFullData(@PathVariable Long id){
        return menuBannerService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<MenuBanner>> updateMenuBanner(
            @PathVariable Long id,
            @RequestBody MenuBanner menuBanner
    ){
        return menuBannerService.update(id, menuBanner);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteMenuBanner(@PathVariable Long id){
        return menuBannerService.deleteById(id);
    }

    @PutMapping("/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActive(@PathVariable Long id){
        return menuBannerService.changeActive(id);
    }

}
