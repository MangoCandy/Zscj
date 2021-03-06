package com.hnxind.map;

import android.content.Context;
import android.graphics.Point;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;

import com.baidu.mapapi.search.route.SuggestAddrInfo;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.hnxind.setting.Theme;
import com.hnxind.utils.OverlayManager;
import com.hnxind.utils.TransitRouteOverlay;
import com.hnxind.utils.WalkingRouteOverlay;
import com.hnxind.zscj.R;

import java.util.ArrayList;
import java.util.List;

public class Act_Map extends AppCompatActivity implements OnGetRoutePlanResultListener {
    MapView mapView;
    Context context=this;
    BaiduMap baiduMap;
    TextView addr;//地名
    View view;
    String addttext;//定位地址
    BDLocation mylocation;

    OverlayManager overlayManager=null;
    public LocationClient mLocationClient = null;

    List<String> luxians=new ArrayList<>();

    OverlayOptions positionIcon;
    public BDLocationListener myListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            mylocation=bdLocation;
            LatLng point=new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
            Log.i("asd",bdLocation.getAddrStr());
            addttext=bdLocation.getAddrStr();
            if(addr!=null){addr.setText(addttext.replaceAll("null","定位失败，尝试开启网络或GPS权限之后重新定位"));}

            mLocationClient.stop();
            //定义地图状态
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(point)
                    .zoom(20)
                    .build();
            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            //改变地图状态
            baiduMap.setMapStatus(mMapStatusUpdate);
            if(positionO!=null){
                positionO.remove();
            }
            initPosition(point);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );    //注册监听函数
        initLocation();
        setContentView(R.layout.activity_map);
        initToolbar();
        initView();
        initSearch();

        dingwei(null);
    }

    Overlay positionO;
    public void initPosition(LatLng point){
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.iconfont_position);
        positionIcon=new MarkerOptions().icon(bitmap).position(point);
        positionO=baiduMap.addOverlay(positionIcon);
    }
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
    public void initToolbar(){
        Theme theme=new Theme(this);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.iconfont_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        toolbar.setTitleTextColor(theme.getTitleColor());
        toolbar.setBackgroundColor(theme.MainColor);

    }

    LuxianAdapter adapter;
    public void initView(){
        dingwei=(FloatingActionButton)findViewById(R.id.dingwei);
        luxian=(FloatingActionButton)findViewById(R.id.view);

        addr=(TextView)findViewById(R.id.addr);

        mapView=(MapView)findViewById(R.id.mapview);
        mapView.showZoomControls(false);

        baiduMap=mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        UiSettings uiSettings=baiduMap.getUiSettings();

        uiSettings.setCompassEnabled(true);
        uiSettings.setCompassPosition(new Point(200,200));

        bottom=(LinearLayout)findViewById(R.id.bottom) ;
        tips=(TextView)findViewById(R.id.tips);
        bottomlist=(LinearLayout)findViewById(R.id.bottomlist);
        listView=(ListView)findViewById(R.id.listview);
        adapter=new LuxianAdapter(luxians,this);
        listView.setAdapter(adapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        search.destroy();
        mapView.onDestroy();
    }

    public void dingwei(View view) {//定位
        mLocationClient.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    RoutePlanSearch search;
    public void initSearch(){
        search=RoutePlanSearch.newInstance();
        search.setOnGetRoutePlanResultListener(this);
    }
    public void searchBus(View view){
        initSearch();
        if(!mylocation.getCity().equals("长沙市")){
            Toast.makeText(this,mylocation.getCity(),Toast.LENGTH_SHORT).show();
            return;
        }
        LatLng latLng=new LatLng(mylocation.getLatitude(),mylocation.getLongitude());
        PlanNode stNode=PlanNode.withCityNameAndPlaceName("湘潭","湘潭火车站");
        PlanNode enNode = PlanNode.withCityNameAndPlaceName("湘潭","湖南城建职业技术学院高新校区(南门)");
        search.transitSearch((new TransitRoutePlanOption())
                .from(stNode)
                .city("长沙")
                .to(enNode));
    }


    public void searchWalk(View view){
        initSearch();
        if(!mylocation.getCity().equals("长沙市")){
            Toast.makeText(this,mylocation.getCity(),Toast.LENGTH_SHORT).show();
            return;
        }
        LatLng latLng=new LatLng(mylocation.getLatitude(),mylocation.getLongitude());
        PlanNode stNode=PlanNode.withLocation(latLng);
        PlanNode enNode = PlanNode.withCityNameAndPlaceName("湘潭","湖南城建职业技术学院高新校区(南门)");
        search.walkingSearch((new WalkingRoutePlanOption())
                .from(stNode).to(enNode));
    }
    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
        gotype.setVisibility(View.GONE);

        if (walkingRouteResult == null || walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (walkingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
            if(overlayManager!=null){
                overlayManager.removeFromMap();
            }
            WalkingRouteLine routeLine=walkingRouteResult.getRouteLines().get(0);
            WalkingRouteOverlay overlay=new WalkingRouteOverlay(baiduMap);
            overlay.setData(routeLine);
            overlayManager=overlay;

            tips.setText("查看路线");
            //将公交路线规划覆盖物添加到地图中
            overlayManager.addToMap();
            overlayManager.zoomToSpan();

            List<WalkingRouteLine> routeLines=walkingRouteResult.getRouteLines();
            List<WalkingRouteLine.WalkingStep> steps=routeLines.get(0).getAllStep();
            luxians.clear();
            for(WalkingRouteLine.WalkingStep step:steps){
                Log.i("asd",step.getInstructions());
                luxians.add(step.getInstructions());
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
        gotype.setVisibility(View.GONE);

        if (transitRouteResult == null || transitRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }else if(transitRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            Toast.makeText(this, "选择正确结果", Toast.LENGTH_SHORT).show();
            SuggestAddrInfo suggestAddrInfo=transitRouteResult.getSuggestAddrInfo();
            List<PoiInfo> infos=suggestAddrInfo.getSuggestStartNode();
            for(PoiInfo info:infos){
                Log.i("asd",info.address);
            }
            return;
        }else{
            List<TransitRouteLine> routeLines=transitRouteResult.getRouteLines();
            for(TransitRouteLine routeLine:routeLines){
                TransitRouteOverlay overlay=new TransitRouteOverlay(baiduMap);
                overlay.setData(routeLine);
                if(overlayManager!=null){
                    overlayManager.removeFromMap();
                }
                overlayManager=overlay;
                //将公交路线规划覆盖物添加到地图中
                overlayManager.addToMap();
                overlayManager.zoomToSpan();
                List<TransitRouteLine.TransitStep> transitSteps=routeLine.getAllStep();
                luxians.clear();
                for(TransitRouteLine.TransitStep transitStep:transitSteps){
                    transitStep.getInstructions();
                    luxians.add(transitStep.getInstructions());
                    Log.i("asd",transitStep.getInstructions());
                }
                adapter.notifyDataSetChanged();
                Log.i("asd","哈哈哈");
            }
        }
    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
        if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
    }


    LinearLayout gotype;
    public void gotype(View view) {
        if(gotype==null){
            gotype=(LinearLayout)findViewById(R.id.goType);
        }
        if(gotype.getVisibility()==View.VISIBLE){
            gotype.setVisibility(View.GONE);
        }else{
            gotype.setVisibility(View.VISIBLE);
        }
    }

    LinearLayout bottom;
    TextView tips;
    LinearLayout bottomlist;
    ListView listView;

    FloatingActionButton dingwei;
    FloatingActionButton luxian;
    public void showluxian(View view) {
        dingwei.setVisibility(View.GONE);
        luxian.setVisibility(View.GONE);
        bottomlist.setVisibility(View.VISIBLE);
    }
    public void hideluxian(View view) {
        dingwei.setVisibility(View.VISIBLE);
        luxian.setVisibility(View.VISIBLE);
        bottomlist.setVisibility(View.GONE);
    }
}
