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
public class AuthorServiceImp implements AuthorService {

    private final AuthorRepo authorRepo;
    private final EmailComponent emailComponent;

    public AuthorServiceImp(AuthorRepo authorRepo, EmailComponent emailComponent) {
        this.authorRepo = authorRepo;
        this.emailComponent = emailComponent;
    }

    //save and update author
    @Override
    public AuthorDto save(AuthorDto authorDto) {
        //if authorDto.getId() == null author data is new
        boolean status = authorDto.getId() == null;
        Author author = Author.builder()
                .id(authorDto.getId())
                .email(authorDto.getEmail())
                .name(authorDto.getName())
                .contact(authorDto.getContact())
                .build();
        author = authorRepo.save(author);
        //if status is true author is new
        if (status) {
            emailComponent.send(author.getEmail(),
                    "Author Registration",
                    "You are added as a author in Book Rental System");
        }
        //if status is false author wants to update his/her data
        else {
            emailComponent.send(author.getEmail(),
                    "Update Author",
                    "Details of author has been updated");

        }
        return AuthorDto.builder().id(author.getId()).build();

    }

    //finding all the authors
    @Override
    public List<AuthorDto> findAll() {
        return authorRepo.findAll().stream().map(author -> AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .contact(author.getContact())
                .build()).collect(Collectors.toList());
    }

    //finding author by id
    @Override
    public AuthorDto findById(Integer integer) {
        Optional<Author> byId = authorRepo.findById(integer);
        Author author;
        //find by id return optional so, if  value is present getting it
        if (byId.isPresent()) {
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

    //delete author by id
    @Override
    public void deleteBYId(Integer integer) {
        authorRepo.deleteById(integer);
    }
}
