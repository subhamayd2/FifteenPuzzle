package com.aztechcorps.fifteenpuzzle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by User on 26-Jul-16.
 */
public class cutAsync extends AsyncTask<Integer, Void, Bitmap[][]> {
    Context context;
    Dialog load;

    cutAsync(Context ctx){ this.context = ctx; }

    @Override
    protected Bitmap[][] doInBackground(Integer... params) {
        int id = params[0];
        int color;
        Bitmap img = LargeBitmap.decodeSampledBitmapFromResource(context.getResources(), id, 400, 400);
        Bitmap[][] piece = new Bitmap[4][4];

        for(int x = 0; x < 4;  ++x){
            for(int y = 0; y < 4; ++y){
                piece[x][y] = Bitmap.createBitmap(img, x * 100, y * 100, 100, 100);
            }
        }

        return piece;
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap[][] aVoid) {
        //super.onPostExecute(aVoid);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                ImageView imageView = new ImageView(context);
                imageView.setImageBitmap(aVoid[i][j]);
                linearLayout.addView(imageView);
            }
        }
    }
}
