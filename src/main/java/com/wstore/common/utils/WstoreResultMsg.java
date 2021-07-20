package com.wstore.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class WstoreResultMsg {
    private Integer code;
    private String msg;
    private Map<String, Object> extend = new HashMap<>();


    /**
     * 处理成功
     * @return WstoreResultMsg
     */
    public static WstoreResultMsg success(){
        WstoreResultMsg msg = new WstoreResultMsg();
        msg.setCode(100);
        msg.setMsg("处理成功");
        return msg;
    }

    /**
     * 处理失败
     * @return WstoreResultMsg
     */
    public static WstoreResultMsg fail(){
        WstoreResultMsg msg = new WstoreResultMsg();
        msg.setCode(200);
        msg.setMsg("处理失败");
        return msg;
    }

    /**
     * 返回时携带对象
     * @param key
     * @param value
     * @return
     */
    public WstoreResultMsg add(String key, Object value){
        this.getExtend().put(key, value);
        return this;
    }

    /**
     * 自定返回状态码
     * @param code
     * @param msg
     * @return
     */
    public static WstoreResultMsg status(Integer code, String msg){
        WstoreResultMsg wstoreResultMsg = new WstoreResultMsg();
        wstoreResultMsg.setCode(code);
        wstoreResultMsg.setMsg(msg);
        return wstoreResultMsg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }
}
