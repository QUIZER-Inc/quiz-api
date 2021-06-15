package ru.project.quiz.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.project.quiz.domain.enums.ituser.PermissionType;
import ru.project.quiz.jwt.JwtAuthenticationEntryPoint;
import ru.project.quiz.jwt.filter.JwtRequestFilter;
import ru.project.quiz.service.ituser.Impl.ITUserServiceImpl;


@Configuration
@EnableWebSecurity
@EnableConfigurationProperties
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    private final ITUserServiceImpl itUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(ITUserServiceImpl itUserService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.itUserService = itUserService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/quiz").hasAuthority(PermissionType.GENERATE_TESTS.name())
                .antMatchers("/api/questions/random/**").hasAuthority(PermissionType.GENERATE_TESTS.name())
                .antMatchers("/api/users/**").hasAuthority(PermissionType.GRAND_PERMISSION.name())
                .antMatchers("/api/questions/**").hasAuthority(PermissionType.GRAND_PERMISSION.name())
                .antMatchers("/api/categories/**").hasAuthority(PermissionType.GRAND_PERMISSION.name())
                .antMatchers("/api/quiz_sample/**").hasAuthority(PermissionType.GRAND_PERMISSION.name())
                .antMatchers("/api/questions/*").hasAuthority(PermissionType.GENERATE_TESTS.name())
                .antMatchers("/api/roles/**").hasAuthority(PermissionType.GRAND_PERMISSION.name())
                .antMatchers("/api/rating/**").hasAuthority(PermissionType.GRAND_PERMISSION.name())
                .antMatchers("/api/quiz/*").hasAuthority(PermissionType.GRAND_PERMISSION.name())
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(itUserService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
