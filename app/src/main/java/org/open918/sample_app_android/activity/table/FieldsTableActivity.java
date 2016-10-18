package org.open918.sample_app_android.activity.table;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.open918.sample_app_android.R;
import org.open918.sample_app_android.domain.ScanResult;
import org.open918.sample_app_android.util.TicketUtil;

public class FieldsTableActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TicketFieldsAdapter mAdapter;

    private ScanResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fields_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        result = TicketUtil.getTicket(savedInstanceState, getIntent());

        if (result != null && result.isReadable()) {
            mRecyclerView = (RecyclerView) findViewById(R.id.list_fields);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new TicketFieldsAdapter(this, result.getTicket().getContents().getFields());
            mRecyclerView.setAdapter(mAdapter);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("ticket", result.getContents());
    }

}
