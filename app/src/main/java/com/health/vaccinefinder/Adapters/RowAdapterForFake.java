package com.health.vaccinefinder.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.health.vaccinefinder.DataBase.FaceVcenter;
import com.health.vaccinefinder.DataBase.Vcenters;
import com.health.vaccinefinder.R;
import com.health.vaccinefinder.Utilities.GPSTracker;
import com.health.vaccinefinder.Utilities.Helper;

import java.util.Collections;
import java.util.List;

/**
 * Created by smsgh on 23/02/2017.
 */

public class RowAdapterForFake extends RecyclerView.Adapter<RowAdapterFakeViewHolder>{
    private Context context;
    private LayoutInflater inflater;
    List<FaceVcenter> data= Collections.emptyList();
    View view=null;
    GPSTracker gpsTracker;
    Helper helper;

    public RowAdapterForFake(Context context, List<FaceVcenter> data, GPSTracker gpsTracker) {
        inflater= LayoutInflater.from(context);
        this.data=data;
        this.context=context;
        this.gpsTracker=gpsTracker;
        helper= new Helper();


    }

    @Override
    public RowAdapterFakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.recycle_row, parent, false);
        // } else if(Application.BillersRecycleViewLayoutType.equalsIgnoreCase("List")){
        // view= inflater.inflate(R.layout.list_item_layout_billers,parent,false);}

        RowAdapterFakeViewHolder holder = new RowAdapterFakeViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RowAdapterFakeViewHolder holder, int position) {

        final FaceVcenter items=data.get(position);

        holder.txthiddenId.setText(items.getId()+"");
        holder.region.setText(items.getRegion());
        holder.district.setText(items.getDistrict());
        holder.subdistrict.setText(items.getSubdistrict());
        holder.facilityname.setText(items.getFacility());
        holder.txtCordinates.setText(items.getLatitude() + "," + items.getLongitude());

        int kilometers = helper.calculateDistanceInKilometer(gpsTracker.getLatitude(),gpsTracker.getLongitude(),Double.parseDouble(items.getLatitude()),Double.parseDouble(items.getLongitude()));
        holder.miles.setText(kilometers + " km");

        Log.d("LatLong","current latitude " + gpsTracker.getLongitude() + " , " +gpsTracker.getLatitude() + " destination longitude " + items.getLongitude() + " , " + items.getLatitude() + "Kilometers" +kilometers);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
class RowAdapterFakeViewHolder extends RecyclerView.ViewHolder {
    TextView region,district,subdistrict,facilityname,miles,txthiddenId,txtCordinates;

    public RowAdapterFakeViewHolder(View itemView) {
        super(itemView);

        txthiddenId=(TextView)itemView.findViewById(R.id.txthiddenId);
        region=(TextView)itemView.findViewById(R.id.txtRegion);
        district=(TextView)itemView.findViewById(R.id.txtDistrict);
        subdistrict=(TextView)itemView.findViewById(R.id.txtsubDistrict);
        facilityname=(TextView)itemView.findViewById(R.id.txtFacilityName);
        miles=(TextView)itemView.findViewById(R.id.txtMiles);
        txtCordinates=(TextView)itemView.findViewById(R.id.txtcordinates);




    }
}