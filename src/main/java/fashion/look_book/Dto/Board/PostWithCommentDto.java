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
    private LocalDateTime updateTime;
    private Long id;
    private String user_nickname;
    private String user_id;
    private String postImgUrl;
    private List<CommentDtoContent> commentList;

    public PostWithCommentDto(Post post, String user_nickname, String user_id, String postImgUrl, List<CommentDtoContent> commentList) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.time = post.getPostTime();
        this.updateTime = post.getLastModifiedDate();
        this.id = post.getId();
        this.user_nickname = user_nickname;
        this.user_id = user_id;
        this.postImgUrl = postImgUrl;
        this.commentList = commentList;
    }
}
