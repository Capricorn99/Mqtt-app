package com.example.mqttapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdvancedOptions extends AppCompatActivity {

    EditText server;
    EditText port;
    EditText pub_topic;
    EditText sub_topic;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_options);
        Intent intent = getIntent();
        server = findViewById(R.id.server);
        port = findViewById(R.id.port);
        pub_topic = findViewById(R.id.pub_topic);
        sub_topic = findViewById(R.id.sub_topic);

        server.setText(intent.getStringExtra("server"));
        port.setText(intent.getStringExtra("port"));
        pub_topic.setText(intent.getStringExtra("pubTopic"));
        sub_topic.setText(intent.getStringExtra("subTopic"));
        save = findViewById(R.id.saveButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("SERVER", server.getText().toString());
                resultIntent.putExtra("PORT", port.getText().toString());
                resultIntent.putExtra("PUBTOPIC", pub_topic.getText().toString());
                resultIntent.putExtra("SUBTOPIC", sub_topic.getText().toString());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

    }
}
