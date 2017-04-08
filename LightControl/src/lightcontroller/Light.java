package lightcontroller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ykk
 */
public class Light {
    private String name;
    private String deviceId;
    
    public Light(String name, String id)
    {
        this.name = name;
        this.deviceId = id;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getId(){
        return this.deviceId;
    }
    
}
