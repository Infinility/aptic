//package com.sourtimestudios.www.emotimail;
//
//import android.app.Activity;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//
//import com.sourtimestudios.www.emotimail.Messaging.GMailer;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//
//public class MainActivity extends Activity {
//
//    String imageUri;
//    private final String TAG = "EmotiMain";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
////        imageUri = "res/drawable-hdpi/" + R.drawable.ic_launcher + ".png";
////        imageUri = "res/drawable-hdpi/" + "ic_launcher.png";
//
//
//        try {
//            writeImagesToDisk();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e(TAG,"write failed catch");
//            Log.e(TAG,e.toString());
//
//        }
//
//
//        Button button = (Button) findViewById(R.id.btnSend);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                MyTask task = new MyTask();
//                try {
//                    task.execute(imageUri);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//
//    private class MyTask extends AsyncTask<String, Void, Void> {
//
//        @Override
//        protected Void doInBackground(String... params) {
//            GMailer.sendMessage(MainActivity.this, params[0]);
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//        }
//    }
//
//    private void writeImagesToDisk() throws IOException {
//
//        InputStream input = getAssets().open("ic_launcher.png");
////
////        FileOutputStream outputStream ;
////
////        try {
////            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
////            outputStream.
////            outputStream.close();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//
//        try {
//            File file = new File(getFilesDir(),"img.png");
//            OutputStream output = new FileOutputStream(file);
//            try {
//                try {
//                    byte[] buffer = new byte[30 * 1024]; // or other buffer size
//                    int read;
//
//                    while ((read = input.read(buffer)) != -1) {
//                        output.write(buffer, 0, read);
//                    }
//                    output.flush();
//                    Log.i(TAG,"Output flushed");
//                } finally {
//                    output.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace(); // handle exception, define IOException and others
//                Log.e(TAG,"first catch");
//            }
//        } finally {
//            input.close();
//            Log.i(TAG, "input closed");
//        }
//
//
//    }
//
//
//}
