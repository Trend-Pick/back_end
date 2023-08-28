package fashion.look_book.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SequenceGenerator(
        name="POST_SEQ_GENERATOR",
        allocationSize=1
)
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "POST_SEQ_GENERATOR")
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member post_member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    public Post() {
    }

    @OneToOne(mappedBy = "post")
    private PostImg postImg;

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

    public void update_postTime(LocalDateTime time)
    {
        this.getLastModifiedDate();
    }
}
