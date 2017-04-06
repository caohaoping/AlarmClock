package com.example.alarmclockdemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.alarmclockdemo.R;
import com.example.alarmclockdemo.adapter.AlarmClockAdapter;
import com.example.alarmclockdemo.bean.AlarmClock;
import com.example.alarmclockdemo.utils.AlarmClockUtil;
import com.example.alarmclockdemo.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author haoping
 * @Date 2016/11/24
 * @Des 闹钟主界面
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ALARM_CLOCK_NEW = 1;
    private static final int REQUEST_ALARM_CLOCK_EDIT = 2;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;
    private LinearLayout mRootLayout;
    private AlarmClockAdapter mAlarmClockAdapter;
    private List<AlarmClock> mAlarmClockList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initEvent();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mRootLayout = (LinearLayout) findViewById(R.id.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mAlarmClockList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.getItemAnimator().setAddDuration(300);
        mRecyclerView.getItemAnimator().setChangeDuration(300);
        mRecyclerView.getItemAnimator().setMoveDuration(300);
        mRecyclerView.getItemAnimator().setRemoveDuration(300);
        mAlarmClockAdapter = new AlarmClockAdapter(mAlarmClockList, this);
        mRecyclerView.setAdapter(mAlarmClockAdapter);
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //新建闹钟
                toNewAlarmClockActivity();
            }
        });

        mAlarmClockAdapter.setOnItemClickListener(new AlarmClockAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position, View itemView) {
                //单击
                AlarmClock alarmClock = mAlarmClockList.get(position);
                toAlarmClockEditActivity(alarmClock);
            }

            @Override
            public void onItemLongClick(int position, View itemView) {
                //长按
            }
        });
    }

    /**
     * 跳转到编辑闹钟Activity
     */
    private void toAlarmClockEditActivity(AlarmClock alarmClock) {
        Intent intent = new Intent(this, AlarmClockEditActivity.class);
        intent.putExtra(Constants.ALARM_CLOCK, alarmClock);
        startActivityForResult(intent, REQUEST_ALARM_CLOCK_EDIT);
        overridePendingTransition(R.anim.move_in_bottom, 0);
    }

    /**
     * 跳转到新建闹钟Activity
     */
    private void toNewAlarmClockActivity() {
        Intent intent = new Intent(this, AlarmClockNewActivity.class);
        startActivityForResult(intent, REQUEST_ALARM_CLOCK_NEW);
        overridePendingTransition(R.anim.move_in_bottom, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_ALARM_CLOCK_NEW:
                AlarmClock alarmClock = data.getParcelableExtra(Constants.ALARM_CLOCK);
                System.out.println(alarmClock.toString());
                //保存闹钟
                mAlarmClockList.add(alarmClock);
                for(AlarmClock ac : mAlarmClockList){
                    AlarmClockUtil.startAlarmClock(this, ac);
                }
                mAlarmClockAdapter.notifyDataSetChanged();
                break;
            case REQUEST_ALARM_CLOCK_EDIT:
                // TODO
                break;
        }

    }
}
