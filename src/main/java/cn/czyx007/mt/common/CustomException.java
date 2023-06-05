package cn.czyx007.mt.common;

/**
 * @author : 张宇轩
 * @createTime : 2023/5/31 - 9:31
 */
public class CustomException extends RuntimeException{
    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
    }
}
