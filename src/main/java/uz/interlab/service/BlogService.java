package uz.interlab.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.blog.Blog;
import uz.interlab.entity.Photo;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.BlogDTO;
import uz.interlab.respository.BlogRepository;
import uz.interlab.util.SlugUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    private final ObjectMapper objectMapper;

    private final PhotoService photoService;

    public ResponseEntity<ApiResponse<Blog>> create(String strBlog, MultipartFile photoFile) {
        ApiResponse<Blog> response = new ApiResponse<>();
        try {
            Blog blog = objectMapper.readValue(strBlog, Blog.class);
            blog.setId(null);

            Photo photo = photoService.save(photoFile);

            blog.setPhotoUrl(photo.getHttpUrl());
            Blog save = blogRepository.save(blog);
            String slug=save.getId()+"-"+SlugUtil.makeSlug(blog.getTitleUz());
            blogRepository.updateSlug(slug, save.getId());
            save.setSlug(slug);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<ApiResponse<BlogDTO>> findById(Long id, String lang) {
        ApiResponse<BlogDTO> response = new ApiResponse<>();
        if (blogRepository.findById(id).isEmpty()) {
            response.setMessage("Blog not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Blog blog = blogRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(new BlogDTO(blog, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<BlogDTO>>> findAll(String lang) {
        ApiResponse<List<BlogDTO>> response = new ApiResponse<>();
        List<Blog> all = blogRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(blog -> response.getData().add(new BlogDTO(blog, lang)));
        response.setMessage("Found " + all.size() + " new(s)");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Blog>> findById(Long id) {
        ApiResponse<Blog> response = new ApiResponse<>();
        if (blogRepository.findById(id).isEmpty()) {
            response.setMessage("Blog not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Blog blog = blogRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(blog);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Blog>> update(Long id, String newJson, MultipartFile newPhoto) {
        ApiResponse<Blog> response = new ApiResponse<>();
        if (blogRepository.findById(id).isEmpty()) {
            response.setMessage("Blog not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        String oldPhotoUrl = blogRepository.findPhotoUrlById(id);
        String slug = blogRepository.findSlugById(id);
        Blog blog = new Blog();
        try {
            if (newJson != null) {
                blog = objectMapper.readValue(newJson, Blog.class);
                if (newPhoto == null || !(newPhoto.getSize() > 0)) {
                    blog.setPhotoUrl(oldPhotoUrl);
                }
                blog.setId(id);
                blog.setSlug(slug);
            } else {
                blog = blogRepository.findById(id).get();
            }
            if (newPhoto != null && newPhoto.getSize() > 0) {
                Photo photo = photoService.save(newPhoto);
                blog.setPhotoUrl(photo.getHttpUrl());
            }
            Blog save = blogRepository.save(blog);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(401).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (blogRepository.findById(id).isEmpty()) {
            response.setMessage("Blog not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        response.setMessage("Successfully deleted");
        blogRepository.deleteById(id);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (blogRepository.findById(id).isEmpty()) {
            response.setMessage("Blog not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Blog blog = blogRepository.findById(id).get();
        boolean active = !blog.isActive();
        blogRepository.changeActive(id, active);
        response.setMessage("Successfully changed! Blog active: " + active);
        return ResponseEntity.status(200).body(response);
    }

}
