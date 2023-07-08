package fashion.look_book.Dto.LoginDtos;

import lombok.Data;

@Data
public class LoginDtoResponse {
    private Long id;
    public LoginDtoResponse(Long id) {
        this.id = id;
    }
}
