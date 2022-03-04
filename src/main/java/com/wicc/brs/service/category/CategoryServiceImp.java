package com.wicc.brs.service.category;


import com.wicc.brs.dto.CategoryDto;
import com.wicc.brs.entity.Category;
import com.wicc.brs.repo.CategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService{

    private final CategoryRepo categoryRepo;

    public CategoryServiceImp(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }


    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        Category category = Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .build();
        Category save = categoryRepo.save(category);
        return CategoryDto.builder().id((save.getId())).build();
    }

    @Override
    public List<CategoryDto> findAll() {
       return  categoryRepo.findAll().stream().map(category -> CategoryDto.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .description(category.getDescription())
                    .build()).collect(Collectors.toList());
    }

    @Override
    public CategoryDto findById(Integer integer) {
        Optional<Category> byId = categoryRepo.findById(integer);
        Category category;
        if(byId.isPresent()){
            category=byId.get();
            return CategoryDto.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .description(category.getDescription())
                    .build();

        }
        return null;
    }

    @Override
    public void deleteBYId(Integer integer) {
        categoryRepo.deleteById(integer);
    }
}
