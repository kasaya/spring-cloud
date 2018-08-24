package util.retry;


import exception.NormalExciton;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

/**
 * 实现普通的retry 逻辑接口
 */
public interface InterfaceRetryService {

    /**
     * 重试3次，如果遇到exception的错误code 为E003 则 进行重试
     */
    @Retryable(value= NormalExciton.class ,maxAttempts=3, exceptionExpression = "#{getCode().equals('E003')}")
    void excute();

    /**
     * 所有重试都失败后的兜底返回
     * @param e
     */
    @Recover
    void recover(NormalExciton e);
}
