package uz.interlab.service.legal;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.interlab.entity.legal.Contact;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.legal.ContactDTO;
import uz.interlab.respository.ContactRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public ResponseEntity<ApiResponse<Contact>> create(Contact contact) {
        ApiResponse<Contact> response = new ApiResponse<>();
        Contact save = contactRepository.save(contact);
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<ContactDTO>> findById(Long id, String lang) {
        ApiResponse<ContactDTO> response = new ApiResponse<>();
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (optionalContact.isEmpty()) {
            response.setMessage("Contact not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Contact contact = optionalContact.get();
        response.setMessage("Found");
        response.setData(new ContactDTO(contact, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<ContactDTO>>> findAll(String lang) {
        ApiResponse<List<ContactDTO>> response = new ApiResponse<>();
        List<Contact> all = contactRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(contact -> response.getData().add(new ContactDTO(contact, lang)));
        response.setMessage("Found " + all.size() + " contact ");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Contact>> findById(Long id) {
        ApiResponse<Contact> response = new ApiResponse<>();
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (optionalContact.isEmpty()) {
            response.setMessage("Contact not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Contact contact = optionalContact.get();
        response.setMessage("Found");
        response.setData(contact);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Contact>> update(Long id, Contact contact) {
        ApiResponse<Contact> response = new ApiResponse<>();
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (optionalContact.isEmpty()) {
            response.setMessage("Contact not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Contact oldContact = optionalContact.get();
        oldContact.setTitleUz(contact.getTitleUz());
        oldContact.setTitleRu(contact.getTitleRu());
        oldContact.setDescriptionUz(contact.getDescriptionUz());
        oldContact.setDescriptionRu(contact.getDescriptionRu());
        oldContact.setPhoneNumbers(contact.getPhoneNumbers());
        oldContact.setButtonCall(contact.getButtonCall());
        Contact save = contactRepository.save(oldContact);
        response.setData(save);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (contactRepository.findById(id).isEmpty()) {
            response.setMessage("Contact not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        contactRepository.deleteById(id);
        response.setMessage("Successfully deleted");
        return ResponseEntity.status(200).body(response);
    }

}
