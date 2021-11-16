package com.cheung.mybatis;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomUserDetailsService userDetailsService;

	@Autowired
	AuthenticationSuccessHandlerImpl authenticationSuccessHandler;


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/index", "/register", "/products/**").permitAll()
				.antMatchers("/admin/**", "/category").hasRole("ADMIN").anyRequest().authenticated().and().formLogin()
				.usernameParameter("userNameOrEmail").passwordParameter("password").loginPage("/login").permitAll()
				.successHandler(authenticationSuccessHandler).and().logout().logoutUrl("/logout").logoutSuccessUrl("/").deleteCookies("JSESSIONID")
				.permitAll().and().rememberMe().userDetailsService(this.userDetailsService)
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
