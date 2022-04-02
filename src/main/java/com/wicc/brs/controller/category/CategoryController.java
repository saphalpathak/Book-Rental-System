package com.wicc.brs.controller.category;

import com.wicc.brs.dto.CategoryDto;
import com.wicc.brs.service.category.CategoryServiceImp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryServiceImp categoryService;

    public CategoryController(CategoryServiceImp categoryService) {
        this.categoryService = categoryService;
    }


    //sending data to home page of category
    @GetMapping("/home")
    public String getHome(Model model) {
        if (model.getAttribute("categoryDto") == null) {
            model.addAttribute("categoryDto", new CategoryDto());
        }
        model.addAttribute("data", categoryService.findAll());
        return "/category/category";
    }

    //creating new category
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute CategoryDto categoryDto, BindingResult bindingResult,
                         Model model) {
        //if input is correct save category
        if (!bindingResult.hasErrors()) {
            try {
                categoryDto = categoryService.save(categoryDto);
                model.addAttribute("message", "Done");
            } catch (Exception e) {
                model.addAttribute("message", "Failed.");
            }
        }
        model.addAttribute("categoryDto", categoryDto);
        model.addAttribute("data", categoryService.findAll());
        return "/category/category";
    }

    //delete category by id
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer integer, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteBYId(integer);
            redirectAttributes.addFlashAttribute("message", "Category Deleted");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Category Can't be Deleted");
        }
        return "redirect:/category/home";
    }

    //update category by id
    @GetMapping("/update/{id}")
    public String update(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("categoryDto", categoryService.findById(id));
        return "redirect:/category/home";
    }


}
