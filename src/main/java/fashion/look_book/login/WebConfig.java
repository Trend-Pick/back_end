package fashion.look_book.login;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 스프링이 제공하는 메서드를 인터셉터 해야함
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
<<<<<<< HEAD
                .excludePathPatterns("/", "/member/add", "/login", "/logout", "/validation/id",
=======
                .excludePathPatterns("/", "/member/add", "/login", "/logout","/mailConfirm",
                        "/member/findPassword",
>>>>>>> f5d3d52b09b6783d244aa052ed3836d0a48fe749
                        "/css/**", "/*.ico", "/error", "/session-info");
    }
}
