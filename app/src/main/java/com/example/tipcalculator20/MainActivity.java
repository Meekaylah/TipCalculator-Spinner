package com.example.tipcalculator20;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText editTextBillAmount;
    EditText editTextPeople;
    TextView textViewTipAmount;
    Spinner spinner;
    DecimalFormat df = new DecimalFormat("￡####.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextBillAmount = findViewById(R.id.editTextText);
        editTextPeople = findViewById(R.id.editTextText2);
        textViewTipAmount = findViewById(R.id.textView3);
        spinner = findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.percentages,
                android.R.layout.simple_spinner_item
        );

        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        editTextPeople.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (!editTextPeople.getText().toString().isEmpty()) {
                    String tipAmount = textViewTipAmount.getText().toString().replace("￡", "").trim();
                    int person = Integer.parseInt(editTextPeople.getText().toString().trim());
                    if (!tipAmount.isEmpty()) {
                        textViewTipAmount.setText(df.format(Double.parseDouble
                                (String.valueOf(Float.parseFloat(tipAmount) / person))));
                    }
                } else {
                    textViewTipAmount.setVisibility(TextView.GONE);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // An item is selected. You can retrieve the selected item using parent.getItemAtPosition(position).
        if(editTextBillAmount.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter the bill!", Toast.LENGTH_SHORT).show();
            parent.setSelection(0);
        } else {
            textViewTipAmount.setVisibility(TextView.VISIBLE);
            String selectedPercentage = (String) parent.getItemAtPosition(position);
            switch (selectedPercentage) {
                case "10%":
                    textViewTipAmount.setText(df.format(Double.parseDouble
                            (editTextBillAmount.getText().toString()) * .10));
                    break;
                case "15%":
                    textViewTipAmount.setText(df.format(Double.parseDouble
                            (editTextBillAmount.getText().toString()) * .15));
                    break;
                case "20%":
                    textViewTipAmount.setText(df.format(Double.parseDouble
                            (editTextBillAmount.getText().toString()) * .20));
                    break;
                default:
                    textViewTipAmount.setVisibility(TextView.GONE);
                    break;
            }
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback.
    }

}