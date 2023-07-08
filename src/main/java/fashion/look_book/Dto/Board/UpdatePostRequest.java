package fashion.look_book.Dto.Board;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;
}