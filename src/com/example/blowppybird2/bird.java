package com.example.blowppybird2;

import java.util.ArrayList;

import org.cocos2d.actions.interval.CCJumpTo;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCTextureCache;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

public class bird {

	CCSprite birdSprite;
	CGPoint basePosition;
	public bird()
	{
		this.birdSprite=CCSprite.sprite(CCTextureCache.sharedTextureCache().addImage("birds.png"), CGRect.make(0,0, 52, 45));
		basePosition=CGPoint.ccp(100, 100);
	}
	public CCSprite GetSprite()
	{
		return this.birdSprite;
	}
	//��ײ��⣬����ֵΪ��ײ�ľ��飬����-1Ϊɶ��û������
	public int CollisionDetectionSprite(ArrayList<CCSprite> spriteList)
	{
		for(int i=0;i<spriteList.size();i++)
		{
			if(CGRect.intersects(this.birdSprite.getBoundingBox(), spriteList.get(i).getBoundingBox()))
			{
				return i;
			}
		}
		return -1;
	}
	
	public boolean CollisionDetectionRect()
	{
		return true;
	}
	//��Ծ
	public void Jump()
	{
		CGPoint previousPoint=this.birdSprite.getPosition();
		CGPoint point=CGPoint.ccp(previousPoint.x, previousPoint.y+100);
		CCJumpTo jump=CCJumpTo.action(1, basePosition, previousPoint.y+100, 1);
		this.birdSprite.runAction(jump);
	}
}
