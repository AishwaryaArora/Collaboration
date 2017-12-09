package net.aish.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc 
//<mvc:annotation-driven></mvc:annotation-driven>
@ComponentScan(basePackages="net.aish")
//<context:component-scan base-package="net.aish"></context:component-scan>

public class WebConfig {

}
