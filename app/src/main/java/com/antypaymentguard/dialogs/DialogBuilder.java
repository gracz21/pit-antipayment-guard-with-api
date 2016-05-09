package com.antypaymentguard.dialogs;

import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class DialogBuilder {

	private DialogBuilder() {
	}

	public static DialogBuilder create(FragmentActivity activityHost) {
		final DialogBuilder builder = new DialogBuilder();

		builder._builder = BaseDialog.createBuilder(activityHost, activityHost.getSupportFragmentManager());
		builder._activityHost = activityHost;
		return builder;
	}

	public static boolean isDialogShowAvailable(FragmentActivity activity) {
		if (activity == null) {
			return false;
		}
		return !(activity.isFinishing() || activity.getSupportFragmentManager().isDestroyed());
	}

	public DialogBuilder setTitle(String title) {
		_builder.setTitle(title);
		return this;
	}

	public DialogBuilder setTitle(int titleStringId) {
		_builder.setTitle(titleStringId);
		return this;
	}

	public DialogBuilder setMessage(String message) {
		_builder.setMessage(message);
		return this;
	}

	public DialogBuilder setMessage(int messageStringId) {
		_builder.setMessage(messageStringId);
		return this;
	}

	public DialogBuilder setRequestrCode(int requestCode) {
		_builder.setRequestCode(requestCode);
		return this;
	}

	public DialogBuilder setButtonPositive(int textStringId) {
		_builder.setPositiveButtonText(textStringId);
		return this;
	}

	public DialogBuilder setButtonNeutral(int textStringId) {
		_builder.setNeutralButtonText(textStringId);
		return this;
	}

	public DialogBuilder setButtonNeutral(String textString) {
		_builder.setNeutralButtonText(textString);
		return this;
	}

	public DialogBuilder setButtonNegative(int textStringId) {
		_builder.setNegativeButtonText(textStringId);
		return this;
	}

	public DialogBuilder setTargetFragment(Fragment fragment, int requestCode) {
		_builder.setTargetFragment(fragment, requestCode);
		return this;
	}

	public DialogBuilder setCancelable(boolean isCancelable) {
		_builder.setCancelable(isCancelable);
		return this;
	}

	@Nullable
	public DialogFragment show() throws IllegalStateException {
		if (_tag == null) {
			return _builder.show();
		}

		if (_activityHost.getSupportFragmentManager().findFragmentByTag(_tag) == null) {
			return _builder.show();
		}
		return null;
	}

	@Nullable
	public DialogFragment showSafe() {
		try {
			return show();
		} catch (IllegalStateException ex) {
			ex.printStackTrace();//simply ignore
		}
		return null;
	}

	public DialogBuilder setTag(String tag) {
		_tag = tag;
		_builder.setTag(tag);
		return this;
	}

	private BaseDialog.BaseDialogBuilder _builder;
	private FragmentActivity _activityHost;
	private String _tag;
}
