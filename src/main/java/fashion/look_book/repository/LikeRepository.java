package fashion.look_book.repository;

import fashion.look_book.domain.Comment;
import fashion.look_book.domain.Like;
import fashion.look_book.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LikeRepository {
    private EntityManager em;

    public void save(Like like) {
        em.persist(like);
    }

    public Like findOne(Long id) {
        return em.find(Like.class, id);
    }

    public List<Like> findByMember(Member member) {
        Long memberId = member.getId();
        return em.createQuery("select l from Like l where l.member = :name", Like.class)
                .setParameter("name", memberId)
                .getResultList();
    }
}
