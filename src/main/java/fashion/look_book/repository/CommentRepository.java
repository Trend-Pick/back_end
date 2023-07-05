package fashion.look_book.repository;

import fashion.look_book.domain.Comment;
import fashion.look_book.domain.Member;
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

    public List<Comment> findByMember(Member member) {
        Long memberId = member.getId();
        return em.createQuery("select c from Comment c where c.comment_member = :name", Comment.class)
                .setParameter("name", memberId)
                .getResultList();
    }
}
