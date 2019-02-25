package com.oycl.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;


public class GlobalExceptionHandler {

    private  Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 捕捉自定义异常
     * @param req
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value= {AplException.class})
    @ResponseBody
    public String dealerAplExceptionHandler(HttpServletRequest req, Exception exception) throws Exception
    {
        logger.error("异常：",exception);

        return "";
    }
    


    /**
     * 捕捉自定义异常
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value= {JsonProcessingException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String JsonExceptionHandler(JsonProcessingException exception) throws Exception
    {
        logger.error("异常：",exception);

        return "";
    }

    /**
     * 捕捉系统异常
     * @param req
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String defaultErrorHandler(HttpServletRequest req, Exception exception) throws Exception {


        logger.error("异常：",exception);

        return "";
    }

    /**
     * 捕捉系统异常
     * @param req
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = SQLException.class)
    @ResponseBody
    public String SqlErrorHandler(HttpServletRequest req, SQLException exception) throws Exception {

        logger.error("异常：",exception);

        return "";
    }

}
