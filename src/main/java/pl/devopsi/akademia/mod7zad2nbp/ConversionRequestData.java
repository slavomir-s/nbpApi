package pl.devopsi.akademia.mod7zad2nbp;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class ConversionRequestData {
    @NotEmpty
    private String quotationsDate;// data w formacie YYYY-MM-DD
    @NotEmpty
    private String convertFromCurrencyTicker;
    @Min(0)
    private Double inputQuantity;
    @NotEmpty
    private String convertToCurrencyTicker;
}
