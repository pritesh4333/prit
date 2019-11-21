package in.co.vyapari.model.response.dto;

import java.util.Date;

/**
 * Created by Bekir.Dursun on 25.10.2017.
 */

public class ExchangeRateDTO {

    private String CurrencyType;
    private Date Date;
    private double ExchangeRate;

    public String getCurrencyType() {
        return CurrencyType;
    }

    public Date getDate() {
        return Date;
    }

    public double getExchangeRate() {
        return ExchangeRate;
    }
}
