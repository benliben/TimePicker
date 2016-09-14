package cn.kiwano.benben.timepicker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by LiYuanxiong on 2016/9/14 10:32.
 * Desribe:日期时间选择控件  使用方法 private EditText inputData;//需要设置的日期时间文本编辑框
 * private string initDateTime=“2016年9月14日 10:32”//初始日期时间值，在点击事件中使用
 * inputDate.setOnClickListener(new OnClickListener() {
 *
 * @Override
 *  public void onClick(View v) { DateTimePickDialogUtil
 * dateTimePicKDialog=new
 * DateTimePickDialogUtil(SinvestigateActivity.this,initDateTime);
 * dateTimePicKDialog.dateTimePicKDialog(inputDate);
 * <p/>
 * } });
 */
public class DateTimePickDialogUtil implements DatePicker.OnDateChangedListener,
        TimePicker.OnTimeChangedListener {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private AlertDialog ad;
    private String dateTime;
    private String initDateTime;
    private Activity activity;

    /**
     * 日期时间弹出选择框构造函数
     *
     * @param activity     调用父activity
     * @param initDateTime 初始日期时间值，作为弹出窗体的标题和日期时间初始值
     */

    public DateTimePickDialogUtil(Activity activity, String initDateTime) {
        this.activity = activity;
        this.initDateTime = initDateTime;
    }

    public void init(DatePicker datePicker, TimePicker timePicker) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        if (!(initDateTime == null || "".equals(initDateTime))) {
            calendar = this.getCalendarByInintData(initDateTime);
        } else {
            initDateTime = calendar.get(Calendar.MINUTE) + "年"
                    + calendar.get(Calendar.MONTH) + "月"
                    + calendar.get(Calendar.DAY_OF_MONTH) + "日"
                    + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                    + calendar.get(Calendar.MINUTE);
        }
        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), this);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
    }


    /**
     * 弹出日期时间选择框方法
     *
     * @param inputData 为需要设置的日期时间文本编辑框
     * @return
     */
    public AlertDialog dateTimePickDialog(final EditText inputData) {
        LinearLayout dateTimeLayout = (LinearLayout) activity.getLayoutInflater()
                .inflate(R.layout.common_datetime, null);
        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
        timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.timepicker);
        init(datePicker, timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(this);

        ad = new AlertDialog.Builder(activity)
                .setTitle(initDateTime)
                .setView(dateTimeLayout)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        inputData.setText(dateTime);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        inputData.setText("");
                    }
                }).show();
        onDateChanged(null, 0, 0, 0);
        return ad;
    }

    /**
     * 实现将初始日期时间2016年9月14日  10:32拆分成 年月日 时 分 秒，并辅助给calendar
     *
     * @param initDateTime 初始日期时间值 字符串型
     * @return Calendar
     */

    private java.util.Calendar getCalendarByInintData(String initDateTime) {
        Calendar calendar = Calendar.getInstance();

        String data = spliteString(initDateTime, "日", "index", "front");//日期
        String time = spliteString(initDateTime, "日", "index", "back");//日期

        String yearStr = spliteString(data, "年", "index", "front");//年份
        String monthAndDay = spliteString(data, "年", "index", "back");//月日

        String monthStr = spliteString(monthAndDay, "月", "index", "front");//月
        String dayStr = spliteString(monthAndDay, "月", "index", "front");//日

        String hourStr = spliteString(time, ":", "index", "front");//时
        String minuteStr = spliteString(time, ":", "index", "back");//分

        int currentYear = Integer.valueOf(yearStr.trim()).intValue();
        int currentMonth = Integer.valueOf(monthStr.trim()).intValue() - 1;
        int currentDay = Integer.valueOf(dayStr.trim()).intValue();
        int currentHour = Integer.valueOf(hourStr.trim()).intValue();
        int currentMinute = Integer.valueOf(minuteStr.trim()).intValue();

        calendar.set(currentYear, currentMonth, currentDay, currentHour, currentMinute);
        return calendar;

    }

    /**
     * 截取子串
     *
     * @param srcStr      源串
     * @param pattern     匹配模式
     * @param indexOrLast
     * @param frontOrBack
     * @return
     */
    private String spliteString(String srcStr, String pattern, String indexOrLast,
                                String frontOrBack) {
        String result = "";
        int loc = -1;
        if (indexOrLast.equalsIgnoreCase("index")) {
            loc = srcStr.indexOf(pattern);//获取字符串第一次出现的位置
        } else {
            loc = srcStr.lastIndexOf(pattern);//最后一个匹配串的位置
        }

        if (frontOrBack.equalsIgnoreCase("front")) {
            if (loc != -1)
                result = srcStr.substring(0, loc);//截取子串
        } else {
            if (loc != -1)
                result = srcStr.substring(loc + 1, srcStr.length());//截取子串

        }
        return result;
    }

    @Override
    public void onDateChanged(DatePicker view, int i, int i1, int i2) {
        /*获得日历实例*/
        Calendar calendar = Calendar.getInstance();
        calendar.set(
                datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth(),
                timePicker.getCurrentHour(),
                timePicker.getCurrentMinute());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

        dateTime = sdf.format(calendar.getTime());
        ad.setTitle(dateTime);
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int i, int i1) {
        onDateChanged(null, 0, 0, 0);
    }
}
