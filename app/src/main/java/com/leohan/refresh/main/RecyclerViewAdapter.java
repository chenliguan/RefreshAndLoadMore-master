package com.leohan.refresh.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leohan.refresh.R;
import com.leohan.refresh.viewpager.AutoSwitchAdapter;
import com.leohan.refresh.viewpager.AutoSwitchView;
import com.leohan.refresh.viewpager.LoopModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends Adapter<ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_HEAD = 2;
    private Context context;
    private List data;

    /**
     * 每项监听
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //广告点击监听
    public AutoSwitchAdapter.OnItemClickListener autoItemClickListener;

    public RecyclerViewAdapter(Context context, List data,
                               AutoSwitchAdapter.OnItemClickListener autoItemClickListener) {
        this.context = context;
        this.data = data;
        this.autoItemClickListener = autoItemClickListener;
    }

    @Override
    public int getItemCount() {
        return data.size() == 0 ? 0 : data.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position) {
            return TYPE_HEAD;
        } else if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_base, parent,
                    false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_foot, parent,
                    false);
            return new FootViewHolder(view);
        } else if (viewType == TYPE_HEAD) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_head, parent,
                    false);
            return new HeadViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //设置item的点击事件
        if (holder instanceof ItemViewHolder) {
            //holder.tv.setText(data.get(position));
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemLongClick(holder.itemView, position);
                        return false;
                    }
                });
            }
        } else if (holder instanceof HeadViewHolder) {
            initViewPager(((HeadViewHolder) holder).autoSwitchView);
            ((HeadViewHolder) holder).autoSwitchView.setTag(position);//设置位置Tag
        }
    }

    static class ItemViewHolder extends ViewHolder {
        TextView tv;

        public ItemViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_date);
        }
    }

    static class FootViewHolder extends ViewHolder {
        public FootViewHolder(View view) {
            super(view);
        }
    }

    static class HeadViewHolder extends ViewHolder {
        AutoSwitchView autoSwitchView;

        public HeadViewHolder(View view) {
            super(view);
            autoSwitchView = (AutoSwitchView) view.findViewById(R.id.loopswitch);
        }
    }

    /**
     * 初始化滑动页
     */
    private void initViewPager(AutoSwitchView autoSwitchView) {
        AutoSwitchAdapter mAdapter;

        List<LoopModel> datas = new ArrayList<>();
        LoopModel model = null;
        LoopModel mode2 = null;
        LoopModel mode3 = null;
        LoopModel mode4 = null;
        LoopModel mode5 = null;
        model = new LoopModel("第一张", R.mipmap.loop_1);
        datas.add(model);
        mode2 = new LoopModel("第二张", R.mipmap.loop_2);
        datas.add(mode2);
        mode3 = new LoopModel("第三张", R.mipmap.loop_3);
        datas.add(mode3);
        mode4 = new LoopModel("第四张", R.mipmap.loop_4);
        datas.add(mode4);
        mode5 = new LoopModel("第五张", R.mipmap.loop_4);
        datas.add(mode5);

        List indicate = new ArrayList<>();
        indicate.add(R.mipmap.ic_indicator);
        indicate.add(R.mipmap.ic_indicator_c);

        mAdapter = new AutoSwitchAdapter(context, datas);
        //条目点击事件监听
        mAdapter.setOnItemClickListener(autoItemClickListener);
        autoSwitchView.setAdapter(mAdapter);

        //初始化指示器
        autoSwitchView.initIndicate(indicate);
        mAdapter.notifyDataSetChanged();
    }

}