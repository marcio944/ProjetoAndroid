package com.aplicativo.lenovouser.meuaplicativo.Usuário;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.aplicativo.lenovouser.meuaplicativo.Models.UsuarioModel;
import com.aplicativo.lenovouser.meuaplicativo.R;
import com.aplicativo.lenovouser.meuaplicativo.Repository.UsuarioRepository;

import java.util.regex.Pattern;

public class recuperarSenhaActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[a-zA-Z])" +
            "(?=.*[@#$%^&+=])" +
            "(?=\\S+$)" +
            ".{4,}" +
            "$");

    EditText edEmail;
    EditText edSenha;
    EditText edRepetirSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);
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
        edSenha = (EditText) findViewById(R.id.editText_Senha);
        String passwordInput = edSenha.getText().toString().trim();
        if (passwordInput.isEmpty()){
            edSenha.requestFocus();
            edSenha.setError("Campo deve ser preenchido");
            return false;
        }else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()){
            edSenha.requestFocus();
            edSenha.setError("Senha inválida. Senha deve ser composta por números, letras maiúsculas e minúsculas e caracteres especias");
            return false;
        }else {
            edSenha.setError(null);
            return true;
        }
    }

    private boolean validaRepetirSenha(){
        edRepetirSenha = (EditText) findViewById(R.id.editText_Repetir);
        edSenha = (EditText) findViewById(R.id.editText_Senha);
        if (edRepetirSenha.getText().toString().isEmpty()){
            edRepetirSenha.requestFocus();
            edRepetirSenha.setError("Campo deve ser preenchido");
            return false;
        }else if (edRepetirSenha.getText().toString().equals(edSenha.getText().toString())){
            return true;
        }else{
            edRepetirSenha.requestFocus();
            edRepetirSenha.setError("Senhas não correspondem");
            return false;
        }
    }

    public void recuperarSenha(View view){

        UsuarioModel usuarioModel = new UsuarioModel();
        UsuarioModel usuario = new UsuarioModel();
        UsuarioRepository usuarioRepository = new UsuarioRepository(this);

        edEmail = (EditText) findViewById(R.id.editText_Email);
        edSenha = (EditText) findViewById(R.id.editText_Senha);

        usuarioModel.setEmail(edEmail.getText().toString());
        usuarioModel.setSenha(edSenha.getText().toString());

        usuario = usuarioRepository.buscar(usuarioModel.getEmail());

        boolean resultadoEmail = validaEmail();
        boolean resultadoSenha = validaSenha();
        boolean resultadoRepetir = validaRepetirSenha();

        if (resultadoEmail && resultadoSenha && resultadoRepetir){
            if (usuarioModel.getEmail().equals(usuario.getEmail())) {
                usuarioRepository.editar(usuarioModel);
                Toast.makeText(recuperarSenhaActivity.this, "Senha recuperada com sucesso", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(recuperarSenhaActivity.this, "Email não cadastrado", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(recuperarSenhaActivity.this, "Erro ao recuperar senha", Toast.LENGTH_LONG).show();
        }

    }

}
