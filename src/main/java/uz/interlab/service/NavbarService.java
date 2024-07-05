package uz.interlab.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.interlab.entity.navbar.Navbar;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.NavbarDTO;
import uz.interlab.respository.NavbarRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class NavbarService {

    private final NavbarRepository navbarRepository;

    public ResponseEntity<ApiResponse<Navbar>> create(Navbar navbar){
        ApiResponse<Navbar> response=new ApiResponse<>();
        Optional<Navbar> firstNavbar = navbarRepository.findAll().stream().findFirst();
        if (firstNavbar.isPresent()){
            Navbar newNavbar = firstNavbar.get();
            newNavbar.setNavbarOptions(navbar.getNavbarOptions());
            Navbar save = navbarRepository.save(newNavbar);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        }
        navbar.setId(null);
        Navbar save = navbarRepository.save(navbar);
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<NavbarDTO>> findById(Long id, String lang){
        ApiResponse<NavbarDTO> response=new ApiResponse<>();
        if (navbarRepository.findById(id).isEmpty()){
            response.setMessage("Navbar not found by id: "+id);
            return ResponseEntity.status(404).body(response);
        }
        Navbar navbar = navbarRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(new NavbarDTO(navbar,lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<NavbarDTO>>> findAll(String lang){
        ApiResponse<List<NavbarDTO>> response=new ApiResponse<>();
        List<Navbar> all = navbarRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(navbar -> response.getData().add(new NavbarDTO(navbar,lang)));
        response.setMessage("Found "+all.size()+" Navbar(s)");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Navbar>> findById(Long id){
        ApiResponse<Navbar> response=new ApiResponse<>();
        if (navbarRepository.findById(id).isEmpty()){
            response.setMessage("Navbar not found by id: "+id);
            return ResponseEntity.status(404).body(response);
        }
        Navbar navbar = navbarRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(navbar);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Navbar>> update(Long id, Navbar updateManuBanner){
        ApiResponse<Navbar> response=new ApiResponse<>();
        if (navbarRepository.findById(id).isEmpty()){
            response.setMessage("Navbar not found by id: "+id);
            return ResponseEntity.status(404).body(response);
        }
        Navbar navbar = navbarRepository.findById(id).get();
        navbar.setId(id);
        navbar.setNavbarOptions(updateManuBanner.getNavbarOptions());
        Navbar save = navbarRepository.save(navbar);
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id){
        ApiResponse<?> response=new ApiResponse<>();
        if (navbarRepository.findById(id).isEmpty()){
            response.setMessage("Navbar not found by id: "+id);
            return ResponseEntity.status(404).body(response);
        }
        navbarRepository.deleteById(id);
        response.setMessage("Successfully deleted");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id){
        ApiResponse<?> response=new ApiResponse<>();
        if (navbarRepository.findById(id).isEmpty()){
            response.setMessage("Navbar not found by id: "+id);
            return ResponseEntity.status(404).body(response);
        }
        Navbar navbar = navbarRepository.findById(id).get();
        boolean active=!navbar.isActive();
        navbarRepository.changeActive(id,active);
        response.setMessage("Successfully changed!. Navbar active: "+active);
        return ResponseEntity.status(200).body(response);
    }

}
