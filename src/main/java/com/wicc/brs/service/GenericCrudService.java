package com.wicc.brs.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface GenericCrudService<D,ID> {
    D save(D d) throws ParseException, IOException;
    List<D> findAll();
    D findById(ID id) throws IOException;
    void deleteBYId(ID id);
}
