package com.example.jake.stripeapitest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

public class MainActivity extends AppCompatActivity {

    private CardInputWidget cardInput;
    private EditText
            editName,
            editAddress1,
            editAddress2,
            editCity,
            editZip;
    private Button payButton;
    private Stripe stripe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        payButton = (Button) findViewById(R.id.payButton);
        cardInput = (CardInputWidget) findViewById(R.id.card_input_widget);
        editName = (EditText) findViewById(R.id.editName);
        editAddress1 = (EditText) findViewById(R.id.editAddress1);
        editAddress2 = (EditText) findViewById(R.id.editAddress2);
        editCity = (EditText) findViewById(R.id.editCity);
        editZip = (EditText) findViewById(R.id.editZip);

        stripe = new Stripe(getApplicationContext(), "pk_test_Nou0qB1WPH8cwe03GGcYhfdu");

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card card = cardInput.getCard();
                if (card == null) {
                    Toast.makeText(getApplicationContext(), "Invalid card information.", Toast.LENGTH_SHORT).show();
                    return;
                }
                card.setName(editName.getText().toString());
                card.setAddressLine1(editAddress1.getText().toString());
                card.setAddressLine2(editAddress2.getText().toString());
                card.setAddressCity(editCity.getText().toString());
                card.setAddressZip(editZip.getText().toString());

                stripe.createToken(
                        card,
                        new TokenCallback() {
                            public void onSuccess(Token token) {
                                Toast.makeText(getApplicationContext(), "Sent card information to server.", Toast.LENGTH_SHORT).show();
                            }
                            public void onError(Exception error) {
                                // Show localized error message
                                Toast.makeText(getApplicationContext(),
                                        error.getLocalizedMessage(),
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                );
            }
        });


    }


}