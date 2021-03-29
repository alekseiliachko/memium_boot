package Component;

import com.degenerates.memium.MemiumApplication;
import com.degenerates.memium.model.dto.LogInForm;
import com.degenerates.memium.model.dto.SignupForm;
import com.degenerates.memium.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MemiumApplication.class)
@AutoConfigureMockMvc
public class AuthComponentTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository.deleteAll();
    }

    @BeforeAll
    static void init() {
        objectMapper.findAndRegisterModules();
    }

    @Test
    void signUpMissing() throws Exception {
        SignupForm signupForm = objectMapper.readValue(new File("src/test/resources/SignupForm.json"), SignupForm.class);
        signupForm.setEmail(null);

        String content = objectMapper.writeValueAsString(signupForm);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().is(400));
    }

    @Test
    void signUp() throws Exception {
        SignupForm signupForm = objectMapper.readValue(new File("src/test/resources/SignupForm.json"), SignupForm.class);

        String content = objectMapper.writeValueAsString(signupForm);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().is(200));
    }

    @Test
    void signIn() throws Exception {
        SignupForm signupForm = objectMapper.readValue(new File("src/test/resources/SignupForm.json"), SignupForm.class);

        String content = objectMapper.writeValueAsString(signupForm);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().is(200));

        LogInForm logInForm = new LogInForm("alex", "password");

        content = objectMapper.writeValueAsString(logInForm);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().is(200));
    }
}
