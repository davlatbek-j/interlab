package uz.interlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.user.Login;
import uz.interlab.payload.user.ResetPassword;
import uz.interlab.security.User;
import uz.interlab.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController
{

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> create(
            @RequestBody User user,
            @RequestParam(value = "sms") Integer sms)
    {
        return userService.create(user, sms);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(
            @RequestBody Login login
    )
    {
        return userService.login(login);
    }

    @PostMapping("/send-sms")
    public ResponseEntity<ApiResponse<?>> sendSms(@RequestParam String phone)
    {
        return userService.sendSms(phone);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<?>> resetPassword(
            @RequestBody ResetPassword resetPassword,
            @RequestParam(value = "sms") Integer sms)
    {
        return userService.setNewPassword(resetPassword, sms);
    }
}
