package SpringBoot.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//@Configuration
//public class WebConfig extends WebMvcConfigurationSupport {
//    // 注册拦截器
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/")
//                .addPathPatterns("/problemset").excludePathPatterns("/problempage/**").excludePathPatterns("/register")
//                .excludePathPatterns("/status").excludePathPatterns("/team").excludePathPatterns("/contest")
//                .excludePathPatterns("/static/**");
//        super.addInterceptors(registry);
//    }
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry){
//        registry.addViewController("/").setViewName("index");
//        registry.addViewController("/problemset").setViewName("problemset");
//        registry.addViewController("/problempage/**").setViewName("problempage");
//        registry.addViewController("/status").setViewName("status");
//        registry.addViewController("/team").setViewName("team");
//    }
//}
