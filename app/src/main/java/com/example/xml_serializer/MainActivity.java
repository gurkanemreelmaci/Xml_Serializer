package com.example.xml_serializer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * created by Gürkan Emre Elmacı
 *
 * oluşturulan projede xml dosyalarından verilerin okunmasoı ve okunana verilerin dinamik
 * bir şwkilde ekrana bvbasılması ve xml dosyalarından okunan verilerin bir dosya
 * içerisinde birleştirilmeis işlemlerini yapmaktadır.
 *
 * !!!!xml işlemleri statik bir yapı için ayarlanmıştır!!!!
 */


public class MainActivity extends AppCompatActivity {

    private LinearLayout debirifingLayout;
    private final PRE pre = new PRE();
    private final ArrayList<LinearLayout> preLayoutList = new ArrayList<>();
    private final ArrayList<PRE> preArrayList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addPreLayout();
        debirifingLayout = findViewById(R.id.debirifing_linearLayout);
        getDebirifingItem();
    }

    // debirifing dosyası üzerinden verilerin okunması ve ekrana basılması
    // işlemini sağlayan fonksiyon

    private void getDebirifingItem() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream is = getAssets().open("XML PATH");
            Document document = db.parse(is);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("XML STATRING TAG");
            // document builder ile okunan veriler liste şeklinde erişilebilmesi
            // amacıyla belirlenen tag'e sahip elementler listeye aktarılır
            Node elemNode = nodeList.item(0);
            if (elemNode.getNodeType() == Node.ELEMENT_NODE) {
                NodeList nodeList1 = elemNode.getChildNodes();
                String tempText = "";
                for (int i = 0; i < nodeList1.getLength(); i++) {
                    // veriler sıralı bir şekilde okunur ve hem xml dosyasına
                    // hem de ekrana aktarılır
                    // xml içerisinde bazı child değerleri boş gelmektedir
                    // burda bu senorya kontrol ediliyor
                    // String s = nodeList1.item(i).getNodeName();
                    if (Objects.equals(nodeList1.item(i).getNodeName(), "PRE")) {
                        Node node = nodeList1.item(i);
                        getPreItem(node);
                    }
                }
                ((TextView) debirifingLayout.getChildAt(0)).setText(tempText);

                for (int i = 0; i < preArrayList.size(); i++) {
                    preLayoutList.get(i).setVisibility(View.VISIBLE);
                    ((TextView) preLayoutList.get(i).getChildAt(0)).setText(preArrayList.get(i).toString());
                }
            }
        } catch (Exception e) {
            Log.e("XmlWritingEror", e.getMessage());
        }
    }

    private void getPreItem(Node node){
        Element element = (Element) node;
        // alınan element üzerinden verilere erişilecek yer
        element.getElementsByTagName("DATA XML TAG").item(0/*VERİ İÇERİSİNDE ALT ÖZELLİKLER VAR İSE BURADAN ERİŞİLECEK*/).getTextContent()

        preArrayList.add(pre);
    }
    // ana activity xml dosyası içerisinde oluşturulan layout yapıları
    // bir diziye aktarılıyor.
    private void addPreLayout() {
        LinearLayout preLayout = findViewById(R.id.pre1_linearLayout);
        preLayoutList.add(preLayout);
        preLayout = findViewById(R.id.pre2_linearLayout);
        preLayoutList.add(preLayout);
        preLayout = findViewById(R.id.pre3_linearLayout);
        preLayoutList.add(preLayout);
        preLayout = findViewById(R.id.pre4_linearLayout);
        preLayoutList.add(preLayout);
        preLayout = findViewById(R.id.pre5_linearLayout);
        preLayoutList.add(preLayout);
        preLayout = findViewById(R.id.pre6_linearLayout);
        preLayoutList.add(preLayout);
        preLayout = findViewById(R.id.pre7_linearLayout);
        preLayoutList.add(preLayout);
        preLayout = findViewById(R.id.pre8_linearLayout);
        preLayoutList.add(preLayout);
        preLayout = findViewById(R.id.pre9_linearLayout);
        preLayoutList.add(preLayout);
        preLayout = findViewById(R.id.pre10_linearLayout);
        preLayoutList.add(preLayout);

    }
}
