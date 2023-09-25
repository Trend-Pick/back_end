package fashion.look_book.Dto.MyPage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPictureDto {
    private String url;
    private Long pictureId;
    private Long likes;
}
