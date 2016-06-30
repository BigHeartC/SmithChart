package com.bigheart.smithchart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 界面逻辑控制
 */
public class MainActivity extends AppCompatActivity {

    private ImageView iv1, iv2;
    private TextView tv1, tv2;
    private Button bt1, bt2;
    private EditText etr, etx, etLLamda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv1 = (ImageView) findViewById(R.id.iv_1);
        iv2 = (ImageView) findViewById(R.id.iv_2);
        tv1 = (TextView) findViewById(R.id.tv_1);
        tv2 = (TextView) findViewById(R.id.tv_2);
        bt1 = (Button) findViewById(R.id.bt_cal_1);
        bt2 = (Button) findViewById(R.id.bt_cal_2);
        etx = (EditText) findViewById(R.id.et_x);
        etr = (EditText) findViewById(R.id.et_r);
        etLLamda = (EditText) findViewById(R.id.et_l_lamda);


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String strX = etx.getText().toString();
                    Float x;
                    if (strX.startsWith("－"))
                        x = -1 * Float.valueOf(etx.getText().toString().substring(1));
                    else
                        x = Float.valueOf(etx.getText().toString());

                    Float r = Float.valueOf(etr.getText().toString());
                    MyDrawable drawable1 = new MyDrawable(r, x);
                    iv1.setImageDrawable(drawable1);
                    tv1.setText(drawable1.getVSWR() + "   " + drawable1.getReflectionNum());

                } catch (NumberFormatException e) {
                    toast("输入错误 !");
                }

            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String strX = etx.getText().toString();
                    Float x;
                    if (strX.startsWith("－"))
                        x = -1 * Float.valueOf(etx.getText().toString().substring(1));
                    else
                        x = Float.valueOf(etx.getText().toString());

                    Float r = Float.valueOf(etr.getText().toString());
                    Float lLamda = Float.valueOf(etLLamda.getText().toString());
                    MyDrawable drawable1 = new MyDrawable(r, x, lLamda);
                    iv2.setImageDrawable(drawable1);
                    tv2.setText(drawable1.getInputImpedance());

                } catch (NumberFormatException e) {
                    toast("输入错误 !");
                }
            }
        });

    }

    private void toast(String s) {
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
    }


}
