import com.degenerates.memium.exceptions.AccessMismatchException;
import com.degenerates.memium.exceptions.BadTokenException;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.security.jwt.JwtUtils;
import com.degenerates.memium.service.AccountService;
import com.degenerates.memium.util.Validators;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

public class ValidatorsTest {

    @Mock
    JwtUtils jwtUtils;

    @Mock
    AccountService accountService;

    @InjectMocks
    Validators validators;

    String token = "invalidToken";

    UUID uuid;
    Account account;


    @BeforeEach
    public void setUp() {

        uuid = UUID.randomUUID();
        account = new Account();
        account.setAccountId(uuid);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInvalidToken() {
        Mockito.when(jwtUtils.getUserNameFromJwtToken(Mockito.anyString())).thenCallRealMethod();

        Assertions.assertThrows(BadTokenException.class, () -> {
            validators.validateTokenAndGetOwner(token);
        });
    }

    @Test
    public void testValidToken() {
        Mockito.when(jwtUtils.getUserNameFromJwtToken(Mockito.anyString())).thenReturn("username");
        Mockito.when(accountService.getByUsername(Mockito.anyString())).thenReturn(account);

        Assertions.assertDoesNotThrow(() -> {
            validators.validateTokenAndGetOwner(token);
        });
    }

    @Test
    public void accountOwnsItem() {

        Assertions.assertDoesNotThrow(() -> {
            validators.accountOwnsItem(account, uuid);
        });
    }

    @Test
    public void accountDoesNotOwnItem() {

        Assertions.assertThrows(AccessMismatchException.class, () -> {
            validators.accountOwnsItem(account, UUID.randomUUID());
        });
    }
}
