package com.cancercarecompany.ccc.ccc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class journal extends AppCompatActivity {

    String lbl_datum;
    TextView header;
    TextView fr_medicin;
    TextView lu_medicin;
    TextView mi_medicin;
    private SeekBar seekFr;
    private SeekBar seekLu;
    private SeekBar seekMi;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        seekFr = (SeekBar) findViewById(R.id.seek_med_fr);
        seekLu = (SeekBar) findViewById(R.id.seek_med_lu);
        seekMi = (SeekBar) findViewById(R.id.seek_med_mi);
        fr_medicin = (TextView) findViewById(R.id.txt_med_ant_fr);
        lu_medicin = (TextView) findViewById(R.id.txt_med_ant_lu);
        mi_medicin = (TextView) findViewById(R.id.txt_med_ant_mi);

        fr_medicin.setText("Ordination: " + seekFr.getMax() + " st tabletter");
        lu_medicin.setText("Ordination: " + seekLu.getMax() + " st tabletter");
        mi_medicin.setText("Ordination: " + seekMi.getMax() + " st tabletter");

        seekFr.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                fr_medicin.setText(R.string.txt_tagit_med);
                fr_medicin.setText("Tagit " + progress + "/" + seekBar.getMax());
                Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
            }
        });

        seekLu.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                lu_medicin.setText(R.string.txt_tagit_med);
                lu_medicin.setText("Tagit " + progress + "/" + seekBar.getMax());
                Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();

            }
        });

        seekMi.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mi_medicin.setText(R.string.txt_tagit_med);
                mi_medicin.setText("Tagit " + progress + "/" + seekBar.getMax());
                Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();

            }
        });
/*
  // Initialize the textview with '0'.
      textView.setText("Covered: " + seekBar.getProgress() + "/" + seekBar.getMax());
      seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
      int progress = 0;

      @Override
      public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
      progress = progresValue;
      Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();

          }
    @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        textView.setText("Covered: " + progress + "/" + seekBar.getMax());
        Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
        }
        });
        }
        // A private method to help us initialize our variables.

        private void initializeVariables() {
        seekBar = (SeekBar) findViewById(R.id.seekBar1);
        textView = (TextView) findViewById(R.id.textView1);
        }

*/
//        header.findViewById(R.id.diary_header);

//        lbl_datum = "Vald Datum";
//        System.out.println("Nu börjas det");
//        System.out.println(header);

    }
}
