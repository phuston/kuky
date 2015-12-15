package com.example.keenan.kuky.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.keenan.kuky.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class KuWriteActivity extends AppCompatActivity {

    @Bind(R.id.ku_compose_cancel) ImageButton mComposeCancel;
    @Bind(R.id.ku_compose_done) ImageButton mComposeDone;
    @Bind(R.id.ku_line_one) EditText mKuLineOne;
    @Bind(R.id.ku_line_two) EditText mKuLineTwo;
    @Bind(R.id.ku_line_three) EditText mKuLineThree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ku_write);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.ku_compose_cancel)
    public void onComposeCancelClick(View view)
    {
        Snackbar.make(view, "Cancel Ku", Snackbar.LENGTH_LONG)
                .setAction("Cancel", null).show();
    }

    @OnClick(R.id.ku_compose_done)
    public void onComposeDoneClick(View view)
    {
        Snackbar.make(view, "Ku Submitted!", Snackbar.LENGTH_LONG)
                .setAction("Submit", null).show();
    }

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
