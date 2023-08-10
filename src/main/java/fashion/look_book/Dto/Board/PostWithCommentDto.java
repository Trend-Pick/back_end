package fashion.look_book.Dto.Board;

import fashion.look_book.domain.Post;
import lombok.Data;

import java.util.List;

@Data
public class PostWithCommentDto {
    private String title;
    private String content;
    private String postImgUrl;
    private List<CommentDtoContent> commentList;

    public PostWithCommentDto(Post post, String postImgUrl, List<CommentDtoContent> commentList) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.postImgUrl = postImgUrl;
        this.commentList = commentList;
    }
}
