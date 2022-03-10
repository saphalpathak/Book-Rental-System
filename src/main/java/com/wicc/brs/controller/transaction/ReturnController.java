package com.wicc.brs.controller.transaction;

import com.wicc.brs.dto.RentDto;
import com.wicc.brs.dto.ReturnDto;
import com.wicc.brs.entity.Transaction;
import com.wicc.brs.enums.RentStatus;
import com.wicc.brs.service.book.BookService;
import com.wicc.brs.service.member.MemberService;
import com.wicc.brs.service.transaction.TransactionImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/return")
public class ReturnController {

    private final BookService bookService;
    private final MemberService memberService;
    private final TransactionImpl transaction;

    public ReturnController(BookService bookService, MemberService memberService, TransactionImpl transaction) {
        this.bookService = bookService;
        this.memberService = memberService;
        this.transaction = transaction;
    }

    @GetMapping("/home")
    public String getHome(Model model){
        model.addAttribute("returnDto", new ReturnDto());
        model.addAttribute("memberData",memberService.findAll());
        Set<String> code = new HashSet<>();
        List<Transaction> all = transaction.findAll();
        for(Transaction transaction : all){
            code.add(transaction.getCode());
        }
        List<Transaction> collect = all.stream()
                .filter(transaction1 -> transaction1.getRentStatus() == RentStatus.RETURN).collect(Collectors.toList());
        model.addAttribute("codes",code);
        model.addAttribute("returnData",collect);
        return "transaction/return";
    }

    @PostMapping("/create")
    public String returnBook(ReturnDto returnDto, RedirectAttributes redirectAttributes){
        try{
            RentDto rentDto = transaction.returnBook(returnDto);
            redirectAttributes.addFlashAttribute("message",rentDto.getCode());
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message","Failed");
        }
        return "redirect:/return/home";
    }


}
