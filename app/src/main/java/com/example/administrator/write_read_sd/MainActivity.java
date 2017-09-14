package com.example.administrator.write_read_sd;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class MainActivity extends AppCompatActivity {

    private Button write_button;
    private EditText xue_write_text;
    private EditText name_write_text;
    private EditText class_write_text;
    private Button read_button;
    private TextView read_text;
    final String FILE_NAME="/lbb1.bin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        write_button = (Button) findViewById(R.id.write_button);
        read_button = (Button) findViewById(R.id.read_button);
        xue_write_text = (EditText) findViewById(R.id.xue_text);
        name_write_text = (EditText) findViewById(R.id.name_text);
        class_write_text = (EditText) findViewById(R.id.class_text);
        read_text = (TextView)findViewById(R.id.read_text);

        write_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String write_text = (xue_write_text.getText().toString().trim())+" "+(name_write_text.getText().toString().trim())+" "+(class_write_text.getText().toString().trim());
                write(write_text);
                xue_write_text.setText("");
                name_write_text.setText("");
                class_write_text.setText("");
            }
        });

        read_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                read_text.setText(read());
            }
        });

    }
    private void write(String content){
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File sdCardDir = Environment.getExternalStorageDirectory();
                File targetFile = new File(sdCardDir.getCanonicalPath()+FILE_NAME);
                RandomAccessFile raf = new RandomAccessFile(targetFile,"rw");
                raf.seek(targetFile.length());
                raf.write(content.getBytes());
                raf.close();
                Toast.makeText(this,"成功",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private String read(){
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File sdCardDir = Environment.getExternalStorageDirectory();
                FileInputStream fis = new FileInputStream(sdCardDir.getCanonicalPath() + FILE_NAME);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                StringBuffer sb = new StringBuffer("");
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return sb.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
