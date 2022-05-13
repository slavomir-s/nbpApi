package pl.devopsi.akademia.mod7zad2nbp;

import lombok.Data;

import java.util.Date;

@Data
public class ConversionResult {
    public ConversionResult( String quotationsDate,
     String convertFromCurrencyTicker,
     Double inputQuantity,
     String convertToCurrencyTicker,
     String outputQuantity){
        this.quotationsDate =quotationsDate;
        this.inputQuantity = inputQuantity;
        this.convertFromCurrencyTicker = convertFromCurrencyTicker;
        this.convertToCurrencyTicker = convertToCurrencyTicker;
        this.outputQuantity = outputQuantity;
    }
    public ConversionResult(){}
    private String quotationsDate;
    private String convertFromCurrencyTicker;
    private Double inputQuantity;
    private String convertToCurrencyTicker;
    private String outputQuantity;
}
