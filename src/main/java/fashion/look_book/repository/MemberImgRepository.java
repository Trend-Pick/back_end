package fashion.look_book.repository;

import fashion.look_book.domain.Member;
import fashion.look_book.domain.MemberImg;
import fashion.look_book.domain.PostImg;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberImgRepository {

    @PersistenceContext
    private EntityManager em;

    public void save (MemberImg memberImg) {
        em.persist(memberImg);
    }

    public MemberImg findOne (Long id) {
        return em.find(MemberImg.class, id);
    }

    public void postImgDelete (Long Id) {
        MemberImg memberImg = findOne(Id);
        if(memberImg!=null) {
            em.remove(memberImg);
        }
    }

    public MemberImg findByMemberId(Long memberId){
        try {
            return em.createQuery("select i from MemberImg i where i.image_member.id = :id", MemberImg.class)
                    .setParameter("id", memberId)
                    .getSingleResult();
        }catch(Exception e){
            return null;
        }
    }
}