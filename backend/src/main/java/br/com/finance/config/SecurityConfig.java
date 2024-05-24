package br.com.finance.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import br.com.finance.domain.service.CustomUserDetailService;
import br.com.finance.domain.service.UserService;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private UserService userService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable()
	    	.cors().and()
	        .authorizeRequests()
	            .antMatchers(HttpMethod.POST, SecurityUtils.LOGIN_URL).permitAll()
	            .antMatchers(HttpMethod.POST, SecurityUtils.RECOVERY_URL).permitAll()
	            .antMatchers(HttpMethod.POST, SecurityUtils.REGISTER_URL).permitAll()
	            .antMatchers("/core/**").hasRole("USER")
	            .antMatchers("/admin/**").hasRole("ADMIN")
	            .anyRequest().permitAll()
	        .and()
	        .addFilter(new JWTAuthentication(authenticationManager(), userService))
	        .addFilter(new JWTAuthorizationFilter(authenticationManager(), customUserDetailService));
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Bean
    CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*"); // Use "*" to allow all origins
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("Authorization"); // Expose the Authorization header
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
