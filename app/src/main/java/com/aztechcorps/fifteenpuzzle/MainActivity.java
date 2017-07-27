package com.aztechcorps.fifteenpuzzle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button[][] buttons = new Button[4][4];
    /*private int[] draw = {R.drawable.a0, R.drawable.a1, R.drawable.a2, R.drawable.a3,R.drawable.a4, R.drawable.a5, R.drawable.a6,
            R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10, R.drawable.a11, R.drawable.a12,
            R.drawable.a13, R.drawable.a14, R.drawable.a15};*/

    private Drawable[] d = new Drawable[16];


    private int count;
    private Bitmap[] piece = new Bitmap[16];

    LinearLayout.LayoutParams lp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bitmap img = LargeBitmap.decodeSampledBitmapFromResource(getResources(), R.drawable.img, 400, 400);

        int k = 0;
        for(int x = 0; x < 4;  ++x){
            for(int y = 0; y < 4; ++y){
                piece[k] = Bitmap.createBitmap(img, x * 100, y * 100, 100, 100);
                d[k] = new BitmapDrawable(getResources(), piece[k]);
                k++;
            }
        }
        /*for(int x = 0; x < 16; x ++){
            d[x] = new BitmapDrawable(getResources(), piece[x]);
        }*/

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        count = sharedPreferences.getInt("timer", 0);

        lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        shuffle();

        LinearLayout rows = new LinearLayout(this);
        rows.setOrientation(LinearLayout.VERTICAL);

        for(int i = 0; i < 4; i++){
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            rows.addView(row);

            for(int j = 0; j < 4; j++){
                row.addView(buttons[i][j]);
            }
        }
        rows.setPadding(10,10,10,10);
        Button show = new Button(this);
        show.setText("Show Photo");
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setImageResource(R.drawable.img);
                new AlertDialog.Builder(MainActivity.this)
                        .setView(imageView)
                        .setCancelable(true)
                        .show();
            }
        });
        rows.addView(show);

        final TextView tv = new TextView(this);
        rows.addView(tv);

        new Thread(){
            @Override
            public void run() {
                for(; true; count++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(count+"");
                        }
                    });
                }
            }
        }.start();

        setContentView(rows);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        /*Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        List<String> city = new ArrayList<>();
        city.add("Bengaluru");
        city.add("Hyderabad");
        city.add("Kolkata");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(home.this, android.R.layout.simple_spinner_item, city);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.reload) {
            lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            shuffle();

            LinearLayout rows = new LinearLayout(this);
            rows.setOrientation(LinearLayout.VERTICAL);

            for(int i = 0; i < 4; i++){
                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                rows.addView(row);

                for(int j = 0; j < 4; j++){
                    row.addView(buttons[i][j]);
                }
            }
            rows.setPadding(10,10,10,10);
            Button show = new Button(this);
            show.setText("Show Photo");
            show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView imageView = new ImageView(MainActivity.this);
                    imageView.setImageResource(R.drawable.img);
                    new AlertDialog.Builder(MainActivity.this)
                            .setView(imageView)
                            .setCancelable(true)
                            .show();
                }
            });
            rows.addView(show);
            setContentView(rows);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void shuffle(){
        List<Integer> l = new ArrayList<>();
        for(int k = 1; k <=15; k++)
            l.add(k);
        //Collections.shuffle(l);
        l.add(0);

        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        int k = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                buttons[j][i] = new Button(this);
                int val;
                if(sp.getString(j + " " + i, l.get(k) + "").equals(""))
                    val = 0;
                else
                    val = Integer.parseInt(sp.getString(j + " " + i, l.get(k) + ""));

                k++;
                //buttons[i][j].setBackgroundResource(draw[val]);
                buttons[j][i].setBackgroundDrawable(d[val]);
            }
        }

        k = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                //buttons[i][j] = new Button(this);
                int val;
                if(sp.getString(i + " " + j, l.get(k) + "").equals(""))
                    val = 0;
                else
                    val = Integer.parseInt(sp.getString(i + " " + j, l.get(k) + ""));

                k++;
                //buttons[i][j].setBackgroundResource(draw[val]);
                //buttons[i][j].setBackgroundDrawable(d[val]);
                if(val != 0)
                    buttons[i][j].setText(val + "");
                else
                    buttons[i][j].setText("");
                buttons[i][j].setLayoutParams(lp);
                buttons[i][j].setOnClickListener(this);
                buttons[i][j].setTag(i + " " + j);
                buttons[i][j].setTextColor(getResources().getColor(android.R.color.white));
            }
        }
        if(buttons[3][3].getText().equals("0"))
            buttons[3][3].setText("");
    }

    @Override
    public void onClick(View v) {
        Button button = (Button)v;
        String[] loc = button.getTag().toString().split(" ");
        int x = Integer.parseInt(loc[0]);
        int y = Integer.parseInt(loc[1]);

        int[] xx = {x - 1, x, x + 1, x};
        int[] yy = {y, y - 1, y, y + 1};

        for(int k = 0; k < 4; k++){
            int i = xx[k];
            int j = yy[k];

            if(i >= 0 && i< 4 && j >= 0 && j < 4 && buttons[i][j].getText().equals("")){
                buttons[i][j].setText(button.getText());
                buttons[i][j].setBackgroundDrawable(d[Integer.parseInt(button.getText().toString())]);
                //buttons[i][j].setBackgroundResource(draw[Integer.parseInt(button.getText().toString())]);
                button.setBackgroundDrawable(d[0]);
                button.setText("");
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle("Save")
                .setCancelable(false)
                .setMessage("Would you like to save the game on your SD card?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp = getPreferences(MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        for(int i = 0; i < 4; i++)
                            for(int j = 0; j< 4; j++){
                                editor.putString(i + " " + j, buttons[i][j].getText().toString());
                            }
                        editor.putInt("timer", count);
                        editor.apply();
                        finish();
                    }
                })
                .setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp = getPreferences(MODE_PRIVATE);
                        sp.edit().clear().apply();
                        finish();
                    }
                })
                .show();
    }
}
