package fashion.look_book.Dto.Vote;

import lombok.Data;


@Data
public class RakingPictureDto {

    private Long id1;
    private Long id2;
    private Long id3;

    public RakingPictureDto(Long id1, Long id2, Long id3) {
        this.id1 = id1;
        this.id2 = id2;
        this.id3 = id3;
    }
}
