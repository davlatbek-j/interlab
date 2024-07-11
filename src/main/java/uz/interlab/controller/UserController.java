package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.interlab.payload.ApiResponse;
import uz.interlab.security.User;
import uz.interlab.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<User>> findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<User>>> findAll() {
        return userService.findAll();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<User>> update(@PathVariable Long id, @RequestBody User user) {
        return userService.update(id, user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id) {
        return userService.deleteById(id);
    }
}
