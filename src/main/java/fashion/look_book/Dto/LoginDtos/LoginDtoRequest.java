package fashion.look_book.Dto.LoginDtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDtoRequest {
    @NotEmpty
    private String user_user_id;
    @NotEmpty
    private String password;
}
