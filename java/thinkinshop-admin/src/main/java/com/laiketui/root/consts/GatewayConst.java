package com.laiketui.root.consts;

public interface GatewayConst {

    /**
     * 基本数据类型
     */
    interface ApiParamBaseType{
        String TYPE_INT = "int";
        String TYPE_CHAR = "char";
        String TYPE_BYTE = "byte";
        String TYPE_DOUBLE = "double";
        String TYPE_FLOAT = "float";
        String TYPE_BOOL = "boolean";
        String TYPE_SHORT = "short";
        String TYPE_LONG = "long";
    }

    /**
     * 包装类
     */
    interface ApiParamBaseWarpType{
        String TYPE_INT = "java.lang.Integer";
        String TYPE_CHAR = "java.lang.Char";
        String TYPE_BYTE = "java.lang.Byte";
        String TYPE_DOUBLE = "java.lang.Bouble";
        String TYPE_FLOAT = "java.lang.Float";
        String TYPE_BOOL = "java.lang.Boolean";
        String TYPE_SHORT = "java.lang.Short";
        String TYPE_LONG = "java.lang.Long";
        String TYPE_STRING = "java.lang.String";
        String TYPE_STRINGBUFFER = "java.lang.StringBuffer";
        String TYPE_STRINGBUILDER = "java.lang.StringBuilder";
    }



}
