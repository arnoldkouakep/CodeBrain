/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.codebrain.ui.application.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KSA-INET
 */
public class StringUtils {

    public static String MD5encode(String password) {
        byte[] uniqueKey = password.getBytes();
        byte[] hash = null;

        try {
            hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
        } catch (NoSuchAlgorithmException e) {
            throw new Error("No MD5 support in this VM.");
        }

        StringBuilder hashString = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1) {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length() - 1));
            } else {
                hashString.append(hex.substring(hex.length() - 2));
            }
        }
        //System.out.println(hashString.toString());
        return hashString.toString();
    }

    public static String MD5decode(String password) {
        return null;
    }

    public static String UTF8Encode(String str) {
        String utf8String = null;

        utf8String = str;
        return utf8String;
    }

    public static String ISO_8859_1Encode(String str) {
        String utf8String = null;

        if (!str.isEmpty()) {

            try {
                byte[] byteStr = str.getBytes("ISO-8859-1");
                utf8String = new String(byteStr, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                utf8String = str;
                Logger.getLogger(StringUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return utf8String;
    }
}
