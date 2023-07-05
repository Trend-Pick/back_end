package fashion.look_book.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;

@Entity
@Getter
public class Comment {

    @Id @Generated
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member comment_member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public Comment() {
    }

    @Builder
    public Comment (Member comment_member, String content, Post post) {
        this.comment_member = comment_member;
        this.content = content;
        this.post = post;
    }
}
