package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListSet;

@Repository
public class PostRepository {

  private ConcurrentSkipListSet<Post> repositoryArray = new ConcurrentSkipListSet<>(Comparator.comparing(o -> o.getId()));

  public List<Post> all() {
    Post[] posts = repositoryArray.toArray(new Post[]{});
    return Arrays.asList(posts);
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(repositoryArray.ceiling(new Post(id)));
  }

  public Post save(Post post) {
    if (post.equals(new Post(0)))
      post.setId(repositoryArray.size());
    repositoryArray.add(post);
    return post;
  }

  public void removeById(long id) {
    repositoryArray.remove(new Post(id));
  }

}
