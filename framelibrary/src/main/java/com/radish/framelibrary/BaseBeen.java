package com.radish.framelibrary;

/**
 * been基类
 * @ Create_time: 2018/4/25 on 18:02.
 * @ description：
 * @ author: xman  15703379121@163.com
 */
public class BaseBeen {

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseBeen{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
