package com.iesebre.dam2.sergi.androidprocesstinkering;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//
//                onClick(this);
//
//            }
//        });

        mImageView = (ImageView) findViewById(R.id.imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    public void onClickIncorrecte1(View v) {
        Bitmap b = loadImageFromNetwork("http://static1.squarespace.com/static/55c1e26ee4b09ad05fa39502/563b09f5e4b064c9b5474223/564d64cbe4b0b92c150b7b16/1448592603973/?format=300w");
        mImageView.setImageBitmap(b);
    }

    public void onClickIncorrecte2(View v) {
        new Thread(new Runnable() {
            public void run() {
                Bitmap b = loadImageFromNetwork("http://static1.squarespace.com/static/55c1e26ee4b09ad05fa39502/563b09f5e4b064c9b5474223/564d64cbe4b0b92c150b7b16/1448592603973/?format=300w");
                mImageView.setImageBitmap(b);
            }
        }).start();
    }

    public void onClick(View v) {
        new Thread(new Runnable() {
            public void run() {
                final Bitmap bitmap = loadImageFromNetwork("http://www.techotopia.com/images/2/21/Android_process_priorities.png");
                mImageView.post(new Runnable() {
                    public void run() {
                        mImageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();

    }

    private Bitmap loadImageFromNetwork(String url){
        try {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();return null;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        /** The system calls this to perform work in a worker thread and
         * delivers it the parameters given to AsyncTask.execute() */
        protected Bitmap doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }

        /** The system calls this to perform work in the UI thread and delivers
         * the result from doInBackground() */
        protected void onPostExecute(Bitmap result) {
            mImageView.setImageBitmap(result);
        }
    }
}
