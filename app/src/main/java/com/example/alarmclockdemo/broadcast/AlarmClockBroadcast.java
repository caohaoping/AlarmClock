package com.example.alarmclockdemo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.alarmclockdemo.bean.AlarmClock;
import com.example.alarmclockdemo.utils.AlarmClockUtil;
import com.example.alarmclockdemo.utils.Constants;
import com.example.alarmclockdemo.utils.ToastUtil;

/**
 * 闹钟响起广播
 */
public class AlarmClockBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmClock alarmClock = intent.getParcelableExtra(Constants.ALARM_CLOCK);
        //开启提醒
        ToastUtil.showLongToast(context, "起床时间到啦...：" + alarmClock.toString());
        System.out.println("起床时间到啦...："+ alarmClock.toString());
        if (alarmClock != null) {
            // 单次响铃
            if (alarmClock.getWeeks() == null) {
                Intent i = new Intent("com.example.alarmClock.AlarmClockOff");
                context.sendBroadcast(i);
            } else {
                // 重复周期闹钟
                AlarmClockUtil.startAlarmClock(context, alarmClock);
            }
        }
        //闹钟到点的响应
/*        Intent it = new Intent(context, AlarmClockOnTimeActivity.class);
        it.putExtra(Constants.ALARM_CLOCK, alarmClock);
        context.startActivity(it);*/
    }
}
