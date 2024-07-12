package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.interlab.entity.Address;
import uz.interlab.payload.AddressDTO;
import uz.interlab.payload.ApiResponse;
import uz.interlab.service.AddressService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Address>> createAddress(
            @RequestParam(value = "json") String address

    ) {
        return addressService.create(address);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<AddressDTO>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return addressService.findById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<AddressDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return addressService.findAll(lang);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Address>> updateNewness(
            @PathVariable Long id,
            @RequestParam(value = "json", required = false) String json
    ) {
        return addressService.update(id, json);
    }

    @PutMapping("/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActive(@PathVariable Long id){
        return addressService.changeActive(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteNewness(@PathVariable Long id) {
        return addressService.deleteById(id);
    }

    @GetMapping("{slug}")
    public ResponseEntity<ApiResponse<AddressDTO>> getBySlug(
            @PathVariable String slug,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return addressService.findBySlug(slug, lang);
    }

    @GetMapping("/get-full-data/{slug}")
    public ResponseEntity<ApiResponse<Address>> getFullDataBySlug(@PathVariable String slug) {
        return addressService.findBySlug(slug);
    }

}
