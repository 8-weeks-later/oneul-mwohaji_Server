package oneulmwohaji.domain.member.controller;

import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/auth/login")
    public ResponseEntity<?> authLoginFailure(
            @RequestParam("error") String error,
            @RequestParam("exception") String errorMessage
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }
}
