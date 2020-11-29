package uteq.student.json_rest_api.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Covid {
    @SerializedName("Countries")
    @Expose
    private ArrayList<Countries> countries;

    public ArrayList<Countries> getCountries() {
        return countries;
    }

    @Override
    public String toString() {
        return "Covid{" +
                "countries=" + countries +
                '}';
    }
}
