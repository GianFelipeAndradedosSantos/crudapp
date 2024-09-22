package com.example.crudapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityCadastro extends AppCompatActivity {

    private EditText Name, Product, Price, Quantity;
    private Button etbtnSave;
    private DatabaseHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        dbHelper = new DatabaseHelper(this);

        Name = findViewById(R.id.Name);
        Product = findViewById(R.id.Product);
        Price = findViewById(R.id.Price);
        Quantity = findViewById(R.id.Quantity);
        etbtnSave = findViewById(R.id.etbtnSave);

        etbtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString();
                String product = Product.getText().toString();
                double price = Double.parseDouble(Price.getText().toString());
                int quantity = Integer.parseInt(Quantity.getText().toString());

                boolean isInserted = dbHelper.insertProduct(name, product, price, quantity);

                if (isInserted) {
                    Toast.makeText(ActivityCadastro.this, "Produto cadastrado!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ActivityCadastro.this, ActivityListar.class));
                } else {
                    Toast.makeText(ActivityCadastro.this, "Erro ao cadastrar!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
