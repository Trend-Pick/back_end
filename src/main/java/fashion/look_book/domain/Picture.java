package fashion.look_book.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SequenceGenerator(
        name="PICTURE_SEQ_GENERATOR",
        allocationSize=1
)
public class Picture {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "PICTURE_SEQ_GENERATOR")
    @Column(name = "picture_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member picture_member;

    @OneToMany(mappedBy = "picture", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    // private MultipartFile cody_img;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    public Picture() {
    }

    @Builder
    public Picture (Member picture_member,String imgName, String oriImgName, String imgUrl) {
        this.picture_member = picture_member;
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;

        Like.builder().picture(this).build();
    }
    // like 리스트가 아니라 like의 개수를 받아와야함


}
