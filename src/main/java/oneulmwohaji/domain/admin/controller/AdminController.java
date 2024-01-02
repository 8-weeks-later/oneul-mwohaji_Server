package oneulmwohaji.domain.admin.controller;

import static oneulmwohaji.global.oauth.ConstantValue.ACCESS_TOKEN;
import static oneulmwohaji.global.oauth.ConstantValue.REFRESH_TOKEN;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oneulmwohaji.domain.admin.dto.request.AdminRequest;
import oneulmwohaji.domain.admin.dto.request.PostRequest;
import oneulmwohaji.domain.admin.dto.response.MemberUpdateResponse;
import oneulmwohaji.domain.admin.service.AdminService;
import oneulmwohaji.domain.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminService adminService;
    @Secured("ROLE_ADMIN")
    @PatchMapping("/admin/modify/userBan")
    public ResponseEntity<MemberUpdateResponse> modifyUserBan(@RequestParam("id")Long id) {
        MemberUpdateResponse memberUpdateResponse = adminService.modifyUserBan(id);
        return ResponseEntity.ok()
                .body(memberUpdateResponse);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/create/post")
    public ResponseEntity<Void> uploadPost(@RequestBody PostRequest postRequest) {
        adminService.addPost(postRequest);
        return ResponseEntity.noContent()
                .build();
    }

    /*
    TODO : 관리자 로그인 로직
     */
    @PostMapping("/admin/signin")
    public ResponseEntity<?> loginAdmin(@RequestBody @Valid AdminRequest adminRequest,
                                        HttpServletResponse httpServletResponse) {
        log.info(adminRequest.getEmail());
        log.info(adminRequest.getOauthId());

        Member member = adminService.login(adminRequest);
        String accessToken = adminService.createAccessToken(member.getOauthId());
        String refreshToken = adminService.createRefreshToken(member.getOauthId());

        Cookie cookie = createHttpOnlyCookie(refreshToken);

        httpServletResponse.addCookie(cookie);
        return ResponseEntity.noContent()
                .header(ACCESS_TOKEN, accessToken)
                .build();
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    private Cookie createHttpOnlyCookie(String refreshToken) {
        Cookie cookie = new Cookie(REFRESH_TOKEN, refreshToken);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        return cookie;
    }
}
