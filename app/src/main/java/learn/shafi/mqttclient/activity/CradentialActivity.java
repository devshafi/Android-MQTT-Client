package learn.shafi.mqttclient.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fragment.WorkingFragment;
import learn.shafi.mqttclient.R;

public class CradentialActivity extends AppCompatActivity {


    Toolbar toolbar;

    TextInputLayout uriTIL;
    TextInputLayout portTIL;
    TextInputLayout clientIdTIL;
    TextInputLayout usernameTIL;
    TextInputLayout passwordTIL;

    EditText uriET;
    EditText portET;
    EditText clientIdET;
    EditText usernameET;
    EditText passwordET;

    Button createConnectionBT;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_cradential);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String toolbarTitle = "MQTT server cradential";
        toolbar.setTitle(toolbarTitle);




        uriTIL = findViewById(R.id.uriTIL);
        portTIL = findViewById(R.id.portTIL);
        clientIdTIL = findViewById(R.id.clientIdTIL);
        usernameTIL = findViewById(R.id.usernameTIL);
        passwordTIL = findViewById(R.id.passwordTIL);
        uriET = findViewById(R.id.uriET);
        portET = findViewById(R.id.portET);
        clientIdET = findViewById(R.id.clientIdET);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        createConnectionBT = findViewById(R.id.createConnectionBT);


        createConnectionBT.setOnClickListener(v->{

            if(isAnyFieldEmpty()){

                Toast.makeText(this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
            }

            else {



               WorkingFragment.URI = uriET.getText().toString();
               WorkingFragment.PORT = portET.getText().toString();
               WorkingFragment.CLIENT_ID = clientIdET.getText().toString();
               WorkingFragment.USERNAME = usernameET.getText().toString();
               WorkingFragment.AIO_KEY = passwordET.getText().toString();

               Intent intent = new Intent(CradentialActivity.this,MainActivity.class);
               startActivity(intent);


            }

        });




    }


    // method for checking if any input field is empty or not
    public boolean isAnyFieldEmpty(){

        return uriET.getText().toString().trim().isEmpty() || portET.getText().toString().trim().isEmpty() ||
                clientIdET.getText().toString().trim().isEmpty() || usernameET.getText().toString().trim().isEmpty() ||
                passwordET.getText().toString().trim().isEmpty();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}
