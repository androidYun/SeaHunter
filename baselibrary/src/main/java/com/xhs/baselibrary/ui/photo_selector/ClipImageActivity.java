package com.xhs.baselibrary.ui.photo_selector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.xhs.baselibrary.R;
import com.xhs.baselibrary.ui.photo_selector.utils.FileUtils;
import com.xhs.baselibrary.ui.photo_selector.utils.SizeUtils;
import com.xhs.baselibrary.ui.photo_selector.view.ClipImageBorderView;
import com.xhs.baselibrary.ui.photo_selector.view.ClipZoomImageView;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */

public class ClipImageActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String IMGPATH = "imgPath";
    public static final String IMGPATH_NEW = "imgPath_new";
    public static final String IMAGE_NAME="imageName";

    private int borderWidth = 20;

    private ClipImageBorderView clipImageView;
    private ClipZoomImageView clipZoomImageView;
    private String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_image);
        List<String> imgPaths = getIntent().getStringArrayListExtra(IMGPATH);
        if (imgPaths != null && imgPaths.size() > 0) {
            imgPath = imgPaths.get(0);
        }
        initView();
    }

    public void initView() {
        clipZoomImageView = (ClipZoomImageView) findViewById(R.id.clipZoomImageView);
        clipImageView = (ClipImageBorderView) findViewById(R.id.clipImageView);

        String newPath = "file://" + imgPath;
        Glide.with(this).load(newPath).into(clipZoomImageView);

        clipZoomImageView.setHorizontalPadding(SizeUtils.dp2px(this, borderWidth));
        clipImageView.setHorizontalPadding(SizeUtils.dp2px(this, borderWidth));

        findViewById(R.id.bt_use).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.bt_use) {
            Bitmap bitmap = clipZoomImageView.clip();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] datas = baos.toByteArray();
            String urlPath = FileUtils.saveToLocal(this, datas);
            String imageName=FileUtils.imageName();
            Intent intent = new Intent();
            intent.putExtra(IMGPATH_NEW, urlPath);
            intent.putExtra(IMAGE_NAME,imageName);
            setResult(RESULT_OK, intent);
            finish();
        } else if (i == R.id.iv_back) {
            finish();
        }
    }


}
