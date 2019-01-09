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

import nchu.com.xutilslogintest.entity.User;

/**
 * Created by Eric on 2018/12/27.
 */
@ContentView(value = R.layout.activity_login)
public class LoginActivity extends AppCompatActivity{
    //手机号编辑框
    @ViewInject(R.id.et_mobile)
    private TextView et_mobile;
    //密码编辑框
    @ViewInject(R.id.et_password)
    private TextView et_password;
    //登录按钮
    @ViewInject(R.id.btn_login)
    private Button btn_login;
    //注册按钮
    @ViewInject(R.id.btn_register)
    private Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

    }

    //设置点击事件
    @Event(value = {R.id.btn_login,R.id.btn_register})
    private void onButtonCilck(View v){
        //获取数据库信息
        DbManager db = x.getDb(((MyApplication)getApplicationContext()).getDaoConfig());
        switch(v.getId()){

            case R.id.btn_login:
                //得到输入框的内容
                String phonenum = et_mobile.getText().toString();
                String password = et_password.getText().toString();

                if(phonenum.isEmpty() || password.isEmpty()){
                    Toast.makeText(this,"电话号码或密码为空，请重新输入！",Toast.LENGTH_SHORT).show();
                    break;
                }

                //匹配数据库电话号码与密码
                User user = findByPhoneNum(db,phonenum,password);
                if(user == null){
                    Toast.makeText(this,"电话号码或密码错误，请重新输入！",Toast.LENGTH_SHORT).show();
                    break;
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("username",phonenum);
                    intent.setClass(this,MainActivity.class);
                    startActivity(intent);
                    break;
                }

            case R.id.btn_register:
                Intent intent = new Intent();
                intent.setClass(this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    //根据电话号码和密码查找用户
    public User findByPhoneNum(DbManager db, String phoneNum, String password){
        try{
            return (User)db.selector(User.class).where("phone_num","=",phoneNum).and("password","=",password).findFirst();
        }catch (DbException e){
            e.printStackTrace();
        }
        return null;
    }
}
