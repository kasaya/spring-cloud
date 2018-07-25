package service;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("main-service")
public interface LoginService {

}
