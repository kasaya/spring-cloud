package cango.scf.com;

import cango.scf.com.service.AuthroizationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorizationServiceApplicationTests {

	@Autowired
	AuthroizationService authroizationService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void test() {
		//authroizationService.register(null);
	}

}
