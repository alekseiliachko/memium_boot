package Component;

import com.degenerates.memium.MemiumApplication;
import com.degenerates.memium.facade.AuthFacade;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.dao.ArticleImage;
import com.degenerates.memium.model.dao.Comment;
import com.degenerates.memium.model.dto.LogInForm;
import com.degenerates.memium.model.dto.LogInSuccess;
import com.degenerates.memium.model.dto.SignupForm;
import com.degenerates.memium.repository.AccountRepository;
import com.degenerates.memium.repository.ArticleImageRepository;
import com.degenerates.memium.repository.ArticleRepository;
import com.degenerates.memium.repository.CommentRepository;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MemiumApplication.class)
@AutoConfigureMockMvc
public class ArticleImageTest {

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
    ArticleImageRepository articleImageRepository;

    private static UUID articleId;

    private static UUID accountId;

    private static String TOKEN;

    private static final ObjectMapper objectMapper = new ObjectMapper();


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

    @BeforeEach
    void setUp() throws IOException {

        accountRepository.deleteAll();
        articleRepository.deleteAll();
        articleImageRepository.deleteAll();

        authFacade.signUserUp(getSignForm());
        Account account = accountRepository.findByUsername("alex").get();
        accountId = account.getAccountId();

        Article article = getArticle();
        article.setAuthorId(accountId);
        article = articleRepository.save(article);
        articleId = article.getArticleId();

        LogInSuccess logInSuccess = authFacade.logUserIn(new LogInForm("alex","password")).getBody();
        TOKEN = "Bearer " + logInSuccess.getToken();
    }


    @Test
    void addImageNotAllowed() throws Exception {

        byte[] articleImage = new byte[100];

        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", articleImage);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/article/image/" + UUID.randomUUID()).file(mockMultipartFile)
                .header("Authorization", TOKEN))
                .andExpect(status().is(404))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .contains(articleId.toString());
    }

    @Test
    void addImageToArticle() throws Exception {

        byte[] articleImage = new byte[100];

        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", articleImage);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/article/image/" + articleId).file(mockMultipartFile)
                .header("Authorization", TOKEN))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .contains(articleId.toString());
    }
}
