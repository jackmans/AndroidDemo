package test.cyz.com.fragmentdemo;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView msgText;
    private TextView newText;
    private TextView contactText;
    private TextView settingText;
    private android.support.v4.app.FragmentManager fragmentManager;

    private Fragment contactFrag;
    private Fragment msgFrag;
    private Fragment newsFrag;
    private Fragment settingFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        fragmentManager = getSupportFragmentManager();
        setSelection(0);


    }

    private void initView(){
        msgText = (TextView)findViewById(R.id.tv_msg);
        newText = (TextView) findViewById(R.id.tv_newText);
        contactText = (TextView) findViewById(R.id.tv_contact);
        settingText = (TextView) findViewById(R.id.tv_setting);
        msgText.setOnClickListener(this);
        newText.setOnClickListener(this);
        contactText.setOnClickListener(this);
        settingText.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_msg:
                setSelection(0);
                break;
            case R.id.tv_contact:
                setSelection(1);
                break;
            case R.id.tv_newText:
                setSelection(2);
                break;
            case R.id.tv_setting:
                setSelection(3);
                break;
            default:
                break;
        }
    }

    private void setSelection(int index){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        clearSelected();
         switch (index){
             case 0:
                 msgText.setTextColor(Color.WHITE);
                 if(msgFrag != null){
                     transaction.show(msgFrag);
                 }
                 else{
                     msgFrag = new msgFragment();
                     transaction.add(R.id.frag_content, msgFrag);
                 }
                 break;
             case 1:
                 contactText.setTextColor(Color.WHITE);
                 if(contactFrag != null){
                     transaction.show(contactFrag);
                 }
                 else{
                     contactFrag = new contactFragment();
                     transaction.add(R.id.frag_content,contactFrag);
                 }
                 break;
             case 2:
                 newText.setTextColor(Color.WHITE);
                 if(newsFrag != null){
                     transaction.show(newsFrag);
                 }
                 else{
                     newsFrag = new newsFragment();
                     transaction.add(R.id.frag_content,newsFrag);
                 }
                 break;
             case 3:
                 settingText.setTextColor(Color.WHITE);
                 if(settingFrag != null){
                     transaction.show(settingFrag);
                 }
                 else{
                     settingFrag = new settingFragment();
                     transaction.add(R.id.frag_content,settingFrag);
                 }
                 break;
             default:
                 break;
         }
        transaction.commit();
    }

    private void clearSelected(){
        msgText.setTextColor(Color.BLACK);
        contactText.setTextColor(Color.BLACK);
        newText.setTextColor(Color.BLACK);
        settingText.setTextColor(Color.BLACK);
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (msgFrag != null) {
            transaction.hide(msgFrag);
        }
        if (contactFrag != null) {
            transaction.hide(contactFrag);
        }
        if (newsFrag != null) {
            transaction.hide(newsFrag);
        }
        if (settingFrag != null) {
            transaction.hide(settingFrag);
        }
    }
}
