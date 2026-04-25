package org.example.racekatteklubben.controller;

import jakarta.servlet.http.HttpSession;
import org.example.racekatteklubben.models.Member;
import org.example.racekatteklubben.service.CatService;
import org.example.racekatteklubben.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
public class MemberController {
    private final CatService catService;
    private final MemberService memberService;

    public MemberController(CatService catService, MemberService memberService) {
        this.catService = catService;
        this.memberService = memberService;
    }

    @PostMapping("/search")
    public String searchForMembers(HttpSession session, Model model, @RequestParam String keyword) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        Member member = memberService.findMemberById(memberId);

        if (member == null) {
            return "redirect:/login";
        }

        model.addAttribute("member", member);
        model.addAttribute("members", memberService.searchForMember(keyword));
        return "pages/home";
    }

    @PostMapping("/delete/{id}")
    public String deleteMember(HttpSession session, @PathVariable int id) {
        Integer memberId = (Integer) session.getAttribute("memberId");

        catService.removeCatByMemberId(id);
        memberService.removeMember(id);

        if (memberId == id) {
            session.invalidate();
            return "redirect:/login";
        }

        return "redirect:/profile";
    }
}
