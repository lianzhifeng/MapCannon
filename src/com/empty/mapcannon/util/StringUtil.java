package com.empty.mapcannon.util;

import java.util.regex.Pattern;

public class StringUtil
{
  public static boolean containsEmoji(String paramString)
  {
    int i = paramString.length();
    for (int j = 0; j < i; j++)
      if (!isEmojiCharacter(paramString.charAt(j)))
        return true;
    return false;
  }

  public static String filterEmoji(String paramString)
  {
    int i = paramString.length();
    StringBuilder localStringBuilder = new StringBuilder(i);
    for (int j = 0; j < i; j++)
    {
      char c = paramString.charAt(j);
      if (isNotEmojiCharacter(c))
        localStringBuilder.append(c);
    }
    return localStringBuilder.toString();
  }

  public static String insteadChangeLine(String paramString)
  {
    if (isEmpty(paramString))
      return null;
    return paramString.replace("\r\n", "\n");
  }

  public static boolean isBlank(String paramString)
  {
    return (paramString == null) || (paramString.equals("")) || (paramString.trim().equals("")) || (trimAll(paramString).equals(""));
  }

  public static boolean isEmail(String paramString)
  {
    return Pattern.compile("^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$").matcher(paramString).matches();
  }

  private static boolean isEmojiCharacter(char paramChar)
  {
    return (paramChar == 0) || (paramChar == '\t') || (paramChar == '\n') || (paramChar == '\r') || ((paramChar >= ' ') && (paramChar <= 55295)) || ((paramChar >= 57344) && (paramChar <= 65533)) || ((paramChar >= 65536) && (paramChar <= 1114111));
  }

  public static boolean isEmpty(String paramString)
  {
    return (paramString == null) || (paramString.equals(""));
  }

  public static boolean isImgFromMT(String paramString)
  {
    return paramString.contains("img2.miaotu.com");
  }

  private static boolean isNotEmojiCharacter(char paramChar)
  {
    return (paramChar == 0) || (paramChar == '\t') || (paramChar == '\n') || (paramChar == '\r') || ((paramChar >= ' ') && (paramChar <= 55295)) || ((paramChar >= 57344) && (paramChar <= 65533)) || ((paramChar >= 65536) && (paramChar <= 1114111));
  }

  public static boolean isNumeric(String paramString)
  {
    return Pattern.compile("[0-9]*").matcher(paramString).matches();
  }

  public static boolean isPhoneNumber(String paramString)
  {
    if (paramString == null)
      return false;
    return paramString.matches("^(13|15|18|17|14|00)[0-9]{9}$");
  }

  public static String trimAll(String paramString)
  {
    return filterEmoji(paramString.replaceAll("\\s*", ""));
  }
}

/* Location:           /home/xshengh/trash/classes_dex2jar.jar
 * Qualified Name:     com.miaotu.util.StringUtil
 * JD-Core Version:    0.6.2
 */