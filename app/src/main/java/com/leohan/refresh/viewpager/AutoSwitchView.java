package com.leohan.refresh.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import loop.boss.com.loopswitch.AutoLoopSwitchBaseAdapter;
import loop.boss.com.loopswitch.AutoLoopSwitchBaseView;

/**
 * @author ryze
 * @since 1.0 2016/07/17
 */
public class AutoSwitchView extends AutoLoopSwitchBaseView {

    public AutoSwitchView(Context context) {
        super(context);
    }

    public AutoSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoSwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AutoSwitchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 页面切换回调
     */
    @Override
    protected void onSwitch(int index, Object object) {
        LoopModel model = (LoopModel) object;
        if (model != null) {
            Log.e("index", "index:" + index);
        }
    }

    @Override
    protected View getFailtView() {
        return null;
    }

    @Override
    protected long getDurtion() {
        return 3000;
    }

    @Override
    public void setAdapter(AutoLoopSwitchBaseAdapter adapter) {
        super.setAdapter(adapter);
        mHandler.sendEmptyMessage(LoopHandler.MSG_REGAIN);
    }
}