package prati.projeto.redeSocial.rest.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.exception.TokenInvalidException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException ex){
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMetthodNotValidException(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map((erro ->
                        erro.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ApiErrors(errors);
    }

    @ExceptionHandler(TokenInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleTokenInvalidException(TokenInvalidException ex){
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }
}
