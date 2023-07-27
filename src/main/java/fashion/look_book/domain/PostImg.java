package fashion.look_book.domain;

import fashion.look_book.Dto.Board.ImgInPostDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name="POSTIMG_SEQ_GENERATOR",
        allocationSize=1
)
public class PostImg {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "POSTIMG_SEQ_GENERATOR")
    @Column(name="img_id")
    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repimgYn; // 대표이미지 여부

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    public PostImg(String imgName, String oriImgName, String imgUrl, String repimgYn, Post post) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
        this.repimgYn = repimgYn;
        this.post = post;
    }

    // postImgService 에서 이미지 수정할 때 사용함
    public void postImgUpdate(String imgName, String oriImgName, String imgUrl) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }

}