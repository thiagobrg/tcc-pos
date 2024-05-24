package br.com.finance.config;

import static br.com.finance.config.SecurityUtils.EXPIRATION_TIME;
import static br.com.finance.config.SecurityUtils.HEADER;
import static br.com.finance.config.SecurityUtils.PREFIX;
import static br.com.finance.config.SecurityUtils.SECRET;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.finance.api.dto.UserDTO;
import br.com.finance.domain.entities.User;
import br.com.finance.domain.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
public class JWTAuthentication extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private UserService userService;

	public JWTAuthentication(AuthenticationManager authenticationManager, UserService userService) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		
		try {
			User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
			
			return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		
		String username = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername();
		
		UserDTO user = userService.findByEmail(username);
		response.addHeader("USER", new ObjectMapper().writeValueAsString(user));
		
		String token = Jwts.builder().setSubject(username).claim("user", new ObjectMapper().writeValueAsString(user))
									 .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
									 .signWith(SignatureAlgorithm.HS512, SECRET)
									 .compact();
		
		response.addHeader(HEADER, PREFIX + token);
		
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		response.addHeader("msg", "Usuario ou senha invalido!");
		super.unsuccessfulAuthentication(request, response, failed);
	}
}
