package com.degenerates.memium.security;

import com.degenerates.memium.security.jwt.AuthEntryPointJwt;
import com.degenerates.memium.security.jwt.AuthTokenFilter;
import com.degenerates.memium.security.services.SecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	SecurityUserDetailsService securityUserDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(securityUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
//			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
				.antMatchers("/v2/api-docs/**",
										"/swagger-ui.html",
										"/swagger-ui/**",
										"/swagger-resources/**",
										"/swagger-resources").permitAll()
				.antMatchers("/api/account/**").hasRole("USER")
				.antMatchers("/api/article/**").hasRole("USER")
				.antMatchers("/api/comment/**").hasRole("USER")
				.antMatchers("/api/feed/**").hasRole("USER")
				.antMatchers("/api/open/account/**").permitAll()
				.antMatchers("/api/open/article/**").permitAll()
				.antMatchers("/api/open/comment/**").permitAll()
				.antMatchers("/api/open/like/**").permitAll()
				.antMatchers("/api/open/sub/**").permitAll()
				.antMatchers("/api/search/**").permitAll()
				.antMatchers("/api/auth/**").permitAll()


				.anyRequest().authenticated();


		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
