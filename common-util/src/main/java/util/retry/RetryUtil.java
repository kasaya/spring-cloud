package util.retry;

import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

public class RetryUtil {

    public void test() throws Exception {
        RetryTemplate template = new RetryTemplate();
        SimpleRetryPolicy policy = new SimpleRetryPolicy();
        policy.setMaxAttempts(3);
        template.setRetryPolicy(policy);
       /* template.execute(new RetryCallback<String, Exception>() {
            @Override
            public String doWithRetry(RetryContext retryContext) throws Exception {
                return null;
            }
        });*/
        template.execute(RetryContext::getLastThrowable);

    }
}
