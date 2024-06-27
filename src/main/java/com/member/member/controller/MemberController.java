package com.member.member.controller;

import com.member.member.dto.MemberDTO;
import com.member.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/member/save")
    public String saveForm(){
        return "save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("memberDTO =" + memberDTO);
        memberService.save(memberDTO);
           return "login";
    }
    @GetMapping("/member/login")
    public  String loginForm(){
        return "login";

    }

    @PostMapping("/member/login")
        public String login(@ModelAttribute MemberDTO memberDTO , HttpSession session){
               MemberDTO loginresult  =memberService.login(memberDTO);
               if (loginresult != null){
                   //로그인성공
                   session.setAttribute("loginEmail" , loginresult.getMemberEmail());
                   return "main";
               }else {
                   //login 실패
                   return "login";
               }

        }

}
