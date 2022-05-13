package pl.devopsi.akademia.mod7zad2nbp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NBPCurrencyTableRatesResponse {
    @JsonProperty("table")
    private String table;
    @JsonProperty("no")
    private String no;
    @JsonProperty("effectiveDate")
    private String effectiveDate;
    @JsonUnwrapped
    @JsonProperty("rates")
    private List<SingleCurrencyRate> rates;
}
