package com.oycl.controller;



import com.oycl.entity.InputParam;
import com.oycl.service.ShowTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class ShowProcess {

    @Autowired
    ShowTaskService showTaskService;

    @PostMapping(value = "/showImg", produces =  MediaType.APPLICATION_JSON_VALUE)
    public void startJob(HttpServletResponse response, @RequestBody InputParam inputParam){
        InputStream imageStream = null;
        try {
            imageStream = showTaskService.ShowImg(inputParam);
            if(imageStream == null){
                throw new Exception();
            }
            // 输出资源内容到相应对象
            byte[] b = new byte[1024];
            int len;
            while ((len = imageStream.read(b, 0, 1024)) != -1) {
                response.getOutputStream().write(b, 0, len);
            }
            response.getOutputStream().flush();
        }catch(Exception e) {

        } finally { // 流关闭
            try {
                imageStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
