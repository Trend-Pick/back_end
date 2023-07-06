package fashion.look_book.Dto.Board;

import fashion.look_book.domain.Comment;
import fashion.look_book.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class PostWithCommentDto {
    private String title;
    private String content;
    private List<Comment> commentList;

    public PostWithCommentDto(Post post, List<Comment> commentList) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.commentList = commentList;
    }
}
