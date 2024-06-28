package com.member.member.controller;


import com.member.member.dto.MemberDTO;
import com.member.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/member/list")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList" , memberDTOList);
        return "list";

    }
    @GetMapping("/member/{id}")
    public String findById(@PathVariable Long id , Model model){
        MemberDTO  memberDTO = memberService.findById(id);
        model.addAttribute("member" ,memberDTO);
        return "detail";



    }
    @GetMapping("/member/update")
     public String updateForm(HttpSession session, Model model){
        String myEmail  =(String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember" , memberDTO);
        return "update";
    }
    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        return "redirect:/member/"+memberDTO.getId();
    }
    @GetMapping("/member/delete/{id}")
    public String delete(@PathVariable Long id){
        memberService.deleteById(id);
        return  "redirect:/member/list";
    }
    @GetMapping("/member/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }
    @PostMapping("/member/email-check")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail")String memberEmail){
        System.out.println("memberEmail =" + memberEmail);
        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult;
//        if (checkResult != null){
//            return "ok";
//        }else {
//            return "no";
//        }

    }

}
