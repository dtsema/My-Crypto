package com.example.daniel.mycrypto;

import android.graphics.Color;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class ExtractPrice extends Thread {
    private String URL, HTMLString, coinName, coinSymbol;
    private Document URLPage;
    private Float currentPrice, coinAmt, coinPpc, profit, sum;
    private TextView priceView, sumView;
    private Boolean coinInput;

ExtractPrice(String URL, Float currentPrice, TextView priceView, String coinName,
             String coinSymbol, Boolean coinInput, Float coinAmt, Float coinPpc,
             Float sum, TextView sumView){
    this.URL = URL;
    this.currentPrice = currentPrice;
    this.priceView=priceView;
    this.coinName=coinName;
    this.coinInput=coinInput;
    this.coinAmt=coinAmt;
    this.coinPpc = coinPpc;
    this.sum= sum;
    this.sumView = sumView;
    this.coinSymbol=coinSymbol;
}

    public void run() {
        connectToURL();
        getHTMLFromURL();
        getCurrentCoinPrice();
        displayProfit();
        displaySumProfits();
    }

    private void connectToURL(){
        try {
            URLPage = Jsoup.connect(URL).ignoreContentType(true).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getHTMLFromURL(){
        HTMLString = URLPage.toString();
    }

    private void getCurrentCoinPrice(){
        String[] alist =  HTMLString.split("<h5>\\$");
        String[] alist1 = alist[1].split("</h5>");
        String result = alist1[0];
        currentPrice = Float.parseFloat(result);
    }

    private void displayProfit(){
        priceView.post(new Runnable() {
                           @Override
                           public void run() {
                               getProfit();

                               setProfitTextColor();

                               setProfitTextView();
                           }
                           private void getProfit(){
                               if (coinAmt>0){
                                   profit = ((currentPrice) * coinAmt) - ((coinPpc) * coinAmt);}
                               else profit = (float) 0;
                           }
                           private void setProfitTextColor(){
                               if (profit < 0) priceView.setTextColor(Color.rgb(153, 0, 0));
                               else if (profit > 0) priceView.setTextColor(Color.rgb(0, 102, 0));
                               else priceView.setTextColor(Color.BLACK);
                           }
                           private void setProfitTextView(){
                               if (profit > 0 || profit < 0) {
                                   String coinName1= coinName.substring(0,1).toUpperCase() +coinName.substring(1);
                                   String coinSymbol1 = coinSymbol.toUpperCase();
                                   priceView.setText(String.format("%s%s%s(%s): %s%s", System.getProperty("line.separator"), System.getProperty("line.separator"), coinName1, coinSymbol1, System.getProperty("line.separator"), String.format("%.2f", profit)));
                               }
                               else {
                                   String coinName1= coinName.substring(0,1).toUpperCase() +coinName.substring(1);
                                   String coinSymbol1 = coinSymbol.toUpperCase();
                                   priceView.setText(String.format("%s%s%s(%s): %s0", System.getProperty("line.separator"), System.getProperty("line.separator"), coinName1, coinSymbol1, System.getProperty("line.separator")));
                               }
                           }
                       }
        );
    }
    private void displaySumProfits(){
        sumView.post(new Runnable() {
            @Override
            public void run() {
                getSumProfit();
                setSumProfitColor();
                setSumProfitTextView();
            }
            private void getSumProfit(){
                MainActivity.sum = MainActivity.sum + profit;
            }
            private void setSumProfitColor(){
                if (MainActivity.sum <= 0) sumView.setTextColor(Color.rgb(153, 0, 0));
                else sumView.setTextColor(Color.rgb(0, 102, 0));
            }
            private void setSumProfitTextView(){
                sumView.setText(Float.toString(MainActivity.sum));
            }
        });
    }
}
