package fashion.look_book.repository;
import fashion.look_book.domain.Member;
import fashion.look_book.domain.Picture;
import fashion.look_book.domain.Post;
import fashion.look_book.domain.PostImg;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostImgRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(PostImg postImg) {
        em.persist(postImg);
    }

    public PostImg findOne(Long id) {
        return em.find(PostImg.class, id);
    }

    public List<PostImg> findAllPostImg(){
        List<PostImg> allPostImg = em.createQuery("select p from PostImg p",PostImg.class).getResultList();
        return allPostImg;
    }

    public PostImg findOneByPostId(Long postId){
        try {
            return em.createQuery("select p from PostImg p where p.post.id = :id", PostImg.class)
                    .setParameter("id", postId)
                    .getSingleResult();
        }catch(Exception e){
            return null;
        }
    }

    public int postImgUpdate(Long postId,String imgName,String oriImgName,String imgUrl){
        PostImg postImg = findOneByPostId(postId);
        Long postImgId = postImg.getId();

      return em.createQuery("update PostImg as p set p.imgName = :imgName, " +
                        "p.oriImgName = :oriImgName, p.imgUrl = :imgUrl where p.id = :postImgId")
                .setParameter("imgName",imgName)
                .setParameter("oriImgName",oriImgName)
                .setParameter("imgUrl",imgUrl)
                .setParameter("postImgId",postImgId)
                .executeUpdate();
    }

    public void postImgDelete(Long postId) {
        PostImg postImg = findOneByPostId(postId);
        if(postImg!=null) {
            em.remove(postImg);
        }
    }
}
