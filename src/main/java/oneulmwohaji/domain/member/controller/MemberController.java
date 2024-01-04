package oneulmwohaji.domain.member.controller;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import oneulmwohaji.domain.member.dto.Request.MemberUpdateNicknameRequest;
import oneulmwohaji.domain.member.dto.Response.MemberInfoResponse;
import oneulmwohaji.domain.member.entity.Member;
import oneulmwohaji.domain.member.service.MemberService;
import oneulmwohaji.domain.post.dto.response.PostResponse;
import oneulmwohaji.domain.post.entity.Post;
import oneulmwohaji.global.jwt.MemberPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping(value = "/user")
    public ResponseEntity<MemberInfoResponse> getUserInfo(@AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        Long id = memberPrincipal.getMember().getId();
        MemberInfoResponse memberInfoResponse = memberService.getMemberInfo(id);
        return ResponseEntity.ok(memberInfoResponse);
    }

    @PatchMapping("/user/username")
    public ResponseEntity<?> modifyMemberNickname(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                                  @RequestBody MemberUpdateNicknameRequest memberUpdateNicknameRequest) {
        Member member = memberPrincipal.getMember();
        MemberInfoResponse memberInfoResponse = memberService.modifyMemberNickname(member,
                memberUpdateNicknameRequest.getUsername());
        return ResponseEntity.ok(memberInfoResponse);
    }
}
