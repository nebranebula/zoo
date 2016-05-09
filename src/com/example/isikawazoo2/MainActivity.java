package com.example.isikawazoo2;

import java.net.MalformedURLException;
import java.net.URL;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Toast;

public class MainActivity extends Activity {
	 private GoogleMap mMap;
//	 private static final LatLng SAMPLE = new LatLng(35.6872, 139.7704);
//	 private static final LatLng ISIKAWA_ZOO = new LatLng(36.433611, 136.545423);

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        try {
	            MapsInitializer.initialize(this);
	            MapView mapView = (MapView) this.findViewById(R.id.map);
	            mapView.onCreate(savedInstanceState);
	            this.setupIfNeeded();
	        } catch (GooglePlayServicesNotAvailableException e) {
	            e.printStackTrace();
	        }
	    }

	    @Override
	    protected void onDestroy() {
	        MapView mapView = (MapView) this.findViewById(R.id.map);
	        mapView.onDestroy();
	        super.onDestroy();
	    }

	    @Override
	    public void onLowMemory() {
	        MapView mapView = (MapView) this.findViewById(R.id.map);
	        mapView.onLowMemory();
	        super.onLowMemory();
	    }

	    @Override
	    protected void onPause() {
	        MapView mapView = (MapView) this.findViewById(R.id.map);
	        mapView.onPause();
	        super.onPause();
	    }

	    @Override
	    protected void onResume() {
	        super.onResume();
	        MapView mapView = (MapView) this.findViewById(R.id.map);
	        mapView.onResume();
	    }

	    @Override
	    protected void onSaveInstanceState(Bundle outState) {
	        MapView mapView = (MapView) this.findViewById(R.id.map);
	        mapView.onSaveInstanceState(outState);
	    }

	    private void setupIfNeeded() {
	        if (this.mMap == null) {
	            MapView mapView = (MapView) this.findViewById(R.id.map);
	            this.mMap = mapView.getMap();
	            if (this.mMap != null) {
	                this.setup();
	            } else {
	                Toast.makeText(this, "Cannot start !", Toast.LENGTH_SHORT).show();
	            }
	        }
	    }
	    
	    private void setup() {
	    	   //現在位置を獲得する
	        mMap.setMyLocationEnabled(true);
	    	
	         this.mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); // Googleさんの通常の地図を表示
	         //this.mMap.setMapType(GoogleMap.MAP_TYPE_NONE);  // Googleさんの地図を表示しない
	        TileProvider tileProvider = new UrlTileProvider(256, 256) {
	            @Override
	            public URL getTileUrl(int x, int y, int zoom) {
	                String url = "http://s1250044.sakura.ne.jp/maptile/"
	                        + zoom + "/" + x + "/" + y + ".png";
//	                http://s1250044.sakura.ne.jp/maptile/	// 用意したマップタイルURL
//	                http://www.finds.jp/ws/tmc/1.0.0/Tokyo5000-900913-L/	// サンプル

	                try {
	                    return new URL(url);
	                } catch (MalformedURLException e) {
	                    e.printStackTrace();
	                    return null;
	                }
	            }
	        };
	        TileOverlay tileOverlay = this.mMap
	                .addTileOverlay(new TileOverlayOptions()
	                        .tileProvider(tileProvider));
	        this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
	        		36.433611, 136.545423), 14.5F));
	    }
}
