package nchu.com.xutilslogintest;

import android.app.Application;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * Created by Eric on 2018/12/27.
 */

public class MyApplication extends Application {

    private DbManager.DaoConfig daoConfig;
    public DbManager.DaoConfig getDaoConfig(){
        return daoConfig;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        x.Ext.init(this);   //xutils初始化

        daoConfig = new DbManager.DaoConfig()
                //.setDbDir()   可以设置数据库存储在你想存储的地方，默认情况下存储在/data/data/应用程序名/database/xxx.db
                .setDbName("xutilDb")       //所创建的数据库的名称
                .setDbVersion(1)        //数据库的版本号
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        //这里是相关的操作
                    }
                });     //数据库的更新操作
    }


}
