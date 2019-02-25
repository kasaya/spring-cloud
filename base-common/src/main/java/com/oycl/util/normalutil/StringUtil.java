package com.oycl.util.normalutil;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil extends StringUtils {

	private StringUtil(){
		throw new AssertionError();
	}
	
	/** 随机数字 */
	public static final String RANDOM_NUMBER = "0123456789";
	
	/** 空 */
	public static final String EMPTY = "";
	/**
	 * 获取UUID
	 * 
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static boolean isEmpty(String s) {
		if (null == s || "".equals(s) || "null".equals(s.toLowerCase())) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

	public static boolean isBlank(String s) {
		if (null == s || "".equals(s) || "".equals(s.trim())
				|| "null".equals(s.toLowerCase())) {
			return true;
		}
		return false;
	}

	public static boolean isNotBlank(String s) {
		return !isBlank(s);
	}

	public static String trim(String s) {
		if (null == s) {
			return null;
		} else {
			return s.trim();
		}
	}

	public static Integer lengthCheck(String s) {
		if (isEmpty(s)) {
			return null;
		}

		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		for (int i = 0; i < s.length(); i++) {
			String temp = s.substring(i, i + 1);
			if (temp.matches(chinese)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	}
	
	public static Object objectTrim(Object obj) {
		Field [] fields = obj.getClass().getDeclaredFields();
		for(int i=0; i< fields.length; i++)
        {
            Field f = fields[i];
            if (f.getType().toString().indexOf("String")>0) {
            	trimFieldValue(obj ,f.getName());
            }
        }
		return obj;
	}
	
	private static void trimFieldValue(Object owner, String fieldName)
    {
        Class<? extends Object> ownerClass = owner.getClass();
        //fieldName -> FieldName  
        String methodName = fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);
        try 
        {
        	Method method = ownerClass.getMethod("get" + methodName);
            String tempStr = (String) method.invoke(owner);
            if (isNotEmpty(tempStr)) {
            	Method setMethod = ownerClass.getMethod("set" + methodName, String.class);
                setMethod.invoke(owner, tempStr.trim());
            } else if (null != tempStr && isBlank(tempStr)) {
            	Method setMethod = ownerClass.getMethod("set" + methodName, String.class);
            	String temp = null;
                setMethod.invoke(owner, temp);
            }
        } 
        catch (Exception e)
        {
        	e.printStackTrace();
        }
    }
	
	/**
	 * 右截位(仅限半角)
	 * 
	 */
	public static String substrRight(String value, int subNum)
    {
		if(!isEmpty(value) && value.length() > subNum) {
			
			value = value.substring(value.length() - subNum);
			return value;
		}
        return value;
        
    }
	/**
	 * 
	 * 生成随机码
	 * 
	 * @return 随机码
	 */
	public static String getRandomString(int length, String base) {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 将字符串转成Integer
	 * 
	 * @param str
	 * @return
	 */
	public static Integer parseInt(String str) {
		Integer temp = 0;
		try {
			temp = Integer.parseInt(str);
		} catch (Exception e) {
			
		}
		return temp;
	}

	/**
	 * 将字符串转成Double
	 * 
	 * @param str
	 * @return
	 */
	public static Double parseDouble(String str) {
		Double temp = 0.0;
		try {
			temp = Double.parseDouble(str);
		} catch (Exception e) {
			
		}
		return temp;
	}

    /**
     * 替换字符串中指定位置的字符
     * 
     * @param str
     * @param pos
     * @param chr
     * @return
     */
    public static String replaceAt(String str, int pos, char chr) {
        char[] arr = str.toCharArray();
        arr[pos - 1] = chr;
        return String.valueOf(arr);
    }
    
    /**
     * 首字母大写
     * @param str
     * @return
     */
    public static String upperCase(String str) {  
        char[] ch = str.toCharArray();  
        if (ch[0] >= 'a' && ch[0] <= 'z') {  
            ch[0] = (char) (ch[0] - 32);  
        }  
        return new String(ch);  
    }
    
    /**
     * 多个空格变成一个空格
     * 
     * @param str
     * @return
     */
    public static String duplicateBlankToBlank(String str) {
        while (str.indexOf("    ") >= 0) {
            str = str.replace(" ", " ");
        }
        while (str.indexOf("　") >= 0) {
            str = str.replace("　", " ");
        }
        while (str.indexOf("  ") >= 0) {
            str = str.replace("  ", " ");
        }
        return str;
    }
    
    public static String objectToString(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        String str = "";
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            if (f.getType().toString().indexOf("String") > 0) {
                str += toStringFieldValue(obj, f.getName());
            }
        }
        if (str.endsWith(",")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
    
    private static String toStringFieldValue(Object owner, String fieldName) {
        String str = "";
        Class<? extends Object> ownerClass = owner.getClass();
        // fieldName -> FieldName
        String methodName = fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);
        try {
            Method method = ownerClass.getMethod("get" + methodName);
            String tempStr = (String) method.invoke(owner);
            if (isNotBlank(tempStr)) {
                str = methodName + ":" + tempStr + ",";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }

    /**
     * 验证金额 小数点后2位
     *
     * @param str
     * @param numLength 整数位长度
     * @return
     */
    public static boolean isNumber(String str, int numLength) {
        numLength = numLength - 1;
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d{0," + numLength +"})|([0]{1}))(\\.(\\d){0,2})?$");
        // 判断小数点后2位的数字的正则表达式
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }
}
