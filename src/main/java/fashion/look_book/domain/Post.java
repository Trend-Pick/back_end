package fashion.look_book.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Post {

    @Id @Generated
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member post_member;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public Post (Member post_member, String title, String content) {
        this.post_member = post_member;
        this.title = title;
        this.content = content;

        Comment.builder().post(this).build();
        // 연관관계 편의 메서드
    }

    public void update_post (String title, String content) {
        this.title = title;
        this.content = content;
    }
}
