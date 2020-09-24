package com.example.flagsandcapitals;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import UIEffects.IntroMenu;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final Button country = findViewById(R.id.country);
        final Button capital = findViewById(R.id.capital);
        final Button flag1 = findViewById(R.id.flag1);
        final Button flag2 = findViewById(R.id.flag2);
        final TextView guess = findViewById(R.id.guess);

        new IntroMenu(this, guess, country, capital, flag1, flag2);
    }

    public void FlagToCountryMode(View view){
        Intent myIntent = new Intent(Menu.this, Game.class);
        myIntent.putExtra("key", "FtoCo");
        Menu.this.startActivity(myIntent);
    }

    public void FlagToCapitalMode(View view){
        Intent myIntent = new Intent(Menu.this, Game.class);
        myIntent.putExtra("key", "FtoCa");
        Menu.this.startActivity(myIntent);
    }

    public void CountryToFlagMode(View view){
        Intent myIntent = new Intent(Menu.this, OppositeGame.class);
        myIntent.putExtra("key", "CotoF");
        Menu.this.startActivity(myIntent);
    }

    public void CapitalToFlagMode(View view){
        Intent myIntent = new Intent(Menu.this, OppositeGame.class);
        myIntent.putExtra("key", "CatoF");
        Menu.this.startActivity(myIntent);
    }
}
