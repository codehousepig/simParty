package codehouse.simparty.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/simparty")
@Log4j2
@RequiredArgsConstructor
public class NavigationController {

    @GetMapping("")
    public String view_main() {

        return "redirect:/simparty/categories/everything";
    }

    @GetMapping("/partyroom")
    public void view_partyroom() {

    }

    @GetMapping("/celebrity")
    public void view_celebrity() {

    }

    @GetMapping("/charge")
    public void view_charge() {

    }
}
