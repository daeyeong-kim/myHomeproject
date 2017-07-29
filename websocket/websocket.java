package com.websocket;




import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


import java.util.logging.Level;


import java.util.logging.Logger;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.websocket.OnClose;


import javax.websocket.OnError;


import javax.websocket.OnMessage;


import javax.websocket.OnOpen;


import javax.websocket.Session;


import javax.websocket.server.ServerEndpoint;




@ServerEndpoint("/Websocket")


public class websocket {

    private static final Logger LOGGER = Logger.getLogger(websocket.class.getName());
    private static Set<Session> clients =Collections.synchronizedSet(new HashSet<Session>());
    private static HashMap<String, String> hs = new HashMap<String,String>();
    

    @OnOpen


    public void onOpen(Session session) throws IOException {
    	clients.add(session);

        LOGGER.log(Level.INFO, "New connection with client: {0}",session.getId());


    }


    


    @OnMessage


    public void onMessage(String message, Session session) throws IOException {


        //LOGGER.log(Level.INFO, "New message from Client [{0}]: {1}", 


                //new Object[] {session.getId(), message});


        	if(message.contains("zwerfderwerioort")==true){
        		String k =message.replaceAll("zwerfderwerioort","");
        		hs.put(session.getId().toString(),k);
        		synchronized(clients){
      	    	  for(Session client : clients){
      	    		
      	    			  Iterator<String> iterator = hs.keySet().iterator();
      	    			  	while (iterator.hasNext()) {
      	    			  		String key = (String) iterator.next();
      	                           client.getBasicRemote().sendText("asdfqwerQWEDsdw"+hs.get(key));
      	    			  		}
      	    		  	}  
        		}
        		return;
        	}
        	
        	


        synchronized(clients){
            // Iterate over the connected sessions


            // and broadcast the received message


            for(Session client : clients){

              if (!client.equals(session)){

            	
                client.getBasicRemote().sendText(message);
                
              }

            }

          }

        //return "Server received [" + message + "]";


    }


    


    @OnClose
    public void onClose(Session session) {
  
    	 Iterator<String> iterator = hs.keySet().iterator();
    	  while (iterator.hasNext()) {
    	        String key = (String) iterator.next();
    	        System.out.print("key="+key);
    	        System.out.println(" value="+hs.get(key));
    	    }
    	  hs.remove(session.getId());
    	clients.remove(session);

        LOGGER.log(Level.INFO, "Close connection for client: {0}", 


                session.getId());


    }


    


    @OnError


    public void onError(Throwable exception, Session session) {


        LOGGER.log(Level.INFO, "Error for client: {0}", session.getId());


    }


}
