package com.starry.fastsky.enums;

/**
 *
 */
public enum Response {
    /** 返回json数据类型**/
    JSON(0,"json"),
    /** 返回text数据类型**/

    TEXT(1,"Text"),
    /** 返回view视图**/

    VIEW(2,"View");
    private int type;
    private String returnType;




     Response(int type, String returnType) {
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
