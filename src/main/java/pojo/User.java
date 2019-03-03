package pojo;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

    private String time;//注册时间

    private String readtime;//最近一次阅读系统通知的时间
}