package com.juhezi.citymemory.util;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juhezi.citymemory.R;
import com.juhezi.citymemory.data.module.User;

/**
 * Created by qiaoyunrui on 16-9-7.
 */
public class PersonDialog {

    private Context mContext;
    private View dialogView;
    private ImageView mImgAvatar;
    private TextView mTvPickname;
    private TextView mTvOwnCount;
    private TextView mTvPipCount;
    private Button mBtnMessage;
    private AlertDialog mDialog;

    private User mUser;

    private ClickListener mClickListener;

    public PersonDialog(Context context) {
        mContext = context;
        dialogView = LayoutInflater.from(mContext)
                .inflate(R.layout.person_dialog, null);
        mImgAvatar = (ImageView) dialogView.findViewById(R.id.img_person_dialog_avatar);
        mTvPickname = (TextView) dialogView.findViewById(R.id.tv_person_dialog_name);
        mTvOwnCount = (TextView) dialogView
                .findViewById(R.id.tv_person_dialog_own_mem_count);
        mTvPipCount = (TextView) dialogView
                .findViewById(R.id.tv_person_dialog_pip_mem_count);
        mBtnMessage = (Button) dialogView
                .findViewById(R.id.btn_person_dialog_message);
        mDialog = new AlertDialog.Builder(mContext)
                .setView(dialogView)
                .create();
        mBtnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onMessageBtnClicked(mUser);
                }
            }
        });
    }


    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public void setData(User user) {
        mUser = user;
    }

    public void show() {
        if (mUser != null) {
            Glide.with(mContext)
                    .load(mUser.getAvatar())
                    .crossFade()
                    .error(R.drawable.ic_avatar)
                    .into(mImgAvatar);
            mTvPickname.setText(mUser.getPickName());
            mTvPipCount.setText(mUser.getPipCount() + "");
            mTvOwnCount.setText(mUser.getOwnCount() + "");
        }
        mDialog.show();
    }

    public interface ClickListener {

        void onMessageBtnClicked(User user);

    }

}
