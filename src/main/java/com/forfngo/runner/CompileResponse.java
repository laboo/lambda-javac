package com.forfngo.runner;

public class CompileResponse {
    boolean compiles;
    String output;
    
    public CompileResponse() {}
    public CompileResponse(boolean compiles, String output) {
        this.compiles = compiles;
        this.output = output;
    }
    public void setCompiles(boolean compiles) {
        this.compiles = compiles;
    }
    public boolean getCompiles() {
        return this.compiles;
    }
    public void setOutput(String output) {
        this.output = output;
    }
    public String getOutput() {
        return this.output;
    }
}
