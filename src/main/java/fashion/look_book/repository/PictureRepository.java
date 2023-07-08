package fashion.look_book.repository;

import fashion.look_book.domain.Member;
import fashion.look_book.domain.Picture;
import fashion.look_book.domain.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PictureRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Picture picture) {
        em.persist(picture);
    }

    public Picture findOne(Long id) {
        return em.find(Picture.class, id);
    }


    public List<Picture> findByMember(Member member) {
        Long memberId = member.getId();
        return em.createQuery("select p from Picture p where p.picture_member = :name", Picture.class)
                .setParameter("name", memberId)
                .getResultList();
    }
    public void delete(Long pictureId){
        Picture findPicture = em.find(Picture.class, pictureId);
        em.remove(findPicture);
    }
}