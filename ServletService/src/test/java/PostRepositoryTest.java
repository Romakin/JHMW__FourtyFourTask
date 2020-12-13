import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;


public class PostRepositoryTest {

    private PostRepository rep;

    @Before
    public void initTest() {
        rep = new PostRepository();
        Post[] posts = new Post[] {
                new Post(1, "post1"),
                new Post(2, "post2"),
                new Post(3, "post3"),
                new Post(4, "post4")
        };
        for (Post p: posts) {
            rep.save(p);
        }
    }

    @Test
    public void testAddSuccess() {
        Post np = rep.save(new Post(5, "post5"));
        Assertions.assertTrue(rep.getById(np.getId()).isPresent());
    }

    @Test
    public void testAddFailed() {
        Post np = rep.save(new Post(4, "post4.1"));
        Assertions.assertFalse(rep.getById(np.getId()).get().getContent().equals(np.getContent()));
    }

    @Test
    public void testRemove() {
        long id = 4;
        rep.removeById(id);
        Assertions.assertFalse(rep.getById(id).isPresent());
    }



}
