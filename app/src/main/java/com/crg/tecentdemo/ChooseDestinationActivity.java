package com.crg.tecentdemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.param.SuggestionParam;
import com.tencent.lbssearch.object.result.SuggestionResultObject;
import com.tencent.tencentmap.mapsdk.map.Projection;
import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

import java.util.ArrayList;
import java.util.List;

public class ChooseDestinationActivity extends AppCompatActivity {
    private EditText inputDestination;
    private TencentSearch tencentSearch;
    private ListView  address_listView;
    private List<String> addressArray;
    private AddressListAdpter adpter;
    private Button backButton;
    private String destination;//就是list里面 字符串 按照"=="切割 下标【2】代表Latitude 【3】代表Longitude

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_destination);
        inputDestination = (EditText) findViewById(R.id.input_destination);
        tencentSearch = new TencentSearch(this);
        address_listView = (ListView) findViewById(R.id.list_suggestAddres);
        addressArray = new ArrayList<>();
        adpter = new AddressListAdpter(this, addressArray);
        address_listView.setAdapter(adpter);
        backButton = (Button) findViewById(R.id.onMyWay_leftBtn);
        destination = null;

//        addMenu();
        /**监听输入的文字变化*/
        inputDestination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("onTextChanged-----", s.toString());
                addressArray.clear();
                adpter.notifyDataSetChanged();
                SuggestionParam param = new SuggestionParam().keyword(s.toString()).region("上海");
                tencentSearch.suggestion(param, new HttpResponseListener() {
                    @Override
                    public void onSuccess(int i, BaseObject baseObject) {
                        SuggestionResultObject oj = (SuggestionResultObject) baseObject;
                        for (SuggestionResultObject.SuggestionData data : oj.data) {
                            StringBuilder builder = new StringBuilder(data.title +
                                    "==" + data.address + "==" + data.location.lat + "==" + data.location.lng);
                            if (!addressArray.contains(builder)) {
                                addressArray.add(builder.toString());
                            }
                        }
                        adpter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(int i, String s, Throwable throwable) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /**监听listView 滑动事件  收起键盘*/
        address_listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(inputDestination.getWindowToken(), 0);
                return false;
            }
        });

        /**监听选中listview*/
        address_listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String s = addressArray.get(position);
//                Toast.makeText(ChooseDestinationActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
            }
        });

    /*
        address_listView.setOnSlideListener(new SlideAndDragListView.OnSlideListener() {
            @Override
            public void onSlideOpen(View view, View parentView, int position, int direction) {

            }

            @Override
            public void onSlideClose(View view, View parentView, int position, int direction) {

            }
        });



        address_listView.setOnMenuItemClickListener(new SlideAndDragListView.OnMenuItemClickListener() {
            @Override
            public int onMenuItemClick(View v, int itemPosition, int buttonPosition, int direction) {
                switch (direction){
                    case MenuItem.DIRECTION_LEFT:
                        switch (buttonPosition){
                            case 0:
                                return Menu.ITEM_SCROLL_BACK;
                        }
                        break;
                    case MenuItem.DIRECTION_RIGHT:
                        switch (buttonPosition){
                            case 0:
                                return Menu.ITEM_DELETE_FROM_BOTTOM_TO_TOP;
                        }
                        break;
                    default:
                        return Menu.ITEM_NOTHING;
                }
                return 0;
            }
        });

    }

    private void addMenu(){
        Menu menu = new Menu(true,true,0);
        menu.addItem(new MenuItem.Builder().setWidth(20)
                .setBackground(new ColorDrawable(Color.RED))
                .setText("确定")
                .setTextColor(Color.WHITE)
                .setTextSize(15)
                .build());
        address_listView.setMenu(menu);
    }

    */
    }

}
