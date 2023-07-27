package fashion.look_book.Dto.LoginDtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class addMemberDtoRequest {

    @NotEmpty(message = "id를 입력하세요")
    @Range(min = 6, max = 10)
    private String user_user_id;

    @NotEmpty(message = "이메일을 입력하세요")
    @Email
    private String email;

    @NotEmpty(message = "password를 입력하세요")
    @Range(min = 6, max = 12)
    private String password;

    @NotEmpty(message = "닉네임을 입력하세요")
    @Range(min = 4, max = 10)
    private String nickname;
}