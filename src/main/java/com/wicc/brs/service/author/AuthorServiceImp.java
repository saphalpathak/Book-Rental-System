package com.wicc.brs.service.author;

import com.wicc.brs.dto.AuthorDto;
import com.wicc.brs.entity.Author;
import com.wicc.brs.repo.AuthorRepo;
import com.wicc.brs.component.EmailComponent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImp implements AuthorService{

    private final AuthorRepo authorRepo;
    private final EmailComponent emailComponent;

    public AuthorServiceImp(AuthorRepo authorRepo, EmailComponent emailComponent) {
        this.authorRepo = authorRepo;
        this.emailComponent = emailComponent;
    }

    @Override
    public AuthorDto save(AuthorDto authorDto) {
        Author author = Author.builder()
                .id(authorDto.getId())
                .email(authorDto.getEmail())
                .name(authorDto.getName())
                .contact(authorDto.getContact())
                .build();
        author= authorRepo.save(author);
        emailComponent.send(author.getEmail(),
                "Added to Book Rental System as a author.",
                "You are added as a author in Book Rental System");
        return AuthorDto.builder().id(author.getId()).build();

    }

    @Override
    public List<AuthorDto> findAll() {
        return authorRepo.findAll().stream().map(author -> AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .contact(author.getContact())
                .build()).collect(Collectors.toList());
    }

    @Override
    public AuthorDto findById(Integer integer) {
        Optional<Author> byId = authorRepo.findById(integer);
        Author author;
        if(byId.isPresent()){
            author = byId.get();
            return AuthorDto.builder()
                    .id(author.getId())
                    .name(author.getName())
                    .email(author.getEmail())
                    .contact(author.getContact())
                    .build();

        }
        return null;
    }

    @Override
    public void deleteBYId(Integer integer) {
        authorRepo.deleteById(integer);
    }
}
