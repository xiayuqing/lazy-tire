package org.jason.lazytire.porter.protocol;

import org.jason.lazytire.porter.bean.ConnectConfig;

/**
 * Created by Jason.Xia on 16/10/20.
 */
public interface Transporter {

    void transport(ConnectConfig config);
}
