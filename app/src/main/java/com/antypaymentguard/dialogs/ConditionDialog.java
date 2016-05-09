package com.antypaymentguard.dialogs;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.antypaymentguard.R;

/**
 * Created by Maciej Kozłowski on 09.05.16.
 */
public class ConditionDialog extends BaseDialog {
    public static void show(FragmentActivity activity) {
        if (!DialogBuilder.isDialogShowAvailable(activity)) {
            return;
        }

        try {
            final ConditionDialog dialog = new ConditionDialog();
            dialog.show(activity.getSupportFragmentManager(), TAG);
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            //Simply ignore
        }
    }

    @Override
    public Builder build(Builder builder) {
        final View view = builder.getLayoutInflater().inflate(R.layout.dialog_condition, null, false);
        builder.setView(view);

        builder.setPositiveButton("Dodaj", new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // Add
                dismiss();
            }
        });

        builder.setNegativeButton("Cofnij", new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                dismiss();
            }
        });


        return builder;
    }

    public static final String TAG = ConditionDialog.class.getSimpleName();
}
