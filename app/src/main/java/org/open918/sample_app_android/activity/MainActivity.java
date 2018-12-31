package org.open918.sample_app_android.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;
import com.journeyapps.barcodescanner.ViewfinderView;

import org.open918.lib.UicTicketParser;
import org.open918.lib.domain.Ticket;
import org.open918.sample_app_android.R;
import org.open918.sample_app_android.activity.detail.TicketDetailActivity;

import java.util.Collections;
import java.util.List;

import static android.view.View.GONE;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CAMERA_PERMISSION = 100;

    private BarcodeView barcodeView;
    private ViewfinderView viewFinder;

    private boolean isRunning = false;
    private Button viewfinderButton;
    private View initialView;
    private View foundView;

    private String lastBarcode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        initialView = findViewById(R.id.view_initial);
        foundView = findViewById(R.id.view_found);
        viewFinder = findViewById(R.id.zxing_viewfinder_view);
        viewfinderButton = findViewById(R.id.button_viewfinder);
        barcodeView = findViewById(R.id.zxing_barcode_surface);

        if (!hasCameraPermission()) {
            requestCameraPermission();
        }

        // This starts decoding
        if (hasCameraPermission()) {
            barcodeView.setDecoderFactory(
                    new DefaultDecoderFactory(Collections.singletonList(BarcodeFormat.AZTEC)));
            barcodeView.decodeSingle(callback);
        }

        viewFinder.setCameraPreview(barcodeView);
        viewfinderButton.setOnClickListener(new ViewfinderClickListener());


        if (getIntent().getAction().contentEquals(Intent.ACTION_SEND)) {
            handleBarcode(getIntent().getStringExtra(Intent.EXTRA_TEXT), false);
        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

            Log.e(TAG, "User needs explanation");
            showPaused();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.title_camera)
                    .setMessage(getString(R.string.text_camera))
                    .setPositiveButton(R.string.label_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            doCameraRequest();
                        }
                    })
                    .show();

        } else {
            doCameraRequest();
        }
    }

    private void doCameraRequest() {
        Log.i(TAG, "Requesting camera permission");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                REQUEST_CAMERA_PERMISSION);
    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG, "Starting camera after receiving permission");
                    removePaused();

                } else {

                    Log.i(TAG, "Permission was denied to camera");
                    showPaused();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
            break;
            default:
                break;
        }
    }

    private void handleBarcode(String contents, boolean isZxing) {
        boolean failed = false;
        if(contents != null) {
            vibrate();

            Ticket t = null;
            try {
                t = UicTicketParser.decode(contents, isZxing);
            } catch (Exception e) {
                Log.e(TAG, "Error reading data", e);
                return;
            }
            if (t != null) {
                lastBarcode = contents;
                initialView.setVisibility(GONE);
                foundView.setVisibility(View.VISIBLE);
                Intent start = new Intent(this, TicketDetailActivity.class);
                start.putExtra("ticket", contents);
                startActivity(start);
            } else {

            }

        }
    }

    private void vibrate() {
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(200);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.d(TAG, "Configuration changed");

    }

    @Override
    protected void onResume() {
        super.onResume();

        isRunning = true;
        barcodeView.resume();
        removePaused();
        initialView.setVisibility(View.VISIBLE);
        foundView.setVisibility(GONE);
    }

    @Override
    protected void onPause() {
        super.onPause();

        showPaused();
        isRunning = false;
        barcodeView.pause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_FOCUS:
            case KeyEvent.KEYCODE_CAMERA:
                // Handle these events so they don't launch the Camera app
                return true;
            // Use volume up/down to turn on light
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                barcodeView.setTorch(false);
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                barcodeView.setTorch(true);
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            handleBarcode(result.getText(), true);
            showPaused();
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
            for (ResultPoint point : resultPoints) {
                viewFinder.addPossibleResultPoint(point);
            }
        }
    };

    private class ViewfinderClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (isRunning) {
                showPaused();
            } else {
                if (hasCameraPermission()) {
                    removePaused();
                } else {
                    requestCameraPermission();
                }
            }
        }
    }

    private void removePaused() {
        barcodeView.decodeSingle(callback);
        barcodeView.resume();
        isRunning = true;
        viewfinderButton.setText(null);
        viewfinderButton.setBackgroundColor(getResources().getColor(R.color.transparent));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void showPaused() {
        barcodeView.stopDecoding();
        barcodeView.pause();
        isRunning = false;
        viewfinderButton.setText(getString(R.string.label_pause));
        viewfinderButton.setBackgroundColor((getResources().getColor(R.color.slightly_transparent)));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

}
