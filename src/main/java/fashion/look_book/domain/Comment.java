package fashion.look_book.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@SequenceGenerator(
        name="COMMENT_SEQ_GENERATOR",
        allocationSize=1
)
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "COMMENT_SEQ_GENERATOR")
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member comment_member;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime commentTime;

    public Comment() {
    }

    @Builder
    public Comment (Member comment_member, String content, Post post, LocalDateTime commentTime) {
        this.comment_member = comment_member;
        this.content = content;
        this.post = post;
        this.commentTime = commentTime;
    }

    public void update_comment (String content) {
        this.content = content;
    }
}
