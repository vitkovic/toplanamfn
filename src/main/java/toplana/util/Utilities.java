package toplana.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Utilities {
	
	public static LocalDate getPoslednjiDanPrethodnogMeseca() {
		Calendar cal = Calendar.getInstance();
		Date d = new Date();		
		
		/*
		   	String input = "01.10.2025";  // your string date
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	        LocalDate datem = LocalDate.parse(input, formatter);
		
	        Date d= Date.from(datem.atStartOfDay(ZoneId.systemDefault()).toInstant());
		*/
		cal.setTime(d);
		cal.add(Calendar.MONTH, -1);
        int lastDateOfPreviousMonth = cal.getActualMaximum(Calendar.DATE);
 
        // Get last date of previous month
        cal.set(Calendar.DATE, lastDateOfPreviousMonth);
 
        Date lastDateOfPreviousMonthDate = cal.getTime();
		
		LocalDate ld = lastDateOfPreviousMonthDate.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	 
		return ld;
	    
	}
	 

}
