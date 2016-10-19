package org.jason.lazy.tire.common;

import org.apache.maven.shared.invoker.MavenInvocationException;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabSession;

import java.io.File;
import java.io.IOException;

/**
 * Created by Jason.Xia on 16/7/21.
 */
public class Run {

    public static final String TOKEN = "p7RxzsjkbkwXL9yyGtAc";
    private static final String HOST = "http://172.16.61.211/";

    public static void main(String[] args) throws IOException, InterruptedException, GitAPIException,
            MavenInvocationException {
        GitlabSession session = GitlabAPI.connect(Run.HOST, "yuqing.xia", "xia19941");
        String privateToken = session.getPrivateToken();
        GitlabAPI gitlabAPI = GitlabAPI.connect(Run.HOST, Run.TOKEN);
        GitlabProject project = gitlabAPI.getProject(59);
        CloneCommand cloneCommand = Git.cloneRepository();
        String projectPath = "/Users/yuqingxia/ci-test/" + project.getName();
        cloneCommand.setDirectory(new File(projectPath));
        cloneCommand.setBranch("master");
        cloneCommand.setURI(project.getHttpUrl());
        cloneCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider("yuqing.xia", "xia199411"));
        cloneCommand.call();
        Git git = Git.open(new File(projectPath));
        boolean successful = git.pull().call().isSuccessful();
    }
}
