package fashion.look_book.Dto.Board;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostListDto {
    private String title;
    private String content;
    private LocalDateTime postTime;
}
