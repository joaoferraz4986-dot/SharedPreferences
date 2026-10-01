package com.example.sharedpreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class HomeActivity extends AppCompatActivity {

    private static final String ARQUIVO_PREFS = "app_prefs";
    private static final String CHAVE_NOME = "nome";
    private static final String CHAVE_EMAIL = "email";
    private static final String CHAVE_HORA = "hora";
    private static final String CHAVE_DATA = "data";

    TextView textoBoasVindas, textoEmail;
    Button botaoNix;
    SharedPreferences preferencias;

    @Override
    protected void onCreate( Bundle estadoSalvo ) {

        super.onCreate( estadoSalvo );
        setContentView( R.layout.activity_home );

        textoBoasVindas = findViewById( R.id.txtWelcome );
        textoEmail = findViewById( R.id.txtEmailDisplay );
        botaoNix = findViewById( R.id.btnGoToNix );

        preferencias = getSharedPreferences( ARQUIVO_PREFS, MODE_PRIVATE );

        carregarDados();
        configurarBarraNavegacao();

        botaoNix.setOnClickListener( v -> {

            Intent intencao = new Intent( Intent.ACTION_VIEW, Uri.parse( "https://nixos.org/" ) );
            startActivity( intencao );

        });

        configurarEfeitoBlur();

    }

    private void configurarEfeitoBlur() {

        BlurView vistaBlur = findViewById( R.id.blurViewHome );
        View decorView = getWindow().getDecorView();
        ViewGroup viewRaiz = decorView.findViewById( android.R.id.content );
        Drawable fundoJanela = decorView.getBackground();

        vistaBlur.setupWith( viewRaiz, new RenderScriptBlur( this ) )
                .setFrameClearDrawable( fundoJanela )
                .setBlurRadius( 20f );

    }

    private void carregarDados() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2026, Calendar.SEPTEMBER, 29);
        String dataAtual = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"))
                .format(calendar.getTime());

        String horaAtual = new SimpleDateFormat("HH:mm", new Locale("pt", "BR"))
                .format(new Date());

        String nome = preferencias.getString( CHAVE_NOME, "Usuário" );
        String email = preferencias.getString( CHAVE_EMAIL, "Não informado" );
        String data = preferencias.getString( CHAVE_DATA, dataAtual );
        String hora = preferencias.getString( CHAVE_HORA, horaAtual );

        textoBoasVindas.setText( "Bem-vindo " + nome + ", logado em " + data + " na hora "  + hora );
        textoEmail.setText( "do email: " + email );

    }

    private void configurarBarraNavegacao() {

        ImageButton botaoHome = findViewById( R.id.btnNavHome );
        ImageButton botaoNavLogin = findViewById( R.id.btnNavLogin );
        ImageButton botaoNavCadastro = findViewById( R.id.btnNavCadastro );
        ImageButton botaoNavLogout = findViewById( R.id.btnNavLogout );

        boolean logado = preferencias.contains( CHAVE_NOME );

        if ( logado ) {
            botaoNavLogin.setVisibility( View.GONE );
            botaoNavCadastro.setVisibility( View.GONE );
            botaoNavLogout.setVisibility( View.VISIBLE );
        } else {
            botaoNavLogin.setVisibility( View.VISIBLE );
            botaoNavCadastro.setVisibility( View.VISIBLE );
            botaoNavLogout.setVisibility( View.GONE );
        }

        botaoNavLogin.setOnClickListener( v -> {
            startActivity( new Intent( HomeActivity.this, MainActivity.class ) );
        });

        botaoNavLogout.setOnClickListener( v -> {
            sairDaConta();
        });

        botaoNavCadastro.setOnClickListener( v -> {
            startActivity( new Intent( HomeActivity.this, CadastroActivity.class ) );
        });

        botaoHome.setOnClickListener( v -> {
        });

    }

    private void sairDaConta() {

        preferencias.edit().clear().apply();

        Intent intencao = new Intent( HomeActivity.this, MainActivity.class );

        intencao.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity( intencao );

    }
}
