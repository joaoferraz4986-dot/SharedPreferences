package com.example.sharedpreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class CadastroActivity extends AppCompatActivity {

    private static final String ARQUIVO_PREFS = "app_prefs";
    private static final String CHAVE_NOME = "nome";
    private static final String CHAVE_EMAIL = "email";
    private static final String CHAVE_SENHA = "senha";
    private static final String CHAVE_DATA = "data";
    private static final String CHAVE_HORA = "hora";

    Button botaoCadastrar;
    EditText campoNome, campoEmail, campoSenha;
    SharedPreferences preferencias;

    @Override
    protected void onCreate( Bundle estadoSalvo ) {

        super.onCreate( estadoSalvo );
        setContentView( R.layout.activity_cadastro );

        botaoCadastrar = findViewById( R.id.btnCadastrar );
        campoNome = findViewById( R.id.edtNomeCadastro );
        campoEmail = findViewById( R.id.edtEmailCadastro );
        campoSenha = findViewById( R.id.edtSenhaCadastro );

        preferencias = getSharedPreferences( ARQUIVO_PREFS, MODE_PRIVATE );

        configurarBarraNavegacao();

        botaoCadastrar.setOnClickListener( v -> {

            String nome = campoNome.getText().toString().trim();
            String email = campoEmail.getText().toString().trim();
            String senha = campoSenha.getText().toString().trim();

            Calendar calendar = Calendar.getInstance();
            calendar.set(2026, Calendar.SEPTEMBER, 29);
            String data = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"))
                    .format(calendar.getTime());

            String hora = new SimpleDateFormat("HH:mm", new Locale("pt", "BR"))
                    .format(new Date());

            if ( nome.isEmpty() || email.isEmpty() || senha.isEmpty() ) {
                return;
            }

            SharedPreferences.Editor editor = preferencias.edit();

            editor.putString( CHAVE_NOME, nome );
            editor.putString( CHAVE_EMAIL, email );
            editor.putString( CHAVE_SENHA, senha );
            editor.putString( CHAVE_DATA, data );
            editor.putString( CHAVE_HORA, hora );

            editor.apply();

            Intent intencao = new Intent( CadastroActivity.this, HomeActivity.class );
            startActivity( intencao );
            finish();

        });

        configurarEfeitoBlur();

    }

    private void configurarEfeitoBlur() {

        BlurView vistaBlur = findViewById( R.id.blurViewCadastro );
        View decorView = getWindow().getDecorView();
        ViewGroup viewRaiz = decorView.findViewById( android.R.id.content );
        Drawable fundoJanela = decorView.getBackground();

        vistaBlur.setupWith( viewRaiz, new RenderScriptBlur( this ) )
                .setFrameClearDrawable( fundoJanela )
                .setBlurRadius( 20f );

    }

    private void configurarBarraNavegacao() {

        ImageButton botaoHome = findViewById( R.id.btnNavHome );
        ImageButton botaoNavLogin = findViewById( R.id.btnNavLogin );
        ImageButton botaoNavCadastro = findViewById( R.id.btnNavCadastro );
        ImageButton botaoNavLogout = findViewById( R.id.btnNavLogout );

        botaoNavLogin.setVisibility( View.VISIBLE );
        botaoNavCadastro.setVisibility( View.VISIBLE );
        botaoNavLogout.setVisibility( View.GONE );

        botaoHome.setOnClickListener( v -> {
            startActivity( new Intent( CadastroActivity.this, HomeActivity.class ) );
        });

        botaoNavLogin.setOnClickListener( v -> {
            startActivity( new Intent( CadastroActivity.this, MainActivity.class ) );
        });

        botaoNavCadastro.setOnClickListener( v -> {
        });

    }
}
