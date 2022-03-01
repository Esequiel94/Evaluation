package com.ear.muestra;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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

    private NestedScrollView nestedScrollView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    int page = 1, limit = 10;


    private Retrofit retrofit;


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
    private ArrayList<ApiData>listadoFiltrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new MyOpenHelper(MainActivity.this);

        nestedScrollView = findViewById(R.id.scroll_view);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progress_bar);

        listaDatosAMostrar = new ArrayList<>();

        //GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(listadoAdapter);

        obtenerDatos(page,limit);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight() ){

                    page++;

                    progressBar.setVisibility(View.VISIBLE);

                    obtenerDatos(page,limit);

                }
            }
        });

    }

    private void obtenerDatos(int page, int limit) {

        listadoElementos = new ArrayList<>();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openbrewerydb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<ApiData>> apiDataRespuestaCall= apiService.obtenerListado(page,limit);

        apiDataRespuestaCall.enqueue(new Callback<List<ApiData>>() {
            @Override
            public void onResponse(Call<List<ApiData>> call, Response<List<ApiData>> response) {

                String id, name,brewery_type,street,address_2,address_3,city,state
                        ,county_province,postal_code,country,longitude,latitude
                        ,phone,website_url,updated_at,created_at;

                if (response.isSuccessful() && response.body() != null){

                    progressBar.setVisibility(View.GONE);

                    listadoElementos=  response.body();

                    //db.borrarTodo();

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

                    listaDatosGuardados = db.getComments();

                    listadoAdapter=new ListadoAdapter(getApplicationContext(),listaDatosGuardados);

                    recyclerView.setAdapter(listadoAdapter);

                }else {

                    Toast.makeText(MainActivity.this ,"Error " + response.errorBody(),Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<ApiData>> call, Throwable throwable) {

                listaDatosGuardados = db.getComments();
                listadoAdapter=new ListadoAdapter(getApplicationContext(),listaDatosGuardados);
                recyclerView.setAdapter(listadoAdapter);

                Toast.makeText(MainActivity.this ,"Error de Conexión" ,Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.main_menu,menu);

        MenuItem searchItem= menu.findItem(R.id.action_search);

        SearchView searchView= (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                filter(s);
                return false;

            }
        });

        return true;
    }

    private void filter(String s) {

        ArrayList<ApiData> dataList= new ArrayList<>();

        for (ApiData item : listaDatosGuardados) {

            if (item.getName().toLowerCase().contains(s.toLowerCase())){

                dataList.add(item);

            }

        }
        if (dataList.isEmpty()){
            Toast.makeText(MainActivity.this ,"No hay elementos" ,Toast.LENGTH_SHORT).show();
        }else{
            listadoAdapter.setFilterList(dataList);
        }



        //listadoAdapter.getFilter().filter(s);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}