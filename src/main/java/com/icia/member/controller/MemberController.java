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

    @GetMapping("/update")
    public String update(Model model, HttpSession session){
        String memberEmail =(String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.findByMemberEmail(memberEmail);
        model.addAttribute("member", memberDTO);
        return "/memberPage/memberUpdate";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        // 업데이트가 끝나면 로그아웃 처리를 하고, 로그인 페이지로
        memberService.update(memberDTO);
        session.removeAttribute("loginEmail");
        return "memberPage/memberLogin";
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id,@RequestBody MemberDTO memberDTO, HttpSession session){
        memberService.update(memberDTO);
        session.removeAttribute("loginEmail");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        memberService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
