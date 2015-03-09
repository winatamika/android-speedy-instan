package com.speedy.instant;

import java.util.List;



import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

class CustomAdapterView extends LinearLayout {        
	public CustomAdapterView(Context context, LocationHotspot lt) 
	{
		super( context );		
		/*setOnClickListener((OnClickListener) context);
		setClickable(true);
		setFocusable(false);*/
		//setId(device.getDeviceID());
		    
		//container is a horizontal layer
		setOrientation(LinearLayout.HORIZONTAL);
		//setPadding(0, 6, 0, 6);
		
		//image:params
		LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		Params.setMargins(6, 0, 6, 0);
//		//image:itself
//		ImageView ivLogo = new ImageView(context);
//		// load image
//			ivLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_hotspot));
		//else if (lt.getDeviceType() == 1)
		//	ivLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.logo2));
		//image:add
			
			//edit.setBackground(getResources().getDrawable(R.drawable.edit));
			//edit.setColorFilter(Color.RED);
			
			//PanelV.addView(edit);
		
		//final LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT,70); 
		
		ImageButton edit = new ImageButton (context);
		//edit.setBackgroundColor(Color.RED);
		edit.setImageResource(R.drawable.edit);
		edit.setBackgroundColor(Color.RED);
		edit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.d("click", "ok");
				
			}
		});
		addView(edit, Params);
		
		LinearLayout.LayoutParams ParamsDelete = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		Params.setMargins(6, 0, 6, 0);
		ImageButton delete = new ImageButton(context);
		delete.setImageResource(R.drawable.discard);
		delete.setBackgroundColor(Color.RED);
		
		delete.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.d("click", "ok");
				
			}
		});
		addView(delete, ParamsDelete);
		
		
		
		//vertical layer for text
		Params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		LinearLayout PanelV = new LinearLayout(context);
		PanelV.setOrientation(LinearLayout.VERTICAL);
		PanelV.setGravity(Gravity.BOTTOM);
		

		
		TextView textName = new TextView( context );
		textName.setTextSize(16);
		textName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		textName.setText( lt.getName());
		textName.setTextColor(Color.RED);
		PanelV.addView(textName);       
		
		TextView textAddress = new TextView( context );
		textAddress.setTextSize(12);
		textAddress.setText(lt.getAddress());
		textAddress.setTextColor(Color.BLACK);
		PanelV.addView(textAddress);    
		
		
		
		
		addView(PanelV, Params);
	}
}
public class LocationAdapter extends BaseAdapter {
	public static final String LOG_TAG = "BI::CA";
    private Context context;
    private List<LocationHotspot> locations;

    public LocationAdapter(Context context, List<LocationHotspot> locations ) { 
        this.context = context;
        this.locations = locations;
    }

    public int getCount() {                        
        return locations.size();
    }

    public Object getItem(int position) {     
        return locations.get(position);
    }

    public long getItemId(int position) {  
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) 
    { 
    	final LocationHotspot lt = locations.get(position);
        View v = new CustomAdapterView(this.context, lt );
        
        v.setBackgroundColor((position % 2) == 1 ? Color.WHITE : Color.WHITE);
        v.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("sd",lt.getAddress());
				
			}
		});
        
        
        
        /*v.setOnClickListener(new OnItemClickListener(position));*/
        return v;
    }

    public void onClick(View v,LocationHotspot lt) {
            Log.v(LOG_TAG, lt.getAddress());
            
            
    }

}
