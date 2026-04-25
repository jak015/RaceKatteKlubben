package org.example.racekatteklubben.controller;

import jakarta.servlet.http.HttpSession;
import org.example.racekatteklubben.models.Cat;
import org.example.racekatteklubben.models.Member;
import org.example.racekatteklubben.models.enums.Gender;
import org.example.racekatteklubben.models.enums.Race;
import org.example.racekatteklubben.models.enums.YearOrMonth;
import org.example.racekatteklubben.service.CatService;
import org.example.racekatteklubben.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class CatController {

    private final CatService catService;
    private final MemberService memberService;

    public CatController(CatService catService, MemberService memberService) {
        this.catService = catService;
        this.memberService = memberService;
    }

    @GetMapping("/profile/cats")
    public String showCats(HttpSession session, Model model) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        Member member = memberService.findMemberById(memberId);
        model.addAttribute("member", member);
        model.addAttribute("cats", catService.findCatsByMemberId(memberId));
        return "pages/cat/catList";
    }

    @GetMapping("/profile/cats/create")
    public String showCreateCatForm(@ModelAttribute Cat cat, HttpSession session, Model model) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        Member member = memberService.findMemberById(memberId);

        model.addAttribute("member", member);
        model.addAttribute("cat", new Cat());
        model.addAttribute("races", Race.values());
        model.addAttribute("yearOrMonths", YearOrMonth.values());
        model.addAttribute("genders", Gender.values());
        return "pages/cat/createCat";
    }

    @PostMapping("/profile/cats/create")
    public String createCat(@ModelAttribute Cat cat, HttpSession session, Model model, @RequestParam MultipartFile file ) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        Member member = memberService.findMemberById(memberId);

        model.addAttribute("member", member);
        model.addAttribute("cat", cat);

        try {
            catService.addCat(cat.getImages(), cat.getCatName(), cat.getRace(), cat.getAge(), cat.getYearOrMonth(), cat.getGender(), member.getId(), file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/profile/cats";
    }

    @GetMapping("profile/cats/update/{catId}")
        public String ShowUpdateCatForm(HttpSession session, Model model, @PathVariable int catId) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        Member member = memberService.findMemberById(memberId);

        Cat cat = catService.findCatById(catId);

        model.addAttribute("member", member);
        model.addAttribute("cat", cat);
        model.addAttribute("races", Race.values());
        model.addAttribute("yearOrMonths", YearOrMonth.values());
        model.addAttribute("genders", Gender.values());

        return "pages/cat/updateCat";
    }

    @PostMapping("profile/cats/update/{catId}")
    public String updateCat(HttpSession session, Model model, @ModelAttribute Cat cat, @PathVariable int catId) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        Member member = memberService.findMemberById(memberId);

        model.addAttribute("member", member);
        model.addAttribute("cat", cat);


        catService.updateCat(cat.getId(),cat.getImages(),cat.getCatName(),cat.getRace(),cat.getAge(), cat.getYearOrMonth(), cat.getGender(), member.getId());

        return "redirect:/profile/cats";
    }

    @PostMapping("/profile/cats/delete/{catId}")
    public String deleteCat(Model model, HttpSession session,  @PathVariable int catId) throws IOException {
        Integer memberId = (Integer) session.getAttribute("memberId");
        Member member = memberService.findMemberById(memberId);

        model.addAttribute("member", member);
        catService.removeCat(catId);
        return "redirect:/profile/cats";
    }

    @PostMapping("/cats/search")
    public String searchForCat(HttpSession session, Model model, @ModelAttribute Cat cat, @RequestParam String keyword) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        Member member = memberService.findMemberById(memberId);

        model.addAttribute("member", member);
        model.addAttribute("cats", catService.seachForCat(keyword));
        model.addAttribute("cat", cat);
        return "pages/home";
    }
}