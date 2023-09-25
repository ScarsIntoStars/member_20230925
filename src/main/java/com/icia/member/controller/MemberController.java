package com.icia.member.controller;

import com.icia.member.dto.MemberDTO;
import com.icia.member.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/save")
    public String save(){
        return "memberPage/memberSave";
    }
    @PostMapping("/save")
    public String save(MemberDTO memberDTO){
        System.out.println(memberDTO);
        memberService.save(memberDTO);
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "/memberPage/memberLogin";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        boolean loginResult = memberService.login(memberDTO);
        if(loginResult) {
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
            return "/memberPage/memberMain";
        } else {
            return "/memberPage/memberLogin";
        }
    }

    @GetMapping("/list")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "/memberPage/memberList";
    }
}
