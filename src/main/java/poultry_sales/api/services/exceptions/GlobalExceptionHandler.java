package poultry_sales.api.services.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Recurso não encontrado
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(
            ResourceNotFoundException ex) {

        Map<String, String> body = Map.of(
                "error", "Not Found",
                "message", ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // 400 - Erros de validação
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null
                                ? error.getDefaultMessage()
                                : "Campo inválido",
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));

        return ResponseEntity.badRequest().body(errors);
    }

    // 404 - URL que não existe
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoResource(
            NoResourceFoundException ex) {

        Map<String, String> body = Map.of(
                "error", "Not Found",
                "message", "Rota não encontrada"
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // 400 - Regra de negócio violada
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Map<String, String>> handleBusinessRule(
            BusinessRuleException ex) {

        Map<String, String> body = Map.of(
                "error", "Bad Request",
                "message", ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 400 - JSON quebrado no corpo da requisição
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleUnreadable(
            HttpMessageNotReadableException ex) {

        Map<String, String> body = Map.of(
                "error", "Bad Request",
                "message", "JSON inválido"
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 500 - Erros internos inesperados
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(
            Exception ex) {

        log.error("Unexpected error", ex);

        Map<String, String> body = Map.of(
                "error", "Internal Server Error",
                "message", "Ocorreu um erro interno no servidor."
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }
}