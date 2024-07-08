package uz.interlab.controller;

import org.springframework.dao.DataIntegrityViolationException;
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
    public ResponseEntity<ApiResponse<?>> handlePhotoTypeException(IllegalPhotoTypeException ex)
    {
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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<?>> handleIllegalArgumentException(IllegalArgumentException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(ex.getMessage(), null));
    }

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolationException(DataIntegrityViolationException e){
//        ApiResponse<Void> response=new ApiResponse<>();
//        response.setMessage("URL already exists. Please use a unique URL.");
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse<Void>> handleException(Exception e){
//        ApiResponse<Void> response=new ApiResponse<>();
//        response.setMessage("An unexpected error occurred");
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//    }

}
