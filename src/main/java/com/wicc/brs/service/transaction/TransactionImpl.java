//package com.wicc.brs.service.transaction;
//
//import com.wicc.brs.component.BookComponent;
//import com.wicc.brs.dto.TransactionDto;
//import com.wicc.brs.entity.Transaction;
//import com.wicc.brs.enums.ActiveClosed;
//import com.wicc.brs.enums.RentStatus;
//import com.wicc.brs.repo.BookRepo;
//import com.wicc.brs.repo.TransactionRepo;
//import org.springframework.stereotype.Service;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.Date;
//@Service
//public class TransactionImpl{
//    private final TransactionRepo transactionRepo;
//    private static Integer remaining;
//    public TransactionImpl(TransactionRepo transactionRepo) {
//        this.transactionRepo = transactionRepo;
//    }
//
//
//    public Transaction rentBook(TransactionDto transactionDto) throws ParseException {
//
//        LocalDate localDate = LocalDate.now().plusDays(transactionDto.getNoOfDays());
//        Date date =Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//
//        Transaction transaction = Transaction.builder()
//                .book(transactionDto.getBook())
//                .code(transactionDto.getCode())
//                .member(transactionDto.getMember())
//                .fromDate(new SimpleDateFormat("dd-MM-yyyy")
//                        .parse(new SimpleDateFormat("dd-MM-yyyy")
//                                .format(new Date())))
//                .toDate((new SimpleDateFormat("dd-MM-yyyy")
//                        .parse(new SimpleDateFormat("dd-MM-yyyy")
//                                .format(date))))
//                .rentStatus(RentStatus.RENT)
//                .activeClosed(ActiveClosed.CLOSED)
//                .build();
//        return transaction = transactionRepo.save(transaction);
//    }
//    public String returnBook(){
//
//        return null;
//
//    }
//}
