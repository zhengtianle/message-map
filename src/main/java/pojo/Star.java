package pojo;

import lombok.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-15
 * @Author ZhengTianle
 * Description:
 * WebSocket客户端点赞发送过来的消息封装
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Star {
    //谁点的赞
    private int uid;

    //给哪条留言点的赞
    private int mid;
}
