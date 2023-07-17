package fashion.look_book.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

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

    private String user_imgName;

    private String user_oriImgName;

    private String user_imgUrl;


    public Member() {
    }

    // 생성자
    @Builder
    public Member(String user_user_id, String password, String nickname) {
        this.user_user_id = user_user_id;
        this.password = password;
        this.nickname = nickname;
    }

    // private MultipartFile profile_img;

}
