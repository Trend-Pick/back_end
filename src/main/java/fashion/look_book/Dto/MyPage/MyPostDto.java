package fashion.look_book.Dto.MyPage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPostDto {
    private String title;
    private String content;
    private String postUrl;
    private LocalDateTime time;
    private Long id;
}
