package fashion.look_book.repository;

import fashion.look_book.domain.Member;
import fashion.look_book.domain.Picture;
import fashion.look_book.domain.Post;
import fashion.look_book.domain.PostImg;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    @EntityGraph(attributePaths = {"member"})
    public Post findOne(Long id) {
        return em.find(Post.class, id);
    }

    public List<Post> findAllPost() {
        List<Post> AllPost = em.createQuery("select p from Post p join fetch p.post_member", Post.class)
                .getResultList();

        return AllPost;
    }

    public void deletePost(Long id) {
        Post findPost = em.find(Post.class, id);
        em.remove(findPost);
    }

    public List<Post> MyPagePost(Long memberId) {
        return em.createQuery("SELECT p FROM Post p where p.post_member.id = :id order by p.PostTime desc ", Post.class)
                .setParameter("id", memberId)
                .setMaxResults(6)
                .getResultList();
    }
}
