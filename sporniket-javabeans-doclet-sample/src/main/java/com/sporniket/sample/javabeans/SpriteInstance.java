/**
 * 
 */
package com.sporniket.sample.javabeans;

/**
 * An instance of sprite is what is displayed on screen, defined by a {@link SpriteDefinition} and a position.
 * 
 * @author dsporn
 *
 */
public class SpriteInstance
{
	/**
	 * <code>true</code> if the instance is to be displayed.
	 */
	private boolean isActive;

	private SpriteDefinition myDefinition;

	private Point myPosition;

	public SpriteDefinition getDefinition()
	{
		return myDefinition;
	}

	public Point getPosition()
	{
		return myPosition;
	}

	public Point getTopLeftPosition()
	{
		int _left = getPosition().getX() - getDefinition().getHotPoint().getX();
		int _top = getPosition().getY() - getDefinition().getHotPoint().getY();
		Point _result = new Point().withX(_left).withY(_top);
		return _result;
	}

	public boolean isActive()
	{
		return isActive;
	}

	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}

	public void setDefinition(SpriteDefinition definition)
	{
		myDefinition = definition;
	}

	public void setPosition(Point position)
	{
		myPosition = position;
	}

	public SpriteInstance withActive(boolean isActive)
	{
		setActive(isActive);
		return this;
	}

	public SpriteInstance withDefinition(SpriteDefinition definition)
	{
		setDefinition(definition);
		return this;
	}

	public SpriteInstance withPosition(Point position)
	{
		setPosition(position);
		return this;
	}
}
