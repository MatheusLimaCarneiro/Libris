package prati.projeto.redeSocial.rest.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import prati.projeto.redeSocial.exception.LivroException;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.exception.TokenInvalidException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServiceResponse<ApiErrors> handleRegraNegocioException(RegraNegocioException ex) {
        ApiErrors errors = new ApiErrors(ex.getMessage());
        return new ServiceResponse<>(errors, "Erro de regra de negócio", false, getFormattedTimestamp());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServiceResponse<ApiErrors> handleMethodNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(erro -> erro.getDefaultMessage())
                .collect(Collectors.toList());

        ApiErrors errors = new ApiErrors(errorMessages);
        return new ServiceResponse<>(errors, "Erro de validação", false, getFormattedTimestamp());
    }

    @ExceptionHandler(TokenInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServiceResponse<ApiErrors> handleTokenInvalidException(TokenInvalidException ex) {
        ApiErrors errors = new ApiErrors(ex.getMessage());
        return new ServiceResponse<>(errors, "Token inválido", false, getFormattedTimestamp());
    }

    @ExceptionHandler(LivroException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServiceResponse<ApiErrors> handleLivroException(LivroException ex) {
        ApiErrors errors = new ApiErrors(ex.getMessage());
        return new ServiceResponse<>(errors, "Erro de Livro", false, getFormattedTimestamp());
    }
}
