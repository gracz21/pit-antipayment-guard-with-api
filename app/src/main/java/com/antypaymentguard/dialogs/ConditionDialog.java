package com.antypaymentguard.dialogs;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.antypaymentguard.R;
import com.antypaymentguard.activities.MainActivity;
import com.antypaymentguard.models.BankAccount;
import com.antypaymentguard.models.conditions.AmountCondition;
import com.antypaymentguard.models.conditions.Condition;
import com.antypaymentguard.models.conditions.NumberCondition;

/**
 * Created by Maciej Kozłowski on 09.05.16.
 */
public class ConditionDialog extends BaseDialog {
    private BankAccount bankAccount;
    public static final String TAG = ConditionDialog.class.getSimpleName();

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public static void show(FragmentActivity activity, BankAccount bankAccount) {
        if (!DialogBuilder.isDialogShowAvailable(activity)) {
            return;
        }

        try {
            final ConditionDialog dialog = new ConditionDialog();
            dialog.setBankAccount(bankAccount);
            dialog.show(activity.getSupportFragmentManager(), TAG);
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Builder build(Builder builder) {
        final View view = builder.getLayoutInflater().inflate(R.layout.dialog_condition, null, false);
        final EditText editText = (EditText) view.findViewById(R.id.editTextCondition);
        final RadioButton radioButtonAmount = (RadioButton) view.findViewById(R.id.radioButtonAmount);
        builder.setView(view);

        builder.setPositiveButton(getContext().getString(R.string.add), new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (editText.getText().length() < 1) {
                    editText.setError(getContext().getString(R.string.condition_can_not_be_empty));
                    return;
                }

                final Condition condition;
                if (radioButtonAmount.isChecked()) {
                    final double value = Double.parseDouble(editText.getText().toString());
                    condition = new AmountCondition(value);
                } else {
                    final int value = Integer.parseInt(editText.getText().toString());
                    condition = new NumberCondition(value);
                }

                bankAccount.setCondition(condition);
                bankAccount.save();
                dismiss();
            }
        });

        builder.setNegativeButton(getContext().getString(R.string.back), new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                dismiss();
            }
        });


        return builder;
    }
}
