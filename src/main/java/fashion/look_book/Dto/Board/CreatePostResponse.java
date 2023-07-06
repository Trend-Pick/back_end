package fashion.look_book.Dto.Board;

import lombok.Data;

@Data
public class CreatePostResponse {
    private Long id;

    public CreatePostResponse(Long id) {
        this.id = id;
    }
}
