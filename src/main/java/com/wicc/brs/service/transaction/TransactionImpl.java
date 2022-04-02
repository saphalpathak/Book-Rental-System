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

    //rent a book
    public RentDto rentBook(RentDto rentDto) throws ParseException, IOException {
        //finding required book by id
        BookDto byId = bookService.findById(rentDto.getBook().getId());
        Integer stock = byId.getRemainingBook();
        //checking the stock count of book
        //if stock is greater than 1 book can be rented
        if (stock > 0) {
            LocalDate localDate = LocalDate.now().plusDays(rentDto.getNoOfDays());
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            Transaction transaction = Transaction.builder()
                    .book(rentDto.getBook())
                    .code(rentDto.getCode())
                    .member(rentDto.getMember())
                    //book rented date
                    .fromDate(new SimpleDateFormat("yyyy-MM-dd")
                            .parse(new SimpleDateFormat("yyyy-MM-dd")
                                    .format(new Date())))
                    //due date
                    .toDate((new SimpleDateFormat("yyyy-MM-dd")
                            .parse(new SimpleDateFormat("yyyy-MM-dd")
                                    .format(date))))
                    //changing status of the book ko rented
                    .rentStatus(RentStatus.RENT)
                    .build();
            //saving the transaction
            transaction = transactionRepo.save(transaction);

            //subtract the stack count
            byId.setRemainingBook(--stock);

            bookService.save(byId);
            return RentDto.builder().id(transaction.getId()).build();
        }
        return null;
    }

    // return book
    public RentDto returnBook(ReturnDto returnDto) throws ParseException, IOException {
        //finding all the rented books
        List<Transaction> all = transactionRepo.findAll();
        // if transaction is null, book is not rented
        Transaction transaction1 = null;
        //finding all the rented books
        for (Transaction transaction : all) {
            //checking if current user rented this book not
            if (Objects.equals(transaction.getCode(), returnDto.getCode()) &&
                    Objects.equals(transaction.getMember().getMid(), returnDto.getMember().getMid()) &&
                    transaction.getRentStatus() == RentStatus.RENT) {
                transaction1 = transaction;
                break;
            }
        }
        if (transaction1 == null) {
            return RentDto.builder().code("Book is not rented by "+returnDto.getMember().getName()).build();
        }
        //user rented this book
        //finding this book by id
        BookDto byId = bookService.findById(transaction1.getBook().getId());
        Integer remainingBook = byId.getRemainingBook();
        //if stock is less than total user have to return this book
        if (remainingBook < byId.getTotalStock()) {
            //changing status of this book to returned
            transaction1.setRentStatus(RentStatus.RETURN);
            //returned date
            transaction1.setDateOfReturn((new SimpleDateFormat("yyyy-MM-dd")
                    .parse(new SimpleDateFormat("yyyy-MM-dd")
                            .format(new Date()))));
            //add count by if after return
            byId.setRemainingBook(++remainingBook);
            //save this book data
            bookService.save(byId);
            return RentDto.builder().code("Returned Successfully").build();

        }
        return RentDto.builder().code("Book Already Returned").build();

    }

    //finding all the transaction
    public List<Transaction> findAll() {
        return transactionRepo.findAll();
    }
}
