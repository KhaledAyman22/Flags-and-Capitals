package QuestionHandling;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.flagsandcapitals.Game;
import com.example.flagsandcapitals.OppositeGame;
import com.example.flagsandcapitals.R;

public class SetQuestion {

    public SetQuestion(Context context, ImageView flag, Button c1, Button c2, Button c3, Button c4, MakeQuestion makeQuestion){

        c1.setBackground(context.getDrawable(R.drawable.button));
        c2.setBackground(context.getDrawable(R.drawable.button));
        c3.setBackground(context.getDrawable(R.drawable.button));
        c4.setBackground(context.getDrawable(R.drawable.button));

        makeQuestion.normalMode();
        flag.setImageResource(Game.getResId(makeQuestion.getImageName()));
        c1.setText(makeQuestion.getChoices()[0]);
        c2.setText(makeQuestion.getChoices()[1]);
        c3.setText(makeQuestion.getChoices()[2]);
        c4.setText(makeQuestion.getChoices()[3]);

        c1.setClickable(true);
        c2.setClickable(true);
        c3.setClickable(true);
        c4.setClickable(true);
    }


    public SetQuestion(Context context, TextView country, ImageButton c1, ImageButton c2, ImageButton c3, ImageButton c4, MakeQuestion makeQuestion){

        c1.setBackground(context.getDrawable(R.drawable.button));
        c2.setBackground(context.getDrawable(R.drawable.button));
        c3.setBackground(context.getDrawable(R.drawable.button));
        c4.setBackground(context.getDrawable(R.drawable.button));

        makeQuestion.oppositeMode();
        country.setText(makeQuestion.getImageName());
        c1.setImageResource(OppositeGame.getResId(makeQuestion.getChoices()[0]));
        c2.setImageResource(OppositeGame.getResId(makeQuestion.getChoices()[1]));
        c3.setImageResource(OppositeGame.getResId(makeQuestion.getChoices()[2]));
        c4.setImageResource(OppositeGame.getResId(makeQuestion.getChoices()[3]));

        c1.setClickable(true);
        c2.setClickable(true);
        c3.setClickable(true);
        c4.setClickable(true);
    }

}
