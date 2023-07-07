package fashion.look_book.Dto.PIcture;

import fashion.look_book.domain.Member;
import fashion.look_book.domain.Picture;
import lombok.Data;

@Data
public class CreatePictureDto {

    private Member picture_member;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    //private String repimgYn;

    public CreatePictureDto(Picture picture) {
        this.picture_member = picture.getPicture_member();
         this.imgName = picture.getImgName();
        this.oriImgName = picture.getOriImgName();
        this.imgUrl = picture.getImgUrl();
    }
}
