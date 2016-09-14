package cn.kiwano.benben.timepicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LiYuanxiong on 2016/9/14 11:58.
 * Desribe:
 */
public class DateTimePickerActivity extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    private EditText startDateTime;
    private EditText endDateTime;

    private String initStartDateTime = "2016年6月6日 06:06"; // 初始化开始时间
    private String initEndDateTime = "2017年7月7日 07:07"; // 初始化结束时间
    private String time = new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(new Date());


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // 两个输入框
        startDateTime = (EditText) findViewById(R.id.inputDate);
        endDateTime = (EditText) findViewById(R.id.inputDate2);

        startDateTime.setText(initStartDateTime);
        endDateTime.setText(initEndDateTime);

        startDateTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("lyx", "time: " + time);
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        DateTimePickerActivity.this, time);

                dateTimePicKDialog.dateTimePickDialog(startDateTime);

            }
        });

        endDateTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        DateTimePickerActivity.this, time);
                dateTimePicKDialog.dateTimePickDialog(endDateTime);
            }
        });
    }
}
