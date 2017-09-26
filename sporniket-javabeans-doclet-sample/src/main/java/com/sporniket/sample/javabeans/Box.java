package com.sporniket.sample.javabeans;

import java.beans.*;
import java.util.*;

/**
 * A box described by a corner (x,y), width (x axis) and height (y axis)..
 *
 * A point is inside the box if x<=px<(x+width) and y<=py<(y+height).
 * 
 * LGPL v3
 * 
 *
 * @version 15.05.01-SNAPSHOT
 * @author David SPORN
 */
public class Box
{

	/**
	 * Property "height" : .
	 */
	private Integer myHeight;

	/**
	 * Property "width" : .
	 */
	private Integer myWidth;

	/**
	 * Property "x" : .
	 * */
	private Integer myX;

	/**
	 * Property "x2" : .
	 * */
	private int myX2;

	/**
	 * flag telling that x2 needs to be computed
	 */
	private boolean myX2Dirty = true;

	/**
	 * Property "y" : .
	 */
	private Integer myY;

	/**
	 * Property "y2" : .
	 * */
	private int myY2;

	/**
	 * flag telling that y2 needs to be computed
	 */
	private boolean myY2Dirty = true;

	/**
	 * Read accessor for property "height" : .
	 *
	 * 
	 *
	 * @return Property "height".
	 *
	 */
	public Integer getHeight()
	{
		return myHeight;
	}

	/**
	 * Read accessor for property "width" : .
	 *
	 * 
	 *
	 * @return Property "width".
	 *
	 */
	public Integer getWidth()
	{
		return myWidth;
	}

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

	public int getX2()
	{
		if (isX2Dirty())
		{
			setX2(getX() + getWidth());
			setX2Dirty(false);
		}
		return myX2;
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

	public int getY2()
	{
		if (isY2Dirty())
		{
			setY2(getY() + getWidth());
			setY2Dirty(false);
		}
		return myY2;
	}

	/**
	 * Write accessor for property "height" : .
	 *
	 * 
	 *
	 * @param height
	 *            Value of property "height".
	 *
	 */
	public void setHeight(Integer height)
	{
		myHeight = height;
		setY2Dirty(true);
	}

	/**
	 * Write accessor for property "width" : .
	 *
	 * 
	 *
	 * @param width
	 *            Value of property "width".
	 *
	 */
	public void setWidth(Integer width)
	{
		myWidth = width;
		setX2Dirty(true);
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
		setX2Dirty(true);
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
		setY2Dirty(true);
	}

	/**
	 * Fluent write accessor for property "height" : .
	 *
	 * 
	 *
	 * @param height
	 *            Value of property "height".
	 *
	 */
	public Box withHeight(Integer height)
	{
		setHeight(height);
		return this;
	}

	/**
	 * Fluent write accessor for property "width" : .
	 *
	 * 
	 *
	 * @param width
	 *            Value of property "width".
	 *
	 */
	public Box withWidth(Integer width)
	{
		setWidth(width);
		return this;
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
	public Box withX(Integer x)
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
	public Box withY(Integer y)
	{
		setY(y);
		return this;
	}

	private boolean isX2Dirty()
	{
		return myX2Dirty;
	}

	private boolean isY2Dirty()
	{
		return myY2Dirty;
	}

	private void setX2(int x2)
	{
		myX2 = x2;
	}

	private void setX2Dirty(boolean x2Dirty)
	{
		myX2Dirty = x2Dirty;
	}

	private void setY2(int y2)
	{
		myY2 = y2;
	}

	private void setY2Dirty(boolean y2Dirty)
	{
		myY2Dirty = y2Dirty;
	}

}