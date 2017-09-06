package com.lsh.boxbox.base;

import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * Created by LSH on 2017/9/4.
 * 公共相应参数
 */

public abstract class BaseResponse {

    private boolean success;
    private String msg;
    private String data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    /**
     * 解析单条数据
     *
     * @param clazz
     * @param <T>
     * @return
     * @throws IllegalAccessException 参数异常（Response中data为空）
     * @throws JsonSyntaxException    Json解析异常
     */
    public abstract <T> T getBean(Class<T> clazz) throws IllegalAccessException, JsonSyntaxException;

    /**
     * 解析数据列表
     *
     * @param type
     * @param <T>
     * @return
     * @throws IllegalAccessException 参数异常（Response中data为空）
     * @throws JsonSyntaxException    json解析异常
     */
    public abstract <T> T getBeanList(Type type) throws IllegalAccessException, JsonSyntaxException;

}
