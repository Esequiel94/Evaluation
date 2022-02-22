package com.ear.muestra;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ear.muestra.DatosApi.ApiService;
import com.ear.muestra.models.ApiData;
import com.ear.muestra.models.ApiDataRespuesta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  {

    private Retrofit retrofit;


    private RecyclerView recyclerView;
    private ListadoAdapter listadoAdapter;

    private List<ApiData> listadoElementos;
    public List<ApiData> listaDatosGuardados;
    public List<ApiData> listaDatosAMostrar;

    private int offset;
    public boolean aptoparacargar;

    private MyOpenHelper db;

    private Context context;
    private Spinner spinner;
    private ArrayList<String>lista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new MyOpenHelper(MainActivity.this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(listadoAdapter);

        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled( RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0){
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastvisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (aptoparacargar){

                        if ( (visibleItemCount+pastvisibleItems) >= totalItemCount){
                            Toast.makeText(MainActivity.this ,"Final de la lista",Toast.LENGTH_SHORT).show();

                            aptoparacargar = false;
                            offset+=20;
                            obtenerDatos(offset);
                        }

                    }
                }
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openbrewerydb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aptoparacargar=true;
        offset=0;
        obtenerDatos(offset);

    }

    private void obtenerDatos(int offset) {

        listadoElementos = new ArrayList<>();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<ApiData>> apiDataRespuestaCall= apiService.obtenerListado(20,offset);

        apiDataRespuestaCall.enqueue(new Callback<List<ApiData>>() {
            @Override
            public void onResponse(Call<List<ApiData>> call, Response<List<ApiData>> response) {

                aptoparacargar = true;

                String id, name,brewery_type,street,address_2,address_3,city,state
                        ,county_province,postal_code,country,longitude,latitude
                        ,phone,website_url,updated_at,created_at;

                if (response.isSuccessful()){

                    listadoElementos=  response.body();

                    db.borrarTodo();

                    ApiData apiData;

                    for (int i = 0; i < listadoElementos.size(); i++) {

                        apiData = listadoElementos.get(i);

                        id = apiData.getId();
                        name =apiData.getName();
                        brewery_type =apiData.getBrewery_type();
                        street =apiData.getStreet();
                        address_2 =apiData.getAddress_2();
                        address_3 =apiData.getAddress_3();
                        city =apiData.getCity();
                        state =apiData.getState();
                        county_province =apiData.getCounty_province();
                        postal_code =apiData.getPostal_code();
                        country =apiData.getCountry();
                        longitude =apiData.getLongitude();
                        latitude =apiData.getLatitude();
                        phone =apiData.getPhone();
                        website_url =apiData.getWebsite_url();
                        updated_at =apiData.getUpdated_at();
                        created_at =apiData.getCreated_at();

                        db.insertar(id, name,brewery_type,street,address_2,address_3,city,state
                                ,county_province,postal_code,country,longitude,latitude
                                ,phone,website_url,updated_at,created_at);


                    }
                    listadoAdapter=new ListadoAdapter(getApplicationContext(),listadoElementos);

                    recyclerView.setAdapter(listadoAdapter);

                }else {

                    Toast.makeText(MainActivity.this ,"Error " + response.errorBody(),Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<ApiData>> call, Throwable throwable) {

                aptoparacargar = true;
                listaDatosGuardados = db.getComments();
                listadoAdapter=new ListadoAdapter(getApplicationContext(),listaDatosGuardados);
                recyclerView.setAdapter(listadoAdapter);

                Toast.makeText(MainActivity.this ,"Error Red, carga de bd" ,Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


}