package cn.czyx007.mt.common;

import cn.czyx007.mt.utils.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author : 张宇轩
 * @createTime : 2023/5/30 - 9:44
 */
//全局异常处理
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        String msg = ex.getMessage();
        String userName = "";
        if(msg.contains("Duplicate entry")){
            userName = msg.split(" ")[2];
        }
        return R.error(userName + "：已经存在，无法注册！");
    }

    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException ex){
        return R.error(ex.getMessage());
    }
}
