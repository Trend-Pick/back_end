package fashion.look_book.Dto.Board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostDtoTitle {
    private String title;
    private LocalDateTime postTime;
    private String postImgUrl;
}
