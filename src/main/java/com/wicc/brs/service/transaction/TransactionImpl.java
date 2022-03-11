package com.wicc.brs.service.transaction;

import com.wicc.brs.dto.BookDto;
import com.wicc.brs.dto.RentDto;
import com.wicc.brs.dto.ReturnDto;
import com.wicc.brs.entity.Transaction;
import com.wicc.brs.enums.RentStatus;
import com.wicc.brs.repo.TransactionRepo;
import com.wicc.brs.service.book.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class TransactionImpl {
    private final TransactionRepo transactionRepo;
    private final BookService bookService;

    public TransactionImpl(TransactionRepo transactionRepo, BookService bookService) {
        this.transactionRepo = transactionRepo;
        this.bookService = bookService;
    }

    public RentDto rentBook(RentDto rentDto) throws ParseException, IOException {
        BookDto byId = bookService.findById(rentDto.getBook().getId());
        Integer stock = byId.getRemainingBook();
        if (stock > 0) {
            LocalDate localDate = LocalDate.now().plusDays(rentDto.getNoOfDays());
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            Transaction transaction = Transaction.builder()
                    .book(rentDto.getBook())
                    .code(rentDto.getCode())
                    .member(rentDto.getMember())
                    .fromDate(new SimpleDateFormat("yyyy-MM-dd")
                            .parse(new SimpleDateFormat("yyyy-MM-dd")
                                    .format(new Date())))
                    .toDate((new SimpleDateFormat("yyyy-MM-dd")
                            .parse(new SimpleDateFormat("yyyy-MM-dd")
                                    .format(date))))
                    .rentStatus(RentStatus.RENT)
                    .build();
            transaction = transactionRepo.save(transaction);

            byId.setRemainingBook(--stock);

            bookService.save(byId);
            return RentDto.builder().id(transaction.getId()).build();
        }
        return null;
    }

    public RentDto returnBook(ReturnDto returnDto) throws ParseException, IOException {
        List<Transaction> all = transactionRepo.findAll();
        Transaction transaction1 = null;
        for (Transaction transaction : all) {
            if (Objects.equals(transaction.getCode(), returnDto.getCode()) &&
                    Objects.equals(transaction.getMember().getMid(), returnDto.getMember().getMid()) &&
                    transaction.getRentStatus() == RentStatus.RENT) {
                transaction1 = transaction;
                break;
            }
        }
        if (transaction1 == null) {
            return RentDto.builder().code("Book is not rented!!").build();
        }
        BookDto byId = bookService.findById(transaction1.getBook().getId());
        Integer remainingBook = byId.getRemainingBook();
        if (remainingBook < byId.getTotalStock()) {
            transaction1.setRentStatus(RentStatus.RETURN);
            transaction1.setDateOfReturn((new SimpleDateFormat("yyyy-MM-dd")
                    .parse(new SimpleDateFormat("yyyy-MM-dd")
                            .format(new Date()))));
            byId.setRemainingBook(++remainingBook);
            bookService.save(byId);
            return RentDto.builder().code("Returned Successfully").build();

        }
        return RentDto.builder().code("Book Already Returned").build();

    }

    public List<Transaction> findAll() {
        return transactionRepo.findAll();
    }
}
