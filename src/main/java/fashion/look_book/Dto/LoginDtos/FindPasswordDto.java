package fashion.look_book.Dto.LoginDtos;

import java.util.HashMap;
import java.util.Map;

public class FindPasswordDto {

    public void findPw(String memberEmail,String memberPw)throws Exception{
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("email", memberEmail);
        map.put("memberPw", memberPw);
    }
}
