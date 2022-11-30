package com.example.lfao;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.lfao.databinding.ActivityListBinding;

import java.util.ArrayList;
import java.util.Collections;

public class ListActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityListBinding binding;

    private ListView listView;

    private final ArrayList<String> textList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Your list");

        listView = findViewById(R.id.list_view);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, textList);
        listView.setAdapter(adapter);

        /* Deletion*/
        listView.setOnItemLongClickListener((parent, itemClicked, position, id) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(ListActivity.this);
            alert.setTitle("Delete item?");
            alert.setPositiveButton("Delete", (dialog, whichButton) -> {
                textList.remove(position);
                adapter.notifyDataSetChanged();
            });

            alert.setNegativeButton("Cancel", (dialog, whichButton) -> {
            });

            alert.show();
            return true;
        });


        /* Editing */
        listView.setOnItemClickListener((parent, itemClicked, position, id) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(ListActivity.this);

            final EditText edittext = new EditText(ListActivity.this);
            edittext.setText(textList.get(position));
            alert.setTitle("Edit item");

            alert.setView(edittext);

            alert.setPositiveButton("Edit", (dialog, whichButton) -> {
                String text = edittext.getText().toString();
                if (!text.isEmpty()) {
                    textList.set(position, text);
                    adapter.notifyDataSetChanged();
                }
            });

            alert.setNegativeButton("Cancel", (dialog, whichButton) -> {
            });

            alert.show();
        });


        /* Adding */
        binding.fab.setOnClickListener(view -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(ListActivity.this);


            final EditText edittext = new EditText(ListActivity.this);
            alert.setTitle("Add item");

            alert.setView(edittext);

            alert.setPositiveButton("Add", (dialog, whichButton) -> {
                String text = edittext.getText().toString();
                if (!text.isEmpty()) {
                    textList.add(text);
                    adapter.notifyDataSetChanged();
                }
            });

            alert.setNegativeButton("Cancel", (dialog, whichButton) -> {
            });

            alert.show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_list);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}