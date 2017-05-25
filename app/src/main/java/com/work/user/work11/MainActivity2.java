package com.work.user.work11;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{
    final int image[] = {R.drawable.chicken,R.drawable.pizza,R.drawable.hamburger,R.drawable.kcn,R.drawable.methomyl,R.drawable.jol,R.drawable.ttt10ml};
    final String imageName[] = {"켄터키 치킨","파인애플 없는 피자","빅맥","사이안화칼륨수용액(KCN)","메소밀 원액 800ml","졸피뎀타르타르산염 30,000mg","1:10 테트로도톡신수용액 10mL"};

    EditText editSecond;
    TextView textView;
    ImageView imageView;
    MyTasks task;

    boolean isStart = false;

    @Override
    public void onClick(View v) {
        switch((String)v.getTag()){
            case "1":
                if(task!=null)
                    task.cancel(true);
                init();
                break;
            case "2":
                if(isStart)
                    task.cancel(true);
                else {
                    task = new MyTasks();
                    String scd = editSecond.getText().toString();
                    int param = scd.isEmpty()?0:Integer.parseInt(scd);
                    task.execute(param<1?1:param);
                }
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setTitle("이게 뭐하는 앱이냐");
        editSecond = (EditText)findViewById(R.id.editSecond);
        textView = (TextView)findViewById(R.id.textView);
        imageView = (ImageView)findViewById(R.id.imageView);
        init();
    }

    private void init() {
        imageView.setImageResource(R.drawable.good);
        textView.setVisibility(View.INVISIBLE);
    }

    class MyTasks extends AsyncTask<Integer,Integer,Void> {
        int imageState = 0;
        int imageSelected = 0;
        int second = 0;

        @Override
        protected void onPreExecute() {
            isStart = true;
            textView.setText("");
            textView.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            textView.setText(imageName[imageState] + "선택 (" + second + "초)");
            textView.setTextColor(Color.BLUE);
            isStart = false;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            textView.setTextColor(Color.RED);
            if(values[0] == 1 || second % values[0] == 1) {
                imageState++;
                if (Math.random() < 0.95)
                    imageView.setImageResource(image[imageSelected = imageState % 3]);
                else {
                    imageView.setImageResource(image[imageSelected = imageState % 4 + 3]);
                }
            }

            textView.setText("시작부터 (" + second + ")초");
        }

        @Override
        protected void onCancelled() {
            textView.setText(imageName[imageSelected] + "선택 (" + second + "초)");
            textView.setTextColor(Color.BLUE);
            isStart = false;
        }

        @Override
        protected Void doInBackground(Integer... aInteger) {
            while(isCancelled() == false){
                second++;
                publishProgress(aInteger);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

}
