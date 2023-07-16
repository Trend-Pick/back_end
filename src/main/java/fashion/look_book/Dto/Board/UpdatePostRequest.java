package fashion.look_book.Dto.Board;

import fashion.look_book.domain.Post;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;


}