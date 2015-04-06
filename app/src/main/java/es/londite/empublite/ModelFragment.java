package es.londite.empublite;


import android.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import de.greenrobot.event.EventBus;

public class ModelFragment extends Fragment {
    private BookContents contents = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity host){
        super.onAttach(host);
        if (contents == null) {
            new LoadThread(host.getAssets()).start();
        }
    }

    public BookContents getBook(){
        return (contents);
    }

    private class LoadThread extends Thread {
        private AssetManager assets=null;

        LoadThread(AssetManager assets) {
            super();

            this.assets=assets;
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        }

        @Override
        public void run() {
            Gson gson = new Gson();

            try {
                InputStream is=assets.open("book/contents.json");
                BufferedReader reader= new BufferedReader(new InputStreamReader(is));

                contents=gson.fromJson(reader, BookContents.class);
                EventBus.getDefault().post(new BookLoaderEvent(contents));
            }
            catch (IOException e) {
                Log.e(getClass().getSimpleName(), "Exception parsing JSON", e);
                /*
                I'm leaving this commented as it is something I need to check how to make it later.

                Context context = ¡¡AQUÍ ESTÁ LA DUDA!!
                CharSequence toastText = "Your book couldn't be loaded";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, toastText, duration);
                toast.show();
                */
            }
        }


    }
}