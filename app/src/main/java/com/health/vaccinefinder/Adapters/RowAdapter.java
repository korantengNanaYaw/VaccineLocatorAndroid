package com.health.vaccinefinder.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.health.vaccinefinder.DataBase.Vcenters;
import com.health.vaccinefinder.R;
import com.health.vaccinefinder.Utilities.GPSTracker;
import com.health.vaccinefinder.Utilities.Helper;

import java.util.Collections;
import java.util.List;

/**
 * Created by smsgh on 20/02/2017.
 */

public class RowAdapter extends RecyclerView.Adapter<RowAdapterViewHolder>{
    private Context context;
    private LayoutInflater inflater;
    List<Vcenters> data= Collections.emptyList();
    View view=null;
    GPSTracker gpsTracker;

    public RowAdapter(Context context, List<Vcenters> data,GPSTracker gpsTracker) {
        inflater= LayoutInflater.from(context);
        this.data=data;
        this.context=context;
        this.gpsTracker=gpsTracker;


    }

    @Override
    public RowAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.recycle_row, parent, false);
        // } else if(Application.BillersRecycleViewLayoutType.equalsIgnoreCase("List")){
        // view= inflater.inflate(R.layout.list_item_layout_billers,parent,false);}

        RowAdapterViewHolder holder = new RowAdapterViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RowAdapterViewHolder holder, int position) {

        final Vcenters items=data.get(position);

        holder.txthiddenId.setText(items.getId()+"");
        holder.region.setText(items.getRegion());
        holder.district.setText(items.getDistrict());
        holder.subdistrict.setText(items.getSubdistrict());
        holder.facilityname.setText(items.getFacility());
        holder.miles.setText(Helper.getMetersToMiles(gpsTracker.getDistanceT0(Double.parseDouble(items.getLatitude()) ,Double.parseDouble(items.getLongitude())) ) + " miles");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
class RowAdapterViewHolder extends RecyclerView.ViewHolder {
    TextView region,district,subdistrict,facilityname,miles,txthiddenId;

    public RowAdapterViewHolder(View itemView) {
        super(itemView);

        txthiddenId=(TextView)itemView.findViewById(R.id.txthiddenId);
        region=(TextView)itemView.findViewById(R.id.txtRegion);
        district=(TextView)itemView.findViewById(R.id.txtDistrict);
        subdistrict=(TextView)itemView.findViewById(R.id.txtsubDistrict);
        facilityname=(TextView)itemView.findViewById(R.id.txtFacilityName);
        miles=(TextView)itemView.findViewById(R.id.txtMiles);




    }
}