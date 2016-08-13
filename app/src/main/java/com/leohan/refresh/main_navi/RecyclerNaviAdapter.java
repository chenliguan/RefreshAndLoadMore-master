package com.leohan.refresh.main_navi;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leohan.refresh.R;
import com.leohan.refresh.navi.NumberedAdapter;
import com.leohan.refresh.viewpager.AutoSwitchAdapter;
import com.leohan.refresh.viewpager.AutoSwitchView;
import com.leohan.refresh.viewpager.LoopModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerNaviAdapter extends Adapter<ViewHolder> {

    private static final int TYPE_HEAD = 0;
    private static final int TYPE_NAVI = 1;
    private static final int TYPE_ITEM = 5;
    private static final int TYPE_FOOT = 9;

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

    public RecyclerNaviAdapter(Context context, List data,
                               AutoSwitchAdapter.OnItemClickListener autoItemClickListener) {
        this.context = context;
        this.data = data;
        this.autoItemClickListener = autoItemClickListener;
    }

    @Override
    public int getItemCount() {
        return data.size() == 0 ? 0 : data.size() + 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position) {
            return TYPE_HEAD;
        } else if (1 == position) {
            return TYPE_NAVI;
        } else if (position + 1 == getItemCount()) {
            return TYPE_FOOT;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_HEAD) {
            view = LayoutInflater.from(context).inflate(R.layout.view_head, parent, false);
            return new HeadViewHolder(view);
        } else if (viewType == TYPE_NAVI) {
            view = LayoutInflater.from(context).inflate(R.layout.view_navi, parent, false);
            return new NaviViewHolder(view);
        } else if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(context).inflate(R.layout.view_item, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOT) {
            view = LayoutInflater.from(context).inflate(R.layout.view_foot, parent, false);
            return new FootViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if (holder instanceof HeadViewHolder) {
            AutoSwitchView autoSwitchView = ((HeadViewHolder) holder).autoSwitchView;
            //初始化ViewPager
            initViewPager(autoSwitchView);
            autoSwitchView.setTag(position);//设置位置Tag
        } else if (holder instanceof NaviViewHolder) {
            //初始化横滑栏
            initNavi((NaviViewHolder) holder);
        } else if (holder instanceof ItemViewHolder) {
            //设置item的点击事件
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
        }
    }

    static class HeadViewHolder extends ViewHolder {
        AutoSwitchView autoSwitchView;

        public HeadViewHolder(View view) {
            super(view);
            autoSwitchView = (AutoSwitchView) view.findViewById(R.id.loopswitch);
        }
    }

    static class NaviViewHolder extends ViewHolder {
        RecyclerView recyclerView;

        public NaviViewHolder(View view) {
            super(view);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
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

    /**
     * 初始化滑动页
     */
    private void initViewPager(AutoSwitchView autoSwitchView) {
        List<LoopModel> datas = new ArrayList<>();
        LoopModel model = new LoopModel("第一张", R.mipmap.loop_1);
        datas.add(model);
        LoopModel mode2 = new LoopModel("第二张", R.mipmap.loop_2);
        datas.add(mode2);
        LoopModel mode3 = new LoopModel("第三张", R.mipmap.loop_3);
        datas.add(mode3);
        LoopModel mode4 = new LoopModel("第四张", R.mipmap.loop_4);
        datas.add(mode4);
        LoopModel mode5 = new LoopModel("第五张", R.mipmap.loop_4);
        datas.add(mode5);

        AutoSwitchAdapter mAdapter = new AutoSwitchAdapter(context, datas);
        //条目点击事件监听
        mAdapter.setOnItemClickListener(autoItemClickListener);
        autoSwitchView.setAdapter(mAdapter);

        List indicate = new ArrayList<>();
        indicate.add(R.mipmap.ic_indicator);
        indicate.add(R.mipmap.ic_indicator_c);
        //初始化指示器
        autoSwitchView.initIndicate(indicate);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化横滑栏
     */
    private void initNavi(NaviViewHolder holder) {
        RecyclerView recyclerView = holder.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new NumberedAdapter(30));
    }
}