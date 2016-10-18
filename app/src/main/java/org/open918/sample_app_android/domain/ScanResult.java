package org.open918.sample_app_android.domain;

import org.open918.lib.domain.Ticket;

/**
 * Created by Joel Haasnoot on 01/05/15.
 */
public class ScanResult {

    private String contents;
    private Ticket ticket;

    public ScanResult(String contents, Ticket ticket) {
        this.contents = contents;
        this.ticket = ticket;
    }

    public String getContents() {
        return contents;
    }

    public boolean isUicTicket() {
        return contents.startsWith("#UT");
    }

    public boolean isReadable() {
        return ticket != null;
    }

    public Ticket getTicket() {
        return ticket;
    }
}
