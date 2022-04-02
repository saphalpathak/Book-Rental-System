package com.wicc.brs.controller.author;
import com.wicc.brs.dto.AuthorDto;
import com.wicc.brs.service.author.AuthorServiceImp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/author")
public class AuthorController {

    private final AuthorServiceImp authorServiceImp;

    public AuthorController(AuthorServiceImp authorServiceImp) {
        this.authorServiceImp = authorServiceImp;
    }


    //sending data to home page
    @GetMapping("/home")
    public String homeAuthor(Model model){
        if(model.getAttribute("authorDto") ==null) {
            model.addAttribute("authorDto", new AuthorDto());
        }
        model.addAttribute("data",authorServiceImp.findAll());
        return "/author/author";
    }

    //create new author
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("authorDto") AuthorDto authorDto, BindingResult bindingResult,
                         Model model){
        //if binding results has no error, save the author
        if(!bindingResult.hasErrors()) {
            try {
                authorDto = authorServiceImp.save(authorDto);
                model.addAttribute("message", "Done");
            } catch (Exception e) {
                model.addAttribute("message", "Failed");
            }
        }
        model.addAttribute("authorDto",authorDto);
        model.addAttribute("data",authorServiceImp.findAll());
        return "/author/author";
    }

    //delete user by id
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer integer,RedirectAttributes redirectAttributes) {
        try {
            authorServiceImp.deleteBYId(integer);
            redirectAttributes.addFlashAttribute("message", "Author Deleted");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("message", "Author Can't be Deleted");
        }
        return "redirect:/author/home";
    }

    //update the user
    @GetMapping("/update/{id}")
    public String update(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("authorDto",authorServiceImp.findById(id));
        return "redirect:/author/home";
    }

}
