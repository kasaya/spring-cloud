package com.oycl;

import com.oycl.dao.MCodeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("integTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DataServiceApplicationTests {

	@Autowired
	MCodeMapper mCodeMapper;

	@Test
	public void contextLoads() {
	}

	@Test
	public void test(){
		boolean b = mCodeMapper.existById(1);
		System.out.println(b);
	}


}
