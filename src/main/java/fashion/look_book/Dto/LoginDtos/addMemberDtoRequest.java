package fashion.look_book.Dto.LoginDtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class addMemberDtoRequest {

    @NotEmpty
    private String user_user_id;

    @NotEmpty
    private String password;

    @NotEmpty
    private String nickname;

    private Long age;

    private boolean sex;
}