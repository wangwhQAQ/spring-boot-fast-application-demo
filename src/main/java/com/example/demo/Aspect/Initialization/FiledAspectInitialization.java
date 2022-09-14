package com.example.demo.Aspect.Initialization;

import com.example.demo.Aspect.Interface.FiledAspect;
import com.example.demo.Model.Person;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

//(非spring容器的实现方法)通过两种方式来注入，一种是扫描，一种是反射
@Component
public class FiledAspectInitialization {
    //扫描
    public void Initialization() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //ClassPathScanningCandidateComponentProvider是Spring提供的工具，可以按自定义的类型，查找classpath下符合要求的class文件。
        ClassPathScanningCandidateComponentProvider classPathScanningCandidateComponentProvider = new ClassPathScanningCandidateComponentProvider(false);

        //AnnotationFilter是一个注解过滤器 这里是新建了一个过滤Type类型的过滤器
        classPathScanningCandidateComponentProvider.addIncludeFilter(new AnnotationTypeFilter(FiledAspect.class));

        //找到所有的BeanDefinition
        Set<BeanDefinition> beanDefinitions = classPathScanningCandidateComponentProvider.findCandidateComponents("com.example.demo");

        for (BeanDefinition beanDefinition:beanDefinitions){
            String beanClassName = beanDefinition.getBeanClassName();
            Class clazz = Class.forName(beanClassName);
            //getDeclaredAnnotation()方法获取指定的声明的注释类型的声明的注释。该方法以对象的形式返回该类。
            FiledAspect filedAspect = (FiledAspect) clazz.getDeclaredAnnotation(FiledAspect.class);
            String name = filedAspect.name();
            Integer age = filedAspect.age();
            //getDeclaredConstructors()方法用于返回一个Constructor对象数组，该数组指示此Class对象所表示的类定义的构造函数的类型
            //这里是在构建实例
            Object object = clazz.getDeclaredConstructor(String.class,int.class).newInstance(name,age);

//            Person person = (Person) object;

            System.out.println("here!!!!!!");
            System.out.println(object);
        }
    }

    //反射
    public void Reflection() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //引入第三方的反射包
        //先扫描指定路径  TypeAnnotationsScanner 表示扫描的是类型的注解
        Reflections reflections = new Reflections("com.example.demo.Model", new TypeAnnotationsScanner(), new SubTypesScanner(), new FieldAnnotationsScanner());
        //获取所有这个注解的对象
        Set<Class<?>> filedAspects = reflections.getTypesAnnotatedWith(FiledAspect.class);

        for (Class clazz : filedAspects){
            //实例化注解对象
            FiledAspect filedAspect = (FiledAspect) clazz.getDeclaredAnnotation(FiledAspect.class);
            String name = filedAspect.name();
            Object object = clazz.getDeclaredConstructor(String.class).newInstance(name);
            System.out.println(object.toString());
        }

        //上面一种方式是获取FiledAspect里面的每一个字段，再新建对应的class
        //下面是获取所有的标注注解的字段，再创建对应的class
        Set<Field> fields = reflections.getFieldsAnnotatedWith(FiledAspect.class);
        for (Field field:fields){
            FiledAspect filedAspect = field.getDeclaredAnnotation(FiledAspect.class);
            Person p = ((Class<Person>) field.getType()).getDeclaredConstructor().newInstance();
            p.setName(filedAspect.name());
            System.out.println(p.toString());
        }


    }
}
