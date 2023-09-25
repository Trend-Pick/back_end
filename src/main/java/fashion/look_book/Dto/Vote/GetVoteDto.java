package fashion.look_book.Dto.Vote;

import lombok.Data;

@Data
public class GetVoteDto {

    private String url;
    private Long id;

    public GetVoteDto(String url, Long id) {
        this.url = url;
        this.id = id;
    }
}