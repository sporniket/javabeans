package com.sporniket.sample.javabeans;


/**
 * A sprite is basically a box from a source canvas that is positionned on a target canvas using a "hot point"..
 *
 * The hot point is relative to the (x,y) corner. When positionning a object described by a Sprite box, the position of this object
 * is the position of the hot point, thus x and y are computed accordingly. To look up more easily a sprite, it is identifiable by
 * an identifier.
 * 
 * LGPL v3
 * 
 *
 * @version 15.05.01-SNAPSHOT
 * @author David SPORN
 */
public class SpriteDefinition
{
	/**
	 * Property "hotPoint" : .
	 */
	private Point myHotPoint;

	/**
	 * Property "id" : .
	 */
	private String myId;

	/**
	 * Property "sourceBox" : .
	 */
	private Box mySourceBox;

	/**
	 * Read accessor for property "hotPoint" : .
	 *
	 * 
	 *
	 * @return Property "hotPoint".
	 *
	 */
	public Point getHotPoint()
	{
		return myHotPoint;
	}

	/**
	 * Read accessor for property "id" : .
	 *
	 * 
	 *
	 * @return Property "id".
	 *
	 */
	public String getId()
	{
		return myId;
	}

	/**
	 * Read accessor for property "sourceBox" : .
	 *
	 * 
	 *
	 * @return Property "sourceBox".
	 *
	 */
	public Box getSourceBox()
	{
		return mySourceBox;
	}

	/**
	 * Write accessor for property "hotPoint" : .
	 *
	 * 
	 *
	 * @param hotPoint
	 *            Value of property "hotPoint".
	 *
	 */
	public void setHotPoint(Point hotPoint)
	{
		myHotPoint = hotPoint;
	}

	/**
	 * Write accessor for property "id" : .
	 *
	 * 
	 *
	 * @param id
	 *            Value of property "id".
	 *
	 */
	public void setId(String id)
	{
		myId = id;
	}

	/**
	 * Write accessor for property "sourceBox" : .
	 *
	 * 
	 *
	 * @param sourceBox
	 *            Value of property "sourceBox".
	 *
	 */
	public void setSourceBox(Box sourceBox)
	{
		mySourceBox = sourceBox;
	}

	/**
	 * Fluent write accessor for property "hotPoint" : .
	 *
	 * 
	 *
	 * @param hotPoint
	 *            Value of property "hotPoint".
	 *
	 */
	public SpriteDefinition withHotPoint(Point hotPoint)
	{
		setHotPoint(hotPoint);
		return this;
	}

	/**
	 * Fluent write accessor for property "id" : .
	 *
	 * 
	 *
	 * @param id
	 *            Value of property "id".
	 *
	 */
	public SpriteDefinition withId(String id)
	{
		setId(id);
		return this;
	}

	/**
	 * Fluent write accessor for property "sourceBox" : .
	 *
	 * 
	 *
	 * @param sourceBox
	 *            Value of property "sourceBox".
	 *
	 */
	public SpriteDefinition withSourceBox(Box sourceBox)
	{
		setSourceBox(sourceBox);
		return this;
	}

}
