package Component;

import com.degenerates.memium.MemiumApplication;
import com.degenerates.memium.facade.AuthFacade;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.AccountDetails;
import com.degenerates.memium.model.dto.*;
import com.degenerates.memium.repository.AccountDetailsRepository;
import com.degenerates.memium.repository.AccountImageRepository;
import com.degenerates.memium.repository.AccountRepository;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MemiumApplication.class)
@AutoConfigureMockMvc
public class AccountUpdateTest {

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
    AccountDetailsRepository accountDetailsRepository;

    private static UUID accountId;

    private static String TOKEN;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    SignupForm getSignForm() throws IOException {
        return objectMapper.readValue(new File("src/test/resources/SignupForm.json"), SignupForm.class);
    }

    @BeforeEach
    void setUp() throws IOException {
        accountRepository.deleteAll();
        accountDetailsRepository.deleteAll();

        authFacade.signUserUp(getSignForm());
        Account account = accountRepository.findByUsername("alex").get();
        accountId = account.getAccountId();

        LogInSuccess logInSuccess = authFacade.logUserIn(new LogInForm("alex","password")).getBody();
        TOKEN = "Bearer " + logInSuccess.getToken();
    }

    @BeforeAll
    static void init() {
        objectMapper.findAndRegisterModules();
    }

    @Test
    void updateAccountData() throws Exception {
        UpdatePasswordEmailDto updatePasswordEmailDto = new UpdatePasswordEmailDto();
        updatePasswordEmailDto.setEmail("NEW UNIQUE EMAIL");

        String content = objectMapper.writeValueAsString(updatePasswordEmailDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/account/email/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header("Authorization", TOKEN))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .contains(updatePasswordEmailDto.getEmail());
    }

    @Test
    void updateAccountDetailsData() throws Exception {
        AccountDetailsDto account = accountDetailsRepository.findById(accountId).get().toAccountDetailsDto();
        account.setBio("NEW UNIQUE BIO");

        String content = objectMapper.writeValueAsString(account);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/account/details/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .header("Authorization", TOKEN))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .contains(account.getBio());
    }

}
