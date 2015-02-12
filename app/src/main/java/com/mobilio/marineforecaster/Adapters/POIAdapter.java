package com.mobilio.marineforecaster.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilio.marineforecaster.POJO.POI;
import com.mobilio.marineforecaster.R;

import java.util.List;

/**
 * Created by nirajkumar.chauhan on 2/12/2015.
 */
public class POIAdapter extends ArrayAdapter<POI> {
    private final Context context;
    private final List<POI> pois;

    public POIAdapter(Context context, List<POI> pois) {
        super(context, R.layout.layout_poi_row); // 2nd parameter takes row layout
        this.context = context;
        this.pois = pois;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        POI poi= pois.get(position);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView = inflater.inflate(R.layout.layout_poi_row, parent, false);

            holder = new ViewHolder();
            holder.poiName = (TextView) convertView.findViewById(R.id.txtPoiName);
            holder.phone1 = (TextView) convertView.findViewById(R.id.phone1);
            holder.phone2 = (TextView) convertView.findViewById(R.id.phone2);
            holder.phone3 = (TextView) convertView.findViewById(R.id.phone3);
            holder.phone4 = (TextView) convertView.findViewById(R.id.phone4);
            holder.showInMap = (Button) convertView.findViewById(R.id.showMap);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.showInMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent will open with the data of lat and lang
                Toast.makeText(context,"Button Clicked",Toast.LENGTH_LONG).show();

            }
        });
        holder.poiName.setText(poi.getCity());
        Toast.makeText(context,poi.getCity(),Toast.LENGTH_LONG).show();
        holder.phone1.setText(poi.getPhone1());
        holder.phone2.setText(poi.getPhone2());
        holder.phone3.setText(poi.getPhone3());
        holder.phone4.setText(poi.getPhone4());
        return convertView;
        //return super.getView(position, convertView, parent);
    }

    public class ViewHolder{
        TextView poiName,phone1,phone2,phone3,phone4;
        Button showInMap;
    }
}
