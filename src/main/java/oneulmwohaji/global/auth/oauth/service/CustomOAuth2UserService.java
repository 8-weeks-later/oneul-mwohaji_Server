package oneulmwohaji.global.auth.oauth.service;

import java.util.Map;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oneulmwohaji.domain.member.exception.MemberExistException;
import oneulmwohaji.domain.member.service.MemberService;
import oneulmwohaji.global.auth.oauth.entity.OAuthAttributes;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberService memberService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        processOAuthUser(userRequest, oAuth2User);
        return oAuth2User;
    }

    private void processOAuthUser(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        Map<String, Object> originAttributes = oAuth2User.getAttributes();
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, originAttributes);

        if (memberService.findMemberByEmail(attributes.getEmail()).isPresent()) {
            throw new MemberExistException();
        }
        memberService.saveMember(attributes.toEntity());
    }
}
