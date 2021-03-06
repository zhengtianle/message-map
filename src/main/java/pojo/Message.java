package pojo;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
    private Integer mid;

    private Integer uid;

    private String location;

    private String content;

    private String time;

    private Integer stars;


}