package fashion.look_book.repository;

import fashion.look_book.domain.Comment;
import fashion.look_book.domain.Member;
import fashion.look_book.domain.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public Comment findOne(Long id) {
        return em.find(Comment.class, id);
    }

    public List<Comment> findByPost(Post post) {
        Long postId = post.getId();
        return em.createQuery("select c from Comment c join fetch c.comment_member where c.post.id = :postId", Comment.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    public void deleteComment(Long id) {
        Comment findComment = em.find(Comment.class, id);
        em.remove(findComment);
    }
}
