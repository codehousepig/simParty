package codehouse.simparty.security.service;

import codehouse.simparty.entity.Member;
import codehouse.simparty.entity.MemberRole;
import codehouse.simparty.repository.MemberRepository;
import codehouse.simparty.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberOAuth2UserDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("==============================");
        System.out.println("MemberOAuth2UserDetailsService_loadUser_userRequest: ");
        System.out.println(userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();
        System.out.println("MemberOAuth2UserDetailsService_loadUser_clientName: ");
        System.out.println(clientName); // Google 로 출력
        System.out.println(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("==============================");
        oAuth2User.getAttributes().forEach((k,v) -> {
            System.out.println(k + ": " + v);
        }); // sub, picture, email, email_verified 등이 출력

        String email = null;

        if (clientName.equals("Google")) { // 구글을 이용하는 경우
            email = oAuth2User.getAttribute("email");
        }
        System.out.println("MemberOAuth2UserDetailsService_loadUser_EMAIL: " + email);

//        Member member = saveSocialMember(email);
//
//        return oAuth2User;
        Member member = saveSocialMember(email);
        AuthMemberDTO authMember = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                true,
                member.getRoleSet().stream().map(
                        role -> new SimpleGrantedAuthority("ROLE_" + role.name())
                ).collect(Collectors.toList()),
                oAuth2User.getAttributes()
        );
        authMember.setName(member.getName());

        return authMember;
    }

    private Member saveSocialMember(String email) {

        // 기존에 동일한 이메일로 가입한 회원이 있는 경우에는 그대로 조회만
        Optional<Member> result = repository.findByEmail(email, true);
        if (result.isPresent()) {
            return result.get();
        }

        // 없다면 회원 추가 패스워드는 1111 이름은 그냥 이메일 주소로
        Member member = Member.builder()
                .email(email)
                .name(email)
                .password(passwordEncoder.encode("1111"))
                .fromSocial(true)
                .build();
        member.addMemberRole(MemberRole.ADMIN);
        repository.save(member);

        return member;
    }
}
