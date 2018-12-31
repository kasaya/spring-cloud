package com.oycl.common.exception;




import org.apache.ibatis.exceptions.TooManyResultsException;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);



    /**
     * 捕捉系统异常
     * @param req
     * @param exception
     * @return
     * @throws Exception
     */
//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public BaseOutput defaultErrorHandler(HttpServletRequest req, Exception exception) throws Exception {
//
//
//        exception.printStackTrace();
//        BaseOutput outPutBean = new BaseOutput();
//        outPutBean.setResultCode(Constants.ResultCode.SYS_ERR);
//        outPutBean.setResultMessage("系统异常,请联系管理员!");
//        logger.error("========[code:205] 系统异常,请联系管理员!======", exception);
//        return outPutBean;
//    }
//
//    /**
//     * 捕捉系统异常
//     * @param req
//     * @param exception
//     * @return
//     * @throws Exception
//     */
//    @ExceptionHandler(value = SQLException.class)
//    @ResponseBody
//    public BaseOutput sqlErrorHandler(HttpServletRequest req, SQLException exception) throws Exception {
//
//        exception.printStackTrace();
//        BaseOutput outPutBean = new BaseOutput();
//        outPutBean.setResultCode(Constants.ResultCode.SYS_ERR);
//        outPutBean.setResultMessage("系统异常,请联系管理员!");
//        logger.error("========[code:205] Sql异常,请联系管理员!======", exception);
//        return outPutBean;
//    }
//
//    /**
//     * 捕捉系统异常
//     * @param req
//     * @param exception
//     * @return
//     * @throws Exception
//     */
//    @ExceptionHandler(value = MyBatisSystemException.class)
//    @ResponseBody
//    public BaseOutput tooManyResultsErrorHandler(HttpServletRequest req, MyBatisSystemException exception) throws Exception {
//
//        exception.printStackTrace();
//        BaseOutput outPutBean = new BaseOutput();
//        Throwable error = exception.getCause();
//        outPutBean.setResultCode(Constants.ResultCode.BUSS_ERR);
//        outPutBean.setResultMessage("Sql异常,请联系管理员!");
//        if(error instanceof TooManyResultsException){
//            outPutBean.setResultCode(Constants.ResultCode.BUSS_ERR);
//            outPutBean.setResultMessage("业务数据不整合，请确认业务是否正确！");
//        }
//        logger.error("========[code:205] Sql异常,请联系管理员!======", exception);
//        return outPutBean;
//    }
}
