package QuestionHandling;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.flagsandcapitals.Game;
import com.example.flagsandcapitals.OppositeGame;
import com.example.flagsandcapitals.R;

public class AnswerChecker {

    private Button c1,c2,c3,c4;
    private ImageButton ci1,ci2,ci3,ci4;

    public AnswerChecker(final Context context, final Button c1, final Button c2, final Button c3, final Button c4, MakeQuestion makeQuestion, int choice, TextView right, TextView wrong){
        this.c1=c1;
        this.c2=c2;
        this.c3=c3;
        this.c4=c4;

        if(choice==makeQuestion.getRightAnsPlace()){
            Button rightButton = clicked(choice);
            assert rightButton != null;
            rightButton.setBackground(context.getDrawable(R.drawable.button_right));
            Game.data.remove(makeQuestion.getCountryNum());
            right.setText(String.valueOf(Integer.parseInt((String) right.getText())+1));
        }
        else{
            Button rightButton = clicked(makeQuestion.getRightAnsPlace()),wrongButton = clicked(choice);
            assert rightButton != null;
            rightButton.setBackground(context.getDrawable(R.drawable.button_right));
            assert wrongButton != null;
            wrongButton.setBackground(context.getDrawable(R.drawable.button_wrong));
            Game.data.remove(makeQuestion.getCountryNum());
            wrong.setText(String.valueOf(Integer.parseInt((String) wrong.getText())+1));
        }
    }

    public AnswerChecker(final Context context, final ImageButton c1, final ImageButton c2, final ImageButton c3, final ImageButton c4, MakeQuestion makeQuestion, int choice, TextView right, TextView wrong){
        this.ci1=c1;
        this.ci2=c2;
        this.ci3=c3;
        this.ci4=c4;

        if(choice==makeQuestion.getRightAnsPlace()){
            ImageButton rightButton = clickedI(choice);
            assert rightButton != null;
            rightButton.setBackground(context.getDrawable(R.drawable.button_right));
            OppositeGame.data.remove(makeQuestion.getCountryNum());
            right.setText(String.valueOf(Integer.parseInt((String) right.getText())+1));
        }
        else{
            ImageButton rightButton = clickedI(makeQuestion.getRightAnsPlace()),wrongButton = clickedI(choice);
            assert rightButton != null;
            rightButton.setBackground(context.getDrawable(R.drawable.button_right));
            assert wrongButton != null;
            wrongButton.setBackground(context.getDrawable(R.drawable.button_wrong));
            OppositeGame.data.remove(makeQuestion.getCountryNum());
            wrong.setText(String.valueOf(Integer.parseInt((String) wrong.getText())+1));
        }
    }

    private Button clicked(int choice){
        c1.setClickable(false);
        c2.setClickable(false);
        c3.setClickable(false);
        c4.setClickable(false);

        switch (choice){
            case 0: return c1;
            case 1: return c2;
            case 2: return c3;
            case 3: return c4;
            default: return null;
        }
    }

    private ImageButton clickedI(int choice){
        switch (choice){
            case 0: return ci1;
            case 1: return ci2;
            case 2: return ci3;
            case 3: return ci4;
            default: return null;
        }
    }
}
