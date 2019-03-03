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
public class SystemNotification {
    private String title;

    private String content;

    private String time;

    private boolean hasRead = true;//数据库中没有这个字段
}
