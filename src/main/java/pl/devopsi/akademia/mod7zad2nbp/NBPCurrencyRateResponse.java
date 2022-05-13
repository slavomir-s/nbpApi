package pl.devopsi.akademia.mod7zad2nbp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NBPCurrencyRateResponse implements Serializable {
    @JsonProperty("table")
    private String table;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("code")
    private String code;
    @JsonUnwrapped
    @JsonProperty("rates")
    private Rate[] rates;
}
