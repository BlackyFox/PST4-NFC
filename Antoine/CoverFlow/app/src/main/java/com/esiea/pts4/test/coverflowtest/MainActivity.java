package com.esiea.pts4.test.coverflowtest;

import com.esiea.pts4.test.CoverFlow;
import com.esiea.pts4.test.ReflectingImageAdapter;
import com.esiea.pts4.test.ResourceImageAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

/****
 * The Class CoverFlowTestingActivity.
 */
public class MainActivity extends Activity {

    private TextView textView;

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);/*
        textView = (TextView) findViewById(this.getResources()
                .getIdentifier("statusText", "id", "pl.polidea.coverflow"));
        // note resources below are taken using getIdentifier to allow importing
        // this library as library.
        final CoverFlow coverFlow1 = (CoverFlow) findViewById(this.getResources().getIdentifier("coverflow", "id",
                "pl.polidea.coverflow"));
        setupCoverFlow(coverFlow1, false);
        final CoverFlow reflectingCoverFlow = (CoverFlow) findViewById(this.getResources().getIdentifier(
                "coverflowReflect", "id", "pl.polidea.coverflow"));*/
        textView = (TextView)findViewById(R.id.statusText);
        final CoverFlow coverFlow1 = (CoverFlow)findViewById(R.id.coverflow);
        setupCoverFlow(coverFlow1, false);
    }

    /**
     * Setup cover flow.
     *
     * @param mCoverFlow
     *            the m cover flow
     * @param reflect
     *            the reflect
     */
    private void setupCoverFlow(final CoverFlow mCoverFlow, final boolean reflect) {
        BaseAdapter coverImageAdapter;
        if (reflect) {
            coverImageAdapter = new ReflectingImageAdapter(new ResourceImageAdapter(this));
        } else {
            coverImageAdapter = new ResourceImageAdapter(this);
        }
        mCoverFlow.setAdapter(coverImageAdapter);
        mCoverFlow.setSelection(2, true);
        setupListeners(mCoverFlow);
    }

    /**
     * Sets the up listeners.
     *
     * @param mCoverFlow
     *            the new up listeners
     */
    private void setupListeners(final CoverFlow mCoverFlow) {
        mCoverFlow.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView< ? > parent, final View view, final int position, final long id) {
                textView.setText("Item clicked! : " + id);
            }

        });
        mCoverFlow.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView< ? > parent, final View view, final int position, final long id) {
                textView.setText("Item selected! : " + id);
            }

            @Override
            public void onNothingSelected(final AdapterView< ? > parent) {
                textView.setText("Nothing clicked!");
            }
        });
    }

}