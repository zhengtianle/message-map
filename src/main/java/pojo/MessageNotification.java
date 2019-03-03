package pojo;

import lombok.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-2-28
 * @Author ZhengTianle
 * Description:
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageNotification {
    private int suid;//产生消息方

    private int ruid;//消息接收方

    private String title;

    private String content;

    private String time;

    private int read;

    private Message message;//数据库中没有这个字段
}
