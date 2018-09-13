package com.aplicativo.lenovouser.meuaplicativo.Usuário;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aplicativo.lenovouser.meuaplicativo.MainActivity;
import com.aplicativo.lenovouser.meuaplicativo.Models.UsuarioModel;
import com.aplicativo.lenovouser.meuaplicativo.R;
import com.aplicativo.lenovouser.meuaplicativo.Repository.UsuarioRepository;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[a-zA-Z])" +
            "(?=.*[@#$%^&+=])" +
            "(?=\\S+$)" +
            ".{4,}" +
            "$");

    private EditText edEmail;
    private EditText edPassword;
    private Button btnLogin;
    private TextView txtUsuario;

    UsuarioRepository usuarioRepository = new UsuarioRepository(this);
    UsuarioModel usuarioModel = new UsuarioModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    private boolean validaEmail(){
        edEmail = (EditText) findViewById(R.id.editText_Email);
        String emailInput = edEmail.getText().toString().trim();
        if (emailInput.isEmpty()){
            edEmail.requestFocus();
            edEmail.setError("Campo não pode ser vazio");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            edEmail.requestFocus();
            edEmail.setError("Digite um email válido");
            return false;
        }else{
            edEmail.setError(null);
            return true;
        }
    }

    private boolean validaSenha(){
        edPassword = (EditText) findViewById(R.id.editText_Password);
        String passwordInput = edPassword.getText().toString().trim();
        if (passwordInput.isEmpty()){
            edPassword.requestFocus();
            edPassword.setError("Campo deve ser preenchido");
            return false;
        }else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()){
            edPassword.requestFocus();
            edPassword.setError("Senha inválida. Senha deve ser composta por números, letras maiúsculas e minúsculas e caracteres especias");
            return false;
        }else {
            edPassword.setError(null);
            return true;
        }
    }

    public void efetuarLogin(View view){

        edEmail = (EditText) findViewById(R.id.editText_Email);
        edPassword = (EditText) findViewById(R.id.editText_Password);

        boolean res = validaEmail();
        boolean resultado = validaSenha();

        if (res && resultado) {
            usuarioModel = usuarioRepository.buscar(edEmail.getText().toString());
            if (edEmail.getText().toString().equalsIgnoreCase(usuarioModel.getEmail()) && edPassword.getText().toString().equalsIgnoreCase(usuarioModel.getSenha())) {
                Toast.makeText(LoginActivity.this, "Login efetuado com sucesso", Toast.LENGTH_LONG).show();
                String txt;
                txt = edEmail.getText().toString();
                Intent intent = new Intent(this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("edEmail", txt);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "Usuário não cadastrado", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void exibirCadastro(View view){
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    public void recuperarSenha(View view){
        Intent intent = new Intent(this, recuperarSenhaActivity.class);
        startActivity(intent);
    }


}
