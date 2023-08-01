package fashion.look_book.Dto.MyPage;

import lombok.Data;

@Data
public class MyPagePictureDto {
    private String imgUrl;
    public MyPagePictureDto(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
