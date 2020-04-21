package com.github.shootercheng.export.reflect;

import com.github.shootercheng.export.common.Constants;
import com.github.shootercheng.export.common.ExportCommon;
import com.github.shootercheng.export.models.User;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author James
 */
public class TestReflect {

    @Test
    public void testUser() {
        Object object = new User("james","123434", 1);
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            Object value;
            if (methodName != null && methodName.startsWith("get")) {
                try {
                    Class<?> returnClazz = method.getReturnType();
                    System.out.println(returnClazz.getCanonicalName());
                    value = method.invoke(object, Constants.NO_ARGUMENTS);
                    System.out.println(methodName + "__" + value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testUserSetterMap() {
        Map<String, String> fieldColumnMap = new HashMap<>();
        fieldColumnMap.put("A", "userName");
        fieldColumnMap.put("C", "seq");
        fieldColumnMap.put("B", "passWord");
        List<Method> getterMethod = ExportCommon.buildParamGetter(User.class, fieldColumnMap);
        Assert.assertEquals(3, getterMethod.size());
    }
}
