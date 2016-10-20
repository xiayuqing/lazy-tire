package org.jason.lazytire.vcs;

/**
 * Created by Jason.Xia on 16/10/19.
 */
public interface VersionController {

    boolean pull(String projectName);

    void checkout(String projectName, String branch);

    void cloneRepository(String url, String branch, String projectName);

}
