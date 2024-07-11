package uz.interlab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.user.AuthResponse;
import uz.interlab.payload.user.Login;
import uz.interlab.respository.UserRepository;
import uz.interlab.security.JwtTokenService;
import uz.interlab.security.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenService tokenService;

    public ResponseEntity<ApiResponse<?>> login(Login login) {
        ApiResponse<AuthResponse> response = new ApiResponse<>();
        User byPhoneNumber = userRepository.findByPhoneNumber(login.phoneNumber());
        if (byPhoneNumber != null) {
            if (passwordEncoder.matches(login.password(), byPhoneNumber.getPassword())) {
                response.setMessage("Success");
                response.setData(new AuthResponse(tokenService.generateToken(login.phoneNumber())));
                return ResponseEntity.status(200).body(response);
            }
        }
        response.setMessage("Username or password incorrect");
        return ResponseEntity.status(401).body(response);
    }

    public ResponseEntity<ApiResponse<User>> create(User user) {
        ApiResponse<User> response = new ApiResponse<>();
        if (checkPhoneNumber(user.getPhoneNumber())) {
            response.setMessage("Phone number is already exists. Please enter other phone number");
            return ResponseEntity.status(409).body(response);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User save = userRepository.save(user);
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<User>> findById(Long id) {
        ApiResponse<User> response = new ApiResponse<>();
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            response.setMessage("User not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        User user = optionalUser.get();
        response.setMessage("Found");
        response.setData(user);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<User>>> findAll() {
        ApiResponse<List<User>> response = new ApiResponse<>();
        List<User> all = userRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(user -> response.getData().add(user));
        response.setMessage("Found " + all.size() + " user(s)");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<User>> update(Long id, User user) {
        ApiResponse<User> response = new ApiResponse<>();
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            response.setMessage("User not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        if (checkPhoneNumber(user.getPhoneNumber())){
            response.setMessage("Phone number is already exists. Please enter other phone number");
            return ResponseEntity.status(409).body(response);
        }
        User oldUser = optionalUser.get();
        oldUser.setPhoneNumber(user.getPhoneNumber());
        oldUser.setFullName(user.getFullName());
        oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        oldUser.setDateOfBirth(user.getDateOfBirth());
        User save = userRepository.save(oldUser);
        response.setData(save);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (userRepository.findById(id).isEmpty()) {
            response.setMessage("User not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        userRepository.deleteById(id);
        response.setMessage("Successfully deleted");
        return ResponseEntity.status(200).body(response);
    }

    private boolean checkPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

}
