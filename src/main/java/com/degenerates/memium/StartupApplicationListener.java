package com.degenerates.memium;

import com.degenerates.memium.facade.*;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.dao.Comment;
import com.degenerates.memium.model.enums.Category;
import com.degenerates.memium.model.enums.ERole;
import com.degenerates.memium.model.dao.Role;
import com.degenerates.memium.model.dto.SignupForm;
import com.degenerates.memium.repository.*;
import com.degenerates.memium.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class StartupApplicationListener implements
        ApplicationListener<ContextRefreshedEvent> {

    public static int counter;

    @Autowired
    AuthFacade authFacade;

    @Autowired
    AccountService accountService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    LikeListRepository likeListRepository;

    @Autowired
    BlackListRepository blackListRepository;

    @Autowired
    BlackListService blackListService;

    @Autowired
    AccountDetailsRepository accountDetailsRepository;

    void populate() {

        articleRepository.deleteAll();
        commentRepository.deleteAll();
        accountRepository.deleteAll();
        accountDetailsRepository.deleteAll();
        likeListRepository.deleteAll();
        blackListRepository.deleteAll();


        SignupForm signupForm1 = new SignupForm();
        signupForm1.setUsername("alex");
        signupForm1.setBio("populated");
        signupForm1.setDob(new Date());
        signupForm1.setGender("gender");
        signupForm1.setEmail("example");
        signupForm1.setPassword("password");

        SignupForm signupForm2 = new SignupForm();
        signupForm2.setUsername("zilber");
        signupForm2.setBio("populated");
        signupForm2.setDob(new Date());
        signupForm2.setGender("gender");
        signupForm2.setEmail("example");
        signupForm2.setPassword("password");

        SignupForm signupForm3 = new SignupForm();
        signupForm3.setUsername("bob");
        signupForm3.setBio("populated");
        signupForm3.setDob(new Date());
        signupForm3.setGender("gender");
        signupForm3.setEmail("example");
        signupForm3.setPassword("password");

        SignupForm signupForm4 = new SignupForm();
        signupForm4.setUsername("alan");
        signupForm4.setBio("populated");
        signupForm4.setDob(new Date());
        signupForm4.setGender("gender");
        signupForm4.setEmail("example");
        signupForm4.setPassword("password");

        SignupForm signupForm5 = new SignupForm();
        signupForm5.setUsername("fisher");
        signupForm5.setBio("populated");
        signupForm5.setDob(new Date());
        signupForm5.setGender("gender");
        signupForm5.setEmail("example");
        signupForm5.setPassword("password");

        authFacade.signUserUp(signupForm1);
        authFacade.signUserUp(signupForm2);
        authFacade.signUserUp(signupForm3);
        authFacade.signUserUp(signupForm4);
        authFacade.signUserUp(signupForm5);

        Account account1 = accountService.getByUsername("alex");
        Account account2 = accountService.getByUsername("zilber");
        Account account3 = accountService.getByUsername("bob");
        Account account4 = accountService.getByUsername("alan");
        Account account5 = accountService.getByUsername("fisher");

        Article article1 = new Article();
        article1.setArticleId(UUID.randomUUID());
        article1.setAuthorId(account1.getAccountId());
        article1.setCategory(Category.Biology);
        article1.setTitle("Are memes genetic weapon of mass destruction?");
        article1.setDate(new Date());
        article1.setData("yes, and Jews are responsible.");

        article1 = articleService.save(article1);

        likeService.byAccountLikePost(account3.getAccountId(), article1.getArticleId());
        likeService.byAccountLikePost(account4.getAccountId(), article1.getArticleId());
        likeService.byAccountLikePost(account5.getAccountId(), article1.getArticleId());


        Comment comment = new Comment();
        comment.setCommendId(UUID.randomUUID());
        comment.setAuthorId(account2.getAccountId());
        comment.setDate(new Date());
        comment.setArticleId(article1.getArticleId());
        comment.setContent("NOOO WE ARE SUPERIOR RACE NOOOO");
        commentService.save(comment);

        Article article2 = new Article();
        article2.setArticleId(UUID.randomUUID());
        article2.setAuthorId(account2.getAccountId());
        article2.setCategory(Category.Biology);
        article2.setTitle("Alt-right monkeys");
        article2.setDate(new Date());
        article2.setData("yes, and Jews are the best!");

        article2 = articleService.save(article2);

        blackListService.byAccountBlockAccount(account4.getAccountId(),account2.getAccountId());

        log.info("Population finished.");
    }

    @Override public void onApplicationEvent(ContextRefreshedEvent event) {

        counter++;

        Role role = new Role(UUID.randomUUID(), ERole.ROLE_USER);
        if (!roleRepository.findByName(ERole.ROLE_USER).isPresent())
            roleRepository.save(role);

        //REMOVE ON PRODUCTION
        populate();

    }
}
