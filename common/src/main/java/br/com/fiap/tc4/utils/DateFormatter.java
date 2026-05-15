package br.com.fiap.tc4.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    
    public final static String formatarData(String data) {

        LocalDate localDate = LocalDate.parse(data);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
        return localDate.format(formatter);
    }
}
