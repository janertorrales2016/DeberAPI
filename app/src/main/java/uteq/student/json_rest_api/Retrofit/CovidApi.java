package uteq.student.json_rest_api.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface CovidApi {

    String BASE_URL="https://api.covid19api.com/";
    @Headers("X-Access-Token: 5cf9dfd5-3449-485e-b5ae-70a60e997864")
    @GET("summary")
    Call<Covid> getCountries();

}
