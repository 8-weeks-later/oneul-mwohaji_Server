package oneulmwohaji.domain.admin.controller;

import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.admin.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @Secured("{ROLE_ADMIN}")
    @PatchMapping("/admin/modify/userBan")
    public ResponseEntity<String> modifyUserBan(@PathVariable Long id) {
        adminService.modifyUserBan(id);
        return ResponseEntity.ok()
                .body("userBan 수정 완료");
    }
}
