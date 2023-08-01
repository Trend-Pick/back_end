package fashion.look_book.repository;

import fashion.look_book.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
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

    public List<Object[]> weeklyLike() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime thisMonday = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime nextSunday = now.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).withHour(11).withMinute(59).withSecond(59);

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
                .setParameter("startOfWeek", thisMonday)
                .setParameter("endOfWeek", nextSunday)
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
