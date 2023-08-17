package fashion.look_book.Dto.Board;

import fashion.look_book.domain.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostWithCommentDto {
    private String title;
    private String content;
    private LocalDateTime time;
    private String user_nickname;
    private String postImgUrl;
    private List<CommentDtoContent> commentList;

    public PostWithCommentDto(Post post, String user_nickname, String postImgUrl, List<CommentDtoContent> commentList) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.time = post.getPostTime();
        this.user_nickname = user_nickname;
        this.postImgUrl = postImgUrl;
        this.commentList = commentList;
    }
}
