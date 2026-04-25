package org.example.racekatteklubben.controller;

import jakarta.servlet.http.HttpSession;
import org.example.racekatteklubben.models.Member;
import org.example.racekatteklubben.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final MemberService memberService;

    public HomeController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String showHomePage(HttpSession session, Model model) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        Member member = memberService.findMemberById(memberId);
        model.addAttribute("member", member);
        return "pages/home";
    }
}
