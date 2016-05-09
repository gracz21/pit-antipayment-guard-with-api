package com.antypaymentguard.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.avast.android.dialogs.fragment.SimpleDialogFragment;

public class BaseDialog extends SimpleDialogFragment {
	public static BaseDialogBuilder createBuilder(Context context,
												  android.support.v4.app.FragmentManager fragmentManager) {
		return new BaseDialogBuilder(context, fragmentManager, BaseDialog.class);
	}

	@Override
	public void onActivityCreated(final Bundle savedInstanceState) {

		if (getDialog() == null) {
			setShowsDialog(false);
		}

		super.onActivityCreated(savedInstanceState);
	}

	public static class BaseDialogBuilder extends SimpleDialogBuilder {

		protected BaseDialogBuilder(final Context context, final FragmentManager fragmentManager,
									final Class<? extends SimpleDialogFragment> clazz) {
			super(context, fragmentManager, clazz);
		}
	}
}
