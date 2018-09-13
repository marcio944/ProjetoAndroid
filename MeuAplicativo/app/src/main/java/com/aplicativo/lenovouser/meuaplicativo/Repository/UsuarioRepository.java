package com.aplicativo.lenovouser.meuaplicativo.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.aplicativo.lenovouser.meuaplicativo.Models.UsuarioModel;

public class UsuarioRepository extends SQLiteOpenHelper {

    private static final String DATABASE = "Aplicativo";
    private static final String TABELA = "usuario";
    private Context contexto;

    public UsuarioRepository(Context context) {
        super(context, DATABASE, null, 9);
        contexto = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String sql = "CREATE TABLE usuario (" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "email TEXT NOT NULL ," +
                    "senha TEXT NOT NULL," +
                    "CONSTRAINT email_unique UNIQUE(email));";
            db.execSQL(sql);
            Toast.makeText(UsuarioRepository.this.contexto, "Tabela criada com sucesso", Toast.LENGTH_LONG).show();
        }catch (SQLException ex){
            Toast.makeText(UsuarioRepository.this.contexto, "Erro ao criar tabela: "+ex, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(UsuarioModel usuarioModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", usuarioModel.getEmail());
        contentValues.put("senha", usuarioModel.getSenha());
        getWritableDatabase().insert(TABELA,null,contentValues);
        getWritableDatabase().close();
    }

    public UsuarioModel buscar(String email){
        UsuarioModel usuarioModel = new UsuarioModel();
        try {
            Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM usuario WHERE email = " + "'" + email + "'", null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                usuarioModel.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                usuarioModel.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
            }
        }catch (SQLException ex){
            Toast.makeText(UsuarioRepository.this.contexto, "Erro: "+ex, Toast.LENGTH_LONG).show();
        }
        return usuarioModel;
    }

    public void editar(UsuarioModel usuarioModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", usuarioModel.getEmail());
        contentValues.put("senha", usuarioModel.getSenha());
        getWritableDatabase().update("usuario", contentValues, "email = ?", new String[]{usuarioModel.getEmail()});
    }

}
