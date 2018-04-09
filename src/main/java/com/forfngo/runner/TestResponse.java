package com.forfngo.runner;

public class TestResponse {
    boolean passes;
    String output;
    
    public TestResponse() {}
    public TestResponse(boolean passes, String output) {
        this.passes = passes;
        this.output = output;
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
    
