package uteq.student.json_rest_api.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Countries {
    @SerializedName("Country")
    @Expose
    private String Country;
    @SerializedName("TotalConfirmed")
    @Expose
    private String TotalConfirmed;
    @SerializedName("TotalDeaths")
    @Expose
    private String TotalDeaths;

    public String getCountry() {
        return Country;
    }

    public String getTotalConfirmed() {
        return TotalConfirmed;
    }

    public String getTotalDeaths() {
        return TotalDeaths;
    }

    @Override
    public String toString() {
        return "Countries{" +
                "Country='" + Country + '\'' +
                ", TotalConfirmed='" + TotalConfirmed + '\'' +
                ", TotalDeaths='" + TotalDeaths + '\'' +
                '}';
    }
}
