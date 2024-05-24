package br.com.finance.config;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
public class SecurityUtils {
	
	public static final String SECRET = "finance";
	public static final String PREFIX = "Bearer ";
	public static final String HEADER = "Authorization";
	public static final long EXPIRATION_TIME = 2592000000l;
	
	//URLS liberadas para uso sem validacao
	public static final String LOGIN_URL = "/login";
	public static final String RESET_URL = "/reset";
	public static final String REGISTER_URL = "/register";
	public static final String RECOVERY_URL = "/recovery";
}
