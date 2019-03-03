package notice;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-2-27
 * @Author ZhengTianle
 * Description:
 * 利用spring AOP
 * 当用户点赞后，通知被点赞的用户
 */
@Aspect
public class SatrNotice {

    @Pointcut("execution(* service.StarMessageService.starAMessage(int, int)) && args(uid ,mid)")
    public void star(int uid, int mid){}

    @AfterReturning("star(uid, mid)")
    public void notice(int uid, int mid){

    }
}
