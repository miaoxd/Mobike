package com.yiwen.mobike.activity.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwen.mobike.MyApplication;
import com.yiwen.mobike.R;
import com.yiwen.mobike.bean.MyUser;
import com.yiwen.mobike.utils.ToastUtils;
import com.yiwen.mobike.views.ClearEditText;
import com.yiwen.mobike.views.TabTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CampusVerifyActivity extends AppCompatActivity {


    @BindView(R.id.title_school)
    TabTitleView   mTitleSchool;
    @BindView(R.id.tv_sc)
    TextView       mTvSc;
    @BindView(R.id.tv_school_select)
    TextView       mTvSchoolSelect;
    @BindView(R.id.lo_school_select)
    RelativeLayout mLoSchoolSelect;
    @BindView(R.id.bt_school_query)
    Button         mBtSchoolQuery;
    @BindView(R.id.et_schoolnub)
    ClearEditText  mEtSchoolnub;
    @BindView(R.id.id_iv_myname)
    TextView  mTv_myneme;

    private int schoolNubLength = 0;
    private MyUser mMyUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_verify);
        ButterKnife.bind(this);
        initView();
        initEvent();

    }

    private void initView() {
        mMyUser= MyApplication.getInstance().getUser();
        if (mMyUser!=null){
            if (mMyUser.getMyName()!=null){
                mTv_myneme.setText(mMyUser.getMyName());
            }
        }
    }

    private void initEvent() {
        mTitleSchool.setOnLeftButtonClickListener(new TabTitleView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                CampusVerifyActivity.this.finish();
            }
        });
        mEtSchoolnub.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                schoolNubLength = s.length();
                if (schoolNubLength > 0) {
                    setButtonRed(mBtSchoolQuery);
                } else {
                    setButtonGray(mBtSchoolQuery);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @OnClick({R.id.lo_school_select, R.id.bt_school_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lo_school_select:
                break;
            case R.id.bt_school_query:
                querySchool();
                break;
        }
    }

    private void querySchool() {
        if (TextUtils.isEmpty(mEtSchoolnub.getText().toString())){
            ToastUtils.show(CampusVerifyActivity.this,"校园账号不能为空");
        }else {
            mMyUser.setSchool("北京大学");
            mMyUser.setSchoolNub(mEtSchoolnub.getText().toString());
            MyUser newUser=new MyUser();
            newUser.setSchool("北京大学");
            newUser.setSchoolNub(mEtSchoolnub.getText().toString());
            MyApplication.getInstance().upDataUser(mMyUser,newUser);
            ToastUtils.show(CampusVerifyActivity.this,"认证成功");
        }
    }

    /**
     * 改变bt颜色red设置可点击
     *
     * @param bt
     */
    private void setButtonRed(Button bt) {
        bt.setClickable(true);
        bt.setBackgroundResource(R.color.red);
    }

    /**
     * 改变bt颜色gray设置不可点击
     *
     * @param bt
     */
    private void setButtonGray(Button bt) {
        bt.setClickable(false);
        bt.setBackgroundResource(R.color.gray);
    }
}
