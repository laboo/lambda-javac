package com.forfngo.runner;

import javax.tools.*;
import com.sun.tools.javac.api.JavacTool;
import java.util.*;
import java.io.IOException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.nio.file.Files;
import java.nio.file.Path;

public class Javac implements RequestHandler<Request, Response> {
    public Response handleRequest(Request request, Context context) {
        CompileResponse cr = null;
        try {
            List<String> options = request.getOptions() != null ? request.getOptions() : new ArrayList<String>();
            Path dir = Files.createTempDirectory("me");
            options.add("-d");
            options.add(dir.toString());
            System.out.println("IdentityId: " + context.getIdentity().getIdentityId());
            System.out.println("abcdefg");
            cr = compile(request.getName(), request.getCode(), options);
        } catch (IOException ioe) {
            cr = new CompileResponse(false, ioe.toString());
        }
        // TODO test response here
        return new Response(cr.getCompiles(), true, cr.getOutput());
    }

    public static CompileResponse compile(String name, String code, List<String> options) throws IOException {
        CompileResponse cr = new CompileResponse();
        cr.setCompiles(true);
        // AWS doesn't offer JDK (and tools.jar), so we have to include tools.jar in the zip file we upload to Lambda.
        // JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        JavaCompiler compiler = JavacTool.create();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        JavaSourceFromString source = new JavaSourceFromString(name, code);
        List<JavaFileObject> sourceList = new ArrayList<>();
        sourceList.add(source);
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        compiler.getTask(null, fileManager, diagnostics, options, null, sourceList).call();
        StringBuilder sb = new StringBuilder();
        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            if (diagnostic.getKind() != Diagnostic.Kind.ERROR) {
                System.out.println(diagnostic);
                continue;
            }
            cr.setCompiles(false);
            sb.append("Line " + diagnostic.getLineNumber()
                      + ", column " + diagnostic.getColumnNumber()
                      + ": error: " + diagnostic.getMessage(null)
                      + ".\n");
            int diagStart = (int) diagnostic.getStartPosition();
            int diagEnd = (int) diagnostic.getEndPosition();
            String errorLine = buildErrorLine(code, diagStart, diagEnd, (int) diagnostic.getColumnNumber());
            if (errorLine != null && !errorLine.equals("")) {
                sb.append(errorLine + "\n");
            }
            break; // Return one error only
        }
        fileManager.close();
        cr.setOutput(sb.toString());
        return cr;
    }

    private static String buildErrorLine(String code, int diagStart, int diagEnd, int column) {
        if (code == null || diagStart >= code.length() || diagStart < 0) {
            return "";
        }

        if (code.charAt(diagStart) == '\n' && diagStart != 0) {
            diagStart -= 1;
        }
        int lineStart = code.lastIndexOf("\n", diagStart);
        int lineEnd = code.indexOf("\n", (int) diagStart);
        lineStart = (lineStart == -1) ? 0 : lineStart;
        lineEnd = (lineEnd == -1) ? code.length()-1 : lineEnd;
        
        /*
        System.out.println("diagStart=" + diagStart + "\n" +
                           "diagEnd  =" + diagEnd + "\n" +
                           "lineStart=" + lineStart + "\n" +
                           "lineEnd  =" + lineEnd + "\n" +
                           "column   =" + column);
        */
        
        int spaceBeforeArrow = column;

        StringBuilder sb = new StringBuilder();
        sb.append(code.substring(lineStart, lineEnd));
        sb.append("\n");
        sb.append(String.format("%" + spaceBeforeArrow + "s", "^"));

        return sb.toString();
    }

}
