package uteq.student.json_rest_api;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uteq.student.json_rest_api.Retrofit.Countries;
import uteq.student.json_rest_api.Retrofit.Covid;
import uteq.student.json_rest_api.Retrofit.CovidApi;
import uteq.student.json_rest_api.WebService.Asynchtask;
import uteq.student.json_rest_api.WebService.WebService;


public class MainActivity extends AppCompatActivity implements Asynchtask {
    public static final String URL = "https://api.covid19api.com/summary";
    private RequestQueue mQueve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueve = Volley.newRequestQueue(this);
    }

    public void btenviar(View view) {
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws = new WebService(URL,
                datos, this, this);
        ws.execute("GET", "X-Access-Token", "5cf9dfd5-3449-485e-b5ae-70a60e997864");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        TextView txtre = (TextView) findViewById(R.id.txtresult);
        txtre.setText("");
        txtre.setMovementMethod(new ScrollingMovementMethod());
        String resultado = "Asynchtask" + "\n" + "Pais" + "\t\t" + "Contagiados" + "\t\t" + "Muertos";
        JSONObject jsnobject = new JSONObject(result);
        JSONArray jsonArray = jsnobject.getJSONArray("Countries");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject explrObject = jsonArray.getJSONObject(i);
            resultado = resultado + "\n" + explrObject.getString("Country").toString() + "\t\t" + explrObject.getString("TotalConfirmed").toString()
                    + "\t\t" + explrObject.getString("TotalDeaths").toString();
        }
        txtre.setText(resultado);
    }

    public void btenviar2(View view) {
        voley();
    }

    private void voley() {
        TextView txtre = (TextView) findViewById(R.id.txtresult);
        txtre.setText("");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Countries");
                            String resultado = "VOLLEY" + "\n" + "Pais" + "\t\t" + "Contagiados" + "\t\t" + "Muertos";
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject explrObject = jsonArray.getJSONObject(i);
                                resultado = resultado + "\n" + explrObject.getString("Country").toString() + "\t\t" + explrObject.getString("TotalConfirmed").toString()
                                        + "\t\t" + explrObject.getString("TotalDeaths").toString();
                            }
                            txtre.setText(resultado);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("X-Access-Token", "5cf9dfd5-3449-485e-b5ae-70a60e997864");
                return headers;
            }
        };
        mQueve.add(request);
    }

    private static final String BASE_URL = "https://api.covid19api.com/";

    public void btenviar3(View view) {
        TextView txtre = (TextView) findViewById(R.id.txtresult);
        txtre.setText("");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CovidApi covidApi = retrofit.create(CovidApi.class);
        Call<Covid> call = covidApi.getCountries();
        call.enqueue(new Callback<Covid>() {
            @Override
            public void onResponse(Call<Covid> call, retrofit2.Response<Covid> response) {
                String resultado = "RETROFIT" + "\n" + "Pais" + "\t\t" + "Contagiados" + "\t\t" + "Muertos";
                ArrayList<Countries> countrieslist = response.body().getCountries();
                for (int i = 0; i < countrieslist.size(); i++) {
                    resultado = resultado + "\n" + countrieslist.get(i).getCountry() + "\t\t" + countrieslist.get(i).getTotalConfirmed() +
                            "\t\t" + countrieslist.get(i).getTotalDeaths();
                }
                txtre.setText(resultado);
            }

            @Override
            public void onFailure(Call<Covid> call, Throwable t) {
                txtre.setText("ERROR" + t.getMessage());
            }
        });
    }
}