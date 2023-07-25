package fashion.look_book.Dto.LoginDtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class addMemberDtoRequest {

    @NotEmpty
    @Size(min = 6, max = 10)
    private String user_user_id;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 6, max = 12)
    private String password;

    @NotEmpty
    @Size(min = 4, max = 10)
    private String nickname;
}