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
    private String user_nickname;
    private Long id;
    private String postImgUrl;
    private List<CommentDtoContent> commentList;

    public PostWithCommentDto(Post post, String user_nickname, Long id, String postImgUrl, List<CommentDtoContent> commentList) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.time = post.getPostTime();
        this.updateTime = post.getLastModifiedDate();
        this.user_nickname = user_nickname;
        this.id = id;
        this.postImgUrl = postImgUrl;
        this.commentList = commentList;
    }
}
