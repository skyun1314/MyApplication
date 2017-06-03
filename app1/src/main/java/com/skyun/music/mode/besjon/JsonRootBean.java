/**
  * Copyright 2017 bejson.com 
  */
package com.skyun.music.mode.besjon;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2017-06-02 23:36:56
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class JsonRootBean implements Serializable {

    private String name;
    private List<City> city;
    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setCity(List<City> city) {
         this.city = city;
     }
     public List<City> getCity() {
         return city;
     }

    @Override
    public String toString() {
        return "JsonRootBean{" +
                "name='" + name + '\'' +
                ", city=" + city +
                '}';
    }
}