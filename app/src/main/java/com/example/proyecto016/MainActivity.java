package com.example.proyecto016;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText et1,et2; //definnimos los atributos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Relaizamos el llamado de las id´s que se encuentran en el activity_main
        et1=findViewById(R.id.et1);//findViewById recorre todo el arbol jererquico del layout xml en busqueda del del recurso requerido en este caso el et1.
        et2=findViewById(R.id.et2);
    }

    public void grabar(View v) {
        String nomarchivo=et1.getText().toString();//asigno nomarchivo a la convercion a String de los valores retornados por et1.
        nomarchivo=nomarchivo.replace('/','-');//replace. Método que nos permite cambiar un carácter por otro dentro de una cadena
        try {//try es una sentencia que desea prever excepciones
            //en caso tal de que el mismo sistema me genere un error o no se pueda ejecutar. Este condicional me permita ejecutar lo que esta dentro de esa condiccion
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(nomarchivo, Activity.MODE_PRIVATE));
            //1.crear un odjeto de la clase OutputStreamWriter
            //2.al constructor se le envian los datos que retora el metodo OutputStreamWriter.
            //3.se le pasa el parametro del nombrre del archivo y el modo de apertura.

            //identificamos que el metodo OutputStreamWriter es un puente ente flujo de caracteres y de byte lo que implica un llamado al metodo write().
            //lo que permite guardar en un Bufer.
            archivo.write(et2.getText().toString());
            archivo.flush();//son los flujos de salida lo que quiere decir que y si los datos o bytes fueron almacenasdos en el fluter.
            archivo.close();//este metodo arroja un IOException o que se produce algun error

            //Observaciones: al almacenar datos en un búfer ayada o se utiliza principalmente para mejorar el rendimineto de entrada y de salida E/S
        } catch (IOException e) {
        }
        Toast t = Toast.makeText(this, "Los datos fueron grabados",
                Toast.LENGTH_SHORT);
        t.show();//enviamos una notificacion de
        et1.setText("");//establece los valores
        et2.setText("");
    }

    public void recuperar(View v) {
        String nomarchivo=et1.getText().toString();//se recupera el editTextque ingresa la fecha al operador de busqueda
        nomarchivo=nomarchivo.replace('/','-');//reemplaza las barra por guiones
        boolean enco = false;
        String[] archivos = fileList();//se obtine la lista de todos los archivos guardados
        for (int f = 0; f < archivos.length; f++)
            if (nomarchivo.equals(archivos[f]))
                enco= true;
        if (enco==true) {
            try {
                InputStreamReader archivo = new InputStreamReader(
                        openFileInput(nomarchivo));
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();
                String todo = "";
                while (linea != null) {
                    todo = todo + linea + "\n";
                    linea = br.readLine();
                }
                br.close();
                archivo.close();
                et2.setText(todo);
            } catch (IOException e) {
            }
        } else
        {
            Toast.makeText(this,"No hay datos grabados para dicha fecha", Toast.LENGTH_LONG).show();
            et2.setText("");
        }
    }
}