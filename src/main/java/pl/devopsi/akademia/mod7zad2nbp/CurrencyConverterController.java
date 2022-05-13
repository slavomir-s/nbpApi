package pl.devopsi.akademia.mod7zad2nbp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;

@Controller
@RequestMapping("/currencyconverter")
public class CurrencyConverterController {
    private final ConverterService converterService;

    public CurrencyConverterController(ConverterService converterService) {
        this.converterService = converterService;
    }

    @GetMapping("/convert")
    public ResponseEntity<ConversionResult> convert(@RequestBody  @Valid ConversionRequestData conversionRequestData) throws Exception {
        ConversionResult conversionResult = converterService.convertCurrency(conversionRequestData);
        if(conversionResult !=null)
            return ResponseEntity.status(HttpStatus.OK).body(conversionResult);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @GetMapping("/ratestable/{quotationDate}")
    public void getCurrencyRatesTableCSVFile(HttpServletResponse servletResponse, @PathVariable String quotationDate) throws IOException {
            servletResponse.setContentType("text/csv");
            servletResponse.addHeader("Content-Disposition","attachment; filename=\"currenciesRateTable.csv\"");

            converterService.getExchangeRatesTable(servletResponse.getOutputStream(), quotationDate);
    }

}
