package com.wicc.brs.controller.category;

import com.wicc.brs.dto.CategoryDto;
import com.wicc.brs.service.category.CategoryServiceImp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryServiceImp categoryService;

    public CategoryController(CategoryServiceImp categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/home")
    public String getHome(Model model) {
        if(model.getAttribute("categoryDto") == null){
            model.addAttribute("categoryDto", new CategoryDto());
        }
        model.addAttribute("data",categoryService.findAll());
        return "/category/category";
    }
    @PostMapping("/create")
    public String create(@ModelAttribute CategoryDto categoryDto, RedirectAttributes redirectAttributes) {
        try {
            categoryDto= categoryService.save(categoryDto);
            redirectAttributes.addFlashAttribute("message", "Done");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Failed.");
            return "redirect:/category/home";
        }
        return "redirect:/category/home";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer integer, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteBYId(integer);
            redirectAttributes.addFlashAttribute("message", "Category Deleted");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("message", "Category Can't be Deleted");
        }
        return "redirect:/category/home";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("categoryDto",categoryService.findById(id));
        return "redirect:/category/home";
    }


}
