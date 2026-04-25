package org.example.racekatteklubben.controller;

import jakarta.servlet.http.HttpSession;
import org.example.racekatteklubben.models.Member;
import org.example.racekatteklubben.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final MemberService memberService;

    public AuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/register")
    public String showSignupForm(Model model) {
        model.addAttribute("member", new Member());
        return "pages/auth/signup";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Member member) {
        memberService.registerMember(member.getMemberName(), member.getEmail(), member.getPassword(), false);
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("member", new Member());
        return "pages/auth/login";
    }

    @PostMapping("/login")
    public String login(HttpSession session, Model model, @ModelAttribute Member member) {
        Member loggedIn = memberService.loginMember(member.getEmail(), member.getPassword());

        if (loggedIn != null) {
            session.setAttribute("memberId", loggedIn.getId());
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "pages/auth/login";
        }
    }

    @GetMapping("/access-denied")
    public String showAccessDeniedPage(Model model) {
        return "/pages/auth/access_denied";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}
