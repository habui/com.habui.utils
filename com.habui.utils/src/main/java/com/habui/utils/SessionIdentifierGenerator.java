/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.habui.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 *
 * @author habui
 */
public final class SessionIdentifierGenerator
{

  private SecureRandom random = new SecureRandom();

  public String nextSessionId()
  {
    return new BigInteger(130, random).toString(32);
  }

}
