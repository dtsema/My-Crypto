package com.example.daniel.mycrypto;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    LinearLayout tickerLinearLayout, pricesLayout;
    WebView currency;
    WebView[] webViewsList;
    Button addCurrency, removeCurrency;
    String[] names = {"Cardano", "BitcoinCash", "Bitcoin", "Dash", "Eos", "EthereumClassic",
            "Ethereum","Icon", "Litecoin", "Iota", "Neo", "Qtum", "Tron", "Nem", "StellarLumens",
            "Monero", "Ripple", "Tezos"}, a;
    String URL;
    SharedPreferences settings, savedCrypto;
    SharedPreferences.Editor editor;
    TextView sumView;
    TextView[] priceViews;
    Boolean input;
    public static Float sum = (float) 0;
    public Spinner symbol;
    Integer totalViews = 0;
    private float coinAmount, pricePerCoin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        loadSettings();

        createSpinnerAdapter();

        getCryptoData();

        hideExtraViews();

        addCurrencyButton();

        removeCurrencyButton();
    }

    private void findViews(){
        tickerLinearLayout = findViewById(R.id.TickerLinearLayout);
        pricesLayout = findViewById(R.id.pricesLayout);
        symbol = findViewById(R.id.symbol);
        sumView = findViewById(R.id.sum);

        TextView wl = findViewById(R.id.w_l);
        TextView wl1 = findViewById(R.id.w_l1);
        TextView wl2 = findViewById(R.id.w_l2);
        TextView wl3 = findViewById(R.id.w_l3);
        TextView wl4 = findViewById(R.id.w_l4);
        TextView wl5 = findViewById(R.id.w_l5);
        TextView wl6 = findViewById(R.id.w_l6);
        TextView wl7 = findViewById(R.id.w_l7);
        TextView wl8 = findViewById(R.id.w_l8);
        TextView wl9 = findViewById(R.id.w_l9);
        TextView wl10 = findViewById(R.id.w_l10);
        TextView wl11 = findViewById(R.id.w_l11);
        TextView wl12 = findViewById(R.id.w_l12);
        TextView wl13 = findViewById(R.id.w_l13);
        TextView wl14 = findViewById(R.id.w_l14);
        TextView wl15 = findViewById(R.id.w_l15);
        TextView wl16 = findViewById(R.id.w_l16);
        TextView wl17 = findViewById(R.id.w_l17);

        priceViews = new TextView[]{wl,wl1,wl2,wl3,wl4,wl5,wl6,wl7,wl8,wl9,wl10,wl11,wl12,wl13,wl14,wl15,wl16,wl17};

        WebView cryptoView = findViewById(R.id.cryptoView);
        WebView cryptoView1 = findViewById(R.id.cryptoView1);
        WebView cryptoView2 = findViewById(R.id.cryptoView2);
        WebView cryptoView3 = findViewById(R.id.cryptoView3);
        WebView cryptoView4 = findViewById(R.id.cryptoView4);
        WebView cryptoView5 = findViewById(R.id.cryptoView5);
        WebView cryptoView6 = findViewById(R.id.cryptoView6);
        WebView cryptoView7 = findViewById(R.id.cryptoView7);
        WebView cryptoView8 = findViewById(R.id.cryptoView8);
        WebView cryptoView9 = findViewById(R.id.cryptoView9);
        WebView cryptoView10 = findViewById(R.id.cryptoView10);
        WebView cryptoView11 = findViewById(R.id.cryptoView11);
        WebView cryptoView12 = findViewById(R.id.cryptoView12);
        WebView cryptoView13 = findViewById(R.id.cryptoView13);
        WebView cryptoView14 = findViewById(R.id.cryptoView14);
        WebView cryptoView15 = findViewById(R.id.cryptoView15);
        WebView cryptoView16 = findViewById(R.id.cryptoView16);
        WebView cryptoView17 = findViewById(R.id.cryptoView17);

        webViewsList = new WebView[]{cryptoView, cryptoView1, cryptoView2,cryptoView3,cryptoView4,
                cryptoView5, cryptoView6, cryptoView7, cryptoView8, cryptoView9, cryptoView10, cryptoView11,
                cryptoView12, cryptoView13, cryptoView14, cryptoView15, cryptoView16, cryptoView17 };

        addCurrency = findViewById(R.id.addCurrency);
        removeCurrency = findViewById(R.id.removeCurrency);
    }

    private void loadSettings(){
        settings = getApplicationContext().getSharedPreferences("BuyPrice", 0);
        savedCrypto = getApplicationContext().getSharedPreferences("savedCrypto", 0);
        editor = savedCrypto.edit();
    }

    private void createSpinnerAdapter(){
        ArrayAdapter coinAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.Symbols));
        symbol.setAdapter(coinAdapter);
    }

    private void getCryptoData(){
        Map<String, ?> savedCryptos = savedCrypto.getAll();
        for (Map.Entry<String, ?> currentCrypto : savedCryptos.entrySet()) {

            if (crytoOn(currentCrypto)){

                showViews();

                enableJavaScript();

                loadCryptoURL(currentCrypto);

                getStoredCyptoData();

                getPrice();

                totalViews++;
            }
        }
    }

    private Boolean crytoOn(Map.Entry<String,?> entry){
        return (Boolean)entry.getValue();
    }

    private void showViews(){
        webViewsList[totalViews].setVisibility(View.VISIBLE);
        priceViews[totalViews].setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void enableJavaScript(){
        webViewsList[totalViews].getSettings().setJavaScriptEnabled(true);
    }

    private void loadCryptoURL(Map.Entry<String,?> currentCrypto){
        a = currentCrypto.getKey().split("\\+");
        webViewsList[totalViews].loadUrl("file:///android_asset/" + a[0] +".html");
        URL = "https://ticker.cryptoreport.com/" + a[1] + "/" + a[0].toUpperCase() +"/ticker?source=website";
    }

    private void getStoredCyptoData(){
        input = settings.getBoolean(a[1] + "PriceOn", false);
        coinAmount = settings.getFloat(a[1]+"Amt", 0);
        pricePerCoin = settings.getFloat(a[1]+"Ppc", 0);
    }

    private void getPrice(){
        float coin = (float) 0;
        ExtractPrice price =  new ExtractPrice(URL, coin, priceViews[totalViews], a[1], a[0], input, coinAmount, pricePerCoin, sum, sumView);
        price.start();
    }

    private void hideExtraViews(){
        for (int i = totalViews; i < webViewsList.length; i++){
            webViewsList[i].setVisibility(View.GONE);
            priceViews[i].setVisibility(View.GONE);
        }
    }

    private void addCurrencyButton(){
        addCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeSelection(true);
                resetActivity();
            }
        });
    }

    private void storeSelection(Boolean on){
        String stringSymbol = symbol.getSelectedItem().toString().toUpperCase();
        String name = names[symbol.getSelectedItemPosition()].toLowerCase();

        editor.putBoolean(stringSymbol.toLowerCase() + "+" + name, on);
        editor.apply();
    }

    private void resetActivity(){
        Intent intent = new Intent(MainActivity.this,MainActivity.class);
        MainActivity.this.startActivity(intent);
    }

    private void removeCurrencyButton(){
        removeCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeSelection(false);
                resetActivity();
            }
        });
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(MainActivity.this, PriceInputPage.class);
        startActivity(intent);
    }
}
