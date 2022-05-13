package pl.devopsi.akademia.mod7zad2nbp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rate implements Serializable {
    @JsonProperty("no")
    private String no;
    @JsonProperty("effectiveDate")
    private String effectiveDate;
    @JsonProperty("mid")
    private String mid;
}
