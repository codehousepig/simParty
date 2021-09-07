package codehouse.simparty.controller;

import codehouse.simparty.dto.ClothesDTO;
import codehouse.simparty.dto.NoticeDTO;
import codehouse.simparty.dto.PageRequestDTO;
import codehouse.simparty.service.ClothesService;
import codehouse.simparty.service.ClothesServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/simparty/categories")
@Log4j2
@RequiredArgsConstructor
public class ClothesController {

    private final ClothesService clothesservice;

    @GetMapping("/everything")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("CCont_list_PageRequestDTO: " + pageRequestDTO);
        model.addAttribute("result", clothesservice.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {

    }

    @PostMapping("/register")
    public String register(ClothesDTO clothesDTO, RedirectAttributes redirectAttributes) {
        log.info("=============REGISTER=============");
        System.out.println("ClothesDTO = " + clothesDTO);

        Long cno = clothesservice.register(clothesDTO);

        redirectAttributes.addFlashAttribute("msg", cno);

        return "redirect:/simparty/categories/everything";
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long cno, PageRequestDTO requestDTO, Model model) {
        log.info("=====ClothesController=====READ=====");
        System.out.println("cno = " + cno);

        ClothesDTO clothesDTO = clothesservice.read(cno);
        model.addAttribute("requestDTO", requestDTO);
        model.addAttribute("dto", clothesDTO);
    }

    @PostMapping("/modify")
    public String modify(ClothesDTO clothesDTO, PageRequestDTO requestDTO, Model model, RedirectAttributes redirectAttributes) {
        log.info("=====ClothesController=====MODIFY=====");

        clothesservice.modify(clothesDTO);

        model.addAttribute("requestDTO", requestDTO);
        redirectAttributes.addAttribute("cno", clothesDTO.getCno());
        redirectAttributes.addAttribute("page", requestDTO.getPage());

        return "redirect:/simparty/categories/read";
    }

    @PostMapping("/remove")
    public String remove(Long cno, RedirectAttributes redirectAttributes) {
        log.info("=====ClothesController=====REMOVE=====");

        clothesservice.remove(cno);
        redirectAttributes.addFlashAttribute("msg", cno);

        return "redirect:/simparty/categories/everything";
    }
}
