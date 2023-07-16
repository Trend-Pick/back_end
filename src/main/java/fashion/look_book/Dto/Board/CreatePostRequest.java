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
public class CreatePostRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

//    private List<Long> postImgIdLIst = new ArrayList<>();
//
//    private List<ImgInPostDto> postImgDtoList = new ArrayList<>();
//
//    public Post toEntity()_{
//        return new Post(title,content);
//    }
}