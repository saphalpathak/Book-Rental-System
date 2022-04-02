package com.wicc.brs.controller.transaction;

import com.wicc.brs.service.transaction.TransactionImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transaction")
public class Log {
    private final TransactionImpl transaction;

    public Log(TransactionImpl transaction) {
        this.transaction = transaction;
    }

    // all transactions
    @GetMapping("/log")
    public String log(Model model){
        model.addAttribute("log",transaction.findAll());
        return "/transaction/log";
    }

}
