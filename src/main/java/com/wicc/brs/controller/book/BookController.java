package com.wicc.brs.controller.book;
import com.wicc.brs.dto.BookDto;
import com.wicc.brs.service.author.AuthorService;
import com.wicc.brs.service.book.BookService;
import com.wicc.brs.service.category.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    private final CategoryService categoryService;
    private final AuthorService authorService;

    public BookController(BookService bookService, CategoryService categoryService, AuthorService authorService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.authorService = authorService;
    }

    @GetMapping("/home")
    public String getBook(Model model) {
        model.addAttribute("bookDto", new BookDto());
        model.addAttribute("data", bookService.findAll());
        model.addAttribute("categoryData", categoryService.findAll());
        model.addAttribute("authorData",authorService.findAll());
        return "/book/book";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute BookDto bookDto, RedirectAttributes redirectAttributes) {
        try {
            bookDto = bookService.save(bookDto);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Book creation failed");
            return "redirect:/book/home";
        }
        if (bookDto != null) {
            redirectAttributes.addFlashAttribute("message", "Book created successfully");
        }
        return "redirect:/book/home";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") Integer integer, Model model) throws IOException {
        model.addAttribute("bookData", bookService.findById(integer));
        model.addAttribute("categoryData", categoryService.findById(bookService.findById(integer).getCategory().getId()));
        model.addAttribute("authorData",bookService.findById(integer).getAuthors());
        return "/book/book_details";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer integer) {
        bookService.deleteBYId(integer);
        return "redirect:/book/home";
    }
    @GetMapping("/back")
    public String getHome(){
        return "redirect:/book/home";
    }
}
