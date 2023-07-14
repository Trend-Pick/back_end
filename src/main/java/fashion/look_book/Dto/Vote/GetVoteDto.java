package fashion.look_book.Dto.Vote;

import lombok.Data;

@Data
public class GetVoteDto {

    private Long id1;
    private Long id2;

    public GetVoteDto(Long id1, Long id2) {
        this.id1 = id1;
        this.id2 = id2;
    }
}