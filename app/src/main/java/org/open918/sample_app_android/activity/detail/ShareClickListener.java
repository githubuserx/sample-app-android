package org.open918.sample_app_android.activity.detail;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import org.open918.sample_app_android.R;

/**
 * Created by Joel Haasnoot on 25/08/16.
 */
class ShareClickListener implements View.OnClickListener {

    private final String base64Contents;
    private final Context context;

    public ShareClickListener(Context ctx, String base64Contents) {
        this.context = ctx;
        this.base64Contents = base64Contents;
    }

    @Override
    public void onClick(View v) {
        if (base64Contents == null) {
            return;
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{ context.getString(R.string.email_barcode)});
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.email_subject));

        String emailBody = context.getString(R.string.email_content)
                + base64Contents;
        intent.putExtra(Intent.EXTRA_TEXT, emailBody);

        context.startActivity(Intent.createChooser(intent, context.getText(R.string.label_send_email)));
    }
}
