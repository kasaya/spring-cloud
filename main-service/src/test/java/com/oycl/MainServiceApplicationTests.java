package com.oycl;

import com.oycl.starter.service.impl.RetryServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainServiceApplicationTests {

    @Autowired
    private RetryServiceImpl retryService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void test1() throws Exception {
        retryService.excute();
    }

}
