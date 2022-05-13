package pl.devopsi.akademia.mod7zad2nbp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.devopsi.akademia.mod7zad2nbp.exceptions.RestTemplateException;
import pl.devopsi.akademia.mod7zad2nbp.exceptions.RestTemplateResponseErrorHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletOutputStream;
import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ConverterService {
    private final String requestUrlMask = "http://api.nbp.pl/api/exchangerates/rates/a/%s/%s/?format=json";
    private final String requestExchangeRatesTable = "https://api.nbp.pl/api/exchangerates/tables/a/%s";

    private RestTemplate restTemplate;

    public ConverterService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
    }

    public ConversionResult convertCurrency(ConversionRequestData conversionRequestData) throws Exception {

            double exchangeAvgRateA = getAvgExchangeRate(
                    conversionRequestData.getConvertFromCurrencyTicker(),
                    conversionRequestData.getQuotationsDate());
            double exchangeAvgRateB = getAvgExchangeRate(
                    conversionRequestData.getConvertToCurrencyTicker(),
                    conversionRequestData.getQuotationsDate());


            double currencyRatio = exchangeAvgRateA / exchangeAvgRateB;
            double outputQuantity = conversionRequestData.getInputQuantity() * currencyRatio;
            String outputQuantityStr = formatDecimalNumber(outputQuantity,"#.##",RoundingMode.CEILING);

            return new ConversionResult(
                    conversionRequestData.getQuotationsDate(),
                    conversionRequestData.getConvertFromCurrencyTicker(),
                    conversionRequestData.getInputQuantity(),
                    conversionRequestData.getConvertToCurrencyTicker(),
                    outputQuantityStr
                    );
    }
    private double getAvgExchangeRate(String currencyTicker,String quotationDate) throws Exception {
        if (currencyTicker.toUpperCase(Locale.ROOT).equals("PLN"))
            return 1;
        String requestUrl = String.format(requestUrlMask,
                currencyTicker,quotationDate);
            ResponseEntity<NBPCurrencyRateResponse> response = restTemplate.getForEntity(requestUrl, NBPCurrencyRateResponse.class);
            NBPCurrencyRateResponse nbpResponse = response.getBody();
            return Double.parseDouble((nbpResponse.getRates())[0].getMid());
    }

    private String formatDecimalNumber(Double value, String decimalFormatPattern, RoundingMode roundingMode){
        DecimalFormat decimalFormat = new DecimalFormat(decimalFormatPattern);
        decimalFormat.setRoundingMode(roundingMode);
        return decimalFormat.format(value);
    }

    public void getExchangeRatesTable(ServletOutputStream servletOutputStream, String quotationDate) throws IOException {
        String requestUrl = String.format(requestExchangeRatesTable,quotationDate);

        ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
        String responseJson = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        CollectionType listType  = mapper.getTypeFactory().constructCollectionType(ArrayList.class, NBPCurrencyTableRatesResponse.class);
        List<NBPCurrencyTableRatesResponse> nbpCurrencyTableRatesResponse = mapper.readValue(responseJson, listType);

        /*BufferedWriter fwriter = new BufferedWriter(new FileWriter("test.csv", true));
        writeCurrencyRatesToCsv(fwriter,(nbpCurrencyTableRatesResponse.get(0).getRates()));*/

        writeCurrencyRatesToCsv(servletOutputStream,(nbpCurrencyTableRatesResponse.get(0).getRates()));
    }

    public void writeCurrencyRatesToCsv(ServletOutputStream servletOutputStream, List<SingleCurrencyRate> rates ) throws IOException {
        PrintWriter pw = new PrintWriter(servletOutputStream);
        try (CSVPrinter csvPrinter = new CSVPrinter(pw, CSVFormat.DEFAULT)) {
            for (SingleCurrencyRate currencyRate : rates) {
                csvPrinter.printRecord(currencyRate.getCurrency(),currencyRate.getCode(), currencyRate.getMid());
            }
        } catch (IOException e) {
            System.err.println(e);
            throw e;
        }
    }
}
