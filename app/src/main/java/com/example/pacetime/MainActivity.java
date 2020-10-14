package com.example.pacetime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button_calculate_time;
    private EditText edittext_min, edittext_sec;
    private TextView textview_time_output;

    private int time_in_sec;
    private static final double pvsc_const = 2.80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("PaceTime");

        edittext_min = findViewById(R.id.edittext_min);
        edittext_sec = findViewById(R.id.edittext_sec);
        button_calculate_time = findViewById(R.id.button_calculate_time);
        textview_time_output = findViewById(R.id.textview_time_output);

        button_calculate_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data_valid())
                {
                    // perform logic for conversion
                    perform_conversion();
                }
            }
        });
    }

    // Add info icon on top-left of the activity


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_info)
            startActivity(new Intent(MainActivity.this, InfoActivity.class));
        return super.onOptionsItemSelected(item);
    }

    private void display_output_time(String output_time)
    {
        textview_time_output.setText(output_time);
    }

    private void showToast(String message)
    {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void perform_conversion()
    {
        double time_output_sec = time_in_sec / pvsc_const;
        time_output_sec = Math.floor(time_output_sec);

        int time_out_sec_int = (int) time_output_sec;

        String output_time = time_out_sec_int / 60 + ":";

        if (time_out_sec_int % 60 < 10)
            output_time = output_time + "0" + time_out_sec_int % 60;
        else
            output_time = output_time + time_out_sec_int % 60;

        output_time = output_time + "\n(" + time_out_sec_int + " sec)";

        display_output_time(output_time);
    }

    private boolean data_valid()
    {
        String min_str = edittext_min.getText().toString();
        String sec_str = edittext_sec.getText().toString();

        if (min_str.isEmpty())
        {
            showToast("Enter minutes");
            return false;
        }

        if (sec_str.isEmpty())
        {
            showToast("Enter seconds");
            return false;
        }

        int min = Integer.parseInt(min_str);
        int sec = Integer.parseInt(sec_str);

        time_in_sec = min * 60 + sec;

        if (time_in_sec == 0)
        {
            showToast("Time cannot be zero");
            return false;
        }
        else
        {
            return true;
        }
    }
}