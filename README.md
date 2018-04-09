Build with: gradle clean buildZip

Outputs a zip file which can be uploaded to AWS lambda.

Example of how to call when built as a jar and running on an EC2 host.
    curl -X POST ec2-54-183-244-77.us-west-1.compute.amazonaws.com:8080/jc3/java-compile -d "code=public class Answer { public static void main(String[] args) { System.out.println(\"Hidy\"); }}"
    