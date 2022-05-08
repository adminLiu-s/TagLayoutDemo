package com.harmony.home.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.didichuxing.doraemonkit.widget.brvah.BaseQuickAdapter;
import com.didichuxing.doraemonkit.widget.brvah.viewholder.BaseViewHolder;
import com.harmony.common.entity.cloud.CloudListResItemEntity;
import com.harmony.home.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class ModelListAdapter extends BaseQuickAdapter<CloudListResItemEntity, BaseViewHolder> {
    protected Context mContext;
    private boolean isEdit=false;
    private boolean isAll=false;

    private ArrayList<Long> choiceList=new ArrayList<>();
    
    public ModelListAdapter(@Nullable ArrayList<CloudListResItemEntity> data, Context mContext) {
        super(R.layout.item_home, data);
        this.mContext = mContext;
    }


    @Override
    @SuppressLint("SimpleDateFormat")
    protected void convert(BaseViewHolder holder, CloudListResItemEntity item) {
//        if (!TextUtils.isEmpty(item.getUrl())) {
//            ImageView imageView = holder.getView(R.id.image);
//            String url = item.getUrl().replaceAll("https://resource.idharmony.com/", "");
//            Glide.with(mContext).load("https://resource.idharmony.com/"+url).into(imageView);
//        }

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
        ImageView imageEdit = holder.getView(R.id.imageEdit);
        if (isEdit){
            imageEdit.setVisibility(View.VISIBLE);
            imageEdit.setImageResource(R.mipmap.ic_quan);
            if (choiceList.contains(item.getId())){
                imageEdit.setImageResource(R.mipmap.ic_gou);
            }
        }else {
            imageEdit.setVisibility(View.GONE);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setEditStatus(boolean edit){
        isEdit=edit;
        if (!isEdit){
            isAll=false;
            choiceList.clear();
        }
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setEditAll(boolean all){
        isAll=all;
        choiceList.clear();
        if (isAll){
            for (int i=0;i<getData().size();i++){
                choiceList.add(getData().get(i).getId());
            }
        }
        notifyDataSetChanged();
    }

    public boolean getEdit(){
        return isEdit;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setId(long id){
        if (choiceList.contains(id)){
            choiceList.remove(id);
        }else{
            choiceList.add(id);
        }
        notifyDataSetChanged();
    }


    public ArrayList<Long> getChoiceList(){
      return choiceList;
    }


    private String replace(String s) {
        if (null != s && s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
}

