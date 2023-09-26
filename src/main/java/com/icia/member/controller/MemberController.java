package com.icia.member.controller;

import com.icia.member.dto.MemberDTO;
import com.icia.member.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/save")
    public String save() {
        return "memberPage/memberSave";
    }

    @PostMapping("/save")
    public String save(MemberDTO memberDTO) {
        System.out.println(memberDTO);
        memberService.save(memberDTO);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "/memberPage/memberLogin";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        boolean loginResult = memberService.login(memberDTO);
        if (loginResult) {
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
            return "/memberPage/memberMain";
        } else {
            return "/memberPage/memberLogin";
        }
    }

    @GetMapping("/list")
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "/memberPage/memberList";
    }

    @GetMapping("/memberDetail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        System.out.println("넘어온 id는 : " + id);
        try {
            MemberDTO memberDTO = memberService.findById(id);
            model.addAttribute("member", memberDTO);
            return "/memberPage/memberDetail";
        } catch (NoSuchElementException e) {
            return "/memberPage/NotFound";
        } catch (Exception e) {
            return "/memberPage/NotFound";
        }
    }

    @PostMapping("/dup-check")
    public ResponseEntity emailCheck(@RequestBody MemberDTO memberDTO) {
        boolean result = memberService.emailCheck(memberDTO.getMemberEmail());
        if (result) {
            return new ResponseEntity<>("사용가능", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("사용불가능", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/axios/{id}")
    public ResponseEntity detailAxios(@PathVariable("id") Long id) {
        try {
            MemberDTO memberDTO = memberService.findById(id);
            return new ResponseEntity<>(memberDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
