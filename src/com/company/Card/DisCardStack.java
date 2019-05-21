package com.company.Card;

//弃牌堆
public class DisCardStack extends CardStack{

    public void push(CardStack pStack, int index) {
        super.push(pStack, index);
        //添加一组卡牌后   卡牌的顶部应该是朝上的
      if(!this.isEmpty())
          this.peek().setFaceUp(true);
      }

}
