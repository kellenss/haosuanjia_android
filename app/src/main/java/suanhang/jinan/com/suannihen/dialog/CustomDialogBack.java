package suanhang.jinan.com.suannihen.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import suanhang.jinan.com.suannihen.R;

/**
 * Create custom Dialog windows for your application Custom dialogs rely on
 * custom layouts wich allow you to create and use your own look & feel.
 * <p/>
 * Under GPL v3 : http://www.gnu.org/licenses/gpl-3.0.html
 * 
 * @author antoine vianey
 */
public class CustomDialogBack extends Dialog {

	public CustomDialogBack(Context context, int theme) {
		super(context, theme);
	}

	public CustomDialogBack(Context context) {
		super(context);
	}

	/**
	 * Helper class for creating a custom dialog
	 */
	public static class Builder {

		private Context context;
		private String message;
		private boolean cancelableTouchOutside = true;
		private boolean cancelable = true;
		private String positiveButtonText;
		private String negativeButtonText;
        int msgTextSize;
		int negativeButtonTextSize;
		int positiveButtonTextSize;
		private OnClickListener listenerRight,
				listenerLeft;
		private boolean isSetMessgeColor = false;

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

		public Builder setMessage(String message, int textSize) {
			this.message = message;
			this.msgTextSize = textSize;
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
		 * Set the Dialog message from resource
		 */
		public Builder setMessage(int message, int textSize) {
			this.message = (String) context.getText(message);
            this.msgTextSize = textSize;
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
		public Builder setMessgeColor(boolean isSetMessgeColor) {
			this.isSetMessgeColor = isSetMessgeColor;
			return this;
		}

		/**
		 * Set the positive button resource and it's listener
		 */
		public Builder setPositiveButton(int positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.listenerRight = listener;
			this.positiveButtonTextSize = 0;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText,
										 OnClickListener listener,int textSize) {
			this.positiveButtonText = positiveButtonText;
			this.listenerRight = listener;
			this.positiveButtonTextSize = textSize;
			return this;
		}

		/**
		 * Set the positive button text and it's listener
		 */
		public Builder setPositiveButton(String positiveButtonText,
				OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.listenerRight = listener;
			this.positiveButtonTextSize = 0;
			return this;
		}

		/**
		 * Set the negative button resource and it's listener
		 */
		public Builder setNegativeButton(int negativeButtonText,
				OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.listenerLeft = listener;
			this.negativeButtonTextSize = 0;
			return this;
		}

		public Builder setNegativeButton(int negativeButtonText,
										 OnClickListener listener,int textSize) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.listenerLeft = listener;
			this.negativeButtonTextSize = textSize;
			return this;
		}

		/**
		 * Set the negative button text and it's listener
		 */
		public Builder setNegativeButton(String negativeButtonText,
				OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.listenerLeft = listener;
			this.negativeButtonTextSize = 0;
			return this;
		}

		/**
		 * Create the custom dialog
		 */
		public CustomDialogBack create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			final CustomDialogBack dialog = new CustomDialogBack(context,
					R.style.CustomDialog);
			View layout = inflater.inflate(R.layout.custom_dialog_back, null);
			int screenWidth = context.getResources().getDisplayMetrics().widthPixels ;
			dialog.addContentView(layout, new LayoutParams((int) (screenWidth * 0.8), LayoutParams.MATCH_PARENT));
			// set the confirm button
			if (positiveButtonText != null) {
				TextView negativeButton = ((TextView) layout.findViewById(R.id.positiveButton));
				negativeButton.setText(positiveButtonText);
				negativeButton
                        .setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                if(listenerRight != null)
                                    listenerRight.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                                dialog.dismiss();
                            }
                        });

				if(negativeButtonTextSize!=0){
					negativeButton.setTextSize(negativeButtonTextSize);
				}
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.positiveButton).setVisibility(
						View.GONE);
				layout.findViewById(R.id.v_line).setVisibility(
						View.GONE);
			}
			// set the cancel button
			if (negativeButtonText != null) {
				TextView negativeButton = ((TextView) layout.findViewById(R.id.negativeButton));
				negativeButton.setText(negativeButtonText);
				if(isSetMessgeColor){
					negativeButton.setTextColor(ContextCompat.getColor(context,R.color.colorTitleBgRed));
				}
				if(negativeButtonTextSize!=0){
					negativeButton.setTextSize(negativeButtonTextSize);
				}

                layout.findViewById(R.id.negativeButton)
                        .setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                if(listenerLeft != null)
                                    listenerLeft.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                                dialog.dismiss();
                            }
                        });
			} else {
				// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.negativeButton).setVisibility(View.GONE);
				layout.findViewById(R.id.v_line).setVisibility(View.GONE);
			}
			// set the content message
			if (message != null) {
                TextView msgText = (TextView) layout.findViewById(R.id.message);
                msgText.setText(message);
				if(isSetMessgeColor){
					msgText.setTextColor(ContextCompat.getColor(context,R.color.list_item_content));
				}
                if(msgTextSize !=0){
                    msgText.setTextSize(msgTextSize);
                }
			}
            
//			dialog.setContentView(layout);
			dialog.setCanceledOnTouchOutside(cancelableTouchOutside);
			dialog.setCancelable(cancelable);
			return dialog;
		}

		public void show() {
			try {
				create().show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}