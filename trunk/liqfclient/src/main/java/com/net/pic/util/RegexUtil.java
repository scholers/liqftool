package com.net.pic.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	  /**
     * �滻�ַ���(��Сд����);
     * @param sourceString
     *             Դ�ַ���
     * @param regexString
     *             ������ʽ
     * @param replaceString
     *             Ҫ�滻�ɵ��ַ�
     * 
     * @return �滻����ַ���
     */
    public static String replaceString(String sourceString,String regexString,String replaceString){
        Pattern p = Pattern.compile(regexString,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sourceString);
        String result = m.replaceAll(replaceString);
        return result;
    }
    
    public static boolean validateSting(String sourceString,String regexString){
        Pattern p = Pattern.compile(regexString);
        Matcher m = p.matcher(sourceString);
        return m.matches();
    }
    
    /**
     * ����ƥ����ַ���list
     * @param sourceString
     * @param regexString
     * @return
     */
    public static List<String> getString(String sourceString,String regexString){
        List<String> result = null;
        try {
            Pattern p = Pattern.compile(regexString);
            Matcher m = p.matcher(sourceString);
            result = new ArrayList<String>();
            while (m.find()){
                result.add(m.group());
            }
        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}


