package gunel.eslanova.chess;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainpageActivity2 extends AppCompatActivity {

    private Button button;
    private TextView textView1;
    private TextView textView2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mainpage2);

        button = findViewById(R.id.button);
//        textView1 = findViewById(R.id.textView1);
//        textView2 = findViewById(R.id.textView2);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                EditText name1=findViewById(R.id.textView1);
                EditText name2=findViewById(R.id.textView2);

                String name_1=name1.getText().toString();
                String name_2=name2.getText().toString();
                if(name_1.isEmpty()){
                    name_1="Player 1";
                }
                if(name_2.isEmpty()){
                    name_2="Player 2";
                }

                Bundle bundle=new Bundle();
                bundle.putString("name1",name_1);
                bundle.putString("name2",name_2);
                Toast.makeText(MainpageActivity2.this, name_1+name_2, Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(MainpageActivity2.this,GameActivity.class);
                intent.putExtra("bundle",bundle);

                startActivity(intent);
                name1.setText("");
                name2.setText("");
            }
        });
    }
}