package pl.devopsi.akademia.mod7zad2nbp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NBPTableList {
    private List<NBPCurrencyTableRatesResponse> nbpResp;
}
