package com.cheung.mybatis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomUserDetailsService userDetailsService;
	
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new AuthenticationSuccessHandlerImpl();
	}
	
	 @Bean
		public AuthenticationFailureHandler authenticationFailureHandler() {
	        return new AuthenticationFailureHandlerImpl();
	    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/index", "/register", "/products/**").permitAll()
				.antMatchers("/admin/**", "/category").hasRole("ADMIN").anyRequest().authenticated().and().formLogin()
				.usernameParameter("userNameOrEmail").passwordParameter("password").loginPage("/login").failureHandler(authenticationFailureHandler()).permitAll()
				.successHandler(authenticationSuccessHandler()).and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
				.deleteCookies("JSESSIONID").permitAll().and().rememberMe().userDetailsService(this.userDetailsService)
				.key("uniqueAndSecret").tokenValiditySeconds(60 * 60);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**");
	}

}
