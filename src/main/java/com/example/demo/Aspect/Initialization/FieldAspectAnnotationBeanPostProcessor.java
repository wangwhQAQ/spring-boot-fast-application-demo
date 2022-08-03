package com.example.demo.Aspect.Initialization;

import com.example.demo.Aspect.Interface.TrueFieldAspect;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author DBISer
 */


//BeanPostProcessor是bean的后置处理器的接口
//这里去实现它，实现之后，也是一个bean
@Component
public class FieldAspectAnnotationBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field field:declaredFields){
            TrueFieldAspect annotation = field.getAnnotation(TrueFieldAspect.class);
            if (annotation == null){
                continue;
            }
            /**
             * 将此对象的 accessible 标志设置为指示的布尔值。值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false 则指示反射的对象应该实施 Java 语言访问检查;实际上setAccessible是启用和禁用访问安全检查的开关,并不是为true就能访问为false就不能访问 ；
             * 由于JDK的安全检查耗时较多.所以通过setAccessible(true)的方式关闭安全检查就可以达到提升反射速度的目的
             */
            field.setAccessible(true);

            try {
                //将bean对应字段修改成annotation.nickname() field里面已经存储了对应的信息
                field.set(bean, annotation.nickname());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return bean;
    }
}
