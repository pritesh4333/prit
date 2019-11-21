package in.co.vyaparienterprise.model.response.dto;

import in.co.vyaparienterprise.model.Currency;
import in.co.vyaparienterprise.model.City;

import java.util.ArrayList;

/**
 * Created by Bekir.Dursun on 25.10.2017.
 */

public class AllBulkDataDTO {

    private ArrayList<String> Countries;
    private ArrayList<City> Cities;
    private ArrayList<Currency> Currencies;
    private ArrayList<String> Units;
    private ArrayList<KeyValueDTO> KdvValues;
    private ArrayList<KeyValueDTO> OtvValues;
    private ArrayList<KeyValueDTO> DiscountTypeValues;
    private long LastModifiedDate;

    public ArrayList<String> getCountries() {
        return Countries;
    }

    public ArrayList<City> getCities() {
        return Cities;
    }

    public ArrayList<Currency> getCurrencies() {
        return Currencies;
    }

    public ArrayList<String> getUnits() {
        return Units;
    }

    public ArrayList<KeyValueDTO> getKdvValues() {
        return KdvValues;
    }

    public ArrayList<KeyValueDTO> getOtvValues() {
        return OtvValues;
    }

    public ArrayList<KeyValueDTO> getDiscountTypeValues() {
        return DiscountTypeValues;
    }

    public long getLastModifiedDate() {
        return LastModifiedDate;
    }
}