package com.example.demo.Aspect.Initialization;

import com.example.demo.Aspect.Interface.FiledAspect;
import com.example.demo.Model.Person;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

@Component
public class FiledAspectInitialization {

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
            FiledAspect filedAspect = (FiledAspect) clazz.getDeclaredAnnotation(FiledAspect.class);
            String name = filedAspect.name();
            Object object = clazz.getDeclaredConstructor(String.class,int.class).newInstance(name,1);

            Person person = (Person) object;
            System.out.println("here!!!!!!");
            System.out.println(person);
        }
    }
}
