package uz.interlab.controller.legal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.interlab.entity.legal.Contact;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.legal.ContactDTO;
import uz.interlab.service.legal.ContactService;

import java.util.List;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Contact>> createContact(
            @RequestBody Contact contact
    ) {
        return contactService.create(contact);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<ContactDTO>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return contactService.findById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<ContactDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return contactService.findAll(lang);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<Contact>> getFullData(@PathVariable Long id) {
        return contactService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Contact>> updateContact(
            @PathVariable Long id,
            @RequestBody Contact contact
    ) {
        return contactService.update(id, contact);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteContact(@PathVariable Long id) {
        return contactService.deleteById(id);
    }

}
