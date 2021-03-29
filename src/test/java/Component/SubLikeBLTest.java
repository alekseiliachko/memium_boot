package Component;

import com.degenerates.memium.MemiumApplication;
import com.degenerates.memium.facade.AccountFacade;
import com.degenerates.memium.facade.AuthFacade;
import com.degenerates.memium.facade.LikeFacade;
import com.degenerates.memium.facade.SubFacade;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.dto.LogInForm;
import com.degenerates.memium.model.dto.LogInSuccess;
import com.degenerates.memium.model.dto.SignupForm;
import com.degenerates.memium.repository.*;
import com.degenerates.memium.util.Populators;
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
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MemiumApplication.class)
@AutoConfigureMockMvc
public class SubLikeBLTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    Populators populators;

    @Autowired
    AuthFacade authFacade;

    @Autowired
    AccountImageRepository accountImageRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    LikeListRepository likeListRepository;

    @Autowired
    SubListRepository subListRepository;

    @Autowired
    BlackListRepository blackListRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    LikeFacade likeFacade;

    @Autowired
    SubFacade subFacade;

    @Autowired
    AccountFacade accountFacade;

    private static UUID accountId;

    private static UUID accountId2;

    private static UUID articleId;

    private static String TOKEN;

    private static final ObjectMapper objectMapper = new ObjectMapper();

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
        likeListRepository.deleteAll();
        subListRepository.deleteAll();
        blackListRepository.deleteAll();

        SignupForm signupForm2 = getSignForm();
        signupForm2.setUsername("max");
        authFacade.signUserUp(signupForm2);
        Account account = accountRepository.findByUsername("max").get();
        accountId2 = account.getAccountId();

        authFacade.signUserUp(getSignForm());
        account = accountRepository.findByUsername("alex").get();
        accountId = account.getAccountId();

        Article article = getArticle();
        article.setAuthorId(accountId);
        article = articleRepository.save(article);
        articleId = article.getArticleId();


        LogInSuccess logInSuccess = authFacade.logUserIn(new LogInForm("alex","password")).getBody();
        TOKEN = "Bearer " + logInSuccess.getToken();
    }

    @BeforeAll
    static void init() {
        objectMapper.findAndRegisterModules();
    }

    @Test
    void like() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/account/like/" + articleId)
                .header("Authorization", TOKEN))
                .andExpect(status().is(200));
    }

    @Test
    void unlike() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/account/like/" + articleId)
                .header("Authorization", TOKEN))
                .andExpect(status().is(200));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/account/like/" + articleId)
                .header("Authorization", TOKEN))
                .andExpect(status().is(200));
    }

    @Test
    void sub() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/account/sub/" + accountId2)
                .header("Authorization", TOKEN))
                .andExpect(status().is(200));
    }

    @Test
    void unsub() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/account/sub/" + accountId2)
                .header("Authorization", TOKEN))
                .andExpect(status().is(200));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/account/sub/" + accountId2)
                .header("Authorization", TOKEN))
                .andExpect(status().is(200));
    }

    @Test
    void block() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/account/bl/" + accountId2)
                .header("Authorization", TOKEN))
                .andExpect(status().is(200));
    }

    @Test
    void unblock() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/account/bl/" + accountId2)
                .header("Authorization", TOKEN))
                .andExpect(status().is(200));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/account/bl/" + accountId2)
                .header("Authorization", TOKEN))
                .andExpect(status().is(200));
    }

}
