package fashion.look_book.Dto.Board;

import lombok.Data;

@Data
public class UpdateCommentResponse {
    private Long id;

    public UpdateCommentResponse(Long id) {
        this.id = id;
    }
}
