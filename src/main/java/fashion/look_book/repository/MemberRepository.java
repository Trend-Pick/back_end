package fashion.look_book.repository;

import fashion.look_book.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext // 영속성 컨텍스트
    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }
    public Member findOneByEmail(String email){
        return em.createQuery("select m from Member m  where m.email=:email",
                        Member.class)
                .setParameter("email",email)
                .getSingleResult();
    }
    public List<Member> findAll() {
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        return members;
    }

    public List<Member> findById(String user_user_id) {
        return em.createQuery("select m from Member m where m.user_user_id = :userid", Member.class)
                .setParameter("userid", user_user_id)
                .getResultList();
    } // 중복회원 검증할 때 필요한 메서드



    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(m -> m.getUser_user_id().equals(loginId))
                .findFirst();
    }

    public List<Member> findByEmail(String email) {
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email",email)
                .getResultList();
    }
    public int updatePassword(String email,String memberPw){
        return em.createQuery("update Member as p set p.password = :password where p.email= :email")
                .setParameter("email",email)
                .setParameter("password",memberPw)
                .executeUpdate();
    }

}
