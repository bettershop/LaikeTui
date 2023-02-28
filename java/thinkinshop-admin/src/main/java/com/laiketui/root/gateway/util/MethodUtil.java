package com.laiketui.root.gateway.util;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: wx
 * @date: Created in 2019/10/14 19:46
 * @version: 1.0.0
 * @modified By:
 */
public class MethodUtil {

    /** 
     * @description  获取参数信息
     * @author wx
     * @date 2019/10/15 10:46
     * @return
     */
    public static List<String> getMethodParamsName(Method method) throws Exception {
        Parameter[] parameters = method.getParameters();
        List<String> list = new ArrayList<String>();
        Arrays.stream(parameters).forEach(parameter -> {
            list.add(parameter.getName());
        });
        return list;
    }


    /** 
     * @description 
     * @author wx
     * @date 2019/10/15 13:38
     * @return
     */
    public List<String> getDubboServiceMethodParameters(String service,String application){
        return null;
}

    /** 
     * @description 
     * @author wx
     * @date 2019/10/15 9:57
     * @return
     */
    public static String[] getAllParamaterName(Method method) throws NotFoundException {
        Class<?> clazz = method.getDeclaringClass();
        ClassPool pool = ClassPool.getDefault();
        CtClass clz = pool.get(clazz.getName());
        CtClass[] params = new CtClass[method.getParameterTypes().length];
        for (int i = 0; i < method.getParameterTypes().length; i++) {
            params[i] = pool.getCtClass(method.getParameterTypes()[i].getName());
        }
        CtMethod cm = clz.getDeclaredMethod(method.getName(), params);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();

        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                .getAttribute(LocalVariableAttribute.tag);
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        String[] paramNames = new String[cm.getParameterTypes().length];
        for (int i = 0; i < paramNames.length; i++) {
            paramNames[i] = attr.variableName(i + pos);
        }
        return paramNames;
    }

    /** 
     * @description 
     * @author wx
     * @date 2019/10/15 14:19
     * @return
     */
    public static void main(String[] args) throws Exception {
//        Class clz = UserServices.class;
//        Method[] method = clz.getDeclaredMethods();
//        Method m = null;
//        for( Method  m1 : method ){
//            if( m1.getName() .equalsIgnoreCase("selectByPrimaryKey") ){
//                m = m1;
//                break;
//            }
//        }
//        System.out.println(getMethodParamsName(m));
    }

}
