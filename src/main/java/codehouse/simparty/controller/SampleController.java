package codehouse.simparty.controller;

import codehouse.simparty.security.dto.AuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/sample/")
public class SampleController {

    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public void exAll() {
        log.info("exAll.............");
    }

    @GetMapping("/member")
    public void exMember(@AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        log.info("exMember.............");
        log.info("========================");
        log.info(authMemberDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public void exAdmin() {
        log.info("exAdmin.............");
    }

    @PreAuthorize("#authMember != null && #authMember.username eq\"user95@abc.com\"")
    @GetMapping("/exOnly")
    public String exMemberOnly(@AuthenticationPrincipal AuthMemberDTO authMember) {
        log.info("exMemberOnly..........");
        log.info(authMember);

        return "/sample/admin";
    }
}
