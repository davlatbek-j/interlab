package uz.interlab.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.interlab.entity.menuBanner.MenuBanner;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.MenuBannerDTO;
import uz.interlab.respository.MenuBannerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuBannerService {

    private final MenuBannerRepository menuBannerRepository;

    public ResponseEntity<ApiResponse<MenuBanner>> create(MenuBanner menuBanner){
        ApiResponse<MenuBanner> response=new ApiResponse<>();
        Optional<MenuBanner> firstMenuBanner = menuBannerRepository.findAll().stream().findFirst();
        if (firstMenuBanner.isPresent()){
            MenuBanner banner = firstMenuBanner.get();
            banner.setMenuOption(menuBanner.getMenuOption());
            MenuBanner save = menuBannerRepository.save(banner);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        }
        menuBanner.setId(null);
        MenuBanner save = menuBannerRepository.save(menuBanner);
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<MenuBannerDTO>> findById(Long id, String lang){
        ApiResponse<MenuBannerDTO> response=new ApiResponse<>();
        if (menuBannerRepository.findById(id).isEmpty()){
            response.setMessage("MenuBanner not found by id: "+id);
            return ResponseEntity.status(404).body(response);
        }
        MenuBanner menuBanner = menuBannerRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(new MenuBannerDTO(menuBanner,lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<MenuBannerDTO>>> findAll(String lang){
        ApiResponse<List<MenuBannerDTO>> response=new ApiResponse<>();
        List<MenuBanner> all = menuBannerRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(menuBanner -> response.getData().add(new MenuBannerDTO(menuBanner,lang)));
        response.setMessage("Found "+all.size()+" MenuBanner(s)");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<MenuBanner>> findById(Long id){
        ApiResponse<MenuBanner> response=new ApiResponse<>();
        if (menuBannerRepository.findById(id).isEmpty()){
            response.setMessage("MenuBanner not found by id: "+id);
            return ResponseEntity.status(404).body(response);
        }
        MenuBanner menuBanner = menuBannerRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(menuBanner);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<MenuBanner>> update(Long id, MenuBanner updateManuBanner){
        ApiResponse<MenuBanner> response=new ApiResponse<>();
        if (menuBannerRepository.findById(id).isEmpty()){
            response.setMessage("MenuBanner not found by id: "+id);
            return ResponseEntity.status(404).body(response);
        }
        MenuBanner menuBanner = menuBannerRepository.findById(id).get();
        menuBanner.setId(id);
        menuBanner.setMenuOption(updateManuBanner.getMenuOption());
        MenuBanner save = menuBannerRepository.save(menuBanner);
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id){
        ApiResponse<?> response=new ApiResponse<>();
        if (menuBannerRepository.findById(id).isEmpty()){
            response.setMessage("MenuBanner not found by id: "+id);
            return ResponseEntity.status(404).body(response);
        }
        menuBannerRepository.deleteById(id);
        response.setMessage("Successfully deleted");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id){
        ApiResponse<?> response=new ApiResponse<>();
        if (menuBannerRepository.findById(id).isEmpty()){
            response.setMessage("MenuBanner not found by id: "+id);
            return ResponseEntity.status(404).body(response);
        }
        MenuBanner menuBanner = menuBannerRepository.findById(id).get();
        boolean active=!menuBanner.isActive();
        menuBannerRepository.changeActive(id,active);
        response.setMessage("Successfully changed!. MenuBanner active: "+active);
        return ResponseEntity.status(200).body(response);
    }

}
