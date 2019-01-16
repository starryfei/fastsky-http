package com.starry.fastsky.enums;

import com.starry.fastsky.common.FastskyCommon;

/**
 * ClassName: FastSkyServerResponse
 * Description: 服务响应枚举类
 *
 * @author: starryfei
 * @date: 2019-01-11 18:04
 **/
public enum FastSkyServerResponse {
    /** 返回json数据类型**/
    JSON(0, FastskyCommon.JSON),
    /** 返回text数据类型**/

    TEXT(1,FastskyCommon.TEXT),
    /** 返回view视图**/

    VIEW(2,"");

    private int type;
    private String returnType;

     FastSkyServerResponse(int type, String returnType) {
        this.type = type;
        this.returnType = returnType;
    }

    public int getType() {
        return type;
    }

   public static String getReturnType(int i) {
        switch (i) {
            case 0:
                return JSON.getReturnType();
            case 1:
                return TEXT.getReturnType();
            case 2:
                return VIEW.getReturnType();
            default:
                return TEXT.getReturnType();
        }
    }
    public String getReturnType() {
        return this.returnType;
    }
}
