//package com.wicc.brs.controller.transaction;
//import com.wicc.brs.dto.TransactionDto;
//import com.wicc.brs.service.book.BookService;
//import com.wicc.brs.service.member.MemberService;
//import com.wicc.brs.service.transaction.TransactionImpl;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import java.text.ParseException;
//
//
//@Controller
//@RequestMapping("/rent")
//public class RentController {
//
//    private final BookService bookService;
//    private final MemberService memberService;
//    private final TransactionImpl transactionImpl;
//
//    public RentController(BookService bookService, MemberService memberService,TransactionImpl transactionImpl) {
//        this.bookService = bookService;
//        this.memberService = memberService;
//        this.transactionImpl = transactionImpl;
//    }
//
//    @GetMapping("/home")
//    public String getHome(Model model){
//        model.addAttribute("transactionDto",new TransactionDto());
//        model.addAttribute("memberData",memberService.findAll());
//        model.addAttribute("bookData",bookService.findAll());
//        return "/transaction/rent";
//    }
//
//    @PostMapping("/rent")
//    public String create(@ModelAttribute TransactionDto transactionDto) throws ParseException {
//        transactionImpl.rentBook(transactionDto);
//        return "redirect:/rent/home";
//    }
//}
