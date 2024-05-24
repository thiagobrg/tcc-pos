package br.com.finance.utils;

import java.math.BigDecimal;

public class MathUtils {
	
	
	/**
	 * 
	 * @param bigDecimals
	 */
	public static BigDecimal somar(BigDecimal...bigDecimals) {
		
		BigDecimal result = BigDecimal.ZERO;
		if(bigDecimals==null) {
			return result;
		}
		
		for (BigDecimal bigDecimal : bigDecimals) {
			result.add(bigDecimal);
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param bigDecimals
	 */
	public static BigDecimal subtract(BigDecimal num1, BigDecimal num2) {
		
		if(num1==null) {
			num1 =  BigDecimal.ZERO;
		}
		
		if(num2==null) {
			num2 =  BigDecimal.ZERO;
		}
		
		return num1.subtract(num2);
	}

}
