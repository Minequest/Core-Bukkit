/**
 * This file, Downloader.java, is part of MineQuest:
 * A full featured and customizable quest/mission system.
 * Copyright (C) 2012 The MineQuest Team
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 **/
package com.theminequest.MineQuest.Utils;

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
