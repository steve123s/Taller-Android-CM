package com.example.daniel.taller3_ahorcado;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> datos = new ArrayList<>();
    String[] datosPalabra = new String[3];

    TextView categoriaTextView;
    TextView intentosTextView;
    TextView palabraTextView;
    ImageView hangmanImageView;

    TextView guessTextView;
    Button guessButton;
    Button restartButton;

    int intentos;
    String palabra;
    String palabraVacia = "";
    String[] letrasAdivinadas= new String[20];

    public String doInBackground() {

        String parsedStr = null;
        String xmlStr = null;
        URL downloadUrl = null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        BufferedReader reader = null;
        XmlPullParser parser = Xml.newPullParser();

        try {
            downloadUrl = new URL("https://www.serverbpw.com/cm/2019-1/hangman.php");
            connection = (HttpURLConnection) downloadUrl.openConnection();
            inputStream = connection.getInputStream();

            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            xmlStr = buffer.toString(); //Código xml convertido a cadena
            System.out.println(xmlStr);
            InputStream stream = new ByteArrayInputStream(xmlStr.getBytes(StandardCharsets.UTF_8)); //xml a leer
            System.out.println("parsing start");

            parser.setInput(stream, null);
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {
                    System.out.println(parser.getName());
                    //Parsing keys and strings
                    if (parser.getName().equals("key") || parser.getName().equals("string")) {
                        if (parser.getEventType() == XmlPullParser.START_TAG) {
                            //Almacenamos temporalmente el elemento
                            String itemText = parser.nextText();

                            //Código para almacenar el parseo
                            System.out.println("menu option: " + itemText);
                            datos.add(itemText);
                        }
                    }
                }
                eventType = parser.next();
            }
        } catch (MalformedURLException e) {
            Log.e("Malformed URL", "Error ", e);

        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }

        int productIndex = 0;
        int lineIndex = 0;
        //Loop through every element in data array
        for (String str_tmp : datos)   // using foreach
        {
            //Add product as a model object
            if (str_tmp.startsWith("PALABRA")) {
                //parseo
                datosPalabra[productIndex] = datos.get(lineIndex+1);
                datosPalabra[productIndex+1] = datos.get(lineIndex+2);
                datosPalabra[productIndex+2] = datos.get(lineIndex+3);
                productIndex++;
            }
            Log.e("Datos - " , str_tmp);
            lineIndex++;


        }

        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                Log.d("UI thread", "I am the UI thread");

                palabra = datosPalabra[1];
                for (int i = 0; i<palabra.length(); i++) {
                    palabraVacia = palabraVacia + "_ ";
                }

                intentos = Integer.parseInt(datosPalabra[2]);
                categoriaTextView.setText(datosPalabra[0]);
                palabraTextView.setText(palabraVacia);
                System.out.println(palabra);
                intentosTextView.setText("Intentos: " + Integer.toString(intentos));

                updateImage();




                guessButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // Code here executes on main thread after user presses button
                         if (intentos<=0) {
                             return;
                        }

                        if (palabra.toLowerCase().contains(guessTextView.getText().toString().toLowerCase())) {
                            if (guessTextView.getText().toString().equals("")) { return; }
                            String letra = guessTextView.getText().toString();
                            String palabraTemporal = "";
                            for (int i = 0; i<palabra.length(); i++) {
                                if (palabra.toLowerCase().contains(letrasAdivinadas[i].toLowerCase())) {
                                    palabraTemporal = palabraTemporal + letrasAdivinadas[i] + " ";
                                } else if (letra.charAt(0) == palabra.charAt(i)) {
                                    palabraTemporal = palabraTemporal + letra + " ";
                                    letrasAdivinadas[i] = letra;
                                } else {
                                    palabraTemporal = palabraTemporal + "_ ";

                                }
                            }
                            palabraVacia = palabraTemporal;
                            palabraTextView.setText(palabraTemporal);
                            guessTextView.setText("");

                            if (!palabraTemporal.toLowerCase().contains("_")) {
                                categoriaTextView.setText("¡Ganaste!");
                                guessButton.setEnabled(false);
                                return;
                            }
                        } else {
                            intentos = intentos - 1;
                            intentosTextView.setText("Intentos: " + Integer.toString(intentos));
                            guessTextView.setText("");
                            if (intentos == 0) {
                                categoriaTextView.setText("¡Perdiste!");
                                guessButton.setEnabled(false);
                                updateImage();
                                return;
                            }
                        }
                        updateImage();
                    }
                });




            }
        });
        return parsedStr;
    }

    public void downloadData() {
        Thread myThread = new Thread(new DownloadImagesThread());
        myThread.start();
    }

    public class DownloadImagesThread implements Runnable {
        public void run() {
            System.out.println(doInBackground());
        }
    }

    public void updateImage() {
        switch (intentos) {
            case 0:
                hangmanImageView.setImageResource(R.drawable.hangman0);
                break;
            case 1:
                hangmanImageView.setImageResource(R.drawable.hangman1);
                break;
            case 2:
                hangmanImageView.setImageResource(R.drawable.hangman2);
                break;
            case 3:
                hangmanImageView.setImageResource(R.drawable.hangman3);
                break;
            case 4:
                hangmanImageView.setImageResource(R.drawable.hangman4);
                break;
            case 5:
                hangmanImageView.setImageResource(R.drawable.hangman5);
                break;
            case 6:
                hangmanImageView.setImageResource(R.drawable.hangman6);
                break;
            default:
                hangmanImageView.setImageResource(R.drawable.hangman6);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoriaTextView = findViewById(R.id.categoria);
        palabraTextView = findViewById(R.id.palabra);
        intentosTextView = findViewById(R.id.intentos);
        hangmanImageView = findViewById(R.id.imageView);
        guessTextView = findViewById(R.id.letra);
        guessButton = findViewById(R.id.guessButton);
        restartButton = findViewById(R.id.restartButton);

        guessTextView.setText("");
        for (int i = 0; i<letrasAdivinadas.length; i++) {
            letrasAdivinadas[i] = "*";
        }

        downloadData();



        restartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                recreate();
            }
        });

    }
}
