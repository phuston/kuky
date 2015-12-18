package com.example.keenan.kuky.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.keenan.kuky.R;
import com.example.keenan.kuky.api.ApiClient;
import com.example.keenan.kuky.helpers.AuthHelper;
import com.example.keenan.kuky.helpers.KuComposeHelper;
import com.example.keenan.kuky.models.KuComposeResponse;
import com.example.keenan.kuky.models.KuRequest;

import java.io.IOException;
import java.util.logging.Handler;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class KuWriteActivity extends AppCompatActivity {

    @Bind(R.id.ku_compose_cancel) ImageButton mComposeCancel;
    @Bind(R.id.ku_compose_done) ImageButton mComposeDone;
    @Bind(R.id.ku_line_one) EditText mKuLineOne;
    @Bind(R.id.ku_line_two) EditText mKuLineTwo;
    @Bind(R.id.ku_line_three) EditText mKuLineThree;

    private static final String TAG = KuWriteActivity.class.getSimpleName();

    private String line1;
    private String line2;
    private String line3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ku_write);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.ic_logo_clear);
    }

    @OnClick(R.id.ku_compose_cancel)
    public void onComposeCancelClick(View view) {
        Snackbar.make(view, "Cancel Ku", Snackbar.LENGTH_LONG)
                .setAction("Cancel", null).show();
        Intent intent = new Intent(getApplicationContext(), KuViewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ku_compose_done)
    public void onComposeDoneClick(View view) {
        Snackbar.make(view, "Ku Submitted!", Snackbar.LENGTH_LONG)
                .setAction("Submit", null).show();
        line1 = mKuLineOne.getText().toString();
        line2 = mKuLineTwo.getText().toString();
        line3 = mKuLineThree.getText().toString();
        Log.d(TAG, line1 + ' ' + line2 + ' ' + line3);
        try {
            KuComposeHelper helper = new KuComposeHelper(getBaseContext());
            int[] syllables = helper.checkKu(line1, line2, line3);
            Log.wtf(TAG, String.valueOf(syllables[0]) + String.valueOf(syllables[1]) + String.valueOf(syllables[2]));
            // TODO: Make sure check syllable works + get location
            if ((syllables[0] == 5) && (syllables[1] == 7) && (syllables[2] == 5)) {
                Log.d(TAG, "Ku correct");
                String kuContent = line1 + ';' + line2 + ';' + line3;
                String[] creds = AuthHelper.getCreds(getBaseContext());
                if (creds != null) {
                    KuRequest req = new KuRequest(kuContent, Integer.parseInt(creds[2]), -40, 20);
                    Log.d(TAG, req.toString());
                    ApiClient.getKukyApiClient(creds)
                            .postKu(req)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<KuComposeResponse>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.wtf(TAG, e);
                                }

                                @Override
                                public void onNext(KuComposeResponse kuComposeResponse) {
                                    Log.d(TAG, kuComposeResponse.toString());
                                    // TODO: Take user back to previous fragment
                                }
                            });
                }
            } else {
                Toast.makeText(this, "Your Ku is shit. Sorry", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            Log.wtf(TAG, "Could not create Ku Helper");
        }

        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), KuViewActivity.class);
                startActivity(intent);
            }
        }.start();

    }

//    @OnClick(R.id.ku_line_one)
//    public void onLineOneClick(View view) {
//        line1 = mKuLineOne.getText().toString();
//        //TODO: Check syllables? (5)
//    }
//
//    @OnClick(R.id.ku_line_two)
//    public void onLineTwoClick(View view) {
//        line2 = mKuLineTwo.getText().toString();
//        //TODO: Check syllables? (7)
//
//    }
//
//    @OnClick(R.id.ku_line_three)
//    public void onLineThreeClick(View view) {
//        line3 = mKuLineThree.getText().toString();
//        //TODO: Check syllables? (5)
//
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
