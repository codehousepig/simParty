package codehouse.simparty.controller;

import codehouse.simparty.dto.NoticeDTO;
import codehouse.simparty.dto.PageRequestDTO;
import codehouse.simparty.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.weaver.ast.Not;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequestMapping("/simparty/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeservice; // final

    @PreAuthorize("permitAll()")
    @GetMapping("")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("=====NoticeController=====LIST=====");
        model.addAttribute("result", noticeservice.getList(pageRequestDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/register")
    public void register() {
        log.info("=====NoticeController=====REGISTER=====");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public String regNotice(NoticeDTO noticeDTO, RedirectAttributes redirectAttributes) {
        log.info("=====NoticeController=====REG_NOTICE=====");
        Long nno = noticeservice.register(noticeDTO);
        redirectAttributes.addFlashAttribute("msg", nno);
        return "redirect:/simparty/notice";
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/read")
    public void read(Long nno, PageRequestDTO requestDTO, Model model) {
        log.info("=====NoticeController=====READ=====");
        System.out.println("nno = " + nno);

        NoticeDTO noticeDTO = noticeservice.read(nno);
        model.addAttribute("requestDTO", requestDTO);
        model.addAttribute("dto", noticeDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/modify")
    public void modify(Long nno, PageRequestDTO requestDTO, Model model) {
        log.info("=====NoticeController=====READ=====");
        System.out.println("nno = " + nno);

        NoticeDTO noticeDTO = noticeservice.read(nno);
        model.addAttribute("requestDTO", requestDTO);
        model.addAttribute("dto", noticeDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/modify")
    public String modify(NoticeDTO noticeDTO, PageRequestDTO requestDTO, Model model, RedirectAttributes redirectAttributes) {
        log.info("=====NoticeController=====POST=====MODIFY=====");

        noticeservice.modify(noticeDTO);

        model.addAttribute("requestDTO", requestDTO);
        redirectAttributes.addAttribute("nno", noticeDTO.getNno());
        redirectAttributes.addAttribute("page", requestDTO.getPage());

        return "redirect:/simparty/notice/read";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/remove")
    public String remove(Long nno, RedirectAttributes redirectAttributes) {
        log.info("=====NoticeController=====REMOVE=====");

        noticeservice.remove(nno);
        redirectAttributes.addFlashAttribute("msg", nno);

        return "redirect:/simparty/notice";
    }
}
