package QuestionHandling;

import com.example.flagsandcapitals.Game;
import com.example.flagsandcapitals.OppositeGame;
import java.util.Random;

public class MakeQuestion {
    private String [] choices = new String[4];
    private String imageName,mode;
    private int rightAnsPlace;
    private int countryNum,id,size;
    private int []wrongChoices = new int[3];

    public MakeQuestion(String mode, String Activity){
        Random random = new Random();
        this.mode=mode;

        if( Activity.equals("Game")){
            countryNum = random.nextInt(Game.data.size());
            id = Game.data.get(countryNum).getId();
            size = Game.fullData.size();
        }
        else{
            countryNum = random.nextInt(OppositeGame.data.size());
            id = OppositeGame.data.get(countryNum).getId();
            size = OppositeGame.fullData.size();
        }

        rightAnsPlace = random.nextInt(4);

        wrongChoices[0]=wrongChoices[1]=wrongChoices[2]=id;

        wrongChoices[0]=getRandomChoice(wrongChoices[1],wrongChoices[2],size,id);
        wrongChoices[1]=getRandomChoice(wrongChoices[0],wrongChoices[2],size,id);
        wrongChoices[2]=getRandomChoice(wrongChoices[1],wrongChoices[0],size,id);
    }

    void normalMode(){
        choices[rightAnsPlace] = mode.equals("FtoCo") ? Game.data.get(countryNum).getCountryName() : Game.data.get(countryNum).getCapitalName();
        imageName = Game.data.get(countryNum).getImageName();
        int j=0;
        for(int i=0;i<4;i++){
            if(choices[i]==null)
            {
                choices[i]=mode.equals("FtoCo") ? Game.fullData.get(wrongChoices[j]).getCountryName() : Game.fullData.get(wrongChoices[j]).getCapitalName();
                j++;
            }
        }
    }

    void oppositeMode(){
        imageName = mode.equals("CotoF") ? OppositeGame.data.get(countryNum).getCountryName() : OppositeGame.data.get(countryNum).getCapitalName();
        choices[rightAnsPlace] = OppositeGame.data.get(countryNum).getImageName();
        int j=0;
        for(int i=0;i<4;i++){
            if(choices[i]==null)
            {
                choices[i]= OppositeGame.fullData.get(wrongChoices[j]).getImageName();
                j++;
            }
        }
    }

    private int getRandomChoice(int choice2, int choice3,int size, int id){
        Random random = new Random();
        int choice1;
        do{ choice1 = random.nextInt(size); }
        while(choice1 == choice2 || choice1 == choice3 || choice1 == id);
        return choice1;
    }

    String[] getChoices() {
        return choices;
    }

    String getImageName() {
        return imageName;
    }

    int getRightAnsPlace() {
        return rightAnsPlace;
    }

    int getCountryNum() {
        return countryNum;
    }
}
