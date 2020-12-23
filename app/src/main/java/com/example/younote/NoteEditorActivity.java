package com.example.younote;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.io.IOException;

public class NoteEditorActivity extends AppCompatActivity {

    private static final int GET_FROM_GALLERY = 1;
    private static final String ROTATE_EDIT_VALUE = "rot";
    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button upload;
    ImageView imageView;
    TextView textView;
    TextView textView2;
    ImageView imageView3;
    ImageView imageView4;
    TextView textView3;
    ImageView imageView5;
    TextView textView4;
    Bitmap b;
    String s;
    EditText editText;
    int count = 0;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ROTATE_EDIT_VALUE, s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        upload = findViewById(R.id.upload);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        imageView = findViewById(R.id.imageView);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        textView3 = findViewById(R.id.textView3);
        imageView5 = findViewById(R.id.imageView5);
        textView4 = findViewById(R.id.textView4);
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivityBackgroundColor(getResources().getColor(R.color.colorRed));
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivityBackgroundColor(getResources().getColor(R.color.colorGold));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivityBackgroundColor(getResources().getColor(R.color.colorTeal));
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivityBackgroundColor(getResources().getColor(R.color.colorBlue));
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivityBackgroundColor(getResources().getColor(R.color.colorGray));
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivityBackgroundColor(getResources().getColor(R.color.colorWhite));
            }
        });

        editText = findViewById(R.id.editText);
        s = editText.getText().toString();
        if (savedInstanceState != null)
        {
            s = savedInstanceState.getString(ROTATE_EDIT_VALUE);
            editText.setText(s);
        }
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri img = data.getData();
            b = null;
            try {
                b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), img);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        Frame imageFrame = new Frame.Builder().setBitmap(b).build();
        String imageText = "";
        SparseArray<TextBlock> txtBlock = textRecognizer.detect(imageFrame);
        for (int i = 0; i < txtBlock.size(); i++) {
            TextBlock textBlock = txtBlock.get(txtBlock.keyAt(i));
            imageText = textBlock.getValue();
        }
        if (count == 0) {
            imageView.setImageBitmap(b);
            textView.setText(imageText);
        }
        else if (count == 1) {
            imageView3.setImageBitmap(b);
            textView2.setText(imageText);
        }
        else if (count == 2) {
            imageView4.setImageBitmap(b);
            textView3.setText(imageText);
        }
        else if (count == 3) {
            imageView5.setImageBitmap(b);
            textView4.setText(imageText);
        }
        count++;
    }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }
}