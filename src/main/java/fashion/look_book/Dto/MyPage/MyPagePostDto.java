package fashion.look_book.Dto.MyPage;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyPagePostDto {
    private String title;
    private String content;
    private LocalDateTime time;
    public MyPagePostDto(String title, String content, LocalDateTime time) {
        this.title = title;
        this.content = content;
        this.time = time;
    }
}