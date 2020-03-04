package com.xhs.baselibrary.weight;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;

/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 17/01/2020.
 * description:
 */
public class IpEditText extends LinearLayout {
    private static final int OCTET_NUM = 4;

    private EditText ipOctet[] = new EditText[OCTET_NUM];

    public IpEditText(Context context) {
        super(context);
        InitUI();
        checkInput();
    }

    public IpEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        InitUI();
        checkInput();
    }

    public IpEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitUI();
        checkInput();
    }

    private void InitUI() {
        TextView colonTxt[] = new TextView[OCTET_NUM - 1];
        for (int i = 0; i < OCTET_NUM; i++) {
            ipOctet[i] = new EditText(getContext());
            ipOctet[i].setTag(i);
            addView(ipOctet[i]);
            if (i < OCTET_NUM - 1) {
                colonTxt[i] = new TextView(getContext());
                colonTxt[i].setText(".");
                colonTxt[i].setTextSize(24);
                addView(colonTxt[i]);
            }
        }

        for (int i = 0; i < OCTET_NUM; i++) {
            ipOctet[i].setGravity(Gravity.CENTER);
            ipOctet[i].setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
            ipOctet[i].setInputType(InputType.TYPE_CLASS_NUMBER);
            LayoutParams params = (LayoutParams) ipOctet[i].getLayoutParams();
            ipOctet[i].setSingleLine(true);
            ipOctet[i].setImeOptions(EditorInfo.IME_ACTION_NEXT);//set keyboard nex button
            params.weight = 1;
            params.width = 0;
        }
    }

    private void checkInput() {
        for (int i = 0; i < OCTET_NUM; i++) {
            ipOctet[i].addTextChangedListener(new MyTextWatcher(ipOctet[i]) {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    EditText editText = getEditTextView();
                    int ipByte;
                    if (editText.getText().length() > 0) {
                        ipByte = Integer.valueOf(editText.getText().toString());
                    } else {
                        ipByte = -1;
                    }
                    if ((editText == ipOctet[0] && ipByte < 1) || ipByte > 255) {
                        addWarnAnim(editText);
                    }
                    int position = (int) editText.getTag();
                    if (s.length() == 3) {
                        if (position < OCTET_NUM - 1) {
                            ipOctet[position + 1].requestFocus();
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            ipOctet[i].setOnEditorActionListener((editText, actionId, event) -> {
                int ipByte;
                if (editText.getText().length() > 0) {
                    ipByte = Integer.valueOf(editText.getText().toString());
                } else {
                    ipByte = -1;
                }
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if ((editText == ipOctet[0] && ipByte < 1) || ipByte > 255 || ipByte < 0) {
                        addWarnAnim((EditText) editText);
                        return true;
                    }
                }
                return false;
            });
        }
    }

    private void addWarnAnim(EditText editText) {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setRepeatCount(8);
        TranslateAnimation rightAnim = new TranslateAnimation(0, 30, 0, 0);
        rightAnim.setDuration(60);
        TranslateAnimation leftAnim = new TranslateAnimation(30, 0, 0, 0);
        rightAnim.setDuration(60);
        animationSet.addAnimation(rightAnim);
        animationSet.addAnimation(leftAnim);
        editText.startAnimation(animationSet);
    }

    public String getIpAddress() {
        StringBuffer sb = new StringBuffer();
        int ipByte;
        for (int i = 0; i < OCTET_NUM; i++) {
            if (ipOctet[i].getText().length() > 0) {
                ipByte = Integer.valueOf(ipOctet[i].getText().toString());
            } else {
                ipByte = -1;
            }
            if ((ipOctet[i] == ipOctet[0] && ipByte < 1) || ipByte > 255 || ipByte < 0) {
                addWarnAnim(ipOctet[i]);
                ipOctet[i].requestFocus();
                return null;
            }
            sb.append(ipOctet[i].getText());
            if (i < OCTET_NUM - 1) {
                sb.append(".");
            }
        }
        return sb.toString();
    }

    public void setIpAddress(String ipAddress) {
        String[] split = ipAddress.split("\\.");
        for (int i = 0; i < split.length; i++) {
            ipOctet[i].setText(split[i]);
        }
    }
}

abstract class MyTextWatcher implements TextWatcher {
    private EditText editText;

    public MyTextWatcher(EditText editText) {
        this.editText = editText;
    }

    public EditText getEditTextView() {
        return editText;
    }

    public ViewParent getParentView() {
        return editText.getParent();
    }

    public View getRootView() {
        return editText.getRootView();
    }
}
