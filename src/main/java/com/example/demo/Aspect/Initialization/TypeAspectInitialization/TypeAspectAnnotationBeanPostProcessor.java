package com.example.demo.Aspect.Initialization.TypeAspectInitialization;

import com.example.demo.Aspect.Interface.FiledAspect;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
//这个例子只是将添加了FiledAspect注解的类添加到注册中心里
public class TypeAspectAnnotationBeanPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        //创建一个扫描器
        ClassPathScanningCandidateComponentProvider classPathScanningCandidateComponentProvider = new ClassPathScanningCandidateComponentProvider(false);
        //扫描所有添加了@FiledAspect的 BeanDefinition
        classPathScanningCandidateComponentProvider.addIncludeFilter(new AnnotationTypeFilter(FiledAspect.class));
        Set<BeanDefinition> beanDefinitions = classPathScanningCandidateComponentProvider.findCandidateComponents("com.example.demo.Model");
        for (BeanDefinition beanDefinition:beanDefinitions){
            registry.registerBeanDefinition(beanDefinition.getBeanClassName(), beanDefinition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
