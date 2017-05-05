package com.example.medicalmonitor.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.medicalmonitor.R;
import com.yuntongxun.ecsdk.ECInitParams;

/**
 * Created by Administrator on 2016/11/21.
 */

/**
 * 加入蓝牙后废弃次文件（以”testbluetooh.java“ 作为启动的activity）
 *
 * **/
public class MenuActivity extends Activity implements View.OnClickListener {

    private Button mButtonRobotDistribute;
    ECInitParams.LoginAuthType mLoginAuthType = ECInitParams.LoginAuthType.NORMAL_AUTH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);

        mButtonRobotDistribute = (Button)findViewById(R.id.btn_remote_control);
        mButtonRobotDistribute.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
//            case R.id.btn_monitor:    //VOIP  video
//                Toast.makeText(MenuActivity.this,"monitor",Toast.LENGTH_SHORT).show();
//                CCPAppManager.callVoIPAction(MenuActivity.this, ECVoIPCallManager.CallType.VIDEO,
//                        nickName, contactID,false);
//                finish();
//                break;
            case R.id.btn_remote_control:    //VOIP IM
                Toast.makeText(MenuActivity.this,"robot_remote_control",Toast.LENGTH_SHORT).show();
                Intent mRemoteCtrIntent = new Intent();
                mRemoteCtrIntent.setClass(MenuActivity.this,RemoteControlCommandActivity.class);
                startActivity(mRemoteCtrIntent);
                break;
//            case R.id.btn_audio:    //VOIP audio
//                Toast.makeText(MenuActivity.this,"btn_audio",Toast.LENGTH_SHORT).show();
//                CCPAppManager.callVoIPAction(MenuActivity.this, ECVoIPCallManager.CallType.VOICE,
//                        nickName, contactID,false);
//                finish();
//                break;

        }
    }
}
