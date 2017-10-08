package com.comp9323.asynctask;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.comp9323.restclient.api.UserAPIImpl;
import com.comp9323.data.DataHolder;
import com.comp9323.main.MainActivity;

/**
 * Created by thomas on 20/9/2017.
 */

public class UserAsyncTask extends AsyncTask<String, Void, Boolean> {

    @Override
    protected Boolean doInBackground(String... strings) {
        Log.d("A*", "start create user");
        String username = strings[0];
        return UserAPIImpl.postUser(username);
    }

    @Override
    protected void onPostExecute(Boolean bool) {
        super.onPostExecute(bool);
        Intent intent = null;
        if (bool) {
            Log.d("A*", "end create user");
            CharSequence welcome = "Welcome " + DataHolder.getInstance().getUserSelf().getUsername() + "!";
            Toast toast = Toast.makeText(DataHolder.getInstance().getContext(), welcome, Toast.LENGTH_SHORT);
            toast.show();
            intent = new Intent(DataHolder.getInstance().getContext(), MainActivity.class);
        } else {
            Log.d("A*", "REST ERROR");
            CharSequence welcome = "500 Internal Service Error, Cannot register new User into DB ";
            Toast toast = Toast.makeText(DataHolder.getInstance().getContext(), welcome, Toast.LENGTH_LONG);
            toast.show();
            intent = new Intent(DataHolder.getInstance().getContext(), UserAsyncTask.class);
        }
        DataHolder.getInstance().getContext().startActivity(intent);

    }
}
