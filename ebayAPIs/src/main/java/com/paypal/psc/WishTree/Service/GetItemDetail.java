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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import com.paypal.psc.WishTree.model.Item;



public class GetItemDetail {

	public GetItemDetail() {
		// TODO Auto-generated constructor stub
	}

	public Item GetItemDetails(String itemId, String ebayAuth) {
		Item i = new Item();
		String endpoint = "https://api.sandbox.ebay.com/ws/api.dll";
		String xml = getXml(itemId,ebayAuth);
		System.out.println(xml);
		HttpPost httppost;
		DefaultHttpClient httpclient = new DefaultHttpClient();
	    try {
	    	httppost = new HttpPost(endpoint);
	        StringEntity se = new StringEntity( xml, HTTP.UTF_8);
	        se.setContentType("text/xml");
	        httppost.setEntity(se);
	        
	        
	        httppost.setHeader("X-EBAY-API-COMPATIBILITY-LEVEL","865");
	        httppost.setHeader("X-EBAY-API-DEV-NAME", "e4578d55-a330-4726-9dd0-179447f00d14");
	        httppost.setHeader("X-EBAY-API-APP-NAME","8729c660-da89-4866-827c-e8dfb7e62e88");
	        httppost.setHeader("X-EBAY-API-CERT-NAME","923df117-f10c-41fd-b88b-661ac24e9b6f");
	        httppost.setHeader("X-EBAY-API-SITEID","0");
	        httppost.setHeader("X-EBAY-API-CALL-NAME","GetItem");
	        HttpResponse httpresponse = httpclient.execute(httppost);
	        HttpEntity resEntity = httpresponse.getEntity();
	        System.out.println("Hiiiiii" + httpresponse.getStatusLine());

	        String responseString = new BasicResponseHandler().handleResponse(httpresponse);
	        System.out.println(responseString);
	        i = getItem(responseString); 
	    } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    i.setItemId(itemId);
		return i;
	}
	
	public String getXml(String itemId, String ebayAuth) {
		String result = "";
		File file = new File("/resources/GetDet.xml");
		String filepath = file.getAbsolutePath();
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);
			Node root = doc.getFirstChild();
			NodeList nodeList = root.getChildNodes();
			for (int count = 0; count < nodeList.getLength(); count++) {
				 
				Node tempNode = nodeList.item(count);
			 
				// make sure it's element node.
				if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
			 
					// get node name and value
					System.out.println(tempNode.getNodeName());
					if(tempNode.getNodeName().equals("RequesterCredentials")) {
						System.out.println("RequestCredFound");
						NodeList tempNodeList = tempNode.getChildNodes();
						for (int i = 0; i < tempNodeList.getLength(); i++) {
							Node tempNode1 = tempNodeList.item(i);
							if(tempNode1.getNodeType() == Node.ELEMENT_NODE) {
								if(tempNode1.getNodeName().equals("eBayAuthToken")) {
									tempNode1.setTextContent(ebayAuth);
								}
							}
							
						}
					}
					else if(tempNode.getNodeName().equals("ItemID"))
					{
						tempNode.setTextContent(itemId);
					}
					
				}
		    }
			
			DOMImplementationLS domImplementation = (DOMImplementationLS) doc.getImplementation();
		    LSSerializer lsSerializer = domImplementation.createLSSerializer();
		    result = (lsSerializer.writeToString(doc));
			
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result1 = new StreamResult(new File(filepath));
			transformer.transform(source, result1);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		//String result = "<GetItemRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\"><RequesterCredentials><eBayAuthToken>"+ ebayAuth+ "</eBayAuthToken></RequesterCredentials><ItemID>"+itemId+"</ItemID></GetItemRequest>";
		return result;
	}
	
	
	public Item getItem(String Xml) {
		Item it = new Item();
		String result = "";
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();
			InputStream is = new ByteArrayInputStream(Xml.getBytes());
			Document doc = docBuilder.parse(is);
			Node root = doc.getFirstChild();
			NodeList nodeList = root.getChildNodes();
			for (int count = 0; count < nodeList.getLength(); count++) {
				 
				Node tempNode = nodeList.item(count);
			 
				// make sure it's element node.
				if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
			 
					if(tempNode.getNodeName().equals("Item")) {
						NodeList tempNodeList = tempNode.getChildNodes();
						for (int i = 0; i < tempNodeList.getLength(); i++) {
							Node tempNode1 = tempNodeList.item(i);
							if(tempNode1.getNodeType() == Node.ELEMENT_NODE) {
								if(tempNode1.getNodeName().equals("StartPrice")) {
									
									it.setPrice(tempNode1.getTextContent());
								}
								else if (tempNode1.getNodeName().equals("Title")) {
									it.setItemTitle(tempNode1.getTextContent());
								}
								else if (tempNode1.getNodeName().equals("Location")) {
									it.setSellerLocation(tempNode1.getTextContent());
								}
								else if(tempNode1.getNodeName().equals("Currency")) {
									it.setCurrency(tempNode1.getTextContent());
								}
								else if(tempNode1.getNodeName().equals("PictureDetails")) {
									NodeList picChilds = tempNode1.getChildNodes();
									for (int j = 0; j < picChilds.getLength(); j++) {
										Node picChild = picChilds.item(j);
										if(picChild.getNodeType() == Node.ELEMENT_NODE) {
											if(picChild.getNodeName().equals("GalleryURL")) {
												it.setGalleryUrl(picChild.getTextContent());
											}
										}
									}
								}
								
								else if(tempNode1.getNodeName().equals("Seller")) {
									NodeList sellerChilds = tempNode1.getChildNodes();
									for (int j = 0; j < sellerChilds.getLength(); j++) {
										Node sellerTempChild = sellerChilds.item(j);
										if(sellerTempChild.getNodeType() == Node.ELEMENT_NODE) {
											if(sellerTempChild.getNodeName().equals("Email")) {
												it.setSellerEmail(sellerTempChild.getTextContent());
											}
											else if(sellerTempChild.getNodeName().equals("PositiveFeedbackPercent")) {
												it.setSellerFeedbackScore(sellerTempChild.getTextContent());
											}
											else if(sellerTempChild.getNodeName().equals("Status")) {
												it.setSellerStatus(sellerTempChild.getTextContent());
											}
											else if(sellerTempChild.getNodeName().equals("UserID")) {
												it.setSellerName(sellerTempChild.getTextContent());
											}
										}
									}
								}
							}
							
						}
					}
					
					
				}
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return it;
		
	}

}
