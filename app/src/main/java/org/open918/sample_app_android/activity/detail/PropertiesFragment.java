package org.open918.sample_app_android.activity.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.open918.lib.domain.Carrier;
import org.open918.lib.domain.GenericTicketDetails;
import org.open918.sample_app_android.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Joel Haasnoot on 18/10/15.
 */
public class PropertiesFragment extends BaseTicketFragment {

    public static Fragment getInstance() {
        return new PropertiesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getTicket();

        View v = inflater.inflate(R.layout.fragment_details, container, false);

        setupTicketFields(v);
        setupBarcodeFields(v);
        setupTicketDetailFields(v);


        return v;
    }

    private void setupTicketDetailFields(View v) {
        // Ticket details
        // TODO - needs better name
        TextView ticketCreated = v.findViewById(R.id.text_barcode_creation);
        Date created = result.getTicket().getHeader().getCreationDateAsDate();
        ticketCreated.setText(SimpleDateFormat.getDateTimeInstance().format(created));

        TextView ticketOrder = v.findViewById(R.id.text_order_number);
        String order = result.getTicket().getHeader().getOrderNumber().trim();
        ticketOrder.setText((order.replace(" ", "").isEmpty()) ? getString(R.string.label_empty) : order);

        TextView ticketLanguages = v.findViewById(R.id.text_languages);
        ticketLanguages.setText(String.format("%s, %s", result.getTicket().getHeader().getLanguage(),
                result.getTicket().getHeader().getLanguage2()));

//        TextView ticketFlags = (TextView) v.findViewById(R.id.text_ticket_flags);
//        EnumSet<TicketFlag> flags = result.getTicket().getHeader().getFlags();
//        StringBuffer out = new StringBuffer();
//        /* TODO: Fix this
//        for (TicketFlag f : flags) {
//            out.append(getString(f.getLabelResource()));
//            out.append("\r\n");
//        }*/
//        ticketFlags.setText(out.toString());
    }

    private void setupBarcodeFields(View v) {
        // Barcode details
        TextView barcodeCarrier = v.findViewById(R.id.barcode_carrier);
        barcodeCarrier.setText(Carrier.fromRics(result.getTicket().getRicsCode()).getLabel());

        TextView barcodeSignatureId = v.findViewById(R.id.barcode_signature_id);
        barcodeSignatureId.setText(result.getTicket().getSignatureKeyId());
    }

    private void setupTicketFields(View v) {
        int grey = ContextCompat.getColor(getActivity(), R.color.text_grey);

        // Travel details
        GenericTicketDetails details = converter.toDetails();
        TextView travelTitle = v.findViewById(R.id.travel_ticket_name);
        if (!details.getTicketTitle().equalsIgnoreCase("")) {
            travelTitle.setText(details.getTicketTitle());
        } else {
            setFieldUnknown(grey, travelTitle);
        }

        TextView travelPassenger = v.findViewById(R.id.travel_passenger_name);
        if (!details.getPassengerName().equalsIgnoreCase("")) {
            travelPassenger.setText(details.getPassengerName());
        } else {
            setFieldUnknown(grey, travelPassenger);
        }

        TextView travelOutward = v.findViewById(R.id.travel_outward);
        if (isNotEmpty(details.getOutwardDeparture())) {
            travelOutward.setText(String.format("%s - %s", details.getOutwardDeparture(), details.getOutwardArrival()));
        } else {
            setFieldUnknown(grey, travelOutward);
        }

        TextView travelReturn = v.findViewById(R.id.travel_return);
        if (isNotEmpty(details.getReturnDeparture())) {
            travelReturn.setText(String.format("%s - %s", details.getReturnDeparture(), details.getReturnArrival()));
        } else {
            setFieldUnknown(grey, travelReturn);
        }

        TextView travelPrice = v.findViewById(R.id.travel_price);
        if (isNotEmpty(details.getPrice())) {
            travelPrice.setText(details.getPrice());
        } else {
            setFieldUnknown(grey, travelPrice);
        }

        TextView travelClass = v.findViewById(R.id.travel_class);
        if (isNotEmpty(details.getTravelClass())) {
            travelClass.setText(details.getTravelClass());
        } else {
            setFieldUnknown(grey, travelClass);
        }
    }

    private boolean isNotEmpty(String field) {
        return field != null && !field.equalsIgnoreCase("") && !field.contains("**");
    }

    private void setFieldUnknown(int grey, TextView text) {
        text.setText(R.string.label_field_unknown);
        text.setTextColor(grey);
    }
}
