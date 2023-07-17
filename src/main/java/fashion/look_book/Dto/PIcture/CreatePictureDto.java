package fashion.look_book.Dto.PIcture;

import fashion.look_book.domain.Member;
import fashion.look_book.domain.Picture;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreatePictureDto {

    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private LocalDateTime pictureTime;
    //private String repimgYn;

    public CreatePictureDto(Picture picture) {
        this.imgName = picture.getImgName();
        this.oriImgName = picture.getOriImgName();
        this.imgUrl = picture.getImgUrl();
        this.pictureTime = picture.getPictureTime();
    }
}
