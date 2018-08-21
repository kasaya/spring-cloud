package util.retry;


import exception.NormalExciton;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

public interface InterfaceRetryService {

    @Retryable(value= NormalExciton.class ,maxAttempts=2, exceptionExpression = "#{getCode().equals('E003')}")
    void excute();

    @Recover
    void recover(NormalExciton e);
}
