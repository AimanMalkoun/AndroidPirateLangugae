package com.example.PirateTranslatorWithTTS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *
 * An android app that allows user to translate text to speach and to convert english to pirate ar ar ar.
 *
 */

public class MainActivity extends AppCompatActivity {



    //variable declaration
    private ToggleButton togleBtn;
    private TextToSpeech tts;
    private EditText inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        togleBtn = findViewById(R.id.toggleButton);
        inputText = findViewById(R.id.editText2);

        //initialise text to speach
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Toast.makeText(MainActivity.this, "Text To Speech Engine Initialized.", Toast.LENGTH_SHORT).show();
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Initilization Failed!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void speak(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }else{
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }


    // This method take user input from the editText and translates it to 'pirate' language
    // Only works when more than one word is put in the editText view.
    public void translate(View view) {


        String paragraphSimple = inputText.getText().toString() + "\n";
        StringBuffer paragraphBuff = new StringBuffer();
        HashMap<String, String> words = new HashMap<String, String>();

        words.put("Hello", "Ahoy");
        words.put("Hi", "Ahoy");
        words.put("yes", "Aye");
        words.put("ok", "Aye");
        words.put("no", "Nay");
        words.put("Treasure", "Booty");
        words.put("money", "Loot");
        words.put("cash", "gold");

        words.put("everyone", "all hands");
        words.put("friend", "bucko");
        words.put("rob", "pillage");
        words.put("sea", "bring");
        words.put("see", "spye");
        words.put("beer", "grog");
        words.put("friends", "me hearties");
        words.put("jerk", "scallywag");
        words.put("pirate", "buccaneer");
        words.put("bag", "duffle");
        words.put("your", "yer");
        words.put("me", "my");
        words.put("telescope", "spyglass");
        words.put("kitchen", "galley");
        words.put("boy", "lad");
        words.put("girl", "lass");
        words.put("clean", "swab");
        words.put("wow", "blimey");
        words.put("room", "cabin");
        words.put("quickly", "smartly");
        words.put("bed", "cot");
        words.put("surprise", "sink me");
        words.put("food", "grub");
        words.put("cheat", "hornswaggle");
        words.put("sailor", "Jack Tar");
        words.put("the", "thee");
        words.put("store", "market");
        words.put("I", "eye");
        words.put("hungry", "starvin");
        words.put("bad", "vile");
        words.put("hit", "skewer");
        words.put("wind", "blow");
        words.put("windy", "good blow");

        StringTokenizer st = new StringTokenizer(paragraphSimple, " .,", true);
        speak(paragraphBuff.toString());

        // PART 4 Will try to add random words from an array list to textView


        while (st.hasMoreTokens()) {
            String nextWord = st.nextToken();

            String[] listt = new String[]{"", "Yar!", "Blimey", "yYo ho ho", "Shiver me timber"};
            int randd = new Random().nextInt(listt.length);
            paragraphBuff.append(listt[randd]);

            if (words.containsKey(nextWord)) {
                paragraphBuff.append(words.get(nextWord));
            } else if (nextWord.equalsIgnoreCase("you")) {
                // PART 3 I guess :)
                String[] list = new String[]{"", "ye", "ye filthy", "ye yellow bellied"};
                int rand = new Random().nextInt(list.length);
                paragraphBuff.append(list[rand]);

            } else {
                paragraphBuff.append(nextWord);
            }
        }

        // PART 5: will try to show the pirate translation in the output text view

        String[] list2 = new String[]{"ye son of a biscuit eater", "ye dog", "ye sea rat", "ye salty sea bass"};
        int rand = new Random().nextInt(list2.length);
        TextView outputText = findViewById(R.id.textView4);
        paragraphBuff.append(list2[rand]);
        outputText.setText(paragraphBuff);


        // PART 6, and 7
        // If the toggle button is on it will call the TTS to say the translation

        if(togleBtn.isChecked()){
            speak(paragraphBuff.toString());
        }

    }

    //this method will listen to the user and writes the result in the the input text
    public void listen(View view){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");

        try {
            startActivityForResult(i, 100);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(MainActivity.this, "Your device doesn't support Speech Recognition", Toast.LENGTH_SHORT).show();
        }
    }

    //This method will recieve the result and put it in the input text
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> res = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String inSpeech = res.get(0);
                inputText.setText(inSpeech);
            }
        }
    }

}


