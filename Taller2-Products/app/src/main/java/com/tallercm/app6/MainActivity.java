package com.tallercm.app6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import  java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public int PRODUCT_MAX = 20;
    Product[] products = new Product[PRODUCT_MAX];

    GridAdapter gridAdapter = new GridAdapter(this, products);
    ArrayList<String> datos = new ArrayList<>();

    public String doInBackground() {

        String parsedStr = null;
        String xmlStr = null;
        URL downloadUrl = null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        BufferedReader reader = null;
        XmlPullParser parser = Xml.newPullParser();

        try {
            downloadUrl = new URL("https://www.serverbpw.com/cm/2019-1/products.php");
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
            if (str_tmp.startsWith("PRODUCTO")) {
                products[productIndex].setNumProd(productIndex+1);
                products[productIndex].setId(datos.get(lineIndex+1));
                products[productIndex].setTitle(datos.get(lineIndex+2));
                products[productIndex].setDescription(datos.get(lineIndex+3));
                products[productIndex].setImageUrl("https://www.serverbpw.com/cm/2019-1/" + products[productIndex].getId() + ".png");
                Log.e("producto creado - " , String.valueOf(productIndex+1));
                Log.e("Url - " , products[productIndex].getImageUrl());
                productIndex++;
            }
            Log.e("Datos - " , str_tmp);
            lineIndex++;

            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");

                    gridAdapter.notifyDataSetChanged();
                }
            });
        }
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i<products.length; i++) {
            products[i] = new Product(i, "", "", "","");
        }

        downloadData();

        final GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(gridAdapter);

        //Cargar segundo Activity
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (products[position].getTitle() != "") {
                    Intent extras = new Intent(MainActivity.this, Main2Activity.class);
                    extras.putExtra("image", products[position].getImageUrl());
                    extras.putExtra("desc", products[position].getDescription());
                    startActivity(extras);
                }
            }
        });
    }
}