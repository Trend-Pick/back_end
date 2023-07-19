package fashion.look_book.Dto.MyPage;

import lombok.Data;

@Data
public class MyPagePictureDto {
    private Long id;
    public MyPagePictureDto(Long id) {
        this.id = id;
    }
}
