package uz.interlab.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.interlab.exception.IllegalPhotoTypeException;
import uz.interlab.exception.LanguageNotSupportException;
import uz.interlab.exception.PhotoNotFoundExcpetion;
import uz.interlab.payload.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(IllegalPhotoTypeException.class)
    public ResponseEntity<ApiResponse<?>> handlePhotoTypeException(IllegalPhotoTypeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("Illegal photo: " + ex.getMessage(), null));
    }

    @ExceptionHandler(LanguageNotSupportException.class)
    public ResponseEntity<ApiResponse<?>> handleLanguageException(LanguageNotSupportException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(ex.getMessage(), null));
    }

    @ExceptionHandler(PhotoNotFoundExcpetion.class)
    public ResponseEntity<ApiResponse<?>> handlePhotoNotFoundException(PhotoNotFoundExcpetion ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(ex.getMessage(), null));
    }

}
