package com.example.crudapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ActivityListar extends AppCompatActivity {

    private ListView listViewProducts;
    private DatabaseHelper dbHelper;
    private ArrayList<String> productList;
    private ArrayList<Integer> productIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        dbHelper = new DatabaseHelper(this);
        listViewProducts = findViewById(R.id.listViewProducts);
        productList = new ArrayList<>();
        productIds = new ArrayList<>();

        loadProducts();

        listViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final int productId = productIds.get(position);

                CharSequence[] options = {"Atualizar", "Deletar"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityListar.this);
                builder.setTitle("Escolha uma opção");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {

                            Intent intent = new Intent(ActivityListar.this, ActivityCadastro.class);
                            intent.putExtra("id", productId);
                            startActivity(intent);
                        } else if (which == 1) {

                            boolean isDeleted = dbHelper.deleteProduct(productId);
                            if (isDeleted) {
                                Toast.makeText(ActivityListar.this, "Produto deletado", Toast.LENGTH_SHORT).show();
                                loadProducts();
                            } else {
                                Toast.makeText(ActivityListar.this, "Erro ao deletar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                builder.show();
            }
        });
    }

    private void loadProducts() {
        Cursor cursor = dbHelper.getAllProducts();
        productList.clear();
        productIds.clear();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String product = cursor.getString(2);
                double price = cursor.getDouble(3);
                int quantity = cursor.getInt(4);

                productList.add(name + " - " + product + " - R$ " + price + " - Quantidade: " + quantity);
                productIds.add(id);
            } while (cursor.moveToNext());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);
        listViewProducts.setAdapter(adapter);
    }
}
