package org.jason.lazytire.compile.support.maven;

import org.apache.maven.shared.invoker.*;
import org.jason.lazy.tire.common.AppContext;
import org.jason.lazy.tire.common.Callback;
import org.jason.lazy.tire.common.bean.TaskStatistics;
import org.jason.lazytire.compile.Compiler;
import org.jason.lazytire.compile.Option;
import org.jason.lazy.tire.common.bean.Result;
import org.jason.lazytire.compile.bean.CompileConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;

/**
 * Created by Jason.Xia on 16/10/18.
 */
public class MavenCompileTask implements Compiler {
    private InvocationRequest request = new DefaultInvocationRequest();
    private Invoker invoker = new DefaultInvoker();
    private Option option;
    private Callback callback;
    private Result result;
    private TaskStatistics taskStatistics = new TaskStatistics();

    public MavenCompileTask(Option option, Callback callback) {
        this.callback = callback;
        this.option = option;
    }

    public static void main(String[] args) {
//        MavenCompileTask compiler = new MavenCompileTask(new ApplicationContextBuilder().build(new
// ContextConfiguration
//                ("/usr/local/Cellar/maven/3.3.3/", "/Users/yuqingxia/ci-test", "/Users/yuqingxia/ci-test")));
//        CompileConfig config = new CompileConfig();
//        config.setProjectName("service-schedule");
//        config.setCompileType(CompileType.MANUAL);
//        config.setProject(new File("/Users/yuqingxia/ci-test/service-schedule"));
//        compiler.init(config);
//        compiler.run();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void init(CompileConfig config) {
//        Map<String, Object> extra = config.getExtra();
//        if (null != extra) {
//            if (extra.containsKey(ValueConstants.MAVEN_GOALS)) {
//                this.goals = (List<String>) extra.get(ValueConstants.MAVEN_GOALS);
//            }
//
//            if (extra.containsKey(ValueConstants.MAVEN_PROFILES)) {
//                this.profiles = (List<String>) extra.get(ValueConstants.MAVEN_PROFILES);
//            }
//
//            if (extra.containsKey(ValueConstants.MAVEN_PROPERTIES)) {
//                this.properties = (Properties) extra.get(ValueConstants.MAVEN_PROPERTIES);
//            }
//        }
//
//        if (null == goals) {
//            goals = new ArrayList<>();
//            goals.add(MAVEN_OPTS.CLEAN);
//            goals.add(MAVEN_OPTS.PACKAGE);
//        }
//
//        if (null == profiles) {
//            profiles = new ArrayList<>();
//            profiles.add(Environment.BETA);
//        }
//
//        if (null == properties) {
//            properties = new Properties();
//            properties.setProperty(ValueConstants.MAVEN_TEST_SKIP, "true");
//        }
    }

    @Override
    public Result call() throws Exception {
        beforeRun();
        run(option);
        afterRun();
        return result;
    }

    @Override
    public void run(Option option) {
        File pomFile = new File(AppContext.getString(AppContext.Field.REPOSITORY_PROJECT) + option.getProjectName() +
                "/pom.xml");
        if (!pomFile.exists()) {
            result = Result.failure("Not find pom.xml. path: " + pomFile.getAbsolutePath());
        }

        request.setGoals(option.getGoals());
        request.setProfiles(option.getProfiles());
        request.setProperties(option.getProperties());
        request.setPomFile(pomFile);

        StringBuilder sb = new StringBuilder(AppContext.getString(AppContext.Field.REPOSITORY_REPORT));
        switch (option.getCompileType()) {
            case SCHEDULE:
                sb.append("/schedule/")
                        .append(new SimpleDateFormat("yyyyMMdd").format(new java.util.Date()));
                break;
            case MANUAL:
                sb.append("/manual/")
                        .append(new SimpleDateFormat("yyyyMMdd").format(new java.util.Date()));
                break;
            default:
                throw new IllegalArgumentException("Unsupported compile type : " + option.getCompileType());
        }

        File currentRepoDir = new File(sb.toString());
        if (!currentRepoDir.exists() && !currentRepoDir.mkdirs()) {
            throw new IllegalStateException("Initial report file directory failure. path:" + currentRepoDir);
        }

        File reportFile = new File(sb.toString(), option.getProjectName() + "_" + new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new java.util.Date()) + ".repo");

        try {
            if (!reportFile.exists() && !reportFile.createNewFile()) {
                result = Result.exception(new IllegalStateException("Failed to create compile result file. file:" +
                        reportFile));
            }
        } catch (IOException e) {
            result = Result.exception(e);
        }

        try {
            request.setOutputHandler(new PrintStreamHandler(new PrintStream(reportFile), false));
            invoker.setMavenHome(new File(AppContext.getString(AppContext.Field.MAVEN_HOME)));
            InvocationResult result = invoker.execute(request);
            if (0 != result.getExitCode()) {
                FileWriter writer = new FileWriter(reportFile, true);
                writer.write("Msg: " + result.getExecutionException().getMessage() + "\nTrace: " + result
                        .getExecutionException().getStackTrace());
            }
        } catch (MavenInvocationException | IOException e) {
            result = Result.exception(e);
        }

        result = Result.success();
    }

    @Override
    public void stop() {

    }

    @Override
    public void afterRun() {
        callback.call();
        taskStatistics.setEnd(System.currentTimeMillis());
        if (null != result) {
            result.setTaskStatistics(taskStatistics);
        }
    }

    @Override
    public void beforeRun() {
        taskStatistics.setStart(System.currentTimeMillis());
    }
}
