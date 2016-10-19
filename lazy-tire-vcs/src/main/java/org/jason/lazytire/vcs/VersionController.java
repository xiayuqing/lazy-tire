package org.jason.lazytire.vcs;

import java.util.List;

/**
 * Created by Jason.Xia on 16/10/19.
 */
public interface VersionController {

    boolean pull(String projectName);

    void checkout(String projectName, String branch);

    /**
     * @param url should be ssh url/http url/ git url
     */
    void cloneRepository(String url, String branch, String projectName);

}
