package com.wicc.brs.service.book;

import com.wicc.brs.component.FileComponent;
import com.wicc.brs.dto.BookDto;
import com.wicc.brs.dto.ResponseDto;
import com.wicc.brs.entity.Book;
import com.wicc.brs.repo.BookRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepo bookRepo;
    private final FileComponent fileComponent;

    public BookServiceImpl(BookRepo bookRepo, FileComponent fileComponent) {
        this.bookRepo = bookRepo;
        this.fileComponent = fileComponent;
    }

    //saving book
    @Override
    public BookDto save(BookDto bookDto) throws ParseException, IOException {
        Book book;
        //if bookDto.getId() does not equal to 0 or null means book exists and data of book to be updated
        if (bookDto.getId() != null && bookDto.getId() != 0) {
            //if id is not valid throw exception
            book = bookRepo.findById(bookDto.getId()).orElseThrow(
                    () -> new RuntimeException("Invalid Book")
            );
            book = Book.builder()
                    .id(bookDto.getId())
                    .name(bookDto.getName())
                    .isbn(bookDto.getIsbn())
                    .noOfPage(bookDto.getNoOfPage())
                    .rating(bookDto.getRating())
                    .totalStock(bookDto.getTotalStock())
                    .remainingStock(bookDto.getRemainingBook())
                    .publishedDate((new SimpleDateFormat("yyyy-MM-dd").parse(bookDto.getPublishedDate())))
                    .filePath(book.getFilePath())
                    .authors(bookDto.getAuthors())
                    .category(bookDto.getCategory())
                    .build();
            //update book
            book = bookRepo.save(book);
            return BookDto.builder().id((book.getId())).build();

            //save new book
        } else {
            //saving file to pc location
            ResponseDto responseDto = fileComponent.storeFile(bookDto.getMultipartFile());
            //if image is converted->
            if (responseDto.isStatus()) {
                Book book1 = Book.builder()
                        .id(bookDto.getId())
                        .name(bookDto.getName())
                        .isbn(bookDto.getIsbn())
                        .noOfPage(bookDto.getNoOfPage())
                        .rating(bookDto.getRating())
                        .totalStock(bookDto.getTotalStock())
                        .remainingStock(bookDto.getTotalStock())
                        .publishedDate(new SimpleDateFormat("yyyy-MM-dd").parse(bookDto.getPublishedDate()))
                        .filePath(responseDto.getMessage())
                        .authors(bookDto.getAuthors())
                        .category(bookDto.getCategory())
                        .build();
                //save
                book = bookRepo.save(book1);
                return BookDto.builder().id((book.getId())).build();

            }
            //if image did not converted to base64
            log.error(responseDto.getMessage());
            return null;
        }

    }


    //finding all the books
    @Override
    public List<BookDto> findAll() {
        return bookRepo.findAll().stream().map(book -> {
            try {
                return BookDto.builder()
                        .id(book.getId())
                        .name(book.getName())
                        .isbn(book.getIsbn())
                        .noOfPage(book.getNoOfPage())
                        .totalStock(book.getTotalStock())
                        .rating(book.getRating())
                        .remainingBook(book.getRemainingStock())
                        .publishedDate(new SimpleDateFormat("yyyy-MM-dd").format(book.getPublishedDate()))
                        //converting file to base64 code
                        .filePath(fileComponent.base64Encoded(book.getFilePath()))
                        .category(book.getCategory())
                        .authors(book.getAuthors())
                        .build();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }).collect(Collectors.toList());
    }

    //finding all books by id
    @Override
    public BookDto findById(Integer integer) throws IOException {
        Optional<Book> byId = bookRepo.findById(integer);
        Book book;
        if (byId.isPresent()) {
            book = byId.get();
            return BookDto.builder()
                    .id(book.getId())
                    .name(book.getName())
                    .isbn(book.getIsbn())
                    .noOfPage(book.getNoOfPage())
                    .totalStock(book.getTotalStock())
                    .rating(book.getRating())
                    .remainingBook(book.getRemainingStock())
                    .publishedDate(new SimpleDateFormat("yyyy-MM-dd").format(book.getPublishedDate()))
                    .filePath(fileComponent.base64Encoded(book.getFilePath()))
                    .category(book.getCategory())
                    .authors(book.getAuthors())
                    .build();

        }
        return null;
    }
    //delete book by id
    @Override
    public void deleteBYId(Integer integer) {
        bookRepo.deleteById(integer);
    }

    //update book
    @Override
    public BookDto update(BookDto bookDto) throws IOException, ParseException {
        ResponseDto responseDto = fileComponent.storeFile(bookDto.getMultipartFile());
        if (responseDto.isStatus()) {
            Book book = Book.builder()
                    .id(bookDto.getId())
                    .name(bookDto.getName())
                    .isbn(bookDto.getIsbn())
                    .noOfPage(bookDto.getNoOfPage())
                    .rating(bookDto.getRating())
                    .totalStock(bookDto.getTotalStock())
                    .publishedDate(new SimpleDateFormat("yyyy-MM-dd").parse(bookDto.getPublishedDate()))
                    .filePath(responseDto.getMessage())
                    .authors(bookDto.getAuthors())
                    .category(bookDto.getCategory())
                    .build();
            book = bookRepo.save(book);
            return BookDto.builder().id((book.getId())).build();
        }
        log.info(responseDto.getMessage());
        return null;
    }
}
