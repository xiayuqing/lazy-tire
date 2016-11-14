package org.jason.lazytire.vcs.support.gitlab;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabBranch;
import org.gitlab.api.models.GitlabProject;
import org.jason.lazy.tire.common.AppContext;
import org.jason.lazytire.vcs.support.AbstractVersionController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason.Xia on 16/10/19.
 */
public class GitlabVersionController extends AbstractVersionController<Git> {

    public static void main(String[] args) throws IOException {
        GitlabAPI connect = GitlabAPI.connect("http://172.16.61.211/", "p7RxzsjkbkwXL9yyGtAc");
        List<GitlabProject> projects = connect.getProjects();
        GitlabProject project = connect.getProject(59);
    }

    @Override
    protected Git openRepository(String projectName) {
        if (repositoryExists(projectName)) {
            throw new IllegalArgumentException("Project " + projectName + " Not found.");
        }

        try {
            return Git.open(new File(AppContext.getProperty(AppContext.REPOSITORY_PROJECT) + "/" + projectName));
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public List<GitlabProject> getProjects(String privateToken) {
        GitlabAPI connect = GitlabAPI.connect(AppContext.getProperty(AppContext.GIT_ADMIN_ADDRESS), privateToken);
        try {
            return connect.getProjects();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public boolean pull(String projectName) {
        try {
            Git git = openRepository(projectName);
            return git.pull().call().isSuccessful();
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public void checkout(String projectName, String branch) {
        try {
            Git git = openRepository(projectName);
            if (branchLocalExists(git, branch)) {
                git.checkout().setName(branch).call();
            } else {
                git.checkout()
                        .setCreateBranch(true)
                        .setName(branch)
                        .setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.SET_UPSTREAM)
                        .setStartPoint("origin/" + branch)
                        .call();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * @param url should be ssh url/http url/ git url
     */
    public void cloneRepository(String url, String branch, String projectName) {
        if (repositoryExists(projectName)) {
            return;
        }

        CloneCommand cloneCommand = Git.cloneRepository();
        cloneCommand.setDirectory(new File(AppContext.getProperty(AppContext.REPOSITORY_PROJECT) + "/" + projectName));
        cloneCommand.setBranch(branch);
        cloneCommand.setURI(url);
        cloneCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(AppContext.getProperty(AppContext
                .GIT_ADMIN_ACCOUNT), AppContext.getProperty(AppContext.GIT_ADMIN_PASSWD)));
        try {
            cloneCommand.call();
        } catch (GitAPIException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public List<GitlabBranch> listBranches(String privateToken, int projectId) {
        GitlabAPI gitlabAPI = GitlabAPI.connect(AppContext.getProperty(AppContext.REPOSITORY_PROJECT), privateToken);
        try {
            return gitlabAPI.getBranches(projectId);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

    }

    private boolean branchLocalExists(Git git, String branch) {
        try {
            for (Ref item : git.branchList().call()) {
                if (item.getName().equals(branch)) {
                    return true;
                }
            }
        } catch (GitAPIException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

        return false;
    }
}
