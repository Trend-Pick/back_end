package fashion.look_book.Dto.MyPage;

import lombok.Data;

@Data
public class MemberPictureDto {
    private Long id;
    public MemberPictureDto(Long id) {
        this.id = id;
    }
}
