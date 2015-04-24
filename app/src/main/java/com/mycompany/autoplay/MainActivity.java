package com.mycompany.autoplay;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    MediaPlayer mediaPlayer;
    SpeechRecognitionHelper srh;
    List<Integer> songsIds;
    int index;
    private List<String> playMatches;
    private List<String> backMatches;
    private List<String> forwardMatches;
    private List<String> pauseMatches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songsIds = getSongsIdsList();
        index = 0;
//        mediaPlayer = MediaPlayer.create(this, songsIds.get(index));
//        File f = Environment.getExternalStorageDirectory();
//        ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
//        Toast.makeText(this, names.toString(), Toast.LENGTH_LONG).show();
//        mediaPlayer = MediaPlayer.create(this,Uri.parse("http://dimechimes.com/media/Rocky_Soundtrack_-_Eye_Of_The_Tiger.mp3"));
        mediaPlayer = MediaPlayer.create(this,Uri.parse("https://00e9e64baca8d25690aa28b9851dabde62cfe8d38bc2438414-apidata.googleusercontent.com/download/storage/v1_internal/b/autoplay_audio/o/youshouldbedancing.mp3?qk=AD5uMEvGlI9V0cN3RMG_fGWEp6pGUJsSVfES4BGr8xRddLyKD8duudxXyocuzEskbP3mlh8fk4lMZtQKQpDE9hVgCeLTFRLPyWr_xMc8km53xttSqdGkXTUSnXGDzZu5-7GJ9w1mzvMtJHBg4r-4XGjExZOMYZNKTlcQ2DFkbQx0KsPVumJX8Rto9hQ7giD1BprTWIX8ZgYcZNrxo8iDNPCwI7Gp43VOVCMcc4ASPO-_SakvdkXa7ue650YbiRv-5IFiGvCZQttt8YCuJsZXtaoyIQ81pCA48n5SenNXgyOawT_a2zGuvuZPCA17YVzSSUjL3pcJvlH2YxB7z0VmZlUguJ9PRD8ayl_5EoAuEWqnhWG4TDcr2cV_z41DjFaQRCLZD-kz-FNQtCHYtM6dfyJRhgv4u_yM8heL34dfOY-8Y2arjQmuV4GDLHV9cGcp5SzDIklzFAqtbSq43q8kP1XbUobFztIjYYIUTEpbFwMwGXvdTHAx7bg5o8fgqGODCGMFF-RMPB-2Z71PRmCaGyEUfRVaMKVx70UeoSikKQBp6InvzkFx4oHXFphvvA27XSnuqK7oEjwi6ZZBpR3QEBgyK7rsvVlK_DECn8C0Wy-5tNfYYYrsdnQBkkcFpqr9r_V9gaBMGbZk8hxdLEvl_YYcM0sz6URm3SV7-rf-IuTsv6YtCP8OGVc6uLa0aNns_iccZkX4UZ6_gDeXDepxBdvXWvUTS8jGgw"));
        mediaPlayer.start();
//        mediaPlayer = MediaPlayer.create(this, Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Music/Flares.mp3"));
//        srh = new SpeechRecognitionHelper();
//        readPools();
//        View v = this.getWindow().getDecorView().findViewById(android.R.id.content);
//        mediaPlayer.start();
//        play(v);
//        pause(v);
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

    public void recognize(View v){
        mediaPlayer.pause();
        srh.run(this);
    }

    public void play(View v){
        if(mediaPlayer == null){
            forward(v);
            return;
        }
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
    }

    public void pause(View v){
        mediaPlayer.pause();
    }

    public void forward(View v){
        if(mediaPlayer != null)
            mediaPlayer.release();
        index = (index + 1 )% songsIds.size();
        mediaPlayer = MediaPlayer.create(this, songsIds.get(index));
        mediaPlayer.start();
    }

    public void back(View v){
        if(mediaPlayer != null)
            mediaPlayer.release();
        index = (index - 1 )% songsIds.size();
        mediaPlayer = MediaPlayer.create(this, songsIds.get(index));
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
    }

    private List<Integer> getSongsIdsList(){
        Field[] fields=R.raw.class.getFields();
        List<Integer> ids= new ArrayList<>();
        for (Field field : fields) {
            try {
                ids.add(field.getInt(field));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return ids;
    }

    // Activity Results handler
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList matches = null;
        // if it’s speech recognition results
        // and process finished ok
        if (requestCode == 2 && resultCode == RESULT_OK) {

            // receiving a result in string array
            // there can be some strings because sometimes speech recognizing inaccurate
            // more relevant results in the beginning of the list
            matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            // in “matches” array we holding a results... let’s show the most relevant
            if (matches.size() > 0) Toast.makeText(this, (String)matches.get(0), Toast.LENGTH_LONG).show();
        }

        analyze(matches);
        super.onActivityResult(requestCode, resultCode, data);
    }

    void analyze(ArrayList input){
        View v = this.getWindow().getDecorView().findViewById(android.R.id.content);
        if(matchesPlay(input))
            play(v);
        else if(matchesPause(input))
            pause(v);
        else if(matchesForward(input))
            forward(v);
        else    if(matchesBack(input))
            back(v);
    }

    private boolean matchesPlay(ArrayList inputArray){
        for(Object word : inputArray){
            if(playMatches.contains(word))
                return true;
        }
        return false;
    }

    private boolean matchesPause(ArrayList inputArray){
        for(Object word : inputArray){
            if(pauseMatches.contains(word))
                return true;
        }
        return false;
    }

    private boolean matchesForward(ArrayList inputArray){
        for(Object word : inputArray){
            if(forwardMatches.contains(word))
                return true;
        }
        return false;
    }

    private boolean matchesBack(ArrayList inputArray){
        for(Object word : inputArray){
            if(backMatches.contains(word))
                return true;
        }
        return false;
    }

    private void readPools(){
        playMatches = readPool("playMatches.txt");
        backMatches = readPool("backMatches.txt");
        forwardMatches = readPool("forwardMatches.txt");
        pauseMatches = readPool("pauseMatches.txt");
    }
    private List<String> readPool(String poolFile){
        List<String> words = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(poolFile), "UTF-8"));
            // do reading, usually loop until end of file reading
            String mLine = reader.readLine();
            while (mLine != null) {
                words.add(mLine);
                mLine = reader.readLine();
            }
        } catch (IOException e) {
            //TODO: log the exception or something
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //TODO: log the exception or something
                }
            }
        }
        return words;
    }



    void masterpiece(){

    }

}
