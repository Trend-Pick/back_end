package fashion.look_book.Dto.LoginDtos;

import lombok.Data;

@Data
public class addMemberDtoResponse {
    private Long id;
    public addMemberDtoResponse(Long id) {
        this.id = id;
    }
}