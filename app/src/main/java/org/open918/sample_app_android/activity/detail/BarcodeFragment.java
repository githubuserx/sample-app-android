package org.open918.sample_app_android.activity.detail;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.open918.sample_app_android.R;
import org.open918.sample_app_android.util.BarcodeWriter;

/**
 * Created by Joel Haasnoot on 18/10/15.
 */
public class BarcodeFragment extends BaseTicketFragment {

    public static Fragment getInstance() {
        return new BarcodeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getTicket();

        View fragment = inflater.inflate(R.layout.fragment_barcode, container, false);

        ImageView barcode = fragment.findViewById(R.id.image_barcode);
        barcode.setImageBitmap(createBarcode(result.getContents()));

        return fragment;
    }

    private Bitmap createBarcode(String encode) {
        return BarcodeWriter.createBarcode(encode);
    }


}
