package fashion.look_book.Dto.Board;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private String time;

    // MultipartFile imgInPost;

}