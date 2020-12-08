import com.degenerates.memium.facade.ArticleFacade;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.Article;
import com.degenerates.memium.model.dao.Comment;
import com.degenerates.memium.model.relations.LikeList;
import com.degenerates.memium.service.ArticleService;
import com.degenerates.memium.service.CommentService;
import com.degenerates.memium.service.LikeService;
import com.degenerates.memium.util.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ArticleCommentAccountTest {

    @Mock
    ArticleService articleService;

    @Mock
    CommentService commentService;

    @Mock
    LikeService likeService;

    @Mock
    Utils utils;

    @InjectMocks
    ArticleFacade articleFacade;

    Account account;

    Article article;

    List<Comment> comments = new ArrayList<>();

    List<LikeList> likeLists = new ArrayList<>();

    UUID accountId = UUID.randomUUID();

    UUID articleId = UUID.randomUUID();

    String token = "token";

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        account = new Account();
        account.setAccountId(accountId);

        article = new Article();
        article.setArticleId(articleId);
        article.setAuthorId(accountId);

        Comment comment1 = new Comment();
        comment1.setAuthorId(UUID.randomUUID());
        comment1.setArticleId(articleId);
        Comment comment2 = new Comment();
        comment2.setAuthorId(UUID.randomUUID());
        comment2.setArticleId(articleId);

        comments.add(comment1);
        comments.add(comment2);

        LikeList likeList1 = new LikeList(UUID.randomUUID(), articleId);
        LikeList likeList2 = new LikeList(UUID.randomUUID(), articleId);
        LikeList likeList3 = new LikeList(UUID.randomUUID(), UUID.randomUUID());

        likeLists.add(likeList1);
        likeLists.add(likeList2);
        likeLists.add(likeList3);

        Mockito.when(commentService.getByArticleId(Mockito.any(UUID.class))).thenReturn(comments);
        Mockito.when(articleService.getById(Mockito.any(UUID.class))).thenReturn(article);
        Mockito.when(utils.validateTokenAndGetOwner(Mockito.anyString())).thenReturn(account);

        Mockito.doAnswer(invocation -> {
            final UUID uuid = (UUID) (invocation.getArguments())[0];
            comments = comments.stream().filter(comment -> !comment.getArticleId().equals(uuid)).collect(Collectors.toList());

            return null;
        }).when(commentService).deleteByAtricleId(Mockito.any(UUID.class));

        Mockito.doAnswer(invocation -> {
            final UUID uuid = (UUID) (invocation.getArguments())[0];
            likeLists = likeLists.stream().filter(comment -> !comment.getArticleId().equals(uuid)).collect(Collectors.toList());

            return null;
        }).when(likeService).unlikeAllByAticleId(Mockito.any(UUID.class));

        Mockito.doNothing().when(articleService).deleteById(Mockito.any(UUID.class));
    }


    @Test
    public void test() {
        Assertions.assertEquals(2, comments.size());
        Assertions.assertEquals(3, likeLists.size());

        articleFacade.deleteArticle(articleId, token);

        Assertions.assertEquals(0, comments.size());
        Assertions.assertEquals(1, likeLists.size());
    }
}
