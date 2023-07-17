package fashion.look_book.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@SequenceGenerator(
        name="MEMBERIMG_SEQ_GENERATOR",
        allocationSize=1
)
public class MemberImg {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "MEMBERIMG_SEQ_GENERATOR")
    @Column(name="memberImg_id")
    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member image_member;

    @Builder
    public MemberImg(Member image_member, String imgName, String oriImgName, String imgUrl) {
        this.image_member = image_member;
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }

    public MemberImg() {

    }

    public void update_memberImg (String imgName, String oriImgName, String imgUrl) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }
}
