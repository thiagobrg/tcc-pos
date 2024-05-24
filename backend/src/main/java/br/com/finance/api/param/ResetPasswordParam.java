package br.com.finance.api.param;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
public class ResetPasswordParam {
	
	private String token;
	private String password;
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
