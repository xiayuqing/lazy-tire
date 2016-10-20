package org.jason.lazytire.porter.protocol.scp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.jason.lazytire.porter.bean.ConnectConfig;
import org.jason.lazytire.porter.protocol.Transporter;

import java.io.*;

/**
 * Created by Jason.Xia on 16/10/20.
 */
public class SCPTransporter implements Transporter {

    private JSch client = new JSch();

    public SCPTransporter() {
    }

    public void transport(ConnectConfig config) {
        FileInputStream fis = null;
        try {
            Session session = client.getSession(config.getUsername(), config.getHost(), config.getPort());
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(config.getPassword());
            session.connect(10000);
            String command = "scp " + config.getTarget();
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();

            channel.connect();

            if (checkAck(in) != 0) {
            }

            File _origi = new File(config.getOriginal());
            // here should be "T <last modified time> 0 <last access time> 0", but it is not accessible with java api.
            String tCommand = "T " + (_origi.lastModified() / 1000) + " 0 " + (_origi.lastModified() / 1000 + " 0\n");
            out.write(tCommand.getBytes());
            out.flush();
            checkAck(in);

            String cCommand = "C0644 " + _origi.length();
            if (config.getOriginal().lastIndexOf('/') > 0) {
                cCommand += config.getOriginal().substring(config.getOriginal().lastIndexOf('/') + 1);
            } else {
                cCommand += config.getOriginal();
            }

            cCommand += "\n";
            out.write(cCommand.getBytes());
            out.flush();
            checkAck(in);

            // send content of original file
            fis = new FileInputStream(config.getOriginal());
            byte[] buf = new byte[1024];
            while (true) {
                int len = fis.read(buf, 0, buf.length);
                if (len <= 0) {
                    break;
                }

                out.write(buf, 0, len);
            }

            fis.close();
            fis = null;
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            checkAck(in);

            out.close();
            channel.disconnect();
            session.disconnect();

        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 0: success
     * 1: warning
     * 2: fatal error
     *
     * @param in
     * @return
     * @throws IOException
     */
    private int checkAck(InputStream in) throws IOException {
        int b = in.read();
        if (b == 0) {
            return b;
        }

        if (b == -1) {
            throw new IllegalStateException("Interruption of transmission cause by SCP error.");
        }

        if (b == 1 || b == 2) {
            StringBuilder sb = new StringBuilder();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            } while (c != '\n');

            throw new IllegalStateException("Interruption of transmission. " + sb.toString());
        }

        return b;
    }

}
