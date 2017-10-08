package com.comp9323.asynctask;

import android.os.AsyncTask;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.comp9323.food.fooddeal.FoodDealRvAdapter;

import java.io.InputStream;


/**
 * Created by thomas on 8/10/2017.
 */

public class DownloadPhoto extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;
    private FoodDealRvAdapter listener;

    public DownloadPhoto(ImageView imageView, FoodDealRvAdapter listener) {
        this.imageView = imageView;
        this.listener = listener;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
        listener.notifyDataSetChanged();
    }
}
