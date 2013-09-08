package com.lujianfei.pulltorefresh;

import java.util.Arrays;
import java.util.LinkedList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.lujianfei.pulldownlist.R;
import com.lujianfei.pulltorefresh.PullToRefreshListView.OnRefreshListener;

public class PullToRefreshActivity extends Activity {    
    private LinkedList<String> mListItems;
    private PullToRefreshListView mlistview;
    int itemIndex = 1;     
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pull_to_refresh);

        // Set a listener to be invoked when the list should be refreshed.
        mlistview =  ((PullToRefreshListView)PullToRefreshActivity.this.findViewById(R.id.usalist) );
        mlistview.setOnRefreshListener(new OnRefreshListener() {			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				 new GetDataTask().execute();
			}
		});
        mListItems = new LinkedList<String>();
        mListItems.addAll(Arrays.asList(mStrings));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mListItems);
        mlistview.setAdapter(adapter);
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                ;
            }
            return mStrings;
        }

        @Override
        protected void onPostExecute(String[] result) {
        	
            mListItems.addFirst("新数据项"+itemIndex);
            ++itemIndex;
            // Call onRefreshComplete when the list has been refreshed.
            mlistview.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

    private String[] mStrings = {
            "数据项5", "数据项4","数据项3", "数据项2","数据项1"};
}

