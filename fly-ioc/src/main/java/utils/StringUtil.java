package utils;

/**
 * @author zeng
 * @Classname StringUtil
 * @Description 字符串转换相关的类
 * @Date 2019/9/10 22:17
 */
public class StringUtil {
    private static final String SET_PREFIX ="set";
    private StringUtil(){}

    /**
     * 使用类名转化为默认的beanName
     * @param className 类名
     * @return beanName
     */
    public static String classNameToDefaultBeanName(String className){
        String startChar= String.valueOf(className.charAt(0));
        return startChar.toLowerCase()+className.substring(1);
    }

    /**
     * 将set方法转换为对应的字段名
     * @param methodName set方法名
     * @return 字段名
     */
    public static String setMethodNameToFieldName(String methodName){
        return String.valueOf(methodName.charAt(3)).toLowerCase()+methodName.substring(4);
    }

    public static boolean isSetMethod(String methodName){
        return methodName.length()>3&&methodName.startsWith(SET_PREFIX);
    }
}
