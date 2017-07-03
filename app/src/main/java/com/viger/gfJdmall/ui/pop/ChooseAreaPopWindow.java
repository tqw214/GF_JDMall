package com.viger.gfJdmall.ui.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.viger.gfJdmall.R;
import com.viger.gfJdmall.bean.ReceviceAdressBean;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.controller.ShopCarController;
import com.viger.gfJdmall.listener.IModeChangeListener;
import com.viger.gfJdmall.listener.IProductSortChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */

public class ChooseAreaPopWindow extends IPopupWindowProtocal implements View.OnClickListener, IModeChangeListener{

    private View mView;
    private TextView submit_tv;
    private ListView province_lv,city_lv,dist_lv;
    private View left_v;
    private ArrayAdapter mProvinceAdapter,mCityAdapter,mAreaAdapter;
    private List<String> mProvinceList,mCityList,mAreaList;
    private ShopCarController mController;
    private List<ReceviceAdressBean> mListProvince, mListCity, mListArea;
    private ReceviceAdressBean mCurrentProvince, mCurrentCity, mCurrentArea;
    private IAdressChangeListener mListener;

    public interface IAdressChangeListener {
        void onAdressChange(ReceviceAdressBean v1, ReceviceAdressBean v2, ReceviceAdressBean v3);
    }

    public void setOnAdressChangeListener(IAdressChangeListener listener) {
        this.mListener = listener;
    }

    public ChooseAreaPopWindow(Context ctx) {
        super(ctx);
        initUI();
    }

    public void initUI() {
        mProvinceList = new ArrayList<String>();
        mCityList = new ArrayList<String>();
        mAreaList = new ArrayList<String>();
        mView = LayoutInflater.from(mContext).inflate(R.layout.address_pop_view, null);
        left_v = mView.findViewById(R.id.left_v);
        left_v.setOnClickListener(this);
        submit_tv = (TextView) mView.findViewById(R.id.submit_tv);
        submit_tv.setOnClickListener(this);

        province_lv = (ListView) mView.findViewById(R.id.province_lv);
        mProvinceAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,
                android.R.id.text1, mProvinceList);
        province_lv.setAdapter(mProvinceAdapter);
        province_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCurrentProvince = mListProvince.get(i);
                mController.sendAsyncMessage(IdiyMessage.GET_CITY, mCurrentProvince.getCode());
            }
        });

        city_lv = (ListView) mView.findViewById(R.id.city_lv);
        mCityAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,
                android.R.id.text1, mCityList);
        city_lv.setAdapter(mCityAdapter);
        city_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCurrentCity = mListCity.get(i);
                mController.sendAsyncMessage(IdiyMessage.GET_AREA, mCurrentCity.getCode());
            }
        });

        dist_lv = (ListView) mView.findViewById(R.id.dist_lv);
        mAreaAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,
                android.R.id.text1, mAreaList);
        dist_lv.setAdapter(mAreaAdapter);
        dist_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCurrentArea = mListArea.get(i);
            }
        });

        mPopWindow = new PopupWindow(mView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mPopWindow.setContentView(mView);
        mPopWindow.setFocusable(true);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.update();

        initController();

    }

    private void initController() {
        mController = new ShopCarController(mContext);
        mController.setModeChangeListener(this);
        mController.sendAsyncMessage(IdiyMessage.GET_PROVINCE);
    }

    @Override
    public void onShow(View view) {
        if(mPopWindow!=null) {
            mPopWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_v:
                onDismiss();
                break;
            case R.id.submit_tv:
                if(mListener != null) {
                    mListener.onAdressChange(mCurrentProvince,mCurrentCity,mCurrentArea);
                }
                break;
        }
    }

    @Override
    public void onModeChange(int action, Object... data) {
        mHandler.obtainMessage(action, data[0]).sendToTarget();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IdiyMessage.GET_PROVINCE:
                    handleProvince((List<ReceviceAdressBean>) msg.obj);
                    break;
                case IdiyMessage.GET_CITY:
                    handleCity((List<ReceviceAdressBean>) msg.obj);
                    break;
                case IdiyMessage.GET_AREA:
                    handleArea((List<ReceviceAdressBean>) msg.obj);
                    break;
            }
        }
    };

    private void handleProvince(List<ReceviceAdressBean> data) {
        if(data != null && data.size()>0) {
            this.mListProvince = data;
            mProvinceList.clear();
            for(ReceviceAdressBean bean : data) {
                mProvinceList.add(bean.getName());
            }
            mProvinceAdapter.notifyDataSetChanged();
            mCurrentProvince = mListProvince.get(0);
            mController.sendAsyncMessage(IdiyMessage.GET_CITY, mCurrentProvince.getCode());
        }
    }

    private void handleCity(List<ReceviceAdressBean> data) {
        if(data != null && data.size()>0) {
            this.mListCity = data;
            mCityList.clear();
            for(ReceviceAdressBean bean : data) {
                mCityList.add(bean.getName());
            }
            mCityAdapter.notifyDataSetChanged();
            mCurrentCity = mListCity.get(0);
            mController.sendAsyncMessage(IdiyMessage.GET_AREA, mCurrentCity.getCode());
        }
    }

    private void handleArea(List<ReceviceAdressBean> data) {
        if(data != null && data.size()>0) {
            this.mListArea = data;
            mAreaList.clear();
            for(ReceviceAdressBean bean : data) {
                mAreaList.add(bean.getName());
            }
            mAreaAdapter.notifyDataSetChanged();
            mCurrentArea = mListArea.get(0);
        }
    }

}
