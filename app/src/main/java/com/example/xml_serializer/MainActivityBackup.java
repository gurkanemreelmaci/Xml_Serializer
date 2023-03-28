package com.example.xml_serializer;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivityBackup extends AppCompatActivity {

    private LinearLayout mainLinearLayout;
    private LinearLayout debirifingLayout;
    private LinearLayout preLayout;
    private PRE pre;
    private final ArrayList<LinearLayout> preLayoutList = new ArrayList<>();
    private final ArrayList<PRE> preArrayList = new ArrayList<>();
    private XmlOperation xmlOperation;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addPreLayout();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        // xml işlemlerini yapmak için kullanılacak sınıf oluşturuluyor
        try {
            xmlOperation = new XmlOperation(getApplicationContext().getFilesDir()+"/debirifing "+currentDateandTime+".xml");
        } catch (IOException e) {
            Log.e("XmlWritingError",e.getMessage());
        }
        mainLinearLayout = findViewById(R.id.base_linearLayout);
        debirifingLayout = findViewById(R.id.debirifing_linearLayout);
        writeXmlDebirifing();
        writeXmlPredict();

    }

    // debirifing dosyası üzerinden verilerin okunması ve ekrana basılması
    // işlemini sağlayan fonksiyon

    private void writeXmlDebirifing() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream is = getAssets().open("debriefing_2023_01_11_11_06_00.612.xml");
            Document document = db.parse(is);
            document.getDocumentElement().normalize();
            xmlOperation.startTag("RELEASE");
            NodeList nodeList = document.getElementsByTagName("RELEASE");
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
                    if ("#text" != nodeList1.item(i).getNodeName()) {
                        xmlOperation.addValueWithTag(nodeList1.item(i).getTextContent(), nodeList1.item(i).getNodeName());
                        tempText = tempText + nodeList1.item(i).getNodeName() + "= " + nodeList1.item(i).getTextContent() + "\n";
                    }
                }
                ((TextView) debirifingLayout.getChildAt(0)).setText(tempText);
            }
        } catch (Exception e) {
            Log.e("XmlWritingEror", e.getMessage());
        }
    }
    // pre dosyası üzerinden verilerin okunması ve ekrana basılması
    // işlemini sağlayan fonksiyon
    private void writeXmlPredict() {
        pre = new PRE();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream is = getAssets().open("pre_2_som_deneme.xml");
            Document document = db.parse(is);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("PRE");
            xmlOperation.startTag("PRES");
            // document builder ile okunan veriler liste şeklinde erişilebilmesi
            // amacıyla belirlenen tag'e sahip elementler listeye aktarılır
            for (int i = 0; i < nodeList.getLength(); i++) {
                pre = new PRE();
                xmlOperation.startTag("PRE");
                Node nodeElement = nodeList.item(i);

                // burda liste üzerinden gelen elemanlar sıralı bir şekilde değil belirlenen değerler şeklinde
                // alınmaktadır
                if (nodeElement.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodeElement;
                    pre.setPRE_INDEX(Integer.parseInt(element.getElementsByTagName("PRE_INDEX").item(0).getTextContent()));
                    xmlOperation.addValueWithTag(element.getElementsByTagName("PRE_INDEX").item(0).getTextContent(),"PRE_INDEX");

                    pre.setLAT(Double.parseDouble(element.getElementsByTagName("LAT").item(0).getTextContent()));
                    xmlOperation.addValueWithTag(element.getElementsByTagName("LAT").item(0).getTextContent(),"LAT");

                    pre.setLON(Double.parseDouble(element.getElementsByTagName("LON").item(0).getTextContent()));
                    xmlOperation.addValueWithTag(element.getElementsByTagName("LON").item(0).getTextContent(),"LON");

                    pre.setELEV(Double.parseDouble(element.getElementsByTagName("ELEV").item(0).getTextContent()));
                    xmlOperation.addValueWithTag(element.getElementsByTagName("ELEV").item(0).getTextContent(),"ELEV");

                    pre.setIMP_ANG(Double.parseDouble(element.getElementsByTagName("IMP_ANG").item(0).getTextContent()));
                    xmlOperation.addValueWithTag(element.getElementsByTagName("IMP_ANG").item(0).getTextContent(),"IMP_ANG");

                    pre.setDIRECT_ATTACK(Boolean.parseBoolean(element.getElementsByTagName("DIRECT_ATTACK").item(0).getTextContent()));
                    xmlOperation.addValueWithTag(element.getElementsByTagName("DIRECT_ATTACK").item(0).getTextContent(),"DIRECT_ATTACK");

                    pre.setIMP_AZ(Double.parseDouble(element.getElementsByTagName("IMP_AZ").item(0).getTextContent()));
                    xmlOperation.addValueWithTag(element.getElementsByTagName("IMP_AZ").item(0).getTextContent(),"IMP_AZ");

                    pre.setROB(Double.parseDouble(element.getElementsByTagName("ROB").item(0).getTextContent()));
                    xmlOperation.addValueWithTag(element.getElementsByTagName("ROB").item(0).getTextContent(),"ROB");

                    pre.setIR_MSL_ALT(Double.parseDouble(element.getElementsByTagName("IR_MSL_ALT").item(0).getTextContent()));
                    xmlOperation.addValueWithTag(element.getElementsByTagName("IR_MSL_ALT").item(0).getTextContent(),"IR_MSL_ALT");

                    pre.setIRMAK_HEADING(Double.parseDouble(element.getElementsByTagName("IRMAK_HEADING").item(0).getTextContent()));
                    xmlOperation.addValueWithTag(element.getElementsByTagName("IRMAK_HEADING").item(0).getTextContent(),"IRMAK_HEADING");

                    pre.setIRMAK_LEN(Double.parseDouble(element.getElementsByTagName("IRMAK_LEN").item(0).getTextContent()));
                    xmlOperation.addValueWithTag(element.getElementsByTagName("IRMAK_LEN").item(0).getTextContent(),"IRMAK_LEN");
                }
                preArrayList.add(pre);
                xmlOperation.endTag("PRE");
            }
            xmlOperation.endTag("PRES");
            xmlOperation.endTag("RELEASE");
            xmlOperation.writeToXmlFile();
            for (int i = 0; i < preArrayList.size(); i++) {
                preLayoutList.get(i).setVisibility(View.VISIBLE);
                ((TextView) preLayoutList.get(i).getChildAt(0)).setText(preArrayList.get(i).toString());
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            Log.e("XmlWritingEror", e.getMessage());
        }

    }
    // ana activity xml dosyası içerisinde oluşturulan layout yapıları
    // bir diziye aktarılıyor.
    private void addPreLayout() {
        preLayout = findViewById(R.id.pre1_linearLayout);
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
