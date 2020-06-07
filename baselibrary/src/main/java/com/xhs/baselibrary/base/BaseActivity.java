package com.xhs.baselibrary.base;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.view.WindowManager;

import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;
import com.xhs.baselibrary.R;
import com.xhs.baselibrary.dialog.base.CommonDialog;
import com.xhs.baselibrary.utils.KindPhonePermissionUtils;
import com.xhs.baselibrary.utils.ToastUtils;
import com.xhs.baselibrary.weight.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 29/04/2019.
 * description:
 */

public class BaseActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:   //返回键的id
                this.finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * 申请运行时权限
     *
     * @param permissions
     */
    public void checkPermissions(String... permissions) {
        checkPermissionsWithCode(0, permissions);
    }

    /**
     * 检查权限
     *
     * @param code        权限申请code（根据code判断结果，接受回调）
     * @param permissions 权限的内容
     * @return 是否开启了权限
     */
    public boolean checkPermissionsWithCode(int code, String... permissions) {
        List<String> needRequestPermissionList = findDeniedPermissions(permissions);
        if (null != needRequestPermissionList && needRequestPermissionList.size() > 0) {
            ActivityCompat.requestPermissions(this, needRequestPermissionList.toArray(new String[needRequestPermissionList.size()]), code);
            return false;
        } else {
            onPermissionsGranted(code, permissions);
            return true;
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
                needRequestPermissionList.add(perm);
            }
        }
        return needRequestPermissionList;
    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] paramArrayOfInt) {
        if (!verifyPermissions(paramArrayOfInt)) {
            onPermissionsDenied();
        } else {
            onPermissionsGranted(requestCode, permissions);
        }
    }

    /**
     * 同意权限
     *
     * @param code
     * @param permissions
     */
    protected void onPermissionsGranted(int code, String[] permissions) {
        if (code == 0) {
            onPermissionsGranted(permissions);
        }
    }

    protected void onPermissionsGranted(String[] permissions) {
    }

    protected void onPermissionsDenied() {
        new CommonDialog.CommonBuilder(this)
                .setMessage(getString(R.string.base_please_open_get_phone_device_permission))
                .setNegativeButton(getString(R.string.base_no), null)
                .setPositiveButton(getString(R.string.base_yes), v -> {
                    KindPhonePermissionUtils.jumpPermission(this);
                }).setWidthPercentage(0.7)
                .create().show();
    }

    protected void handleError(Throwable e) {
        String errorString = e.toString();
        if (errorString.contains("ConnectException")) {
            ToastUtils.show("未连接到服务器");
        } else if (errorString.contains("SocketTimeoutException")) {
            ToastUtils.show("请求超时");
        } else if (errorString.contains("NetworkErrorException")) {
            ToastUtils.show("网络错误");
        } else if (errorString.contains("JSONException")) {
            ToastUtils.show("内部服务器出错");
        } else if (errorString.contains("LoginException")) {
            ToastUtils.show(e.getLocalizedMessage());
        } else if (errorString.contains("UnknownHostException")) {
            ToastUtils.show("网络连接失败");
        } else if (errorString.contains("SocketException")) {
            ToastUtils.show("未连接到服务器");
        } else {
            ToastUtils.show(e.getMessage());
        }
    }

    private LoadingDialog mLoading;

    protected void showProgressDialog() {
        if (mLoading == null) {
            mLoading = new LoadingDialog(this);
            mLoading.setOnDismissListener(dialog -> mLoading = null);
        }
        if (!this.isFinishing()) {
            mLoading.show();
        }
    }


    /**
     * 不显示加载dialog
     */
    public void hideProgressDialog() {
        if (mLoading != null) {
            mLoading.cancel();
        }
    }

    public View getEmptyView() {
        return getLayoutInflater().inflate(R.layout.empty_data_layout, null);
    }


}
