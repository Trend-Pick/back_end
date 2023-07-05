package fashion.look_book.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Picture {

    @Id @GeneratedValue
    @Column(name = "picture_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member picture_member;

    @OneToMany(mappedBy = "picture")
    private List<Like> likes = new ArrayList<>();

    // private MultipartFile cody_img;

    public Picture() {
    }

    @Builder
    public Picture (Member picture_member) {
        this.picture_member = picture_member;

        Like.builder().picture(this).build();
    }
    // like 리스트가 아니라 like의 개수를 받아와야함


}
