package pojo;

import lombok.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 18-12-22
 * @Author ZhengTianle
 * Description:
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserAndMessage {
    private Integer mid;

    private Integer uid;//表示留言者id

    private String location;

    private String content;

    private String time;

    private Integer stars;

    private String username;
}
