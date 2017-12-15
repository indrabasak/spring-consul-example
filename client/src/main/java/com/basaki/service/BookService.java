package com.basaki.service;

import com.basaki.data.entity.Book;
import com.basaki.data.repository.BookRepository;
import com.basaki.model.BookRequest;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@code BookService} provides CRUD functioanality on book.
 * <p/>
 *
 * @author Indra Basak
 * @since 12/7/17
 */
@Service
public class BookService {

    private BookRepository repository;

    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Book create(BookRequest request) {
        Book entity = new Book();
        entity.setTitle(request.getTitle());
        entity.setAuthor(request.getAuthor());

        return repository.save(entity);

    }

    public Book read(UUID id) {
        return repository.findOne(id);
    }
}
