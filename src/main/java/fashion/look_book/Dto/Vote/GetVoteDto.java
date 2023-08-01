package fashion.look_book.Dto.Vote;

import lombok.Data;

@Data
public class GetVoteDto {

    private String url;

    public GetVoteDto(String url) {
        this.url = url;
    }
}