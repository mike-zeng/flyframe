package utils;

/**
 * @author zeng
 * @Classname StringUtil
 * @Description 提供一系列字符串转换方法
 * @Date 2019/9/10 22:17
 */
public class StringUtil {
    private static final String SET_PREFIX ="set";
    private StringUtil(){}

    /**
     * 将类名转化为默认的beanName
     * @param className 类名
     * @return beanName 默认的beanName
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

    /**
     * 根据方法名判断该方法是否为set方法
     * @param methodName 方法名
     * @return 是否为set方法，如果是set方法返回true，否则返回false
     */
    public static boolean isSetMethod(String methodName){
        return methodName.length()>3&&methodName.startsWith(SET_PREFIX);
    }
}
