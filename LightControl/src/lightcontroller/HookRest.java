package lightcontroller;

import java.util.*;
import org.restlet.data.Form;
import org.restlet.Response;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.Client;
import org.restlet.representation.Representation;
import java.io.IOException;
import org.json.*;
import org.json.JSONObject;
import org.restlet.data.MediaType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ykk
 */
public class HookRest {
    
    private static String servername = "https://api.gethook.io/v1";
    private String token;
    private int numDevices;
    
    public HookRest(){      
    }
    public HookRest(String token)
    {
        this.token = token;
    }
    public String getToken()
    {
        return token;
    }
    public int triggerAction(Light light, String action)
    {
        System.out.println("turn " + action);
        try{
            
            ClientResource resource = new ClientResource(servername + "/device/trigger/"+light.getId()+"/"+action+"/?token=" + this.token);
            try{
                JSONObject data = new JSONObject(resource.get(MediaType.APPLICATION_JSON).getText());
                String response = data.getString("return_value");
                if(response== "1")
                    return 1;
                else
                    return 0;

            }catch(IOException e){
                System.out.println("IOEXCEPTION");
                return -1;
            }
        }catch(ResourceException e){
             System.out.println("RESOURCE EXCEPTION");
             return -1;
        }
    }
    public void getDevice(ArrayList lights)
    {
        System.out.println("Getting Devices");
        try{
            ClientResource resource = new ClientResource(servername + "/device/?token=" + this.token); 
           
                try{
                    JSONObject data = new JSONObject(resource.get(MediaType.APPLICATION_JSON).getText());
                    JSONArray devices = data.getJSONArray("data").getJSONArray(0);
                    
                    for(int i=0; i<devices.length(); i++)
                    {
                        JSONObject device = devices.getJSONObject(i);
                        String name = device.getString("device_name");
                        String id = device.getString("device_id");
                        lights.add(new Light(name,id));
                    }
   
                }catch(IOException e){
                    System.out.println("IOEXCEPTION");
                }

        }catch(ResourceException e){
             System.out.println("RESOURCE EXCEPTION");
        }
    }
    public Boolean hookRestLogin(CharSequence username, CharSequence pw){
        
        try{
            Form form = new Form();
            form.add("username", username.toString());
            form.add("password", pw.toString());

            ClientResource resource = new ClientResource(servername + "/user/login");
            Representation response = resource.post(form.getWebRepresentation());

            if (resource.getStatus().isSuccess()) {
                System.out.println("Success! " + resource.getStatus());

                try{
                   JSONObject obj = new JSONObject(response.getText());
                   this.token = obj.getJSONObject("data").getString("token");
                }catch(IOException e){
                    e.printStackTrace();
                    System.out.println("FAILED TO GET TOKEN! " + resource.getStatus());
                    return false;
                }
                return true;

            } else {
                System.out.println("ERROR! " + resource.getStatus());
                return false;
            }

        } catch (ResourceException e) {
            // Login Error
            System.out.println("LOGIN ERROR! " + e.getStatus());
            return false;
        }   
    }
      
}
