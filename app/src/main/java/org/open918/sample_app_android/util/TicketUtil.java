package org.open918.sample_app_android.util;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.open918.lib.UicTicketParser;
import org.open918.lib.domain.Ticket;
import org.open918.sample_app_android.activity.detail.TicketDetailActivity;
import org.open918.sample_app_android.domain.ScanResult;

import java.text.ParseException;
import java.util.zip.DataFormatException;

/**
 * Created by Joel Haasnoot on 18/10/2016.
 */

public class TicketUtil {

    private static final String TAG = TicketUtil.class.getSimpleName();

    public static ScanResult getTicket(Bundle savedInstanceState, Intent i) {
        String ticketString;
        if (savedInstanceState != null) {
            ticketString = savedInstanceState.getString("ticket");
        } else {
            ticketString = i.getStringExtra("ticket");
        }
        if (ticketString != null && !ticketString.isEmpty()) {
            Ticket t = null;
            try {
                t = UicTicketParser.decode(ticketString, true);
            } catch (DataFormatException | ParseException e) {
                Log.e(TAG, "Failed to parse ticket", e);
            }
            return new ScanResult(ticketString, t);
        }
        return null;
    }


}
