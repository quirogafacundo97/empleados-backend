package Jar;

import Jar.dto.ErrorResponseDTO;
import Jar.exception.DepartamentoNoEncontradoException;
import Jar.exception.EmpleadoNoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmpleadoNoEncontrado(EmpleadoNoEncontradoException ex, HttpServletRequest request){

        ErrorResponseDTO error = new ErrorResponseDTO();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DepartamentoNoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleDepartamentoNoEncontrado(DepartamentoNoEncontradoException ex, HttpServletRequest request){

        ErrorResponseDTO error = new ErrorResponseDTO();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> erroresPorCampo = new HashMap<>();

        for(FieldError fieldError : ex.getBindingResult().getFieldErrors()){
            erroresPorCampo.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErrorResponseDTO error = new ErrorResponseDTO();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Error de validacion en los campos enviados");
        error.setDetails(erroresPorCampo);
        error.setTimestamp(LocalDateTime.now());
        error.setPath(request.getRequestURI());

        return ResponseEntity.badRequest().body(error);

    }
}
