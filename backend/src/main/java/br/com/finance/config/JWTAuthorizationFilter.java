package br.com.finance.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import br.com.finance.domain.service.CustomUserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	private final CustomUserDetailService customUserDetailService;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, CustomUserDetailService customUserDetailService) {
		super(authenticationManager);
		
		this.customUserDetailService = customUserDetailService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		String header = request.getHeader(SecurityUtils.HEADER);
		
		if(StringUtils.isEmpty(header) || !header.trim().startsWith(SecurityUtils.PREFIX)) {
			SecurityContextHolder.clearContext();
			chain.doFilter(request, response);
			return;
		}
		
		UsernamePasswordAuthenticationToken token = null;
		
		try {
			token = getUsernamePasswordAuthenticationToken(header.trim());
		} catch (Exception e) {
			
			if( e instanceof ExpiredJwtException  ) {
				response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
				return;
			}
			e.printStackTrace();
		}
			
		SecurityContextHolder.getContext().setAuthentication(token);
		chain.doFilter(request, response);
	}
	
	/**
	 * Trata o HEADER e retorna o Username
	 * 
	 * @param header
	 * @return
	 */
	public UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String header) throws Exception {
		
		//Remove o PREFIX.
		header = header.replace(SecurityUtils.PREFIX, "");
		
		String userName = Jwts.parser().setSigningKey(SecurityUtils.SECRET).parseClaimsJws(header).getBody().getSubject();
		
		UserDetails userDetails = customUserDetailService.loadUserByUsername(userName);
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, null, userDetails.getAuthorities());
		
		return token;
	}
}
