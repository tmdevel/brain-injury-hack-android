package com.totalmobile.risksapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageFragment extends Fragment{
  
	private int imageResource;
	
    public static ImageFragment newInstance(int resId){
    	
    	ImageFragment f = new ImageFragment();
    	
    	Bundle args = new Bundle();
        args.putInt("Resource id", resId);
        f.setArguments(args);

        return f;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	Bundle args = getArguments();
    	if(args != null) {
    		imageResource = args.getInt("Resource id");
    	}
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	View v = inflater.inflate(R.layout.image_fragment, container, false);
    	ImageView iView = (ImageView) v.findViewById(R.id.image_view);
		iView.setImageResource(imageResource);
    	return v;
    }
    
    
  }

