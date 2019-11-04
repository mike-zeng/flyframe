package site.flyframe.aop.util;

import site.flyframe.aop.annotation.Advise;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 用来判断一个对象中的某个方法，是否需要被处理
 */
class AspectObjectMatcher {
    private String packageName;
    private Class aClass;
    private int accessType;
    private Class retType;
    private String methodName;
    private Class[] args;


    boolean match(Advise advise, Object target, Method method){
        this.packageName=target.getClass().getPackage().getName();
        this.aClass=target.getClass();
        this.accessType=method.getModifiers();
        this.retType=method.getReturnType();
        this.methodName=method.getName();
        this.args=method.getParameterTypes();

        return matchPackage(advise.packageName())&&matchClassName(advise.className())&&
                machAccessType(advise.accessType())&&machRetType(advise.retType())&&
                matchMethodName(advise.methodName())&&matchArgs(advise.args());
    }

    /**
     * 匹配包名
     * @param packageName 包名
     * @return 是否匹配成功
     */
    private boolean matchPackage(String packageName){
        // 匹配所有包
        if ("*".equals(packageName)){
            return true;
        }
        // 不匹配子包
        if (!packageName.endsWith("+")){
            return this.packageName.equals(packageName);
        }else{
            packageName= packageName.substring(0, packageName.length() - 1);
            return this.packageName.startsWith(packageName)&&this.packageName.charAt(packageName.length())=='.';
        }
    }

    /**
     * 匹配类名
     * @param className 类名
     * @return 是否匹配成功
     */
    private boolean matchClassName(String className){
        // 如果指定包名，类名可以简写
        if (!packageName.equals("*")&& !className.contains(".")){
            className=packageName+"."+className;
        }
        if ("*".equals(className)){
            return true;
        }
        try {
            if (className.endsWith("+")){
                Class<?> aClass = Class.forName(className.substring(0,className.length()-1));
                aClass.isAssignableFrom(this.aClass);
            }else{
                Class<?> aClass = Class.forName(className);
                return this.aClass.equals(aClass);
            }
        } catch (ClassNotFoundException e) {
            return false;
        }
        return false;
    }

    /**
     * 匹配访问修饰符
     * @param accessType 访问修饰符
     * @return 是否成功
     */
    private boolean machAccessType(String accessType){
        if ("*".equals(accessType)){
            return true;
        }
        return accessType.equals("public")&& Modifier.isPublic(this.accessType)||
                accessType.equals("protected") && Modifier.isProtected(this.accessType)||
                accessType.equals("private")&& Modifier.isPrivate(this.accessType);
    }

    /**
     * 匹配返回类型
     * @param type 返回类型
     * @return 是否成功
     */
    private boolean machRetType(String type){
        if ("*".equals(type)){
            return true;
        }
        try {
            if (type.endsWith("+")){
                Class<?> aClass = Class.forName(type.substring(0, type.length() - 1));
                return aClass.isAssignableFrom(this.retType);
            }else {
                Class<?> aClass = Class.forName(type);
                return this.retType.equals(aClass);
            }
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 匹配方法名
     * @param methodName 方法名
     * @return 是否匹配成功
     */
    private boolean matchMethodName(String methodName){
        if ("*".startsWith(methodName)){
            return true;
        }
        if (methodName.endsWith("+")){
            return this.methodName.startsWith(methodName.substring(0,methodName.length()-1));
        }
        return this.methodName.endsWith(methodName);
    }

    /**
     * 匹配参数列表
     * @param args 参数类型列表
     * @return 返回值
     */
    private boolean matchArgs(String args){
        if ("*".equals(args)){
            return true;
        }
        String[] argsTypeStr = args.split(" ");
        for (int i = 0; i < this.args.length; i++) {
            if (!this.args[i].getName().equals(argsTypeStr[i])){
                return false;
            }
        }
        return true;
    }
}
