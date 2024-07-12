package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.footer.Footer;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.FooterDTO;
import uz.interlab.service.FooterService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/footer")
public class FooterController
{
    private final FooterService footerService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Footer>> create(
            @RequestParam("json") String json,
            @RequestPart("logo") MultipartFile logo,
            @RequestPart("telegram-icon") MultipartFile tgIcon,
            @RequestPart("youtube-icon") MultipartFile youtubeIcon,
            @RequestPart("instagram-icon") MultipartFile instagramIcon,
            @RequestPart("facebook-icon") MultipartFile facebookIcon,
            @RequestPart("creator-icon") MultipartFile creatorIcon,
            @RequestPart(value = "meta-icon",required = false) MultipartFile meta)
    {

        return footerService.create(json, logo,tgIcon,youtubeIcon,instagramIcon,facebookIcon,creatorIcon );

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<FooterDTO>> findById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return footerService.findById(id, lang);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Footer>> update(
            @PathVariable Long id,
            @RequestParam("json") String json,
            @RequestPart("logo") MultipartFile logo,
            @RequestPart("telegram-icon") MultipartFile tgIcon,
            @RequestPart("youtube-icon") MultipartFile youtubeIcon,
            @RequestPart("instagram-icon") MultipartFile instagramIcon,
            @RequestPart("facebook-icon") MultipartFile facebookIcon,
            @RequestPart("creator-icon") MultipartFile creatorIcon,
            @RequestPart(value = "meta-icon",required = false) MultipartFile meta)

    {
        return footerService.update(id, json, logo, tgIcon,youtubeIcon,instagramIcon,facebookIcon,creatorIcon);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id)
    {
        return footerService.delete(id);
    }
}
