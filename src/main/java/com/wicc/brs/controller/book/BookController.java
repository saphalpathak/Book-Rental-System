package com.wicc.brs.controller.book;

import com.wicc.brs.dto.BookDto;
import com.wicc.brs.service.author.AuthorService;
import com.wicc.brs.service.book.BookService;
import com.wicc.brs.service.category.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
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

    //sending required data to home of book page
    @GetMapping("/home")
    public String getBook(Model model) {
        model.addAttribute("bookDto", new BookDto());
        model.addAttribute("data", bookService.findAll());
        model.addAttribute("categoryData", categoryService.findAll());
        model.addAttribute("authorData", authorService.findAll());
        return "/book/book";
    }

    //create a new book
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute BookDto bookDto, BindingResult bindingResult,
                         Model model) {
        if (!bindingResult.hasErrors()) {
            try {
                bookDto = bookService.save(bookDto);
                model.addAttribute("message", "Book created successfully");

            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("message", "Book creation failed");
            }
        }
        model.addAttribute("bookDto", bookDto);
        model.addAttribute("data", bookService.findAll());
        model.addAttribute("categoryData", categoryService.findAll());
        model.addAttribute("authorData", authorService.findAll());
        return "/book/book";
    }

    //find book by id
    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") Integer integer, Model model) throws IOException {
        model.addAttribute("bookData", bookService.findById(integer));
        model.addAttribute("categoryData", categoryService.findById(bookService.findById(integer).getCategory().getId()));
        model.addAttribute("authorData", bookService.findById(integer).getAuthors());
        return "/book/book_details";
    }

    //delete book by id
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) throws IOException {
        BookDto byId = bookService.findById(id);
        File file = new File(byId.getFilePath());
        if(file.exists()){
            file.delete();
        }
        bookService.deleteBYId(id);
        return "redirect:/book/home";
    }

    @GetMapping("/back")
    public String getHome() {
        return "redirect:/book/home";
    }


    //update user by id
    @GetMapping("/update/{id}")
    public String update(@PathVariable Integer id, Model model) throws IOException {
        model.addAttribute("bookDto", bookService.findById(id));
        model.addAttribute("categoryData", categoryService.findAll());
        model.addAttribute("authorData", authorService.findAll());
        return "book/book_update";
    }

    //getting the update data and save to database if data is correct
    @PostMapping("/update")
    public String update(@Valid @ModelAttribute BookDto bookDto, BindingResult bindingResult,
                         Model model) {
        if (!bindingResult.hasErrors()) {
            try {
                bookDto = bookService.update(bookDto);
                model.addAttribute("message", "Update successfully");

            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("message", "Update Failed");
            }
        }
        model.addAttribute("bookDto", new BookDto());
        model.addAttribute("data", bookService.findAll());
        model.addAttribute("categoryData", categoryService.findAll());
        model.addAttribute("authorData", authorService.findAll());
        return "/book/book";
    }
}
