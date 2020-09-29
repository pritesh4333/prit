package com.prit.tambolagame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> numberlist=new ArrayList<>();
    TextView numbers;
    private TextToSpeech repeatTTS;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberlist.add(0);
        numberlist.add(1);
        numberlist.add(2);
        numberlist.add(3);
        numberlist.add(4);
        numberlist.add(5);
        numberlist.add(6);
        numberlist.add(7);
        numberlist.add(8);
        numberlist.add(9);
        numberlist.add(10);
        numberlist.add(11);
        numberlist.add(12);
        numberlist.add(13);
        numberlist.add(14);
        numberlist.add(15);
        numberlist.add(16);
        numberlist.add(17);
        numberlist.add(18);
        numberlist.add(19);
        numberlist.add(20);
        numberlist.add(21);
        numberlist.add(22);
        numberlist.add(23);
        numberlist.add(24);
        numberlist.add(25);
        numberlist.add(26);
        numberlist.add(27);
        numberlist.add(28);
        numberlist.add(29);
        numberlist.add(30);
        numberlist.add(31);
        numberlist.add(32);
        numberlist.add(33);
        numberlist.add(34);
        numberlist.add(35);
        numberlist.add(36);
        numberlist.add(37);
        numberlist.add(38);
        numberlist.add(39);
        numberlist.add(40);
        numberlist.add(41);
        numberlist.add(42);
        numberlist.add(43);
        numberlist.add(44);
        numberlist.add(45);
        numberlist.add(46);
        numberlist.add(47);
        numberlist.add(48);
        numberlist.add(49);
        numberlist.add(50);
        numberlist.add(51);
        numberlist.add(52);
        numberlist.add(53);
        numberlist.add(54);
        numberlist.add(55);
        numberlist.add(56);
        numberlist.add(57);
        numberlist.add(58);
        numberlist.add(59);
        numberlist.add(60);
        numberlist.add(61);
        numberlist.add(62);
        numberlist.add(63);
        numberlist.add(64);
        numberlist.add(65);
        numberlist.add(66);
        numberlist.add(67);
        numberlist.add(68);
        numberlist.add(69);
        numberlist.add(70);
        numberlist.add(71);
        numberlist.add(72);
        numberlist.add(73);
        numberlist.add(74);
        numberlist.add(75);
        numberlist.add(76);
        numberlist.add(77);
        numberlist.add(78);
        numberlist.add(79);
        numberlist.add(80);
        numberlist.add(81);
        numberlist.add(82);
        numberlist.add(83);
        numberlist.add(84);
        numberlist.add(85);
        numberlist.add(86);
        numberlist.add(87);
        numberlist.add(88);
        numberlist.add(89);
        numberlist.add(90);
        numberlist.add(91);
        numberlist.add(92);
        numberlist.add(93);
        numberlist.add(94);



        Log.e("TotoalSize",""+numberlist.size());
            numbers=(TextView)findViewById(R.id.numbers);
        Button nextNo=(Button)findViewById(R.id.nextNo);
        repeatTTS=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    repeatTTS.setLanguage(Locale.UK);
                }
            }
        });
        nextNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Random  randomGenerator = new Random();
                    int index = randomGenerator.nextInt(numberlist.size());

                    numbers.setText("" + numberlist.get(index));
                    String textToSay = numbers.getText().toString();
                    repeatTTS.speak(textToSay, TextToSpeech.QUEUE_FLUSH, null);
                    numberlist.remove(index);
                    Log.e("Size " +i,""+numberlist.size() +" "+textToSay);
                    i++;
                }catch (Exception e){
                    numbers.setText("No Number / Game Over");
                }
            }
        });

    }
}