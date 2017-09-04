package vn.fjobs.base.view.customeview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import project.fjobs.R;

public class CustomConfirmDialog extends AlertDialog implements DialogInterface.OnClickListener {
    private OnButtonClickListener buttonClickListener;
    private OnCancelClickListener cancelClickListener;

    public interface OnButtonClickListener {
        public void onYesClick();
    }

    public interface OnCancelClickListener {
        public void OnCancelClick();
    }

    public CustomConfirmDialog(Context context, String title, String msg,
                               boolean isYesNo) {
        super(context);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
        if (!TextUtils.isEmpty(msg)) {
            setMessage(msg);
        }

        if (isYesNo) {
            setButton(BUTTON_POSITIVE, context.getText(R.string.common_yes),
                    this);
            setButton(BUTTON_NEGATIVE, context.getText(R.string.common_cancel),
                    (OnClickListener) null);
        } else {
            setButton(BUTTON_POSITIVE, context.getText(R.string.common_ok),
                    this);
        }
    }

    public void setOnButtonClick(OnButtonClickListener listen) {
        buttonClickListener = listen;
    }

    public void setCancelClickListener(OnCancelClickListener cancelClickListener) {
        this.cancelClickListener = cancelClickListener;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == BUTTON_NEGATIVE) {
            if (cancelClickListener != null) {
                cancelClickListener.OnCancelClick();
            }
        } else if (which == BUTTON_POSITIVE) {
            if (buttonClickListener != null) {
                buttonClickListener.onYesClick();
            }
        }
    }

}
