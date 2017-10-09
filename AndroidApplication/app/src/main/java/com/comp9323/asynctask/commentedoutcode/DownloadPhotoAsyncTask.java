//package com.comp9323.asynctask;
//
//import android.content.res.Resources;
//import android.graphics.Rect;
//import android.os.AsyncTask;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.util.Log;
//import android.widget.ImageView;
//
//import com.comp9323.food.fooddeal.FoodDealRvAdapter;
//
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//
///**
// * Created by thomas on 8/10/2017.
// */
//
//public class DownloadPhotoAsyncTask extends AsyncTask<String, Void, Bitmap> {
//    private ImageView imageView;
//    private FoodDealRvAdapter listener;
//
//    public DownloadPhotoAsyncTask(ImageView imageView, FoodDealRvAdapter listener) {
//        this.imageView = imageView;
//        this.listener = listener;
//    }
//
//    protected Bitmap doInBackground(String... urls) {
//        String urldisplay = urls[0];
//        Bitmap mIcon11 = null;
//        try {
//            InputStream in = new java.net.URL(urldisplay).openStream();
//            OutputStream out = new ByteArrayOutputStream();
//
//            //mIcon11 =decodeSampledBitmapInputStreem(in,100, 100);
//
//            final BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inScaled = true;
//            options.outHeight = 4;
//            options.outWidth = 4;
//
//            BitmapFactory.decodeStream(in,null, options).compress(Bitmap.CompressFormat.WEBP, 0, out);
//            byte[] bitmapdata = ((ByteArrayOutputStream) out).toByteArray();
//            in.close();
//            out.close();
//            mIcon11 = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("Error", e.getStackTrace().toString());
//            e.printStackTrace();
//        }
//        return mIcon11;
//    }
//
//    protected void onPostExecute(Bitmap result) {
//        imageView.setImageBitmap(result);
//        listener.notifyDataSetChanged();
//    }
//
//    public static Bitmap decodeSampledBitmapInputStreem(InputStream in,int reqWidth, int reqHeight) {
//
//        // First decode with inJustDecodeBounds=true to check dimensions
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeStream(in, null, options);
//
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeStream(in, null, options);
//    }
//
//    public static int calculateInSampleSize(
//            BitmapFactory.Options options, int reqWidth, int reqHeight) {
//        // Raw height and width of image
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//
//        if (height > reqHeight || width > reqWidth) {
//
//            final int halfHeight = height / 2;
//            final int halfWidth = width / 2;
//
//            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
//            // height and width larger than the requested height and width.
//            while ((halfHeight / inSampleSize) >= reqHeight
//                    && (halfWidth / inSampleSize) >= reqWidth) {
//                inSampleSize *= 2;
//            }
//        }
//
//        return inSampleSize;
//    }
//}
