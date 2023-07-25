package fashion.look_book.repository;

import fashion.look_book.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LikeRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Like like) {
        em.persist(like);
    }

    public Like findOne(Long id) {
        return em.find(Like.class, id);
    }

    /*public List<Picture> weeklyLike() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.with(DayOfWeek.MONDAY);
        LocalDateTime endOfWeek = startOfWeek.plusDays(7);

        List<Picture> pictures = em.createQuery("SELECT l.picture " +
                        "FROM Like l " +
                        "WHERE l.likeTime >= :startOfWeek " +
                        "AND l.likeTime < :endOfWeek " +
                        "GROUP BY l.picture.id " +
                        "ORDER BY SUM(CASE WHEN l.status = :likeStatus THEN 1 " +
                        "                  WHEN l.status = :dislikeStatus THEN -1 " +
                        "                  ELSE 0 END) DESC", Picture.class)
                .setParameter("startOfWeek", startOfWeek)
                .setParameter("endOfWeek", endOfWeek)
                .setParameter("likeStatus", LikeStatus.LIKE)
                .setParameter("dislikeStatus", LikeStatus.DISLIKE)
                .setMaxResults(3)
                .getResultList();

        return pictures;
    }*/
    public List<Object[]> weeklyLike() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.with(DayOfWeek.MONDAY);
        LocalDateTime endOfWeek = startOfWeek.plusDays(7);

        List<Object[]> resultList = em.createQuery(
                        "SELECT l.picture, " +
                                "SUM(CASE WHEN l.status = :likeStatus THEN 1 " +
                                "         WHEN l.status = :dislikeStatus THEN -1 " +
                                "         ELSE 0 END) as likeDislikeDifference " +
                                "FROM Like l " +
                                "WHERE l.likeTime >= :startOfWeek " +
                                "AND l.likeTime < :endOfWeek " +
                                "GROUP BY l.picture.id " +
                                "ORDER BY likeDislikeDifference DESC", Object[].class)
                .setParameter("startOfWeek", startOfWeek)
                .setParameter("endOfWeek", endOfWeek)
                .setParameter("likeStatus", LikeStatus.LIKE)
                .setParameter("dislikeStatus", LikeStatus.DISLIKE)
                .setMaxResults(3)
                .getResultList();

        return resultList;
    }

    public List<Object[]> monthlyLike() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1);

        List<Object[]> pictures = em.createQuery("SELECT l.picture, " +
                        "SUM(CASE WHEN l.status = :likeStatus THEN 1 " +
                        "         WHEN l.status = :dislikeStatus THEN -1 " +
                        "         ELSE 0 END) as likeDislikeDifference " +
                        "FROM Like l " +
                        "WHERE l.likeTime >= :startOfMonth " +
                        "AND l.likeTime < :endOfMonth " +
                        "GROUP BY l.picture.id " +
                        "ORDER BY likeDislikeDifference DESC", Object[].class)
                .setParameter("startOfMonth", startOfMonth)
                .setParameter("endOfMonth", endOfMonth)
                .setParameter("likeStatus", LikeStatus.LIKE)
                .setParameter("dislikeStatus", LikeStatus.DISLIKE)
                .setMaxResults(3)
                .getResultList();

        return pictures;
    }
}
