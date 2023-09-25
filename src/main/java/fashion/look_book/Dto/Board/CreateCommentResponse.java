package fashion.look_book.Dto.Board;

import lombok.Data;

@Data
public class CreateCommentResponse {
    private Long id;

    public CreateCommentResponse(Long id) {
        this.id = id;
    }
}