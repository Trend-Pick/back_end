package fashion.look_book.Dto.LoginDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDtoRequest {
    private String user_user_id;
    private String password;
}
