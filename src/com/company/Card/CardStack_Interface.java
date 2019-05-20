package com.company.Card;

import java.util.ArrayList;

public interface CardStack_Interface {
    // 存放卡牌

    ArrayList<Card> pokers_card = null;

    //获取list的大小

     int size();
    ArrayList<Card> getPokers_card();
    void init(Card pCard);
    //获取顶部卡片
     Card peek();

    //获取指定位置的卡片
     Card peek(int index);

    //list是否为空
     boolean isEmpty();
     //push一组卡牌
//    void push(CardStack pStack, int index);

    //删除一组卡牌
    void pop(Card pCard);


    //删除顶部卡片
    void pop();
  void clear();
}
