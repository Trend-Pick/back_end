package fashion.look_book.Dto.MyPage;

import fashion.look_book.Dto.Board.CommentDtoContent;
import fashion.look_book.domain.Member;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MyPagePostDto {
    private String member_url;
    private String nickname;
    private String email;
    private String user_user_id;
    private String password;
    private List<MyPostDto> postlist;

    public MyPagePostDto(String imgUrl, Member member, List<MyPostDto> postlist) {
        this.member_url = imgUrl;
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.user_user_id = member.getUser_user_id();
        this.password = member.getPassword();
        this.postlist = postlist;
    }
}