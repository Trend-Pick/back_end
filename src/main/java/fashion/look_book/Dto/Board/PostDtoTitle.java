package fashion.look_book.Dto.Board;

import fashion.look_book.domain.Member;
import fashion.look_book.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDtoTitle {
    private String title;
    private String content;
    private LocalDateTime postTime;
    private LocalDateTime lastTime;
    private String postImgUrl;
    private Long id;
    private String user_nickname;

    public PostDtoTitle(Post post, Member member, String imgUrl) {
        title = post.getTitle();
        content = post.getContent();
        postTime = post.getCreatedDate();
        lastTime = post.getLastModifiedDate();
        postImgUrl = imgUrl;
        id = post.getId();
        user_nickname = member.getNickname();
    }
}
