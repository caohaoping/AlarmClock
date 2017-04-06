package com.example.alarmclockdemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.example.alarmclockdemo.R;
import com.example.alarmclockdemo.bean.AlarmClock;
import com.example.alarmclockdemo.utils.Constants;

import java.util.Collection;
import java.util.TreeMap;

/**
 * @Author haoping
 * @Date 2016/11/24
 * @Des 新建闹钟
 */
public class AlarmClockNewActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    /**
     * 周一按钮状态，默认未选中
     */
    private Boolean isMondayChecked = false;

    /**
     * 周二按钮状态，默认未选中
     */
    private Boolean isTuesdayChecked = false;

    /**
     * 周三按钮状态，默认未选中
     */
    private Boolean isWednesdayChecked = false;

    /**
     * 周四按钮状态，默认未选中
     */
    private Boolean isThursdayChecked = false;

    /**
     * 周五按钮状态，默认未选中
     */
    private Boolean isFridayChecked = false;

    /**
     * 周六按钮状态，默认未选中
     */
    private Boolean isSaturdayChecked = false;

    /**
     * 周日按钮状态，默认未选中
     */
    private Boolean isSundayChecked = false;

    /**
     * 保存重复描述信息String
     */
    private StringBuilder mRepeatStr;

    private TimePicker mTimePicker;
    private ImageView mAction_cancel;
    private ImageView mAtion_accept;
    private TextView mRemainTime;
    private TextView mRepeatDescribe;
    private ToggleButton monday;
    private ToggleButton tuesday;
    private ToggleButton wednesday;
    private ToggleButton thursday;
    private ToggleButton friday;
    private ToggleButton saturday;
    private ToggleButton sunday;
    private EditText mAlarmTag;
    private TextView mRingDescribe;
    private AlarmClock mAlarmClock;
    private TreeMap<Integer, String> mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_new_edit);
        // 设置软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        initView();
        initData();
        initEvent();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mAction_cancel = (ImageView) findViewById(R.id.action_cancel);//取消按钮
        mAtion_accept = (ImageView) findViewById(R.id.action_accept);//完成按钮
        mRemainTime = (TextView) findViewById(R.id.remain_time_tv);//剩余时间
        mTimePicker = (TimePicker) findViewById(R.id.time_picker);//闹钟控件
        mRepeatDescribe = (TextView) findViewById(R.id.repeat_describe);//闹钟重复描述

        monday = (ToggleButton) findViewById(R.id.tog_btn_monday);//周一
        tuesday = (ToggleButton) findViewById(R.id.tog_btn_tuesday);//周二
        wednesday = (ToggleButton) findViewById(R.id.tog_btn_wednesday);//周三
        thursday = (ToggleButton) findViewById(R.id.tog_btn_thursday);//周四
        friday = (ToggleButton) findViewById(R.id.tog_btn_friday);//周五
        saturday = (ToggleButton) findViewById(R.id.tog_btn_saturday);//周六
        sunday = (ToggleButton) findViewById(R.id.tog_btn_sunday);//周日

        mAlarmTag = (EditText) findViewById(R.id.tag_edit_text);//闹钟标签
        mRingDescribe = (TextView) findViewById(R.id.ring_describe);//铃声名
    }


    /**
     * 初始化数据
     */
    private void initData() {
        mMap = new TreeMap();
        mAlarmClock = new AlarmClock();
        //默认闹钟打开
        mAlarmClock.setOnOff(true);
        mAlarmClock.setTag("闹钟");//标签默认为闹钟
        mAlarmClock.setWeeks(null);
        mRepeatStr = new StringBuilder();
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        mAction_cancel.setOnClickListener(this);
        mAtion_accept.setOnClickListener(this);
        //闹钟选择器
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mAlarmClock.setHour(hourOfDay);
                mAlarmClock.setMinute(minute);
            }
        });

        monday.setOnCheckedChangeListener(this);
        tuesday.setOnCheckedChangeListener(this);
        wednesday.setOnCheckedChangeListener(this);
        thursday.setOnCheckedChangeListener(this);
        friday.setOnCheckedChangeListener(this);
        saturday.setOnCheckedChangeListener(this);
        sunday.setOnCheckedChangeListener(this);

        mAlarmTag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals("")){
                    mAlarmClock.setTag(s.toString());//设置闹钟标签
                }else {
                    mAlarmClock.setTag("闹钟");//默认
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mRingDescribe.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_accept:
                // 确认
                Intent intent = new Intent();
                intent.putExtra(Constants.ALARM_CLOCK, mAlarmClock);
                setResult(Activity.RESULT_OK, intent);
                finish();
                overridePendingTransition(0, R.anim.move_out_bottom);
                break;
            case R.id.action_cancel:
                // 取消
                finish();
                overridePendingTransition(0, R.anim.move_out_bottom);
                break;
            case R.id.ring_describe:
                // 选择铃声
                // TODO
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.tog_btn_monday:
                // 周一
                if (isChecked) {
                    isMondayChecked = true;
                    mMap.put(1, "一");
                    setRepeatDescribe();
                } else {
                    isMondayChecked = false;
                    mMap.remove(1);
                    setRepeatDescribe();
                }
                break;
            case R.id.tog_btn_tuesday:
                // 周二
                if (isChecked) {
                    isTuesdayChecked = true;
                    mMap.put(2, "二");
                    setRepeatDescribe();
                } else {
                    isTuesdayChecked = false;
                    mMap.remove(2);
                    setRepeatDescribe();
                }
                break;
            case R.id.tog_btn_wednesday:
                // 周三
                if (isChecked) {
                    isWednesdayChecked = true;
                    mMap.put(3, "三");
                    setRepeatDescribe();
                } else {
                    isWednesdayChecked = false;
                    mMap.remove(3);
                    setRepeatDescribe();
                }
                break;
            case R.id.tog_btn_thursday:
                // 周四
                if (isChecked) {
                    isThursdayChecked = true;
                    mMap.put(4, "四");
                    setRepeatDescribe();
                } else {
                    isThursdayChecked = false;
                    mMap.remove(4);
                    setRepeatDescribe();
                }
                break;
            case R.id.tog_btn_friday:
                // 周五
                if (isChecked) {
                    isFridayChecked = true;
                    mMap.put(5, "五");
                    setRepeatDescribe();
                } else {
                    isFridayChecked = false;
                    mMap.remove(5);
                    setRepeatDescribe();
                }
                break;
            case R.id.tog_btn_saturday:
                // 周六
                if (isChecked) {
                    isSaturdayChecked = true;
                    mMap.put(6, "六");
                    setRepeatDescribe();
                } else {
                    isSaturdayChecked = false;
                    mMap.remove(6);
                    setRepeatDescribe();
                }
                break;
            case R.id.tog_btn_sunday:
                // 周日
                if (isChecked) {
                    isSundayChecked = true;
                    mMap.put(7, "日");
                    setRepeatDescribe();
                } else {
                    isSundayChecked = false;
                    mMap.remove(7);
                    setRepeatDescribe();
                }
                break;
        }
    }

    /**
     * 设置重复描述的内容
     */
    private void setRepeatDescribe() {
        // 全部选中
        if (isMondayChecked & isTuesdayChecked & isWednesdayChecked & isThursdayChecked & isFridayChecked & isSaturdayChecked & isSundayChecked) {
            mRepeatDescribe.setText(getResources().getString(R.string.every_day));
            mAlarmClock.setRepeat(getString(R.string.every_day));
            // 响铃周期
            mAlarmClock.setWeeks("2,3,4,5,6,7,1");
            // 周一到周五全部选中
        } else if (isMondayChecked & isTuesdayChecked & isWednesdayChecked & isThursdayChecked & isFridayChecked & !isSaturdayChecked & !isSundayChecked) {
            mRepeatDescribe.setText(getString(R.string.week_day));
            mAlarmClock.setRepeat(getString(R.string.week_day));
            mAlarmClock.setWeeks("2,3,4,5,6");
            // 周六、日全部选中
        } else if (!isMondayChecked & !isTuesdayChecked & !isWednesdayChecked & !isThursdayChecked & !isFridayChecked & isSaturdayChecked & isSundayChecked) {
            mRepeatDescribe.setText(getString(R.string.week_end));
            mAlarmClock.setRepeat(getString(R.string.week_end));
            mAlarmClock.setWeeks("7,1");
            // 没有选中任何一个
        } else if (!isMondayChecked & !isTuesdayChecked & !isWednesdayChecked & !isThursdayChecked & !isFridayChecked & !isSaturdayChecked & !isSundayChecked) {
            mRepeatDescribe.setText(getString(R.string.repeat_once));
            mAlarmClock.setRepeat(getResources().getString(R.string.repeat_once));
            mAlarmClock.setWeeks(null);

        } else {
            mRepeatStr.setLength(0);
            mRepeatStr.append(getString(R.string.week));
            Collection<String> col = mMap.values();
            for (String aCol : col) {
                mRepeatStr.append(aCol).append(getResources().getString(R.string.caesura));
            }
            // 去掉最后一个","
            mRepeatStr.setLength(mRepeatStr.length() - 1);
            mRepeatDescribe.setText(mRepeatStr.toString());
            mAlarmClock.setRepeat(mRepeatStr.toString());

            mRepeatStr.setLength(0);
            if (isMondayChecked) {
                mRepeatStr.append("2,");
            }
            if (isTuesdayChecked) {
                mRepeatStr.append("3,");
            }
            if (isWednesdayChecked) {
                mRepeatStr.append("4,");
            }
            if (isThursdayChecked) {
                mRepeatStr.append("5,");
            }
            if (isFridayChecked) {
                mRepeatStr.append("6,");
            }
            if (isSaturdayChecked) {
                mRepeatStr.append("7,");
            }
            if (isSundayChecked) {
                mRepeatStr.append("1,");
            }
            mAlarmClock.setWeeks(mRepeatStr.toString());
        }

    }
}

