package com.theminequest.MineQuest;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.MessageDigest;

/**
 * Downloader allows for efficient downloading of
 * files from external destinations by using Java NIO,
 * and the checking of the sha256sums with the given
 * algorithms.<br>
 * <code>This file is part of flo, the Java RPM Package Manager.
 * flo is released under the terms of the GNU General Public License,
 * version 3 or later. NO warranty is given. For more information,
 * see the included LICENSE.txt file or online at github.com/Lincom/flo.</code>
 * @author Robert Xu <rxu@lincomlinux.org>
 * @version dev-SNAPSHOT
 * @since 1.6
 */
public class Downloader {

	public static void download(String url, String destination) throws IOException{
	    URL todownload = new URL(url);
	    ReadableByteChannel rbc = Channels.newChannel(todownload.openStream());
	    FileOutputStream fos = new FileOutputStream(destination);
	    fos.getChannel().transferFrom(rbc, 0, 1 << 24);
	}
	
	public static String sha256Check(String file) throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        FileInputStream fis = new FileInputStream(file);
 
        byte[] dataBytes = new byte[1024];
 
        int nread = 0; 
        while ((nread = fis.read(dataBytes)) != -1) {
          md.update(dataBytes, 0, nread);
        };
        byte[] mdbytes = md.digest();
        
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        return sb.toString();
	}
	
}
