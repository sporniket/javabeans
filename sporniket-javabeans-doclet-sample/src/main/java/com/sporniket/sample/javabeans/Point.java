package com.sporniket.sample.javabeans;

import java.beans.*;
import java.util.*;

/**
 * Coordinate (x,y)..
 *
 * A point
 * 
 * LGPL v3
 * 
 *
 * @version 15.05.01-SNAPSHOT
 * @author David SPORN
 */
public class Point
{

	/**
	 * Property "x" : .
	 */
	private Integer myX;

	/**
	 * Property "y" : .
	 */
	private Integer myY;

	/**
	 * Read accessor for property "x" : .
	 *
	 * 
	 *
	 * @return Property "x".
	 *
	 */
	public Integer getX()
	{
		return myX;
	}

	/**
	 * Read accessor for property "y" : .
	 *
	 * 
	 *
	 * @return Property "y".
	 *
	 */
	public Integer getY()
	{
		return myY;
	}

	/**
	 * Write accessor for property "x" : .
	 *
	 * 
	 *
	 * @param x
	 *            Value of property "x".
	 *
	 */
	public void setX(Integer x)
	{
		myX = x;
	}

	/**
	 * Write accessor for property "y" : .
	 *
	 * 
	 *
	 * @param y
	 *            Value of property "y".
	 *
	 */
	public void setY(Integer y)
	{
		myY = y;
	}

	/**
	 * Fluent write accessor for property "x" : .
	 *
	 * 
	 *
	 * @param x
	 *            Value of property "x".
	 *
	 */
	public Point withX(Integer x)
	{
		setX(x);
		return this;
	}

	/**
	 * Fluent write accessor for property "y" : .
	 *
	 * 
	 *
	 * @param y
	 *            Value of property "y".
	 *
	 */
	public Point withY(Integer y)
	{
		setY(y);
		return this;
	}

}
