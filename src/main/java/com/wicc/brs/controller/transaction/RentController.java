package com.wicc.brs.controller.transaction;

import com.wicc.brs.dto.RentDto;
import com.wicc.brs.entity.Transaction;
import com.wicc.brs.enums.RentStatus;
import com.wicc.brs.service.book.BookService;
import com.wicc.brs.service.member.MemberService;
import com.wicc.brs.service.transaction.TransactionImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/rent")
public class RentController {

    private final BookService bookService;
    private final MemberService memberService;
    private final TransactionImpl transactionImpl;

    public RentController(BookService bookService, MemberService memberService, TransactionImpl transactionImpl) {
        this.bookService = bookService;
        this.memberService = memberService;
        this.transactionImpl = transactionImpl;
    }

    //sending required data to rent page
    @GetMapping("/home")
    public String getHome(Model model) {
        model.addAttribute("rentDto", new RentDto());
        model.addAttribute("memberData", memberService.findAll());
        model.addAttribute("bookData", bookService.findAll());
        List<Transaction> all = transactionImpl.findAll();
        //data of rented bok
        List<Transaction> collect = all.stream().filter(transaction -> transaction.getRentStatus() == RentStatus.RENT)
                .collect(Collectors.toList());
        model.addAttribute("rentData",collect);
        return "/transaction/rent";
    }

    //rent book
    @PostMapping("/create")
    public String create(@ModelAttribute RentDto rentDto, RedirectAttributes redirectAttributes)
            throws ParseException {
        try {
            //saving transaction to database
            rentDto = transactionImpl.rentBook(rentDto);
            //if data is not saved
            if(rentDto ==null){
                redirectAttributes.addFlashAttribute("status",
                        "Failed!! Out of stock");
            }

            //if data is saved
            else if(rentDto.getId() != null){
                redirectAttributes.addFlashAttribute("status",
                        "Done");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("status",
                    "Failed!!");
        }
        return "redirect:/rent/home";
    }
}
