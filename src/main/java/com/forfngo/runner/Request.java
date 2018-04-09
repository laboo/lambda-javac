package com.forfngo.runner;
import java.util.List;
public class Request {
    String name;
    String code;
    List<String> options;
    
    public Request() {System.out.println("In default constructor");};
    public Request(String name, String code, List<String> options) {
        System.out.println("In other constructor");
        this.name = name;
        this.code = code;
        this.options = options;
    }
    public void setName(String name) {
        System.out.println("In setName");
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public void setCode(String code) {
        System.out.println("In setCode");
        this.code = code;
    }
    public String getCode() {
        return this.code;
    }
    public List<String> getOptions() {
        return this.options;
    }
    public void setOptions(List<String> options) {
        System.out.println("In setOptions");
        this.options = options;
    }
}
