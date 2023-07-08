package fashion.look_book.Dto.Board;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDtoContent {
    @NotEmpty
    private String content;
}
