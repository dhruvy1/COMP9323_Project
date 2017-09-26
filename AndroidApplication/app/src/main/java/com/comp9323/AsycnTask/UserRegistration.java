package com.comp9323.AsycnTask;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.AsyncTask;
import android.service.autofill.SaveInfo;
import android.util.Log;
import android.widget.Toast;

import com.comp9323.FoodDeal.FoodDealFragment;
import com.comp9323.RestAPI.APIImpl.UserImpl;
import com.comp9323.RestAPI.Beans.FoodDeal;
import com.comp9323.RestAPI.DataHolder.SingletonDataHolder;
import com.comp9323.myapplication.MainActivity;

/**
 * Created by thomas on 20/9/2017.
 */

public class UserRegistration extends AsyncTask<String, Void, Boolean> {
    @Override
    protected Boolean doInBackground(String... strings) {
        Log.d("A*", "start create user");
        String username = strings[0];
        return UserImpl.CreateUser(username);
    }
    @Override
    protected void onPostExecute(Boolean bool){
        super.onPostExecute(bool);
        Intent intent =null;
        if ( bool) {
            Log.d("A*", "end create user");
            CharSequence welcome = "Welcome " + SingletonDataHolder.getInstance().getUserSelf().getUsername() + "!";
            Toast toast = Toast.makeText(SingletonDataHolder.getInstance().getContext(), welcome, Toast.LENGTH_SHORT);
            toast.show();
            intent = new Intent(SingletonDataHolder.getInstance().getContext(), MainActivity.class);
        }else{
            Log.d("A*", "REST ERROR");
            CharSequence welcome = "500 Internal Service Error, Cannot register new User into DB ";
            Toast toast = Toast.makeText(SingletonDataHolder.getInstance().getContext(), welcome, Toast.LENGTH_LONG);
            toast.show();
            intent = new Intent(SingletonDataHolder.getInstance().getContext(), UserRegistration.class);
        }
        SingletonDataHolder.getInstance().getContext().startActivity(intent);

    }
}
