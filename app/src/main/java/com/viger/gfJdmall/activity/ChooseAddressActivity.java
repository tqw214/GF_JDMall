package com.viger.gfJdmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import com.viger.gfJdmall.R;
import com.viger.gfJdmall.adapter.ChooseAddressAdapter;
import com.viger.gfJdmall.application.MyApplication;
import com.viger.gfJdmall.bean.AdressBean;
import com.viger.gfJdmall.bean.RResult;
import com.viger.gfJdmall.cons.IdiyMessage;
import com.viger.gfJdmall.controller.ShopCarController;
import com.viger.gfJdmall.listener.IModeChangeListener;
import com.viger.gfJdmall.ui.FlexiListView;

import java.util.List;

/**
 * Created by Administrator on 2017/6/22.
 */

public class ChooseAddressActivity extends BaseActivity implements IModeChangeListener {

    private FlexiListView mListView;
    private ChooseAddressAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);
        initView();
        initController();
    }

    @Override
    protected void initController() {
        super.initController();
        mController = new ShopCarController(this);
        mController.setModeChangeListener(this);
        mController.sendAsyncMessage(IdiyMessage.CHOOSE_ADDRESS, getUserId(), false);
    }

    protected long getUserId() {
        return ((MyApplication)getApplication()).getUserInfo().getId();
    }

    private Handler mHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IdiyMessage.CHOOSE_ADDRESS:
                    handleAddressList((List<AdressBean>) msg.obj);
                    break;
                case IdiyMessage.DEL_ADDRESS:
                    handleDelAddress((RResult) msg.obj);
                    break;
            }
        }
    };

    private void handleDelAddress(RResult result) {
        if (result != null) {
            if(result.isSuccess()) {
                tip("成功删除地址");
                mController.sendAsyncMessage(IdiyMessage.CHOOSE_ADDRESS, getUserId(), false);
            }else {
                tip(result.getErrorMsg());
            }
        }
    }

    private void handleAddressList(List<AdressBean> data) {
        mAdapter.setDatas(data);
    }

    @Override
    protected void initView() {
        mListView = (FlexiListView) findViewById(R.id.lv);
        mAdapter = new ChooseAddressAdapter(this);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnDeleteAddressListener(new ChooseAddressAdapter.OnDeleteAddressListener() {
            @Override
            public void onDelete(int position) {
                AdressBean item = (AdressBean) mAdapter.getItem(position);
                long userId = getUserId();
                mController.sendAsyncMessage(IdiyMessage.DEL_ADDRESS, userId, item.getId());
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AdressBean item = (AdressBean) mAdapter.getItem(i);
                Intent intent = new Intent();
                intent.putExtra("data", item);
                setResult(0, intent);
                finish();
            }
        });
    }

    public void goBack(View view) {
        finish();
    }

    @Override
    public void onModeChange(int action, Object... data) {
        mHandle.obtainMessage(action, data[0]).sendToTarget();
    }
}
