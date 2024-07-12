package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.blog.Blog;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.BlogDTO;
import uz.interlab.service.BlogService;

import java.util.List;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Blog>> createBlog(
            @RequestParam(value = "json") String blog,
            @RequestPart(value = "photo") MultipartFile photo
    ) {
        return blogService.create(blog, photo);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<BlogDTO>> getById(
            @PathVariable Long id,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return blogService.findById(id, lang);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<BlogDTO>>> getAll(
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return blogService.findAll(lang);
    }

    @GetMapping("/get-all-page")
    public ResponseEntity<ApiResponse<org.springframework.data.domain.Page<BlogDTO>>> getAllWithPageNation(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size",required = false, defaultValue = "12") Integer size,
            @RequestHeader(value = "Accept-Language") String lang
    ) {
        return blogService.findAllWithPagination(lang, page, size);
    }

    @GetMapping("/get-full-data/{id}")
    public ResponseEntity<ApiResponse<Blog>> getFullData(@PathVariable Long id) {
        return blogService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Blog>> updateBlog(
            @PathVariable Long id,
            @RequestParam(value = "json", required = false) String json,
            @RequestPart(value = "photo", required = false) MultipartFile photo
    ) {
        return blogService.update(id, json, photo);
    }

    @PutMapping("/change-active/{id}")
    public ResponseEntity<ApiResponse<?>> changeActive(@PathVariable Long id) {
        return blogService.changeActive(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteBlog(@PathVariable Long id) {
        return blogService.deleteById(id);
    }

}
