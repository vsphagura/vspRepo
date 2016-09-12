package com.vsp.project1.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//import spittr.config.RootConfig.WebPackage;

@Configuration
@ComponentScan(basePackages={"com.vsp.project1"},
    excludeFilters={
        @Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)}
)
public class RootConfig {
  
}
