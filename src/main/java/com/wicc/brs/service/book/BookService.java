package com.wicc.brs.service.book;

import com.wicc.brs.dto.BookDto;
import com.wicc.brs.service.GenericCrudService;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.text.ParseException;

public interface BookService extends GenericCrudService<BookDto,Integer> {

    BookDto update(BookDto bookDto) throws IOException, ParseException;
}
