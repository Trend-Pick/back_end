package fashion.look_book.Dto.Vote;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyRankingDto {
    private String imgUrl;
    private Long Like;
}