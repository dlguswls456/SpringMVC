package com.example.hello_servlet.springmvc.v3;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hello_servlet.model.Member;
import com.example.hello_servlet.repository.MemberRepository;

/*
 * HTTP 메서드 용도에 맞도록 Http 메서드로 구분
 * ModelAndView 없이 & @RequestParam 어노테이션으로 변수에 바로 주입
 */

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {
    
    private MemberRepository memberRepository = MemberRepository.getInstance();
    
    @GetMapping("/new-form")
    public String newForm() {
        return "new-form";
    }
    
    @PostMapping("/save")
    public String save(
              @RequestParam("userName") String userName, 
              @RequestParam("age") int age,
              Model model) {
        
        Member member = new Member(userName, age);
        memberRepository.save(member);
        
        model.addAttribute("member", member);
        return "save-result";
    }
}
