package com.wicc.brs.controller.member;

import com.wicc.brs.dto.MemberDto;
import com.wicc.brs.service.member.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //sending required data to home page of member
    @GetMapping("/home")
    public String homeMember(Model model) {
        if (model.getAttribute("memberDto") == null) {
            model.addAttribute("memberDto", new MemberDto());
        }
        model.addAttribute("data", memberService.findAll());
        return "/member/member";
    }

    //creating new member
    @PostMapping("/create")
    public String createMember(@Valid @ModelAttribute MemberDto memberDto, BindingResult bindingResult,
                               Model model) {
        //if all input is correct save member to database
        if (!bindingResult.hasErrors()) {
            try {
                memberDto = memberService.save(memberDto);
                model.addAttribute("message", "Done");
            } catch (Exception e) {
                model.addAttribute("message", "Failed");
            }
        }
        model.addAttribute("memberDto", memberDto);
        model.addAttribute("data", memberService.findAll());
        return "/member/member";

    }

    //delte member by id
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer integer, RedirectAttributes redirectAttributes) {
        try {
            memberService.deleteBYId(integer);
            redirectAttributes.addFlashAttribute("message", "Member Deleted");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Member Can't be Deleted");
        }
        return "redirect:/member/home";
    }

    //update member
    @GetMapping("/update/{mid}")
    public String update(@PathVariable Integer mid, RedirectAttributes redirectAttributes) throws IOException {
        redirectAttributes.addFlashAttribute("memberDto", memberService.findById(mid));
        return "redirect:/member/home";
    }
}
