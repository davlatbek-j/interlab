package uz.interlab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TestController
{
    @GetMapping
    public ResponseEntity<String> test()
    {
        return ResponseEntity.ok("Запрос успешно дошел до сервера");
    }
}
