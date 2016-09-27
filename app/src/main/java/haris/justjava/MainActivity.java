//TODO Urdu Translations or other language translatios
package haris.justjava;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    CheckBox chocolateCB;
    CheckBox whizzedCreamCB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chocolateCB = (CheckBox)findViewById(R.id.chocolateCB);
        whizzedCreamCB = (CheckBox) findViewById(R.id.whizzed_creamCB);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        orderSummary();
    }

    public void increment(View view){
        if(quantity == 100){
            toastShow("Order Maximum 100 Coofies",Toast.LENGTH_SHORT);
            return;
        }
        quantity++;
        display(quantity);
    }

    public void decrement(View view){
        if(quantity == 1){
            toastShow("Order Minimum One Cofiees",Toast.LENGTH_SHORT);
            return;
        }
        quantity--;
        display(quantity);

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private double calculatePrice(boolean isWhizzedCream, boolean isChocolate){
        int baseprice = 50;
        int chocolateToping = isChocolate?10:0;
        int whizzedToping = isWhizzedCream?5:0;
        return quantity*(baseprice+chocolateToping+whizzedToping);

    }

    /**
     * This method displays the given price on the screen.
     */
    private void orderSummary() {


        EditText nameED = (EditText)findViewById(R.id.nameED);
        String orderSummary = "Order Summary\n"+
                nameED.getText().toString().toUpperCase()+"\n"+
                "Whizzed Cream Topping ? "+whizzedCreamCB.isChecked()+"\n"+
                "Chocolate Topping ? "+chocolateCB.isChecked()+"\n"+
                "Quantity = "+quantity+
                "Total Price :"+NumberFormat.getCurrencyInstance(new Locale("en","pk")).format(calculatePrice(whizzedCreamCB.isChecked(),chocolateCB.isChecked()))+"\nThankyou!";

        Email(orderSummary,nameED.getText().toString());
    }

    private void toastShow(String msg, int length){
        Toast t = Toast.makeText(this,msg+"Length:"+length,length);
        t.show();

    }

    private void Email(String msg,String name){
        // TODO make Only Email Application

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, "m.harisjaved487@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order For Mr/Miss "+name);
        intent.putExtra(Intent.EXTRA_TEXT, msg);



        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(Intent.createChooser(intent, "Send Email"));
        }
        else{
            toastShow("No Email Application Currently You Have",Toast.LENGTH_SHORT);
        }

    }
}
