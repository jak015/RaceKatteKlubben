package org.example.racekatteklubben.controller;

import jakarta.servlet.http.HttpSession;
import org.example.racekatteklubben.dtos.UpdateProfileRequest;
import org.example.racekatteklubben.models.Member;
import org.example.racekatteklubben.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private final MemberService memberService;

    public ProfileController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String showProfile(HttpSession session, Model model) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        Member member = memberService.findMemberById(memberId);
        model.addAttribute("member", member);
        return "pages/member/profile";
    }

    @GetMapping("/update")
    public String ShowUpdateProfile(HttpSession session, Model model) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        Member member = memberService.findMemberById(memberId);
        model.addAttribute("member", member);
        return "pages/member/update";
    }

    @PostMapping("/update")
    public String updateProfile(HttpSession session, Model model, @RequestParam String confirmPassword, @ModelAttribute UpdateProfileRequest request) {
        Integer memberId = (Integer) session.getAttribute("memberId");

        if (!request.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "pages/member/update";
        }

        memberService.updateMember(memberId, request.getMemberName(), request.getEmail(),
                request.getOldPassword(), request.getPassword());
        return "redirect:/profile";
    }
}
