package fashion.look_book.domain;

import fashion.look_book.Dto.Board.ImgInPostDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
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

    // for service
//    public ImgEntity(String imgName, String oriImgName, String imgUrl) {
//        this.imgName = imgName;
//        this.oriImgName = oriImgName;
//        this.imgUrl = imgUrl;
//    }

    // for service_patch_파라미터로 new entity 를 받음
//    public void imgPatch(ImgEntity imgEntity){
//        if(imgEntity.imgName != null){
//            this.imgName = imgEntity.imgName;
//        }
//        if(imgEntity.oriImgName != null){
//            this.oriImgName = imgEntity.oriImgName;
//        }
//        if(imgEntity.imgUrl != null){
//            this.imgUrl = imgEntity.imgUrl;
//        }
//    }

    // postImgService 에서 이미지 수정할 때 사용함
    public void postImgUpdate(String imgName, String oriImgName, String imgUrl){
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }

    // 글+이미지 찾을 때 사용함
    public ImgInPostDto toDto(){
        return new ImgInPostDto(id,imgName,oriImgName,imgUrl,repimgYn);
    }

}