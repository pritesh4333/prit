package in.co.vyaparienterprise.model;

import java.util.ArrayList;

/**
 * Created by Bekir.Dursun on 25.10.2017.
 */

public class City {

    private String Id;
    private String Name;
    private ArrayList<String> Districts;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public ArrayList<String> getDistricts() {
        return Districts;
    }

    public void setDistricts(ArrayList<String> districts) {
        this.Districts = districts;
    }
}
