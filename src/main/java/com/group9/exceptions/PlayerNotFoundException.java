/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group9.exceptions;

/**
 *
 * @author Black
 */
public class PlayerNotFoundException extends Exception
{
  public PlayerNotFoundException(String message)
  {
    super(message);
  }
}