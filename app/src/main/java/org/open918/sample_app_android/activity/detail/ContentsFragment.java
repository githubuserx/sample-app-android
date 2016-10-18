package org.open918.sample_app_android.activity.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.open918.sample_app_android.R;
import org.open918.sample_app_android.activity.table.FieldsTableActivity;
import org.open918.sample_app_android.util.BarcodeWriter;

/**
 * Created by Joel Haasnoot on 18/10/15.
 */
public class ContentsFragment extends BaseTicketFragment {

    public static Fragment getInstance() {
        return new ContentsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getTicket();

        View fragment = inflater.inflate(R.layout.fragment_contents, container, false);

        TextView ticketContents = (TextView) fragment.findViewById(R.id.label_ticket_contents);
        ticketContents.setText(converter.toText());
        ticketContents.setTypeface(Typeface.MONOSPACE);

        Button shareButton = (Button) fragment.findViewById(R.id.button_share);
        shareButton.setOnClickListener(new ShareClickListener(getContext(), getResultAsBase64()));

        Button buttonTable = (Button) fragment.findViewById(R.id.button_table);
        buttonTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(getActivity(), FieldsTableActivity.class);
                start.putExtra("ticket", result.getContents());
                startActivity(start);
            }
        });

        return fragment;
    }


}
