package com.sourtimestudios.www.emotimail.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.CursorAdapter;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sourtimestudios.www.emotimail.Database.EmotiDatabaseHelper;
import com.sourtimestudios.www.emotimail.Database.Emoticon;
import com.sourtimestudios.www.emotimail.Database.SentLog;
import com.sourtimestudios.www.emotimail.Database.UserManager;
import com.sourtimestudios.www.emotimail.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by user on 01/10/15.
 */
public class LogFragment extends ListFragment {


    static final String TAG = "LogFragment";

    private EmotiDatabaseHelper mHelper;
    private LogCursorAdapter mAdapter;
    private EmotiDatabaseHelper.LogsCursor mCursor;
    private UserManager mUserManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mHelper = EmotiDatabaseHelper.getInstance(getActivity());
//        mCursor = mHelper.queryLlogs();
        mUserManager = UserManager.getInstance(getActivity());
        mCursor = mUserManager.getLogs();
        mAdapter = new LogCursorAdapter(getActivity(),mCursor);
        setListAdapter(mAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        final ListView listView = (ListView) v.findViewById(android.R.id.list);

        listView.setAdapter(mAdapter);

        return v;
    }


    private static class LogCursorAdapter extends CursorAdapter {

        private EmotiDatabaseHelper.LogsCursor logsCursor;
        private int[] emoidarray = {R.drawable.emo1, R.drawable.emo2, R.drawable.emo3,
                R.drawable.emo4, R.drawable.emo5, R.drawable.emo6, R.drawable.emo7,
                R.drawable.emo8, R.drawable.emo9, R.drawable.emo10};

        public LogCursorAdapter(Context context, EmotiDatabaseHelper.LogsCursor c) {
            super(context, c);
            logsCursor = c;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(R.layout.emoticon_count_item, viewGroup, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            SentLog log = logsCursor.getLog();

            ImageView iv = (ImageView) view.findViewById(R.id.ivEmoCount);
            TextView tv = (TextView) view.findViewById(R.id.tvEmocount);

            long millisecondTime = log.getmSentTime();
            Log.i(TAG, ""+millisecondTime);
//            Log.d(TAG,"emo id: " + emo.getmId());
//            Log.d(TAG, "emo count: " + count);

            String date = getDate(millisecondTime, "dd/MM/yyyy hh:mm:ss");

            iv.setImageResource(emoidarray[((int) (log.getmId()-1))]);
            tv.setText(String.valueOf(date));
        }
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
