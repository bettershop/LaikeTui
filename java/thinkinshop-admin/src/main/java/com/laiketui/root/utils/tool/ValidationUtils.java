package com.laiketui.root.utils.tool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * javabean字段校验工具类
 * @author wangxian
 */
public class ValidationUtils {

    private final static String PIX_GET = "get";

    /**
     * 校验列表中传入的字段值非空
     *
     * @param object 待检测对象
     * @param checkedFieldNames 被检查变量
     * @throws Exception 如果被校验字段的值为空，抛出此异常
     */
    public static void checkNotEmpty(Object object, List<String> checkedFieldNames) throws Exception {
        if (null == object) {
            throw new Exception("参数为空");
        }
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        String fieldName = "";
        StringBuilder methodName = null;
        Method method = null;
        for (Field field : fields) {
            fieldName = field.getName();
            if (!hasElement(fieldName, checkedFieldNames)) {
                continue;
            }
            methodName = new StringBuilder(PIX_GET);
            methodName = methodName.append(fieldName.substring(0, 1).toUpperCase())
                    .append(fieldName.substring(1));
            method = clazz.getDeclaredMethod(methodName.toString());
            Object result = method.invoke(object);
            if (null == result || "".equals(result)) {
                throw new Exception("字段【".concat(fieldName).concat("】为空！"));
            }else if(result instanceof Integer){
                if(Integer.parseInt(result.toString()) == 0){
                    throw new Exception("字段【".concat(fieldName).concat("】为0！"));
                }
            }
        }
    }

    /**
     * 验证对象所有字段是否为空
     * @param object
     * @throws Exception
     */
    public static void checkNotEmpty(Object object) throws Exception {
        checkNotEmpty(object,getAllFiled(object));
    }

    /**
     * 检测container数组是否包含element元素
     * @return boolean，true 包含
     */
    private static boolean hasElement(String element, List<String> containers) {
        if (containers.contains(element)) {
            return true;
        }
        return false;
    }



    /**
     * 获取所有字段名
     * @return
     */
    private  static  List<String> getAllFiled(Object c){
        List<String> retList = new ArrayList<>();
        try {
            Field[] fields = c.getClass().getDeclaredFields();
            for (Field field:fields) {
                retList.add(field.getName());
            }
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return retList;
    }

    public static void main(String[] args) throws Exception {
        cat cat = new cat();
        cat.setAge(1);
        cat.setName("dfd");

        Map map = BeanUtils.bean2Map(cat);
        System.out.println(map);
        com.laiketui.root.utils.tool.cat c = BeanUtils.map2Bean(map,cat.getClass());
        System.out.println(c.getName());
//        List<String> checkList = new ArrayList<>();
//        checkList.add("price");
//        checkList.add("age");
//        ValidationUtils.checkNotEmpty(cat,checkList);



    }
}

class cat{

   int age;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    String name;
   BigDecimal price;

   public int getAge() {
       return age;
   }

   public void setAge(int age) {
       this.age = age;
   }

   public String getName() {
       return name;
   }

   public void setName(String name) {
       this.name = name;
   }
}
