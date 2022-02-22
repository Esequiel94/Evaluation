package com.ear.muestra;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ear.muestra.models.ApiData;

import java.util.ArrayList;
import java.util.List;

public class ListadoAdapter extends RecyclerView.Adapter<ListadoAdapter.ViewHolder> {

    private ArrayList<ApiData> dataSet;
    private Context context;

    public ListadoAdapter(Context context, List<ApiData> dataSet) {
        this.context=context;
        this.dataSet= (ArrayList<ApiData>) dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_api,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ListadoAdapter.ViewHolder holder, int position) {

        ApiData apiData = dataSet.get(position);

        holder.nombreTextView.setText(apiData.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), DetallesActivity.class);

                intent.putExtra("id",apiData.get_id());
                intent.putExtra("name",apiData.getName());
                intent.putExtra("brewery_type",apiData.getBrewery_type());
                intent.putExtra("street",apiData.getStreet());
                intent.putExtra("address_2",apiData.getAddress_2());
                intent.putExtra("address_3",apiData.getAddress_3());
                intent.putExtra("city",apiData.getCity());
                intent.putExtra("state",apiData.getState());
                intent.putExtra("county_province",apiData.getCounty_province());
                intent.putExtra("postal_code",apiData.getPostal_code());
                intent.putExtra("country",apiData.getCountry());
                intent.putExtra("longitude",apiData.getLatitude());
                intent.putExtra("latitude",apiData.getLongitude());
                intent.putExtra("phone",apiData.getPhone());
                intent.putExtra("website_url",apiData.getWebsite_url());
                intent.putExtra("updated_at",apiData.getUpdated_at());
                intent.putExtra("created_at",apiData.getCreated_at());

                v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nombreTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nombreTextView = (TextView) itemView.findViewById(R.id.nombre);
        }
    }
}
