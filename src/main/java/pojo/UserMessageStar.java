package pojo;

import lombok.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-2-27
 * @Author ZhengTianle
 * Description:
 * 比UserAndMessage多一个liked属性
 */
@Setter
@Getter
@NoArgsConstructor
@ToString
public class UserMessageStar {
    private Integer mid;

    private Integer uid;//表示留言者id

    private String location;

    private String content;

    private String time;

    private Integer stars;

    private String username;

    private boolean liked;//表示当前登录用户是否已经赞过此条留言

    public UserMessageStar(UserAndMessage userAndMessage) {
        this.mid = userAndMessage.getMid();
        this.uid = userAndMessage.getUid();
        this.location = userAndMessage.getLocation();
        this.content = userAndMessage.getContent();
        this.time = userAndMessage.getTime();
        this.stars = userAndMessage.getStars();
        this.username = userAndMessage.getUsername();
        this.liked = false;
    }
}
