package com.jinan.haosuanjia.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.jinan.haosuanjia.R;


/**
 * Create custom Dialog windows for your application Custom dialogs rely on
 * custom layouts wich allow you to create and use your own look & feel.
 * <p/>
 * Under GPL v3 : http://www.gnu.org/licenses/gpl-3.0.html
 * 
 * @author antoine vianey
 */
public class CustomDialog extends Dialog {

	public CustomDialog(Context context, int theme) {
		super(context, theme);
	}

	public CustomDialog(Context context) {
		super(context);
	}

	/**
	 * Helper class for creating a custom dialog
	 */
	public static class Builder {

		private Context context;
		private String title;
		private String message;
		private boolean cancelableTouchOutside = true;
		private boolean cancelable = true;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private boolean noScroll;
		private int negativeButtonTextColor;

		private OnClickListener positiveButtonClickListener,
				negativeButtonClickListener;

		public Builder(Context context) {
			this.context = context;
		}

		/**
		 * Set the Dialog message from String
		 */
		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		/**
		 * Set the Dialog message from resource
		 */
		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}

		/**
		 * Set the Dialog title from resource
		 */
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		/**
		 * Set the Dialog title from String
		 */
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		/**
		 * Set a custom content view for the Dialog. If a message is set, the
		 * contentView is not added to the Dialog...
		 */
		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		public Builder setCancelableTouchOutside(boolean cancelableTouchOutside) {
			this.cancelableTouchOutside = cancelableTouchOutside;
			return this;
		}

		public Builder setCancelable(boolean cancelable) {
			this.cancelable = cancelable;
			return this;
		}

		/**
		 * Set the positive button resource and it's listener
		 */
		public Builder setPositiveButton(int positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		/**
		 * Set the positive button text and it's listener
		 */
		public Builder setPositiveButton(String positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		/**
		 * Set the negative button resource and it's listener
		 */
		public Builder setNegativeButton(int negativeButtonText,
				OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		/**
		 * Set the negative button text and it's listener
		 */
		public Builder setNegativeButton(String negativeButtonText,
				OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButtonTextColor(int color){
			negativeButtonTextColor = color;
			return this;
		}
		public Builder setNoScroll(boolean noScroll){
			this.noScroll = noScroll;
			return this;
		}

		/**
		 * Create the custom dialog
		 */
		public CustomDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme

			final CustomDialog dialog = new CustomDialog(context,
					R.style.CustomDialog);
			View layout = inflater.inflate(R.layout.custom_dialog, null);
			int screenWidth = context.getResources().getDisplayMetrics().widthPixels ;
			dialog.addContentView(layout, new LayoutParams((int) (screenWidth * 0.8), LayoutParams.WRAP_CONTENT));
			// set the dialog title
			((TextView) layout.findViewById(R.id.title)).setText(title);
			// set the confirm button
			if (positiveButtonText != null) {
				TextView positiveButton = (TextView) layout.findViewById(R.id.positiveButton);
				positiveButton.setText(positiveButtonText);
				positiveButton.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								if (positiveButtonClickListener != null)
									positiveButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								dialog.dismiss();
							}
						});
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.positiveButton).setVisibility(View.GONE);
				layout.findViewById(R.id.v_divider).setVisibility(View.GONE);
			}
			// set the cancel button
			if (negativeButtonText != null) {
				TextView negativeButton = (TextView) layout.findViewById(R.id.negativeButton);
				negativeButton.setText(negativeButtonText);
				negativeButton.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								if (negativeButtonClickListener != null)
									negativeButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
								dialog.dismiss();
							}
						});
				if(negativeButtonTextColor != 0)
					negativeButton.setTextColor(negativeButtonTextColor);
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.negativeButton).setVisibility(View.GONE);
				layout.findViewById(R.id.v_divider).setVisibility(View.GONE);
			}
			if(noScroll){
				((TextView) layout.findViewById(R.id.tv_message_noscroll)).setText(message);
				layout.findViewById(R.id.tv_message_noscroll).setVisibility(View.VISIBLE);
			} else {
				// set the content message
				if (!TextUtils.isEmpty(message)) {
					((TextView) layout.findViewById(R.id.tv_message_scroll)).setText(message);
					layout.findViewById(R.id.scrollview).setVisibility(View.VISIBLE);
				}
			}
			dialog.setCanceledOnTouchOutside(cancelableTouchOutside);
			dialog.setCancelable(cancelable);
			return dialog;
		}

		public void show() {
			create().show();
		}

	}

}