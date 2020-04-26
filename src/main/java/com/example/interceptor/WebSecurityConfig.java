package com.example.interceptor;

import com.example.auth.MyAuthenctiationFailureHandler;
import com.example.auth.MyAuthenctiationSuccessHandler;
import com.example.auth.MyExpiredSessionStrategy;
import com.example.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailServiceImpl myUserDetailService ;

    @Resource
    MyAuthenctiationSuccessHandler myAuthenctiationSuccessHandler;

    @Resource
    MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;

        @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }

      @Override
      protected void configure(HttpSecurity httpSecurity) throws Exception {
       httpSecurity
               .authorizeRequests()
               .antMatchers("/admin/**").hasAnyAuthority("admin")
               .anyRequest().permitAll()
               .and()
               .headers().frameOptions().disable()
               .and()
               .sessionManagement()
               .and().csrf().disable();
       httpSecurity.formLogin()
               .loginPage("/")
               .loginProcessingUrl("/login")
               .successHandler(myAuthenctiationSuccessHandler)
               .failureHandler(myAuthenctiationFailureHandler)
               .and()
               .logout()
               .logoutUrl("/logout")
               .deleteCookies("token")
               .logoutSuccessUrl("/")
               .invalidateHttpSession(true)
               .and().sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) //创建session
               .invalidSessionUrl("/") //session失效跳转
               .sessionFixation().migrateSession()
               .maximumSessions(1)
               .maxSessionsPreventsLogin(false)
               .expiredSessionStrategy(new MyExpiredSessionStrategy());
      }
    //忽略静态资源
    @Override
    public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/statis/**");
        super.configure(web);
    }

    @Autowired
      public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(myUserDetailService).passwordEncoder(passwordEncoder());
      }

   



}
