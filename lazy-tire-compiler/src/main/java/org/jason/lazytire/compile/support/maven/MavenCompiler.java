package org.jason.lazytire.compile.support.maven;

import org.apache.maven.shared.invoker.*;
import org.jason.lazy.tire.common.AppContext;
import org.jason.lazy.tire.common.ApplicationContextBuilder;
import org.jason.lazy.tire.common.ContextConfiguration;
import org.jason.lazytire.compile.CompileException;
import org.jason.lazytire.compile.bean.*;
import org.jason.lazytire.compile.support.AbstractCompiler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Jason.Xia on 16/10/18.
 */
public class MavenCompiler extends AbstractCompiler {
    private AppContext context;
    private Properties properties;
    private List<String> profiles;
    private List<String> goals;

    public MavenCompiler(AppContext context) {
        this.context = context;

    }

    public static void main(String[] args) {
        MavenCompiler compiler = new MavenCompiler(new ApplicationContextBuilder().build(new ContextConfiguration
                ("/usr/local/Cellar/maven/3.3.3/", "/Users/yuqingxia/ci-test", "/Users/yuqingxia/ci-test")));
        CompileConfig config = new CompileConfig();
        config.setProjectName("service-schedule");
        config.setCompileType(CompileType.MANUAL);
        config.setProject(new File("/Users/yuqingxia/ci-test/service-schedule"));
        compiler.init(config);
        compiler.start();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void init(CompileConfig config) {
        super.init(config);
        Map<String, Object> extra = config.getExtra();
        if (null != extra) {
            if (extra.containsKey(ValueConstants.MAVEN_GOALS)) {
                this.goals = (List<String>) extra.get(ValueConstants.MAVEN_GOALS);
            }

            if (extra.containsKey(ValueConstants.MAVEN_PROFILES)) {
                this.profiles = (List<String>) extra.get(ValueConstants.MAVEN_PROFILES);
            }

            if (extra.containsKey(ValueConstants.MAVEN_PROPERTIES)) {
                this.properties = (Properties) extra.get(ValueConstants.MAVEN_PROPERTIES);
            }
        }

        if (null == goals) {
            goals = new ArrayList<>();
            goals.add(MAVEN_OPTS.CLEAN);
            goals.add(MAVEN_OPTS.PACKAGE);
        }

        if (null == profiles) {
            profiles = new ArrayList<>();
            profiles.add(Environment.BETA);
        }

        if (null == properties) {
            properties = new Properties();
            properties.setProperty(ValueConstants.MAVEN_TEST_SKIP, "true");
        }
    }

    @Override
    public void start() {
        InvocationRequest request = new DefaultInvocationRequest();
        if (!new File(project.getAbsolutePath() + "/pom.xml").exists()) {
            throw new CompileException("Not find pom.xml. path: " + project.getAbsolutePath() + "/pom.xml");
        }

        request.setGoals(goals);
        request.setProfiles(profiles);
        request.setProperties(properties);
        request.setPomFile(new File(project.getAbsolutePath() + "/pom.xml"));
        Invoker invoker = new DefaultInvoker();
        StringBuilder sb = new StringBuilder(context.getREPORT_REPOSITORY());
        switch (config.getCompileType()) {
            case SCHEDULE:
                sb.append("/schedule/")
                        .append(new SimpleDateFormat("yyyyMMdd").format(new java.util.Date()));
                break;
            case MANUAL:
                sb.append("/manual/")
                        .append(new SimpleDateFormat("yyyyMMdd").format(new java.util.Date()));
                break;
            default:
                throw new IllegalArgumentException("Unsupported compile type : " + config.getCompileType());
        }


        File currentRepoDir = new File(sb.toString());
        if (!currentRepoDir.exists() && !currentRepoDir.mkdirs()) {
            throw new IllegalStateException("Initial report file directory failure. path:" + currentRepoDir);
        }

        File reportFile = new File(sb.toString(), config.getProjectName() + "_" + new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new java.util.Date()) + ".repo");

        try {
            if (!reportFile.exists() && !reportFile.createNewFile()) {
                throw new IllegalStateException("Failed to create compile result file. file:" + reportFile);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

        try {
            request.setOutputHandler(new PrintStreamHandler(new PrintStream(reportFile), false));
            invoker.setMavenHome(new File(context.getMAVEN_HOME()));
            InvocationResult result = invoker.execute(request);
            if (0 != result.getExitCode()) {
                FileWriter writer = new FileWriter(reportFile, true);
                writer.write("Msg: " + result.getExecutionException().getMessage() + "Trace: " + result
                        .getExecutionException().getStackTrace());
            }
        } catch (MavenInvocationException | IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public void stop() {
        super.stop();
    }
}
