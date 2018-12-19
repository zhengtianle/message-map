package pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer uid;

    private String tel;

    private String password;

    private String avatar;

    private String username;

    private Integer sex;

    private String birthday;

    private String address;

    private String profile;

    private String sid;

    private String institute;

    private Integer status;


}