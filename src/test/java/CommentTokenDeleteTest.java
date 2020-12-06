import com.degenerates.memium.exceptions.AccessMismatchException;
import com.degenerates.memium.facade.CommentFacade;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.dao.Comment;
import com.degenerates.memium.service.ArticleService;
import com.degenerates.memium.service.CommentService;
import com.degenerates.memium.util.Validators;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class CommentTokenDeleteTest {

    @Mock
    CommentService commentService;

    @Mock
    ArticleService articleService;

    @Mock
    Validators validators;

    @InjectMocks
    CommentFacade commentFacade;

    Comment comment;

    Account account;

    Article article;

    UUID accountFromTokenId = UUID.randomUUID();

    UUID articleId = UUID.randomUUID();

    UUID commentId = UUID.randomUUID();

    String token = "token";

    void encaps(HttpStatus code) {

    }



    @BeforeEach
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        account = new Account();

        article = new Article();
        article.setArticleId(articleId);

        comment = new Comment();
        comment.setCommendId(commentId);
        comment.setArticleId(articleId);

        Mockito.when(commentService.getById(Mockito.any(UUID.class))).thenReturn(comment);
        Mockito.when(articleService.getById(Mockito.any(UUID.class))).thenReturn(article);
        Mockito.when(validators.validateTokenAndGetOwner(Mockito.anyString())).thenReturn(account);

        Mockito.doNothing().when(commentService).deleteById(Mockito.any(UUID.class));
    }


    @Test
    public void deleteCommentByCommentOwner() {
        account.setAccountId(accountFromTokenId);
        comment.setAuthorId(accountFromTokenId);

        article.setAuthorId(UUID.randomUUID());

        Assertions.assertEquals(HttpStatus.OK, commentFacade.deleteComment(commentId, token));
    }

    @Test
    public void deleteCommentByArticleOwner() {
        account.setAccountId(accountFromTokenId);
        article.setAuthorId(accountFromTokenId);

        comment.setAuthorId(UUID.randomUUID());

        Assertions.assertEquals(HttpStatus.OK, commentFacade.deleteComment(commentId, token));
    }

    @Test
    public void deleteCommentByOtherAccount() {
        account.setAccountId(accountFromTokenId);

        article.setAuthorId(UUID.randomUUID());
        comment.setAuthorId(UUID.randomUUID());

        Assertions.assertThrows(AccessMismatchException.class, () -> {
            commentFacade.deleteComment(commentId, token);
        });
    }
}
