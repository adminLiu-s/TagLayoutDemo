package com.harmony.home.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.didichuxing.doraemonkit.widget.brvah.BaseQuickAdapter;
import com.didichuxing.doraemonkit.widget.brvah.viewholder.BaseViewHolder;
import com.harmony.common.entity.cloud.CloudListResItemEntity;
import com.harmony.home.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class HomeAdapter extends BaseQuickAdapter<CloudListResItemEntity, BaseViewHolder> {
    protected Context mContext;

    public HomeAdapter(@Nullable ArrayList<CloudListResItemEntity> data, Context mContext) {
        super(R.layout.item_home, data);
        this.mContext = mContext;
    }


    @Override
    @SuppressLint("SimpleDateFormat")
    protected void convert(BaseViewHolder holder, CloudListResItemEntity item) {
        if (!TextUtils.isEmpty(item.getUrl())) {
            ImageView imageView = holder.getView(R.id.image);
            ConstraintLayout mConstraintLayout = holder.getView(R.id.llImage);

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(mConstraintLayout);
            constraintSet.setDimensionRatio(R.id.image,replace(item.getWidth()+"")+":"+replace(item.getHeight()+""));
            constraintSet.applyTo(mConstraintLayout);
            
            String url = item.getUrl().replaceAll("https://resource.idharmony.com/", "");
            Glide.with(mContext).load("https://resource.idharmony.com/"+url).into(imageView);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String re_StrTime = sdf.format(new Date(item.getCreateTime()));

        holder.setText(R.id.tv_name, item.getFolderName()+" " +replace(item.getHeight()+"")+"*"+replace(item.getWidth()+"")+"mm");
        holder.setText(R.id.tv_date, re_StrTime);
    }


    private String replace(String s) {
        if (null != s && s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
}

