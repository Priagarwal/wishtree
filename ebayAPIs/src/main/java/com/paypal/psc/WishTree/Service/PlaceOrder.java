package com.paypal.psc.WishTree.Service;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import com.paypal.psc.WishTree.model.*;

//import org.w3c.dom.Document;

public class PlaceOrder {
	Order orderOb;
	String ebayAuth;
	
	public PlaceOrder(Order orderOb, String ebayAuth) {
		this.orderOb = orderOb;
		this.ebayAuth = ebayAuth;
	}

	private String getResponseString() {
		String responseString = "";
		String parsedXML = createReqString();
		String endpoint = "https://api.sandbox.ebay.com/ws/api.dll";
		HttpPost httppost;
		DefaultHttpClient httpclient = new DefaultHttpClient();
	    try {
	    	httppost = new HttpPost(endpoint);
	        StringEntity se = new StringEntity(parsedXML, HTTP.UTF_8);
	        se.setContentType("text/xml");
	        httppost.setEntity(se); 
	        httppost.setHeader("X-EBAY-API-COMPATIBILITY-LEVEL","867");
	        httppost.setHeader("X-EBAY-API-DEV-NAME", "1781b2ee-6b2d-460b-9521-c7b64fa35495");
	        httppost.setHeader("X-EBAY-API-APP-NAME","LohitKum-669a-4b03-ad96-175ba9deec3a");
	        httppost.setHeader("X-EBAY-API-CERT-NAME","bb8f5acd-6f30-4f4a-bbae-b193c64f5f36");
	        httppost.setHeader("X-EBAY-API-SITEID","0");
	        httppost.setHeader("X-EBAY-API-CALL-NAME","PlaceOffer");
	        HttpResponse httpresponse = httpclient.execute(httppost);
	        System.out.println("Hiiiiii" + httpresponse.getStatusLine());
	        responseString = new BasicResponseHandler().handleResponse(httpresponse);
	        System.out.println(responseString);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return responseString;
	}
	
	public String getTransactionId() {
		String xmlResponseString = getResponseString();
        String transactionID = "";
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = docFactory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(xmlResponseString.getBytes());
            Document doc = docBuilder.parse(is); 
            Node root =  doc.getFirstChild();
            if((root.getNodeName()).equals("PlaceOfferResponse")) {
            	NodeList children = root.getChildNodes();
                for (int count = 0; count < children.getLength(); count++) {
                	if((children.item(count).getNodeName()).equals("TransactionID")){
                		transactionID = children.item(count).getTextContent();
                	}
                 }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(transactionID);
        return transactionID;     
   }
	
	public String createReqString () {
		String result = "";
		String filePath = "C:/Users/vbharill/Desktop/projects/BuyerMessagingApi-master/BuyerMessaging/src/main/resources/placeOrderReq.xml";
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(filePath));
			Node root =  doc.getFirstChild();
			if (root.getNodeName() == "PlaceOfferRequest") {
				NodeList children = root.getChildNodes();
				for (int i = 0; i < children.getLength(); i++){
					Node temp = children.item(i);
					if ((temp.getNodeName()).equals("RequesterCredentials")) {
						NodeList authToken = temp.getChildNodes();
						for(int k = 0; k < authToken.getLength(); k++) {
							if ((authToken.item(k).getNodeName()).equals("eBayAuthToken")) {
								authToken.item(k).setTextContent(ebayAuth);
							}
						}
					}
					if ((children.item(i).getNodeName()).equals("EndUserIP")) {
						children.item(i).setTextContent(orderOb.getEndUserIP());
					}
					
					if ((children.item(i).getNodeName()).equals("ItemID")) {
						children.item(i).setTextContent(orderOb.getItemId());
					}

					if((children.item(i).getNodeName()).equals("Offer")) {
						NodeList childrenOfOffer = children.item(i).getChildNodes();
						for (int j = 0; j < childrenOfOffer.getLength(); j++) {
							if((childrenOfOffer.item(j).getNodeName()).equals("MaxBid")) {
								childrenOfOffer.item(j).setTextContent(orderOb.getPrice());
							}
						}
					}
				}
			}
			DOMImplementationLS domImplementation = (DOMImplementationLS) doc.getImplementation();
		    LSSerializer lsSerializer = domImplementation.createLSSerializer();
		    
		    result = (lsSerializer.writeToString(doc));
		    
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result1 = new StreamResult(new File(filePath));
			transformer.transform(source, result1);
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}
	public void setHeaders(HttpPost httppost, String compatibility_level, String dev_name, String app_name, String cert_name, String siteID, String api_name) {
		httppost.setHeader("X-EBAY-API-COMPATIBILITY-LEVEL",compatibility_level);
        httppost.setHeader("X-EBAY-API-DEV-NAME", dev_name);
        httppost.setHeader("X-EBAY-API-APP-NAME", app_name);
        httppost.setHeader("X-EBAY-API-CERT-NAME",cert_name);
        httppost.setHeader("X-EBAY-API-SITEID", siteID);
        httppost.setHeader("X-EBAY-API-CALL-NAME", api_name);
	}

}

