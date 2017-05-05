package com.example.medicalmonitor.ui.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.example.medicalmonitor.R;
import com.example.medicalmonitor.database.DatabaseCreate;
import com.example.medicalmonitor.ui.drawer.LineGraphicView;
import com.example.medicalmonitor.ui.helper.IMChattingHelper;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.im.ECTextMessageBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Administrator on 2016/12/14.
 */

public class RemoteControlCommandActivity extends Activity implements IMChattingHelper.OnMessageReportCallback{

//    private Button mSendButton;
//    private EditText mReceiveEditText, mSendEditText;
    private final static String sendNO = "20170418";
    private LineGraphicView tu;
    private ArrayList<Double> yList;
    private ArrayList<Double> tempData;
    private SQLiteDatabase database;
    private Vector allStudents; // load all students in the class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_remote_control_command);

        tu = (LineGraphicView) findViewById(R.id.line_graphic);
//        mSendButton = (Button)findViewById(R.id.btn_command_send);
//        mReceiveEditText = (EditText)findViewById(R.id.received_command);
//        mSendEditText = (EditText)findViewById(R.id.send_info);

        tempData = new ArrayList<Double>();
//        mSendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!TextUtils.isEmpty(mSendEditText.getText())){
//                    String text = mSendEditText.getText().toString();
//                    handleSendTextMessage(text);
//                }else {
//                    Toast.makeText(RemoteControlCommandActivity.this, "发送文本为空", Toast.LENGTH_SHORT).show();                }
//            }
//        });
        testDraw();
        loadStudent(" ", " ");  //预先加载数据到缓存集合框架
    }

    @Override
    protected void onResume() {
        super.onResume();
        IMChattingHelper.setOnMessageReportCallback(this);
        Log.d("TIEJIANG", "TIEJIANG-skyee---[RemoteControlCommandActivity]-onResume");// add by tiejiang
    }

    public void testDraw(){
        yList = new ArrayList<Double>();
        yList.add((double) 1.0);
        yList.add(2.0);
        yList.add(2.0);
        yList.add(3.0);
        yList.add(5.0);
        yList.add(3.0);
        yList.add(1.0);

        ArrayList<String> xRawDatas = new ArrayList<String>();
        xRawDatas.add("周一");
        xRawDatas.add("周二");
        xRawDatas.add("周三");
        xRawDatas.add("周四");
        xRawDatas.add("周五");
        xRawDatas.add("周六");
        xRawDatas.add("周日");
//        xRawDatas.add("周二");
        tu.setData(yList, xRawDatas, 10, 2);
    }

    public void drawArc(ArrayList<Double> arrayList){

        ArrayList<String> xRawDatas = new ArrayList<String>();
        if (arrayList.size() == 7){
            setContentView(R.layout.activity_remote_control_command);
            tu = (LineGraphicView) findViewById(R.id.line_graphic);

            xRawDatas.add("周一");
            xRawDatas.add("周二");
            xRawDatas.add("周三");
            xRawDatas.add("周四");
            xRawDatas.add("周五");
            xRawDatas.add("周六");
            xRawDatas.add("周日");
            tu.setData(arrayList, xRawDatas, 10, 2);
        }else {
            Log.d("TIEJIANG", "arrayList length= " + arrayList.size());
            Toast.makeText(this, "data error !", Toast.LENGTH_SHORT).show();
        }
//        yList = new ArrayList<Double>();
//        yList.add((double) 2.103);
//        yList.add(4.05);
//        yList.add(6.60);
//        yList.add(3.08);
//        yList.add(4.32);
//        yList.add(2.0);
//        yList.add(7.0);
    }

    @Override
    public void onMessageReport(ECError error, ECMessage message) {

    }

    /**
     * 接收数据并解析
     * 接收数据看格式： W,data,X,data,E
     * */
    @Override
    public void onPushMessage(String sessionId, List<ECMessage> msgs) {
        int msgsSize = msgs.size();
        String  message = " ";
        for (int i = 0; i < msgsSize; i++){
            message = ((ECTextMessageBody) msgs.get(i).getBody()).getMessage();
            Log.d("TIEJIANG", "[RemoteControlCommandActivity]" + "i = " + i + ", message = " + message);// add by tiejiang
        }
        // clear first before load new data
        if (!tempData.isEmpty()){
            tempData.clear();
        }
        String[] msgArray = message.split(",");
        Log.d("TIEJIANG", "[RemoteControlCommandActivity]" + "msgArray.length = " + msgArray.length);// add by tiejiang
        if (msgArray.length == 7){
            for (int i = 0; i < msgArray.length; i ++){
                tempData.add(Double.valueOf(msgArray[i]));
                Log.d("TIEJIANG", "[RemoteControlCommandActivity]" + "tempData[] = " + tempData.get(i));// add by tiejiang
            }
            drawArc(tempData);
        }
        Log.d("TIEJIANG", "[RemoteControlCommandActivity]" + ",sessionId = " + sessionId);// add by tiejiang
//        mReceiveEditText.setText(message);
    }

    //load data from database
    private void loadStudent(String stu_college, final String stu_class){
        new Thread(new Runnable() {
            @Override
            public void run() {
                allStudents = new Vector();
                Cursor mCursor;
//                if (stu_class.equals("10")){
                    database = SQLiteDatabase.openOrCreateDatabase(DatabaseCreate.DATABASE_PATH + DatabaseCreate.dbName, null);
                    String sql = "SELECT * FROM data ";
                    mCursor = database.rawQuery(sql, null);
                    if (mCursor.moveToFirst()){
                        do {
                            allStudents.add(mCursor.getString(mCursor.getColumnIndex("temperature")));
                        }while (mCursor.moveToNext());
                    }
                    // test code begin
					for (int i = 0; i < allStudents.size(); i ++){
						Log.d("TIEJIANG", "data temperature= " + allStudents.get(i));
					}
                    // test code end
//                }else if (stu_class.equals("11")){
//                    database = SQLiteDatabase.openOrCreateDatabase(DatabaseCreate.DATABASE_PATH + DatabaseCreate.dbName, null);
//                    String sql = "SELECT * FROM class_11 ";
//                    mCursor = database.rawQuery(sql, null);
//                    if (mCursor.moveToFirst()){
//                        do {
//                            allStudents.add(mCursor.getString(mCursor.getColumnIndex("student_name")));
//                        }while (mCursor.moveToNext());
//                    }
//                    // test code begin
////					for (int i = 0; i < allStudents.size(); i ++){
////						Log.d("TIEJIANG", "class_11 student_name= " + allStudents.get(i));
////					}
//                    // test code end
//                }

            }
        }).start();
    }

    /**
     * 处理文本发送方法事件通知
     * @param text
     */
    public static void handleSendTextMessage(CharSequence text) {
        if(text == null) {
            return ;
        }
        if(text.toString().trim().length() <= 0) {
//            canotSendEmptyMessage();
            return ;
        }
        // 组建一个待发送的ECMessage
        ECMessage msg = ECMessage.createECMessage(ECMessage.Type.TXT);
        // 设置消息接收者
//        msg.setTo(mRecipients);
        msg.setTo(sendNO); // attenion  this number is not the login number! / modified by tiejiang
        ECTextMessageBody msgBody=null;
        Boolean isBQMMMessage=false;
        String emojiNames = null;
//        if(text.toString().contains(CCPChattingFooter2.TXT_MSGTYPE)  && text.toString().contains(CCPChattingFooter2.MSG_DATA)){
//            try {
//                JSONObject jsonObject = new JSONObject(text.toString());
//                String emojiType=jsonObject.getString(CCPChattingFooter2.TXT_MSGTYPE);
//                if(emojiType.equals(CCPChattingFooter2.EMOJITYPE) || emojiType.equals(CCPChattingFooter2.FACETYPE)){//说明是含有BQMM的表情
//                    isBQMMMessage=true;
//                    emojiNames=jsonObject.getString(CCPChattingFooter2.EMOJI_TEXT);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        if (isBQMMMessage) {
            msgBody = new ECTextMessageBody(emojiNames);
            msg.setBody(msgBody);
            msg.setUserData(text.toString());
        } else {
            // 创建一个文本消息体，并添加到消息对象中
            msgBody = new ECTextMessageBody(text.toString());
            msg.setBody(msgBody);
            Log.d("TIEJIANG", "[RemoteControlCommandActivity]-handleSendTextMessage" + ", txt = " + text);// add by tiejiang
        }

//        String[] at = mChattingFooter.getAtSomeBody();
//        msgBody.setAtMembers(at);
//        mChattingFooter.clearSomeBody();
        try {
            // 发送消息，该函数见上
            long rowId = -1;
//            if(mCustomerService) {
//                rowId = CustomerServiceHelper.sendMCMessage(msg);
//            } else {
            Log.d("TIEJIANG", "[RemoteControlCommandActivity]-SendECMessage");// add by tiejiang
                rowId = IMChattingHelper.sendECMessage(msg);

//            }
            // 通知列表刷新
//            msg.setId(rowId);
//            notifyIMessageListView(msg);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TIEJIANG", "[RemoteControlCommandActivity]-send failed");// add by tiejiang
        }
    }
}
