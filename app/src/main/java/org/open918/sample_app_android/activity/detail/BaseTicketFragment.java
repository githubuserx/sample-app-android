package org.open918.sample_app_android.activity.detail;

import android.support.v4.app.Fragment;
import android.util.Base64;

import org.open918.lib.encodings.ConverterFactory;
import org.open918.lib.encodings.TicketConverter;
import org.open918.sample_app_android.domain.ScanResult;


/**
 * Created by Joel Haasnoot on 18/10/15.
 */
public class BaseTicketFragment extends Fragment {

    protected ScanResult result;
    protected TicketConverter converter;

    protected void getTicket() {
        if (getActivity() != null && getActivity() instanceof TicketDetailActivity) {
            result = ((TicketDetailActivity) getActivity()).getScanResult();
            converter = getTicketConverter();
        }
    }

    private TicketConverter getTicketConverter() {
        return ConverterFactory.getInstance(result.getTicket().getContent().getLayout(),
                result.getTicket().getContent().getFields());
    }

    protected String getResultAsBase64() {
        return getResultAsBase64(result);
    }

    public static String getResultAsBase64(ScanResult result) {
        return Base64.encodeToString(result.getContents().getBytes(), Base64.DEFAULT);
    }

}
