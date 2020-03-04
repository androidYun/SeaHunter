package com.xhs.baselibrary.ui.crash;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.xhs.baselibrary.R;
import com.xhs.baselibrary.utils.ToastUtils;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 18/04/2019.
 * description:
 */
public class CrashActivity extends AppCompatActivity {

    TextView tvCrash, tvBug;

    private static final String CRASH_RESULT_KEY = "CRASH_RESULT_KEY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_layout);
        tvCrash = findViewById(R.id.tvCrash);
        tvBug = findViewById(R.id.tvBug);
        if (getIntent() != null && getIntent().getExtras() != null) {
            tvCrash.setText(getIntent().getExtras().getString(CRASH_RESULT_KEY) == null ? "没有捕获到bug 请找程序员仔细查找" : getIntent().getExtras().getString(CRASH_RESULT_KEY));
        }
        tvBug.setOnClickListener(v -> {
            ClipboardManager myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            myClipboard.setPrimaryClip(ClipData.newPlainText("text", tvCrash.getText()));
           ToastUtils.show("复制成功");
        });
    }

    public static Bundle getArgument(String result) {
        Bundle bundle = new Bundle();
        bundle.putString(CRASH_RESULT_KEY, result);
        return bundle;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            System.exit(0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
