package com.example.alarmclockdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.alarmclockdemo.R;
import com.example.alarmclockdemo.bean.AlarmClock;
import com.example.alarmclockdemo.utils.AlarmClockUtil;
import com.example.alarmclockdemo.view.SwitchButton;

import java.util.List;

/**
 * @Author haoping
 * @Date 2016/11/24
 * @Des 闹钟适配器
 */
public class AlarmClockAdapter extends RecyclerView.Adapter<AlarmClockAdapter.MyViewHolder> {

    private Context context;
    private List<AlarmClock> alarmClocks;
    private ItemClickListener onItemClickListener;

    public AlarmClockAdapter(List<AlarmClock> data, Context context) {
        this.alarmClocks = data;
        this.context = context;
    }

    @Override
    public AlarmClockAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(context, R.layout.alram_item, null));
    }

    @Override
    public void onBindViewHolder(final AlarmClockAdapter.MyViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position, holder.itemView);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.onItemLongClick(position, holder.itemView);
                return false;
            }
        });
        AlarmClock alarmClock = alarmClocks.get(position);
        holder.mAlarmTime.setText(AlarmClockUtil.formatTime(alarmClock.getHour(), alarmClock.getMinute()));
        holder.mAlarmTag.setText(alarmClock.getTag());
        holder.mSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {// 如果是选中状态
                    // 开启闹钟


                } else {// 如果是未选中状态
                    // 关闭闹钟


                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (alarmClocks != null) {
            return alarmClocks.size();
        }
        return 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mAlarmTime;
        private TextView mAlarmTag;
        private SwitchButton mSb;

        public MyViewHolder(View itemView) {
            super(itemView);
            mAlarmTime = (TextView) itemView.findViewById(R.id.alarmTime);
            mAlarmTag = (TextView) itemView.findViewById(R.id.alarmTag);
            mSb = (SwitchButton) itemView.findViewById(R.id.sb);
        }
    }

    /**
     * itemView点击监听接口
     */
    public interface ItemClickListener{
        void onItemClick(int position, View itemView);
        void onItemLongClick(int position, View itemView);
    }

    /**
     * 给itemView设置监听器
     * @param onItemClickListener 监听器
     */
    public void setOnItemClickListener(ItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
