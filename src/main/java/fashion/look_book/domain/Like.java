package fashion.look_book.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "likes")
@Getter
public class Like {

    @Id @GeneratedValue
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member like_member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @Enumerated(EnumType.STRING)
    private LikeStatus status;

    @Builder
    public Like (Member like_member, Picture picture, LikeStatus status) {
        this.like_member = like_member;
        this.picture = picture;
        this.status = status;
    }

    public void update_like (Member like_member, Picture picture, LikeStatus status) {
        this.like_member = like_member;
        this.picture = picture;
        this.status = status;
    }
}