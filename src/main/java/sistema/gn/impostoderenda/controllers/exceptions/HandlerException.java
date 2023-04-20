package sistema.gn.impostoderenda.controllers.exceptions;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sistema.gn.impostoderenda.services.exceptions.NotFound;

import java.time.Instant;

@ControllerAdvice
public class HandlerException {

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<StandardError> notFoundException(NotFound notFound, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(StandardError.builder()
                        .timestamp(Instant.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .error("Recurso Não Encontrado")
                        .message(notFound.getMessage())
                        .path(request.getRequestURI())
                        .build());
    }

}
