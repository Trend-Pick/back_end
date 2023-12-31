package fashion.look_book.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String nickname;

    @OneToMany(mappedBy = "picture_member")
    private List<Picture> pictureList = new ArrayList<>();

    @OneToMany(mappedBy = "like_member")
    private List<Like> likeList = new ArrayList<>();

    @OneToMany(mappedBy = "comment_member")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post_member")
    private List<Post> postList = new ArrayList<>();

    @OneToOne(mappedBy = "image_member")
    private MemberImg memberImg;

    public Member() {
    }

    // 생성자
    @Builder
    public Member(String user_user_id, String email, String password, String nickname) {
        this.user_user_id = user_user_id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public void update_password (String password) {
        this.password = password;
    }
}
