package com.oycl.controller;


import com.oycl.service.ShowTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class ShowProcess {

    @Autowired
    ShowTaskService showTaskService;

    @RequestMapping(value = "/showImg/{instanceId}")
    public void startJob(HttpServletResponse response, @PathVariable(value = "instanceId") String instanceId){
        InputStream imageStream = null;
        try {
            response.setHeader("Content-Type","image/png");
            imageStream = showTaskService.ShowImg(instanceId);
            //System.out.println(IOUtils.toString(imageStream, "UTF-8"));
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
            //TODO
            e.printStackTrace();
        } finally { // 流关闭
            try {
                imageStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
