package uz.interlab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TestController
{
    public ResponseEntity<String> test()
    {
        return ResponseEntity.ok("Запрос поступил на сервер");
    }
}
