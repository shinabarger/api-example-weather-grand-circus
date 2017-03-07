package com.test.controller;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

@Controller
public class HomeController {

    @RequestMapping("/")

    public ModelAndView helloWorld() {
        return new
                ModelAndView("welcome", "message", "HELLO WORLD!");

    }

    @RequestMapping("welcome")

    public ModelAndView helloWorld2() {
        int[] ar = {1, 2, 3, 4};
        return new
                ModelAndView("welcome2", "message", ar);

    }

    @RequestMapping("getWeather")
    //this returns a string
    public String getWeather() {

        try {

            //httpclient is programming interface that allows us to send requests thru http protocol to other http servers
            HttpClient http = HttpClientBuilder.create().build();

            //address this wants us to call, 80 is the default
            HttpHost host = new HttpHost("forecast.weather.gov", 80, "http");

            //get info
            HttpGet getPage = new HttpGet("/MapClick.php?lat=42.3314&lon=83.0458&FcstType=xml");

            //execute the HTTP request and get the HTTP response back
            HttpResponse resp = http.execute(host, getPage);

            String result = "";
            String xmlString = EntityUtils.toString(resp.getEntity());

            //define a factory API that enables application to obtain a parser that produces a XML tree for us
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();

            //this uses simple api xml (SAX) to create info about our input source
            InputSource inStream = new InputSource();
            inStream.setCharacterStream(new StringReader(xmlString)); //this sets the character string for me
            Document doc = db.parse(inStream);

            String weatherForecast = "";

            //create a nodelist that helps us to access data in an XML tree
            NodeList nl = doc.getElementsByTagName("text");

            //loop thru data to process information and print it out //loop thru return from nodelist

            for (int i = 0; i < nl.getLength(); i++) {
                org.w3c.dom.Element nameElement = (org.w3c.dom.Element) nl.item(i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (Exception e) { //this is a generic catch thing that should cover all things that need catching. Like kittens, babies, and baseballs.
            e.printStackTrace();
        }

        return "weather";
    }

}
