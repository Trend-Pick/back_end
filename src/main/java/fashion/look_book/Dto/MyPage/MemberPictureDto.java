package fashion.look_book.Dto.MyPage;

import lombok.Data;

@Data
public class MemberPictureDto {
    private String imgUrl;
    public MemberPictureDto(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
