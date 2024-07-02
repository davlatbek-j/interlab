package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.interlab.service.PhotoService;


@RequiredArgsConstructor

@Controller
@RequestMapping("/photo")
public class PhotoController
{
    private final PhotoService photoService;

    @GetMapping("/{name-or-id}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable(name = "name-or-id") String nameOrId) {
        return photoService.findByNameOrId(nameOrId);
    }

}
