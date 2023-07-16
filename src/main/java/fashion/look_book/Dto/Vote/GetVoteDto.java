package fashion.look_book.Dto.Vote;

import lombok.Data;

@Data
public class GetVoteDto {

    private Long id;

    public GetVoteDto(Long id) {
        this.id = id;
    }
}