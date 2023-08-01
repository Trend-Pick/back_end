package fashion.look_book.Dto.Vote;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class WeeklyRankingDto {
    private String imgUrl;
    private Long Like;
}
