package Component;

import com.degenerates.memium.MemiumApplication;
import com.degenerates.memium.facade.AuthFacade;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.dao.Comment;
import com.degenerates.memium.model.dto.LogInForm;
import com.degenerates.memium.model.dto.LogInSuccess;
import com.degenerates.memium.model.dto.SignupForm;
import com.degenerates.memium.repository.*;
import com.degenerates.memium.util.Populators;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MemiumApplication.class)
@AutoConfigureMockMvc
public class ArticleDeleteTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    Populators populators;

    @Autowired
    AuthFacade authFacade;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CommentRepository commentRepository;

    private static UUID articleId;

    private static UUID accountId;

    private static String TOKEN;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static Article article;

    private static Account account;

    private static List<Comment> commentList;

    @BeforeAll
    static void init() {
        objectMapper.findAndRegisterModules();
    }

    SignupForm getSignForm() throws IOException {
        return objectMapper.readValue(new File("src/test/resources/SignupForm.json"), SignupForm.class);
    }

    Article getArticle() throws IOException {
        return objectMapper.readValue(new File("src/test/resources/Article.json"), Article.class);
    }

    List<Comment> getCommentList() throws IOException {
        return objectMapper.readValue(new File("src/test/resources/CommentsForArticle.json"), new TypeReference<List<Comment>>() {});
    }

    @BeforeEach
    void setUp() throws IOException {

        accountRepository.deleteAll();
        articleRepository.deleteAll();
        commentRepository.deleteAll();

        authFacade.signUserUp(getSignForm());
        Account account = accountRepository.findByUsername("alex").get();
        accountId = account.getAccountId();

        Article article = getArticle();
        article.setAuthorId(accountId);
        article = articleRepository.save(article);
        articleId = article.getArticleId();

        Comment comment = getCommentList().get(0);
        comment.setArticleId(articleId);
        comment.setArticleId(articleId);

        commentRepository.save(comment);

        LogInSuccess logInSuccess = authFacade.logUserIn(new LogInForm("alex","password")).getBody();
        TOKEN = "Bearer " + logInSuccess.getToken();
    }

    @Test
    void testNotAllowedToDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/article/", articleId)).andExpect(status().is(403));
    }

    @Test
    void allowedToDelete() throws Exception {

        //Assert thar article EXISTS and comment DO EXIST
        mockMvc.perform(MockMvcRequestBuilders.get("/api/open/article/articleId/" + articleId))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .contains(articleId.toString());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/open/comment/" + articleId))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .contains(articleId.toString());

        //Now delete article
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/article/" + articleId)
                .header("Authorization", TOKEN)).andExpect(status().is(200));

        //Make sure article is unavailable now and so is the comment
        mockMvc.perform(MockMvcRequestBuilders.get("/api/open/article/articleId/" + articleId))
                .andExpect(status().is(404));

        assertFalse(mockMvc.perform(MockMvcRequestBuilders.get("/api/open/comment/" + articleId))
                .andExpect(status().is(200))
                        .andReturn()
                        .getResponse()
                        .getContentAsString()
                        .contains(articleId.toString()));
    }



}
