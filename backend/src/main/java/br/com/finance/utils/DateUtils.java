package br.com.finance.utils;

import java.time.LocalDate;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
public class DateUtils {

	/**
	 * Retorna o Data com o maior dia do mes.
	 * @param date
	 * @return
	 */
	public static LocalDate getMaxDateOfMonth(LocalDate date) {
		
		if(date==null) {
			date = LocalDate.now();
		}

		return date.withDayOfMonth(date.lengthOfMonth());
	}
	
	/**
	 * Retorna o Data com o menor dia do mes.
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDate getLeastDateOfMonth(LocalDate date) {
		
		if(date==null) {
			date = LocalDate.now();
		}
		
		return date.withDayOfMonth(1);
	}
}
