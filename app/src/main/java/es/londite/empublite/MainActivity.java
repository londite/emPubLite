package es.londite.empublite;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;

import de.greenrobot.event.EventBus;


public class MainActivity extends ActionBarActivity {
    private ViewPager pager=null;
    private ContentsAdapter adapter=null;
    private static final String MODEL = "model";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager)findViewById(R.id.pager);

       }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case android.R.id.home:
               return (true);

           case R.id.about:
               Intent i=new Intent(this, SimpleContentActivity.class);
               i.putExtra(SimpleContentActivity.EXTRA_FILE, "file:///android_asset/misc/about.html");
               startActivity(i);

               return (true);

           case R.id.help:
               i = new Intent(this, SimpleContentActivity.class);
               i.putExtra(SimpleContentActivity.EXTRA_FILE, "file:///android_asset/misc/help.html");
               startActivity(i);

               return (true);
       }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);

        if (adapter==null){
            ModelFragment mfrag = (ModelFragment)getFragmentManager().findFragmentByTag(MODEL);

            if (mfrag==null) {
                getFragmentManager().beginTransaction().add(new ModelFragment(), MODEL).commit();
            }
        }
    }

    @Override
    public void onPause(){
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    private void setupPager(BookContents contents){
        adapter = new ContentsAdapter(this, contents);
        pager.setAdapter(adapter);
        findViewById(R.id.progressBar1).setVisibility(View.GONE);
        pager.setVisibility(View.VISIBLE);
    }

    public void onEventMainThread(BookLoaderEvent event) {
        setupPager(event.getBook());
    }
}
