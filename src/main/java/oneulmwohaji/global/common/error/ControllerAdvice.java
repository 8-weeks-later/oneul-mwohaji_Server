package oneulmwohaji.global.common.error;

import oneulmwohaji.domain.member.exception.MemberExistException;
import oneulmwohaji.domain.member.exception.MemberNotFoundException;
import oneulmwohaji.global.auth.jwt.exception.TokenException;
import oneulmwohaji.global.auth.jwt.exception.TokenExpiredException;
import oneulmwohaji.global.auth.jwt.exception.TokenUnsupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler({
            MemberNotFoundException.class,
            MemberExistException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFound(final RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler({
            TokenExpiredException.class,
            TokenUnsupportedException.class,
            TokenException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(final RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }
}
