package com.juhezi.citymemory.setting.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.juhezi.citymemory.R;
import com.juhezi.citymemory.other.Config;
import com.juhezi.citymemory.setting.SettingActivity;

/**
 * Created by qiaoyunrui on 16-8-26.
 */
public class SettingFragment extends Fragment implements SettingContract.View {

    private static final String TAG = "SettingFragment";

    private SettingContract.Presenter mPresenter;

    private View rootView;
    private ImageView mImgAvatar;
    private TextView mTvPickname;
    private ImageView mImgPicknameEdit;
    private TextView mTvOwnMemNum;
    private TextView mTvPipMemNum;
    private Button mBtnSignout;

    private AlertDialog mDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.setting_frag, container, false);
        mImgAvatar = (ImageView) rootView.findViewById(R.id.img_setting_avatar);
        mImgPicknameEdit = (ImageView) rootView.findViewById(R.id.img_setting_pick_name_edit);
        mTvPickname = (TextView) rootView.findViewById(R.id.tv_setting_pick_name);
        mTvOwnMemNum = (TextView) rootView.findViewById(R.id.tv_setting_own_mem_count);
        mTvPipMemNum = (TextView) rootView.findViewById(R.id.tv_setting_pip_mem_count);
        mBtnSignout = (Button) rootView.findViewById(R.id.btn_setting_sign_out);
        initEvent();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        initDialog();
    }

    private void initDialog() {
        View dialogView = LayoutInflater
                .from(getContext())
                .inflate(R.layout.dialog_edit_pickname, null);
        final EditText newPickname = (EditText) dialogView.findViewById(R.id.et_new_pickname);
        mDialog = new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .setTitle("修改用户名")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.editPickname(newPickname.getText().toString());
                    }
                }).setNegativeButton("取消", null)
                .create();
    }

    private void initEvent() {
        mBtnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.signout();
            }
        });
        mImgPicknameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        mImgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SettingActivity) getActivity()).openAvatarFrag();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.start();
        }
    }

    @Override
    public void onDestroy() {
        mPresenter = null;
        super.onDestroy();
    }

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showUserData(AVUser user) {
        Glide.with(this)
                .load(user.get(Config.USER_AVATAR)

                        .toString())
                .error(R.drawable.ic_avatar_primary)
                .into(mImgAvatar);
        mTvPickname.setText((String) user.get(Config.USER_PICK_NAME));
        //set the pip and own
        mTvPipMemNum.setText("0");
        mTvOwnMemNum.setText("0");
    }

    @Override
    public void turn2MapActivity() {
        getActivity().setResult(Config.SETTING_CODE);
        getActivity().finish();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog() {
        mDialog.show();
    }

    @Override
    public void setShowPickname(String pickname) {
        mTvPickname.setText(pickname);
    }


}
