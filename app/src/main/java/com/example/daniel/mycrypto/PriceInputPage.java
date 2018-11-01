package com.example.daniel.mycrypto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class PriceInputPage  extends AppCompatActivity {
    Float pricePerCoin;
    String buyAmt, buyCost, spinnerChoice;
    EditText purchaseCost, purchaseAmt;
    Spinner spinner;
    ArrayAdapter<String> spinnerCurrencyArrayAdapter;
    Button submit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.price_input);

        findViews();

        setSpinnerAdapter();

        submitButton();
    }

    private void findViews(){
        purchaseCost = findViewById(R.id.buyPrice);
        purchaseAmt = findViewById(R.id.buyAmt);
        submit = findViewById(R.id.submit);
        spinner = findViewById(R.id.spinner);
    }

    private void setSpinnerAdapter(){
        spinnerCurrencyArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Currency));
        spinner.setAdapter(spinnerCurrencyArrayAdapter);
    }

    private void submitButton(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserInputs();

                SharedPreferences settings =  getApplicationContext().getSharedPreferences("BuyPrice", 0);
                SharedPreferences.Editor editor = settings.edit();

                storePurchasePricePerCoin(editor);

                editor.putFloat(spinnerChoice + "Amt", Float.parseFloat(buyAmt));
                editor.putBoolean(spinnerChoice+"PriceOn", true);

                editor.apply();
            }
        });
    }

    private void getUserInputs(){
        buyCost = purchaseCost.getText().toString();
        buyAmt = purchaseAmt.getText().toString();
        spinnerChoice = spinner.getSelectedItem().toString();
    }

    private void storePurchasePricePerCoin(SharedPreferences.Editor editor){
        if (Float.parseFloat(buyAmt)>0) {
            pricePerCoin = (Float.parseFloat(buyCost)) / (Float.parseFloat(buyAmt));
            editor.putFloat(spinnerChoice +"Ppc", pricePerCoin);
        }
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(PriceInputPage.this, MainActivity.class);
        startActivity(intent);
    }
}
