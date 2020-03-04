package com.xhs.baselibrary.ui.photo_selector;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.loader.app.LoaderManager;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;
import androidx.core.content.FileProvider;
import androidx.loader.content.Loader;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.xhs.baselibrary.R;
import com.xhs.baselibrary.base.BaseActivity;
import com.xhs.baselibrary.ui.photo_selector.bean.Image;
import com.xhs.baselibrary.ui.photo_selector.utils.FileUtils;
import com.xhs.baselibrary.utils.StatusBarUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片选择器
 * 支持单选、多选、内部照相（自动选中）
 */
public class PhotoSelectActivity extends BaseActivity {

    public static final String CHOOSE_MODE = "choose_mode";
    public static final String SHOW_CAMERA = "show_camera";
    public static final String MAX_COUNT = "max_count";

    public static final String RESULT_LIST = "result_list";


    public static final int SINGLE_MODE = 1;    //单选模式
    public static final int MULTI_MODE = 2;     //多选模式

    private static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION_FILE = 110;
    private static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION_CAMERA = 111;
    private static final int REQUEST_PERMISSION_CAMERA = 112;

    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_CLIP = 101;
    private static final String KEY_TEMP_FILE = "key_temp_file";
    private static final int LOADER_ALL = 0;    //加载全部
    private static final int LOADER_CATEGORY = 1;   //分类加载

    private GridView gridView;
    private TextView confirm;
    private ImageGridAdapter gridAdapter;

    private int clums = 4; //四列
    private int mode; //模式
    private boolean isShowCamera; //是否显示相机
    private int maxCount;  //最大选择数量

    private File mTmpFile;  //拍照临时文件
    private ArrayList<String> resultList = new ArrayList<>();   //储存结果图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBar(this, getResources().getColor(R.color.base_color_theme));
        setContentView(R.layout.activity_photo_selector);
        initData();
        initView();
        getPhotos();
    }

    private void initData() {
        mode = getIntent().getIntExtra(CHOOSE_MODE, SINGLE_MODE); //默认单选
        isShowCamera = getIntent().getBooleanExtra(SHOW_CAMERA, false); //默认不显示相机
        maxCount = getIntent().getIntExtra(MAX_COUNT, 9); //默认最大数量9
    }

    private void initView() {
        gridView = findViewById(R.id.grid);
        gridAdapter = new ImageGridAdapter(this, isShowCamera, clums);
        gridAdapter.showSelectIndicator(mode == MULTI_MODE);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (gridAdapter.isShowCamera()) {
                    if (i == 0) {
                        showCameraAction();
                    } else {
                        Image image = (Image) adapterView.getAdapter().getItem(i);
                        selectImageFromGrid(image, mode);
                    }
                } else {
                    Image image = (Image) adapterView.getAdapter().getItem(i);
                    selectImageFromGrid(image, mode);
                }
            }
        });
        confirm = findViewById(R.id.confirm);
        confirm.setVisibility(mode == MULTI_MODE ? View.VISIBLE : View.GONE);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putStringArrayListExtra(RESULT_LIST, resultList);
                setResult(RESULT_OK, resultIntent);
                PhotoSelectActivity.this.finish();
//                Toast.makeText(PhotoSelectActivity.this, "已选中" + resultList.size() + "张图片", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPhotos() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        "调用照片需要开启储存权限",
                        REQUEST_STORAGE_WRITE_ACCESS_PERMISSION_FILE);
            }
        } else {
            getSupportLoaderManager().initLoader(LOADER_ALL, null, mLoaderCallback);
        }
    }

    /**
     * Open camera
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showCameraAction() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    "调用相机拍照需要开启储存权限",
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION_CAMERA);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.CAMERA,
                    "请开启相机权限",
                    REQUEST_PERMISSION_CAMERA);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                try {
                    mTmpFile = FileUtils.createTmpFile(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (mTmpFile != null && mTmpFile.exists()) {
                    Uri uri = null;
                    if (Build.VERSION.SDK_INT >= 24) {
                        uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", mTmpFile);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                    } else {
                        uri = Uri.fromFile(mTmpFile);
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else {
                    Toast.makeText(this, "图片错误", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "没有发现相机", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (shouldShowRequestPermissionRationale(permission)) {
            new AlertDialog.Builder(this)
                    .setTitle("权限被拒绝")
                    .setMessage(rationale)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton("取消", null)
                    .create().show();
        } else {
            requestPermissions(new String[]{permission}, requestCode);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_WRITE_ACCESS_PERMISSION_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showCameraAction();
            }
        } else if (requestCode == REQUEST_STORAGE_WRITE_ACCESS_PERMISSION_FILE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhotos();
            }
        } else if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showCameraAction();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_TEMP_FILE, mTmpFile);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mTmpFile = (File) savedInstanceState.getSerializable(KEY_TEMP_FILE);
        }
    }

    private void selectImageFromGrid(Image image, int mode) {
        if (image != null) {
            if (mode == MULTI_MODE) {
                if (resultList.contains(image.path)) {
                    resultList.remove(image.path);
                } else {
                    if (maxCount == resultList.size()) {
                        Toast.makeText(this, "已达到最大选择数量", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    resultList.add(image.path);
                }
                gridAdapter.select(image);
            } else if (mode == SINGLE_MODE) {
                resultList.clear();
                resultList.add(image.path);     //集合中只有一个元素
                Intent intent = new Intent(this, ClipImageActivity.class);
                intent.putStringArrayListExtra(ClipImageActivity.IMGPATH, resultList);
                startActivityForResult(intent, REQUEST_CLIP);
            }
        }
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {

        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media._ID};

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            CursorLoader cursorLoader = null;
            if (id == LOADER_ALL) {
                cursorLoader = new CursorLoader(PhotoSelectActivity.this,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        IMAGE_PROJECTION[4] + ">0 AND " + IMAGE_PROJECTION[3] + "=? OR " + IMAGE_PROJECTION[3] + "=? ",
                        new String[]{"image/jpeg", "image/png"}, IMAGE_PROJECTION[2] + " DESC");
            } else if (id == LOADER_CATEGORY) {
                cursorLoader = new CursorLoader(PhotoSelectActivity.this,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        IMAGE_PROJECTION[4] + ">0 AND " + IMAGE_PROJECTION[0] + " like '%" + args.getString("path") + "%'",
                        null, IMAGE_PROJECTION[2] + " DESC");
            }
            return cursorLoader;
        }

        private boolean fileExist(String path) {
            if (!TextUtils.isEmpty(path)) {
                return new File(path).exists();
            }
            return false;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (data != null) {
                if (data.getCount() > 0) {
                    List<Image> images = new ArrayList<>();
                    data.moveToFirst();
                    do {
                        String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                        String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                        long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                        if (!fileExist(path)) {
                            continue;
                        }
                        Image image = null;
                        if (!TextUtils.isEmpty(name)) {
                            image = new Image(path, name, dateTime);
                            images.add(image);
                        }
                    } while (data.moveToNext());

                    gridAdapter.setData(images);
                    if (resultList != null && resultList.size() > 0) {
                        gridAdapter.setDefaultSelected(resultList);
                    }
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                if (mTmpFile != null) {
                    if (resultList.size() < maxCount) {
                        resultList.add(mTmpFile.getAbsolutePath());
                        addCameraToShow(mTmpFile, true);    //将拍照结果放到adapter中显示并选中
                        saveCameraPic(mTmpFile);    //将拍照结果放到系统中
                    } else {
                        addCameraToShow(mTmpFile, false);  //将拍照结果放到adapter中显示不选中
                        Toast.makeText(this, "已达到最大选择数量", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                // delete tmp file
                while (mTmpFile != null && mTmpFile.exists()) {
                    boolean success = mTmpFile.delete();
                    if (success) {
                        mTmpFile = null;
                    }
                }
            }
        } else if (requestCode == REQUEST_CLIP) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String path = data.getStringExtra(ClipImageActivity.IMGPATH_NEW);
                    String imageName = data.getStringExtra(ClipImageActivity.IMAGE_NAME);
                    resultList.clear();
                    resultList.add(path);
                    Intent resultIntent = new Intent();
                    resultIntent.putStringArrayListExtra(RESULT_LIST, resultList);
                    resultIntent.putExtra(ClipImageActivity.IMAGE_NAME, imageName);
                    setResult(RESULT_OK, resultIntent);
                    PhotoSelectActivity.this.finish();
                }
            }
        }
    }

    private void addCameraToShow(File imgFile, boolean isSelected) {
        Image image = new Image(imgFile.getAbsolutePath(), imgFile.getName(), System.currentTimeMillis());
        gridAdapter.addDataAndNotify(image, isSelected);
    }

    private boolean saveCameraPic(File imgFile) {
        try {
            ContentValues values = new ContentValues();
            long time = System.currentTimeMillis();
            values.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DATA, imgFile.getAbsolutePath());
            values.put(MediaStore.Images.Media.DISPLAY_NAME, imgFile.getName());
            values.put(MediaStore.Images.Media.DATE_ADDED, time);
            values.put(MediaStore.Images.Media.TITLE, imgFile.getName());
            values.put(MediaStore.Images.ImageColumns.DATE_TAKEN, time);
            Application app = getApplication();
            ContentResolver cr = app.getContentResolver();
            cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void jumpPhotoSelectActivity(Activity activity, int choose_mode, boolean show_camera,
                                               int max_count, int requestCode) {
        Intent intent = new Intent();
        intent.putExtra(PhotoSelectActivity.CHOOSE_MODE, choose_mode);
        intent.putExtra(PhotoSelectActivity.SHOW_CAMERA, show_camera);
        intent.putExtra(PhotoSelectActivity.MAX_COUNT, max_count);
        intent.setClass(activity, PhotoSelectActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }
}
