package com.wicc.brs.controller.member;

import com.wicc.brs.dto.MemberDto;
import com.wicc.brs.service.member.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/home")
    public String homeMember(Model model){
        model.addAttribute("memberDto",new MemberDto());
        model.addAttribute("data",memberService.findAll());
        return "/member/member";
    }

    @PostMapping("/create")
    public String createMember(@ModelAttribute MemberDto memberDto, RedirectAttributes redirectAttributes){
        try{
            memberDto = memberService.save(memberDto);
        }
        catch (Exception e){
            redirectAttributes.addFlashAttribute("message","Member creation failed");
            return "redirect:/member/home";
        }
        if(memberDto!=null){
            redirectAttributes.addFlashAttribute("message","Member created");
        }
        return "redirect:/member/home";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer integer, RedirectAttributes redirectAttributes) {
        try {
            memberService.deleteBYId(integer);
            redirectAttributes.addFlashAttribute("message", "Member Deleted");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("message", "Member Can't be Deleted");
        }
        return "redirect:/member/home";
    }
}
