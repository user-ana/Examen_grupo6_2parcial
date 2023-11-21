package com.example.pm2examengrupo6;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.pm2examengrupo6.Config.Persona;
import com.example.pm2examengrupo6.Config.RestApiMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactosActivity extends AppCompatActivity {

    private List<Persona> Lista = new ArrayList<>();
    Button btnActualizar, btnAtras, btnEliminar, btnMapa;
    EditText txtBuscarNombre;
    private String idCont;
    ListView listaContactos;
    List<Persona> contactosLista;
    Persona nombre;
    ArrayList<String> arrayContactos;
    ArrayAdapter adp;

    int previousPosition = 1;
    int count = 1;
    long previousMil = 0;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);


        btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnAtras = (Button) findViewById(R.id.btnAtras);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnMapa = (Button) findViewById(R.id.btnUbicacion);
        txtBuscarNombre = (EditText) findViewById(R.id.txtBuscarNombre);
        listaContactos = (ListView) findViewById(R.id.listaContactos);
        contactosLista = new ArrayList<>();
        arrayContactos = new ArrayList<String>();

        mostrarContactos();


        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });


        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                // DISPARA LA CONSULTA
                alertDialogBuilder.setTitle("Eliminar Usuario");
                // MANDA EL MENSAJE
                alertDialogBuilder
                        .setMessage("¿Está seguro de eliminar el contato " + nombre.getNombre() + " ?")
                        .setCancelable(false)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // DE DAR CLICK EN SI LLA MA EL METODO eliminarPersona
                                eliminarPersona(String.valueOf(nombre.getId()));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // SI PRESIONA NO EL DIALOG SE CIERRA Y NO HACE NADA
                                dialog.cancel();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });


        txtBuscarNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    BuscarnNombre(txtBuscarNombre.getText().toString());
                    if (txtBuscarNombre.getText().toString().equals("")) {
                        mostrarContactos();
                    }
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Valor invalido", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityMapa.class);
                intent.putExtra("latitud", nombre.getLatitud());
                intent.putExtra("longitud", nombre.getLongitud());
                startActivity(intent);
            }
        });

        //-- lista evento click
        listaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (previousPosition == i) {
                    count++;
                    if (count == 2 && System.currentTimeMillis() - previousMil <= 1000) {
                        //Toast.makeText(getApplicationContext(), "Doble Click ",Toast.LENGTH_LONG).show();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setTitle("Acción");
                        alertDialogBuilder
                                .setMessage("¿Desea ir a la Ubicacion de " + nombre.getNombre() + "?")
                                .setCancelable(false)
                                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // se procede a ir a la ubicacion seteando los parametros
                                        try {
                                            Intent intent = new Intent(getApplicationContext(), ActivityMapa.class);
                                            intent.putExtra("latitud", nombre.getLatitud());
                                            intent.putExtra("longitud", nombre.getLongitud());
                                            startActivity(intent);

                                        } catch (Exception ex) {
                                            ex.toString();
                                        }
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        count = 1;
                    }
                } else {
                    previousPosition = i;
                    count = 1;
                    previousMil = System.currentTimeMillis();
                    //un clic
                    nombre = contactosLista.get(i);//lleno la lista de contacto
                    setSelection();//obtengo el usuario seleccionado de la lista
                    //Toast.makeText(getApplicationContext(),"usuario id: "+usuario.getId(), Toast.LENGTH_SHORT).show();
                }
            }


        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (idCont != null) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("idCont", String.valueOf(idCont));
                    startActivity(intent);
                } else {
                    dialog_seleccione();
                }
            }
        });
    }

        private void dialog_seleccione() {
            new AlertDialog.Builder(this)
                    .setTitle("Aviso")
                    .setMessage("Seleccione un contacto de la lista")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();






    } // fin Principal
    
    

    private void mostrarContactos() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, RestApiMethods.EndPointGetList, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            arrayContactos.clear(); // Limpiar la lista de usuarios antes de comenzar a listar

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject contacto = response.getJSONObject(i);
                                Persona nombre = new Persona(
                                        contacto.getInt("id"),
                                        contacto.getString("nombre"),
                                        contacto.getInt("telefono"),
                                        contacto.getString("latitud"),
                                        contacto.getString("longitud"),
                                        contacto.getString("foto")
                                );

                                contactosLista.add(nombre);
                                arrayContactos.add(nombre.getNombre() + ' ' + '/' + ' ' + nombre.getTelefono());
                            }

                            adp = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_checked, arrayContactos);
                            listaContactos.setAdapter(adp);

                        } catch (JSONException ex) {
                            Toast.makeText(getApplicationContext(), "Error al procesar la respuesta: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en la solicitud: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonArrayRequest);
    }


    //METODO PARA BUSCAR POR NOMBRE
    private void BuscarnNombre(String dato) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, RestApiMethods.EndPointGetPerson + dato,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray contactoArray = jsonObject.getJSONArray("contacto");

                            arrayContactos.clear();//limpiar la lista de usuario antes de comenzar a buscar

//                            if ()){
//                                Toast.makeText(getApplicationContext(), "No se encontro el valor", Toast.LENGTH_SHORT).show();
//                           }

                            for (int i = 0; i < contactoArray.length(); i++) {
                                JSONObject RowUsuario = contactoArray.getJSONObject(i);
                                Persona persona = new Persona(RowUsuario.getInt("id"),
                                        RowUsuario.getString("nombre"),
                                        RowUsuario.getInt("telefono"),
                                        RowUsuario.getString("latitud"),
                                        RowUsuario.getString("longitud"),
                                        RowUsuario.getString("firma")
                                );
                                contactosLista.add(persona);
                                arrayContactos.add(persona.getNombre() + ' ' + persona.getTelefono());
                            }

                            adp = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_checked, arrayContactos);
                            listaContactos.setAdapter(adp);

                        } catch (JSONException ex) {
                            Toast.makeText(getApplicationContext(), "mensaje " + ex, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "mensaje "+error, Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);

    }

    private void eliminarPersona(String dato) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                RestApiMethods.EndPointDeletePerson + dato,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Usuario eliminado exitosamente", Toast.LENGTH_SHORT).show();
                        mostrarContactos();
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(getApplicationContext(), "mensaje "+error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void setSelection() {


        Intent intent = new Intent(getApplicationContext(),ContactosActivity.class);
        intent.putExtra("id", nombre.getId()+"");
        intent.putExtra("nombre", nombre.getNombre());
        intent.putExtra("telefono", nombre.getTelefono()+"");
        intent.putExtra("latitud", nombre.getLatitud());
        intent.putExtra("longitud", nombre.getLongitud());
        intent.putExtra("foto", nombre.getFoto()+"").toString();

        //startActivity(intent);
    }


}