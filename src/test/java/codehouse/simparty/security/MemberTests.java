package codehouse.simparty.security;

import codehouse.simparty.entity.Member;
import codehouse.simparty.entity.MemberRole;
import codehouse.simparty.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberTests {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

/*    @Test
    public void testRead() {
        Optional<Member> result = repository.findByEmail("user95@abc.com", false);
        Member member = result.get();
        System.out.println(member);
    }*/

    /*@Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@abc.com")
                    .name("사용자" + i)
                    .formSocial(false)
                    .password(passwordEncoder.encode("11111"))
                    .build();
            member.addMemberRole(MemberRole.USER);
            if (i > 80) {
                member.addMemberRole(MemberRole.MANAGER);
            }
            if (i > 90) {
                member.addMemberRole(MemberRole.ADMIN);
            }
            repository.save(member);
        });
    }*/
}
