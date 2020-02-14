package app.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SecurityUtil {

    public static void main(String args[]) {
        String str = "12345678901";
        String str1 = desEncrypt(str);
        String str2 = desDecrypt(str1);
        System.out.println(str1);
        System.out.println(str2);
    }

    public static String SHA(String str) {
        String result = "";

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA");

            byte[] byteArray = str.getBytes("UTF-8");
            byte[] md5Bytes = sha.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            result = hexValue.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static String MD5(String plainText) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer();
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            result = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String base64Encode(String str) {
        String result = "";
        try {
            result = Base64.getEncoder().encodeToString(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String base64Decode(String str) {
        String result = "";
        try {
            byte[] asBytes = Base64.getDecoder().decode(str);
            result = new String(asBytes, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String desPwd = "4828ab6fbb2d5aa223fdcf7022bb4733";

    public static String desEncrypt(String str){
        String result = "";
        if(str == null || str.equals("")){
            return result;
        }
        try{
            byte[] bytes = str.getBytes();
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(desPwd.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            byte[] resultBytes = cipher.doFinal(bytes);
            resultBytes = Base64.getEncoder().encode(resultBytes);
            result = new String(resultBytes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static String desDecrypt(String str){
        String result = "";
        if(str == null || str.equals("")){
            return result;
        }
        try{
            byte[] bytes = str.getBytes();
            bytes = Base64.getDecoder().decode(bytes);
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(desPwd.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);
            byte[] resultBytes = cipher.doFinal(bytes);
            result = new String(resultBytes);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
