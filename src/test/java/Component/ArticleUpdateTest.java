package Component;

import com.degenerates.memium.MemiumApplication;
import com.degenerates.memium.facade.AuthFacade;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.dto.ArticleDto;
import com.degenerates.memium.model.dto.LogInForm;
import com.degenerates.memium.model.dto.LogInSuccess;
import com.degenerates.memium.model.dto.SignupForm;
import com.degenerates.memium.repository.AccountRepository;
import com.degenerates.memium.repository.ArticleRepository;
import com.degenerates.memium.util.Populators;
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
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MemiumApplication.class)
@AutoConfigureMockMvc
public class ArticleUpdateTest {

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
    void updateDescription() throws Exception {

        ArticleDto article = articleRepository.findById(articleId).get().toArticleDto();
        article.setDescription("NEW UNIQUE DESCRIPTION");

        String content = objectMapper.writeValueAsString(article);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/article/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header("Authorization", TOKEN))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .contains(article.getDescription());
    }
}
