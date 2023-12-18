package oneulmwohaji.domain.admin.controller;

import static oneulmwohaji.global.oauth.ConstantValue.ACCESS_TOKEN;
import static oneulmwohaji.global.oauth.ConstantValue.REFRESH_TOKEN;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oneulmwohaji.domain.admin.dto.request.AdminRequestDto;
import oneulmwohaji.domain.admin.dto.request.PostRequestDto;
import oneulmwohaji.domain.admin.service.AdminService;
import oneulmwohaji.domain.member.entity.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminService adminService;
    @Secured("ROLE_ADMIN")
    @PatchMapping("/admin/modify/userBan")
    public ResponseEntity<String> modifyUserBan(@PathVariable Long id) {
        adminService.modifyUserBan(id);
        return ResponseEntity.ok()
                .body("userBan 수정 완료");
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/admin/create/post")
    public ResponseEntity<?> uploadPost(@RequestBody PostRequestDto postRequestDto) {
        adminService.addPost(postRequestDto);
        return ResponseEntity.ok()
                .body("post 업로드 완료");
    }

    /*
    TODO : 관리자 로그인 로직
     */
    @PostMapping("/admin/signin")
    public ResponseEntity<?> loginAdmin(@RequestBody @Valid AdminRequestDto adminRequestDto,
                                        HttpServletResponse httpServletResponse) {
        log.info(adminRequestDto.getEmail());
        log.info(adminRequestDto.getOauthId());

        Member member = adminService.login(adminRequestDto);
        String accessToken = adminService.createAccessToken(member.getOauthId());
        String refreshToken = adminService.createRefreshToken(member.getOauthId());

        Cookie cookie = createHttpOnlyCookie(refreshToken);

        httpServletResponse.addCookie(cookie);
        return ResponseEntity.ok()
                .header(ACCESS_TOKEN, accessToken)
                .body(member.getAuthorities());
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
