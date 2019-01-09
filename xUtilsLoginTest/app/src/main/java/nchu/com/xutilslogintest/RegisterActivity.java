package nchu.com.xutilslogintest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.regex.Pattern;

import nchu.com.xutilslogintest.entity.User;

/**
 * Created by Eric on 2018/12/27.
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends AppCompatActivity {
    //手机号编辑框
    @ViewInject(R.id.reg_mobile)
    private TextView reg_mobile;
    //密码编辑框
    @ViewInject(R.id.reg_password)
    private TextView reg_password;
    //注册按钮
    @ViewInject(R.id.btn_regSuc)
    private Button btn_regSuc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

    }

    //设置点击事件
    @Event(value = {R.id.btn_regSuc})
    private void onButtonCilck(View v) {
        //获取数据库信息
        DbManager db = x.getDb(((MyApplication) getApplicationContext()).getDaoConfig());

        if(v.getId() == R.id.btn_regSuc){
            //得到输入框的内容
            String phonenum = reg_mobile.getText().toString();
            String password = reg_password.getText().toString();
            //判断输入是否合法
            if(phonenum.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                return;
            }else if(!Pattern.compile("^[A-Za-z0-9]+$").matcher(phonenum).matches() ||
                    phonenum.length()<6 || phonenum.length() > 12){
                Toast.makeText(this,"用户名或密码只能为6至12位英文或数字",Toast.LENGTH_SHORT).show();
                return;
            }else if (!Pattern.compile("1[345789][0-9]{9}").matcher(phonenum).matches()){
                Toast.makeText(this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
                return;
            }else{
                //注册
                User user = new User();
                user.setPhone_num(phonenum);
                user.setPassword(password);
                try{
                    db.save(user);
                }catch(DbException e){
                    e.printStackTrace();
                }
                //成功
                Toast.makeText(this,"注册成功！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(this,LoginActivity.class);
                startActivity(intent);
            }


        }
    }
}
