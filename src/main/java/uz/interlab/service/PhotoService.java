package uz.interlab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.Photo;
import uz.interlab.exception.IllegalPhotoTypeException;
import uz.interlab.exception.PhotoNotFoundExcpetion;
import uz.interlab.respository.PhotoRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor

@Service
public class PhotoService
{
    @Value("${photo.upload.path}")
    private String photoUploadPath;

    private final PhotoRepository photoRepo;

    public Photo save(MultipartFile file)
    {
        if (file.getContentType() != null && !(file.getContentType().equals("image/png") ||
                file.getContentType().equals("image/svg+xml") ||
                file.getContentType().equals("image/jpeg")))
        {
            throw new IllegalPhotoTypeException("Unsupported image type: " + file.getContentType());
        }

        try
        {
            Photo photo = photoRepo.save(new Photo());
            String originalFilename = photo.getId() + "-" + file.getOriginalFilename().replaceAll(" ", "%20");

            Path filePath = Paths.get(photoUploadPath + File.separator + originalFilename);
            File uploadDir = new File(filePath.toUri());
            if (!uploadDir.exists())
                uploadDir.mkdirs();
            file.transferTo(uploadDir);

            photo.setName(originalFilename);
            photo.setFilepath(uploadDir.getAbsolutePath());
            photo.setType(file.getContentType());
            photo.setHttpUrl("http://213.230.91.55:8100/photo/" + photo.getName());

            return photoRepo.save(photo);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }


    public ResponseEntity<byte[]> findByNameOrId(String nameOrId)
    {
        try
        {
            Long id = null;
            try
            {
                id = Long.valueOf(nameOrId);
            } catch (NumberFormatException ignored)
            {
                nameOrId = nameOrId.replaceAll(" ", "%20");
            }

            Photo photo = photoRepo.findByIdOrName(id, nameOrId);

            Path imagePath = Paths.get(photo.getFilepath());
            byte[] imageBytes = Files.readAllBytes(imagePath);

            if (photo.getType().equals("image/png"))
                return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
            else if (photo.getType().equals("image/jpeg"))
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
            else if (photo.getType().equals("image/svg+xml"))
            {
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE, "image/svg+xml");

                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            }

        } catch (IOException e)
        {
            e.printStackTrace();
            throw new PhotoNotFoundExcpetion(e.getMessage());
        }
        return null;
    }

}
