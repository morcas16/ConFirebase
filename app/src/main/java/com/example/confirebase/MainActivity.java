package com.example.confirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //defining view objects
    private EditText TextEmail;
    private EditText TextPassword;
    private Button btnRegistrar,btnLogin;
    private ProgressDialog progressDialog;

    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        //Referenciamos los views
        TextEmail = (EditText) findViewById(R.id.txtEmail);
        TextPassword = (EditText) findViewById(R.id.txtPassword);
        btnRegistrar = (Button) findViewById(R.id.botonRegistrar);
        btnLogin = (Button) findViewById(R.id.botonLoguin);
        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        btnRegistrar.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private void registrarUsuario(){

        //Obtenemos el email y la contraseña desde las cajas de texto
        String email = TextEmail.getText().toString().trim();
        String password  = TextPassword.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){

                            Toast.makeText(MainActivity.this,"Se ha registrado el usuario con el email: "+ TextEmail.getText(),Toast.LENGTH_LONG).show();
                        }else{

                            Toast.makeText(MainActivity.this,"No se pudo registrar el usuario ",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }
private void loguearUsuario(){
    //Obtenemos el email y la contraseña desde las cajas de texto
    final String email = TextEmail.getText().toString().trim();
    String password  = TextPassword.getText().toString().trim();

    //Verificamos que las cajas de texto no esten vacías
    if(TextUtils.isEmpty(email)){
        Toast.makeText(this,"Se debe ingresar un email", Toast.LENGTH_LONG).show();
        return;
    }

    if(TextUtils.isEmpty(password)){
        Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
        return;
    }


    progressDialog.setMessage("Ingresando a su cuenta...");
    progressDialog.show();

    //loguear usurio
    firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //checking if success
                    if(task.isSuccessful()){

                        Toast.makeText(MainActivity.this,"Bienvenido: "+ TextEmail.getText(),Toast.LENGTH_LONG).show();
                        Intent intecion =new Intent(getApplication(),Bienvenido1Activity.class);
                        intecion.putExtra(Bienvenido1Activity.user,email);
                        startActivity(intecion);

                    }else{

                        Toast.makeText(MainActivity.this,"No se pudo registrar el usuario ",Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }
            });

}
    @Override
    public void onClick(View view) {
        //Invocamos al método:
        switch(view.getId())
        {
            case R.id.botonRegistrar:
                registrarUsuario();
                break;

             case R.id.botonLoguin:
                 loguearUsuario();
                 break;

        }
    }
}
