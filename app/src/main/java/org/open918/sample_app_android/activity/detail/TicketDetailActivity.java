package org.open918.sample_app_android.activity.detail;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.open918.lib.UicTicketParser;
import org.open918.lib.domain.Ticket;
import org.open918.sample_app_android.R;
import org.open918.sample_app_android.activity.MainActivity;
import org.open918.sample_app_android.domain.ScanResult;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

import static org.open918.sample_app_android.util.TicketUtil.getTicket;


public class TicketDetailActivity extends AppCompatActivity {

    private static final String TAG = TicketDetailActivity.class.getSimpleName();

    private ScanResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.header_ticket_detail);
        setContentView(R.layout.activity_ticket_detail);

        result = getTicket(savedInstanceState, getIntent());

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);

        if (result != null && result.isReadable()) {
            viewPager.setAdapter(new DetailsFragmentPagerAdapter(getSupportFragmentManager(),
                    TicketDetailActivity.this));
            tabLayout.setupWithViewPager(viewPager);
        } else {
            viewPager.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);

            CardView card = (CardView) findViewById(R.id.card_unknown);
            card.setVisibility(View.VISIBLE);

            Button shareButton = (Button) findViewById(R.id.button_share);
            ShareClickListener share = new ShareClickListener(this, BaseTicketFragment.getResultAsBase64(result));
            shareButton.setOnClickListener(share);
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("ticket", result.getContents());
    }


    public ScanResult getScanResult() {
        return result;
    }

    public class DetailsFragmentPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = new String[] { getString(R.string.tab_properties),
                getString(R.string.tab_contents),
                getString(R.string.tab_barcode) };
        private ArrayList<Fragment> fragments = new ArrayList<>();

        public DetailsFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            fragments.add(PropertiesFragment.getInstance());
            fragments.add(ContentsFragment.getInstance());
            fragments.add(BarcodeFragment.getInstance());
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

}
