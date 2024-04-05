package com.cmarinre.wallet.util;

import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Util {

  public static String generateUniqueId(String text) {
    UUID id = UUID.nameUUIDFromBytes(text.getBytes());
    String str=""+id;
    int uid=str.hashCode();
    String filterStr=""+uid;
    str=filterStr.replaceAll("-", "");
    return str;
  }

}
