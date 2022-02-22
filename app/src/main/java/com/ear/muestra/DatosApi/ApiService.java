package com.ear.muestra.DatosApi;

import com.ear.muestra.models.ApiData;
import com.ear.muestra.models.ApiDataRespuesta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("breweries")
    Call<List<ApiData>>obtenerListado(@Query("limit") int limit, @Query("offset") int offset );


}
