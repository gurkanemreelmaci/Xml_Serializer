package com.example.xml_serializer;

import android.util.Xml;
import org.xmlpull.v1.XmlSerializer;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by Gürkan Emre Elmacı
 *
 * xml üzerine yazma işlemleri daha dinamik bir yapıya
 * sahip olması amacıyla oluşturulmuştur.
 *
 *
 * oluşturulan sınıf dosya üzerinden okuma yapması nedeniyle
 * IOException throw etmektedir.
 */
public class XmlOperation{
    private final XmlSerializer xmlSerializer;
    private final StringWriter writer;
    private final FileWriter fileWriter;

    public XmlOperation(String path) throws IOException {
        xmlSerializer = Xml.newSerializer();
        //xmlSerializer.endDocument();
        writer = new StringWriter();
        fileWriter = new FileWriter(path,true);
        xmlSerializer.setOutput(writer);
        xmlSerializer.startDocument("UTF-8", true);
    }

    public void setPrefix(String prefix) throws IOException {
        xmlSerializer.setPrefix("",prefix);

    }

    public void startTag(String startTag) throws IOException {
        xmlSerializer.startTag("",startTag);
    }

    public void endTag(String endTag) throws IOException {
        xmlSerializer.endTag("",endTag);
    }

    public void addValue(String value) throws IOException {
        xmlSerializer.text(value);
    }

    public void addValueWithTag(String value,String tag) throws IOException {
        startTag(tag);
        xmlSerializer.text(value);
        endTag(tag);
    }

    public void writeToXmlFile() throws IOException {
        xmlSerializer.endDocument();
        fileWriter.write(writer.toString());
        fileWriter.flush();
        fileWriter.close();
    }
}
