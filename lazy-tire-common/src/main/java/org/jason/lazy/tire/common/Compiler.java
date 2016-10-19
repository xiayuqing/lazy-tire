package org.jason.lazy.tire.common;

import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Jason.Xia on 16/10/17.
 */
public class Compiler {
    public static void main(String[] args) throws MavenInvocationException {
        String projectPath = "/Users/yuqingxia/ci-test/service-schedule";
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File(projectPath + "/pom.xml"));
        List<String> ops = new ArrayList<String>();
        ops.add("clean");
        ops.add("package");
        request.setGoals(ops);
        List<String> profiles = new ArrayList<String>();
        profiles.add("prod");
        request.setProfiles(profiles);
        Properties properties = new Properties();
        properties.setProperty("maven.test.skip", "true");
        request.setProperties(properties);
//        request.setDebug(true);
        Invoker invoker = new DefaultInvoker();
        InvocationResult execute = invoker.execute(request);
        if (0 != execute.getExitCode()) {
            throw new IllegalStateException(execute.getExecutionException().getMessage(), execute
                    .getExecutionException());
        }
    }
}
