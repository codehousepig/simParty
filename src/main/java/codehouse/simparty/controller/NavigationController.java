package codehouse.simparty.controller;

import codehouse.simparty.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/simparty")
@Log4j2
@RequiredArgsConstructor
public class NavigationController {

//    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("#authMember != null && #authMember.username eq\"codehouse95@gmail.com\"")
    @GetMapping("/chunu")
    public String view_info(@AuthenticationPrincipal AuthMemberDTO authMember){
        log.info("=====view_info=====");
        log.info(authMember);
        return "/simparty/chunu";
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/test")
    public void view_test() {

    }

    @PreAuthorize("permitAll()")
    @GetMapping("")
    public String view_main() {

        return "redirect:/simparty/categories/index";
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/partyroom")
    public void view_partyroom() {

    }

    @PreAuthorize("permitAll()")
    @GetMapping("/celebrity")
    public void view_celebrity() {

    }

    @PreAuthorize("permitAll()")
    @GetMapping("/charge")
    public void view_charge() {

    }
}
