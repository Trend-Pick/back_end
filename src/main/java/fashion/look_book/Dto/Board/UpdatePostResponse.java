package fashion.look_book.Dto.Board;

import lombok.Data;

@Data
public class UpdatePostResponse {
    private Long id;

    public UpdatePostResponse(Long id) {
        this.id = id;
    }
}
