package nchu.com.xutilslogintest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(value = R.layout.activity_main)
public class MainActivity extends AppCompatActivity{
    @ViewInject(R.id.hello)
    private TextView hello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        Intent inetnt = getIntent();
        String name = inetnt.getStringExtra("username");
        hello.setText("hello," + name + "!");
    }

}
