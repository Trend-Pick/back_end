package fashion.look_book.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SequenceGenerator(
        name="MEMBER_SEQ_GENERATOR",
        allocationSize=1
)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
        generator = "MEMBER_SEQ_GENERATOR")
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String user_user_id;

    @NotEmpty
    private String password;

    @NotEmpty
    private String nickname;

    private Long age;

    private boolean sex;

    @OneToMany(mappedBy = "picture_member")
    private List<Picture> pictureList = new ArrayList<>();

    @OneToMany(mappedBy = "like_member")
    private List<Like> likeList = new ArrayList<>();

    @OneToMany(mappedBy = "comment_member")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post_member")
    private List<Post> postList = new ArrayList<>();


    public Member() {
    }

    // 생성자
    @Builder
    public Member(String user_user_id, String password, String nickname, Long age, boolean sex) {
        this.user_user_id = user_user_id;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
        this.sex = sex;
    }

    // private MultipartFile profile_img;

}
