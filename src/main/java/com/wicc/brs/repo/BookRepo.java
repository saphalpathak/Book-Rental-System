package com.wicc.brs.repo;

import com.wicc.brs.dto.BookDto;
import com.wicc.brs.entity.Book;
import com.wicc.brs.service.GenericCrudService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book,Integer> {

}
