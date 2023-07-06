package fashion.look_book.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "likes")
@Getter
@SequenceGenerator(
        name="LIKES_SEQ_GENERATOR",
        allocationSize=1
)
public class Like {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "LIKES_SEQ_GENERATOR")
    @Column(name = "like_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member like_member;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @Enumerated(EnumType.STRING)
    private LikeStatus status;

    public Like() {
    }

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