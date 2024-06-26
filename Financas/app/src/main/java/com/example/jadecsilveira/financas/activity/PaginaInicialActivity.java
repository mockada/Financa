package com.example.jadecsilveira.financas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jadecsilveira.financas.R;
import com.example.jadecsilveira.financas.adapter.SaldoAdapter;
import com.example.jadecsilveira.financas.dao.DatabaseHelper;
import com.example.jadecsilveira.financas.util.Constantes;
import com.example.jadecsilveira.financas.util.MetodosComuns;
import com.example.jadecsilveira.financas.vo.SaldoVO;

import java.util.ArrayList;

public class PaginaInicialActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView gridSaldos;
    SaldoAdapter adapter;
    ArrayList<SaldoVO> saldos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
//        this.deleteDatabase(Constantes.BANCO_DE_DADOS);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        gridSaldos = (ListView) findViewById(R.id.gridSaldos);

        DatabaseHelper db = new DatabaseHelper(this);
        saldos = db.getSaldos();

        TextView total = (TextView) findViewById(R.id.tvTotal);

        if(!saldos.isEmpty()){
            View header = (View) getLayoutInflater().inflate(R.layout.header_saldos, null);
            gridSaldos.addHeaderView(header);
            total.setText("R$ " + MetodosComuns.convertToDoubleView(saldos.get(saldos.size()-1).getValor()));
        }else{
            total.setText("R$ 0,00");
        }
        adapter = new SaldoAdapter(this, saldos);
        gridSaldos.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pagina_inicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sair) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;

        if (id == R.id.nav_despesas) {
            intent = new Intent(PaginaInicialActivity.this, DespesaActivity.class);
            startActivity(intent);
            this.finish();
        } else if (id == R.id.nav_rendimentos) {
            intent = new Intent(PaginaInicialActivity.this, RendimentoActivity.class);
            startActivity(intent);
            this.finish();
        }
        PaginaInicialActivity.this.overridePendingTransition(0, 0);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
