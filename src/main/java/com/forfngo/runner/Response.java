package com.forfngo.runner;

public class Response {
    boolean compiles;
    boolean passes;
    String output;
    
    public Response() {}
    public Response(boolean compiles, boolean passes, String output) {
        this.compiles = compiles;
        this.passes = passes;
        this.output = output;
    }
    public void setCompiles(boolean compiles) {
        this.compiles = compiles;
    }
    public boolean getCompiles() {
        return this.compiles;
    }
    public void setPasses(boolean passes) {
        this.passes = passes;
    }
    public boolean getPasses() {
        return this.passes;
    }
    public void setOutput(String output) {
        this.output = output;
    }
    public String getOutput() {
        return this.output;
    }
}
