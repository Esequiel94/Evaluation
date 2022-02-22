package com.ear.muestra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.ear.muestra.models.ApiData;

public class DetallesActivity extends AppCompatActivity {


    TextView name,brewery_type,street,address_2,address_3,city,state
            ,county_province,postal_code,country,longitude,latitude
            ,phone,website_url,updated_at,created_at;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        name=(TextView) findViewById(R.id.Brewery_name);
        brewery_type=(TextView) findViewById(R.id.Brewery_type);
        street=(TextView) findViewById(R.id.Brewery_street);
        address_2=(TextView) findViewById(R.id.Brewery_address_2);
        address_3=(TextView) findViewById(R.id.Brewery_address_3);
        city=(TextView) findViewById(R.id.Brewery_city);
        state=(TextView) findViewById(R.id.Brewery_state);
        county_province=(TextView) findViewById(R.id.Brewery_county_province);
        postal_code=(TextView) findViewById(R.id.Brewery_postal_code);
        country=(TextView) findViewById(R.id.Brewery_country);
        longitude=(TextView) findViewById(R.id.Brewery_longitude);
        latitude=(TextView) findViewById(R.id.Brewery_latitude);
        phone=(TextView) findViewById(R.id.Brewery_phone);
        website_url=(TextView) findViewById(R.id.Brewery_website_url);
        updated_at=(TextView) findViewById(R.id.Brewery_updated_at);
        created_at=(TextView) findViewById(R.id.Brewery_created_at);

        Bundle datos = this.getIntent().getExtras();

        name.setText(datos.getString("name"));
        brewery_type.setText(datos.getString("brewery_type"));
        street.setText(datos.getString("street"));
        address_2.setText(datos.getString("address_2"));
        address_3.setText(datos.getString("address_3"));
        city.setText(datos.getString("city"));
        state.setText(datos.getString("state"));
        county_province.setText(datos.getString("county_province"));
        postal_code.setText(datos.getString("postal_code"));
        country.setText(datos.getString("country"));
        longitude.setText(datos.getString("longitude"));
        latitude.setText(datos.getString("latitude"));
        phone.setText(datos.getString("phone"));
        website_url.setText(datos.getString("website_url"));
        updated_at.setText(datos.getString("updated_at"));
        created_at.setText(datos.getString("created_at"));
    }
}