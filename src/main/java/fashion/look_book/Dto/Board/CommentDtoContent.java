package fashion.look_book.Dto.Board;

import fashion.look_book.domain.MemberImg;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDtoContent {
    @NotEmpty
    private String content;

    private String imgUrl;
}
