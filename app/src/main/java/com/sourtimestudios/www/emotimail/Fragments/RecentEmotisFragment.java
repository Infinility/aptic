package com.sourtimestudios.www.emotimail.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sourtimestudios.www.emotimail.Database.EmotiDatabaseHelper;
import com.sourtimestudios.www.emotimail.Database.Emoticon;
import com.sourtimestudios.www.emotimail.Database.UserManager;
import com.sourtimestudios.www.emotimail.R;

/**
 * Created by user on 01/10/15.
 */
public class RecentEmotisFragment extends ListFragment {

    static final String TAG = "RecentEmotisFragment";

    private static final int REQUEST_CONFIRM = 0;

    private EmotiDatabaseHelper mHelper;
    private EmoticonCursorAdapter mAdapter;
    private EmotiDatabaseHelper.EmoticonCursor mCursor;
    private UserManager mUserManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mHelper = EmotiDatabaseHelper.getInstance(getActivity());
//        mCursor = mHelper.queryEmoticonsByFrequency();
        mUserManager = UserManager.getInstance(getActivity());
        mCursor = mUserManager.getRecentEmoticons();
        mAdapter = new EmoticonCursorAdapter(getActivity(), mCursor);
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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

//        mCursor.moveToPosition(position+1);
//        Emoticon emo = mCursor.getEmoticon();
//        long pos =  emo.getEmo_id() + 1;
//        mCursor.close();
////        int pos = emo.getmId();
//        Log.d(TAG,"emo_id = " + emo.getEmo_id());
//
//        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//        boolean isNetworkAvailable = cm.getBackgroundDataSetting() && cm.getActiveNetworkInfo() != null;
//        if (isNetworkAvailable == false){
//            Toast.makeText(getActivity(), R.string.enable_network, Toast.LENGTH_SHORT).show();
//        }else {
////
//            FragmentManager fm = getActivity().getSupportFragmentManager();
//            ConfirmSendFragment confirmSendFragment = ConfirmSendFragment.newInstance((int)pos);
//            confirmSendFragment.setTargetFragment(RecentEmotisFragment.this, REQUEST_CONFIRM);
//            confirmSendFragment.show(fm, String.valueOf(R.string.confirm_title));
//        }
    }

    private static class EmoticonCursorAdapter extends CursorAdapter {

        private EmotiDatabaseHelper.EmoticonCursor emoticonCursor;
        private int[] emoidarray = {R.drawable.emo1, R.drawable.emo2, R.drawable.emo3,
                R.drawable.emo4, R.drawable.emo5, R.drawable.emo6, R.drawable.emo7,
                R.drawable.emo8, R.drawable.emo9, R.drawable.emo10};

        public EmoticonCursorAdapter(Context context, EmotiDatabaseHelper.EmoticonCursor c) {
            super(context, c);
            emoticonCursor = c;
        }


        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(R.layout.emoticon_count_item, viewGroup, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            Emoticon emo = emoticonCursor.getEmoticon();

            ImageView iv = (ImageView) view.findViewById(R.id.ivEmoCount);
            TextView tv = (TextView) view.findViewById(R.id.tvEmocount);

            int count = emo.getmCount();
//            Log.d(TAG,"emo id: " + emo.getmId());
            Log.d(TAG, "emo count: " + count);


            iv.setImageResource(emoidarray[((int) (emo.getEmo_id()))]);
            tv.setText("Send count: "+String.valueOf(count));
        }
    }
}
