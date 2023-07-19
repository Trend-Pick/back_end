package fashion.look_book.Dto.Board;

import fashion.look_book.domain.Post;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    MultipartFile imgInPost;

}