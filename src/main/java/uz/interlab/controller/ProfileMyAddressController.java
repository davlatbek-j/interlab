package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.interlab.entity.ProfileMyAddress;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.ProfileMyAddressDTO;
import uz.interlab.service.ProfileMyAddressService;

import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileMyAddressController {

    private final ProfileMyAddressService profileMyAddressService;

    @PostMapping("/address/create")
    public ResponseEntity<ApiResponse<ProfileMyAddress>> createAddress(@RequestBody ProfileMyAddress profileMyAddress) {
        return profileMyAddressService.create(profileMyAddress);
    }

    @GetMapping("/address/get/{id}")
    public ResponseEntity<ApiResponse<ProfileMyAddressDTO>> findById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return profileMyAddressService.findById(id, lang);
    }

    @GetMapping("/address/get-full-data/{id}")
    public ResponseEntity<ApiResponse<ProfileMyAddress>> findByIdFullData(@PathVariable Long id) {
        return profileMyAddressService.findById(id);
    }

    @GetMapping("/address/get-all")
    public ResponseEntity<ApiResponse<List<ProfileMyAddressDTO>>> findAll(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return profileMyAddressService.findAll(lang);
    }

    @PutMapping("/address/update/{id}")
    public ResponseEntity<ApiResponse<ProfileMyAddress>> update(
            @PathVariable Long id,
            @RequestBody ProfileMyAddress profileMyAddress
    ) {
        return profileMyAddressService.update(id, profileMyAddress);
    }

    @PutMapping("/address/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActive(@PathVariable Long id) {
        return profileMyAddressService.changeActive(id);
    }

    @DeleteMapping("/address/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteById(@PathVariable Long id) {
        return profileMyAddressService.deleteById(id);
    }

}
