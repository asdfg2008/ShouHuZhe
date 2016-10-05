package com.crg.tecentdemo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.param.SearchParam;
import com.tencent.lbssearch.object.result.SearchResultObject;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapActivity;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

public class MainActivity extends MapActivity   {

    private TencentLocationManager locationManager;
    private TencentLocationRequest request;
    private TencentLocationListener listener;
    private TencentMap tencentMap;
    private MapView mapView;
    private Button rightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        request = TencentLocationRequest.create();
        request.setInterval(1000);
        locationManager = TencentLocationManager.getInstance(this);
        mapView = (MapView) findViewById(R.id.mapview);
        tencentMap = mapView.getMap();
        tencentMap.setZoom(17);

        rightButton = (Button) findViewById(R.id.onMyWay_rightBtn);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到 目的地选择
                Intent intent = new Intent(MainActivity.this, ChooseDestinationActivity.class);
                startActivity(intent);
            }
        });

        //加载一个进度  显示定位中.....
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("守护者");
        progressDialog.setMessage("正在定位中....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final TencentLocation[] temp = new TencentLocation[1];
        listener = new TencentLocationListener() {
            @Override
            public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
                 temp[0] = tencentLocation;
                Log.d("定位监听------------","------------");
                if (TencentLocation.ERROR_OK == i) {
                    // 定位成功
                    progressDialog.dismiss();
                    tencentMap.setCenter(new LatLng(tencentLocation.getLatitude(),tencentLocation.getLongitude()));
                    //在地图上添加位置标识
                    Marker marker = tencentMap.addMarker(new MarkerOptions()
                            .position(new LatLng(tencentLocation.getLatitude(),tencentLocation.getLongitude()))
                            .title("守护者")
                            .anchor(0.5f, 0.5f)
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker())
                            .draggable(true));
                    marker.showInfoWindow();


                    locationManager.removeUpdates(listener);

                } else {
                    // 定位失败
                }
            }

            @Override
            public void onStatusUpdate(String s, int i, String s1) {
            }
            

        };
        navigatorTo();

    }

    private void navigatorTo(){
        int error = locationManager.requestLocationUpdates(request, listener);
    }




}
