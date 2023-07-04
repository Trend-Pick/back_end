package fashion.look_book.repository;

import fashion.look_book.domain.Member;
import fashion.look_book.domain.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public Post findOne(Long id) {
        return em.find(Post.class, id);
    }

    public List<Post> findByMember(Member member) {
        Long memberId = member.getId();
        return em.createQuery("select p from Post p where p.member = :name", Post.class)
                .setParameter("name", memberId)
                .getResultList();
    }

    public List<Post> findAllPost() {
        List<Post> AllPost = em.createQuery("select p from Post p", Post.class).getResultList();
        return AllPost;
    }
}
