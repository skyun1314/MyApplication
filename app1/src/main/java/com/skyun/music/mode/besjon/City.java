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
public class City implements Serializable {

    private String name;
    private List<String> area;
    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setArea(List<String> area) {
         this.area = area;
     }
     public List<String> getArea() {
         return area;
     }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", area=" + area +
                '}';
    }
}