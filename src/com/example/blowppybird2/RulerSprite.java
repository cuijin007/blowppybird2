package com.example.blowppybird2;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

public class RulerSprite{

	CCSprite ruler;
	
	public RulerSprite()
	{
		this.ruler=CCSprite.sprite("birds.png");
	}
	public CCSprite GetRulerSprite()
	{
		return ruler;
	}
	public void SetPosition(float x,float y)
	{
		this.ruler.setPosition(CGPoint.ccp(x, y));
	}
}
