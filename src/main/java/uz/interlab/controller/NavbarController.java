package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.interlab.entity.navbar.Navbar;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.NavbarDTO;
import uz.interlab.service.NavbarService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/navbar")
public class NavbarController {

    private final NavbarService navbarService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Navbar>> createNavbar(
            @RequestBody Navbar navbar
    ){
        return navbarService.create(navbar);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<NavbarDTO>>getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ){
        return navbarService.findById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<NavbarDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang
    ){
        return navbarService.findAll(lang);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<Navbar>> getFullData(@PathVariable Long id){
        return navbarService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Navbar>> updateNavbar(
            @PathVariable Long id,
            @RequestBody Navbar navbar
    ){
        return navbarService.update(id, navbar);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteNavbar(@PathVariable Long id){
        return navbarService.deleteById(id);
    }

    @PutMapping("/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActive(@PathVariable Long id){
        return navbarService.changeActive(id);
    }

}
