package com.luxoft.utils;

import com.luxoft.entity.model.Customer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

/**
 * Created by iivaniv on 07.10.2015.
 */

public class CustomerParser extends DefaultHandler {

    private Customer customer;

//    private void parseDocument() {
//        // parse
//        SAXParserFactory factory = SAXParserFactory.newInstance();
//        try {
//            SAXParser parser = factory.newSAXParser();
//            parser.parse(bookXmlFileName, this);
//        } catch (ParserConfigurationException e) {
//            System.out.println("ParserConfig error");
//        } catch (SAXException e) {
//            System.out.println("SAXException : xml not well formed");
//        } catch (IOException e) {
//            System.out.println("IO error");
//        }
//    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
    }
}
