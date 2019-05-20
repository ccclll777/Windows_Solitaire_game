package com.company.Card;

public interface Card_Interface {

      Rank aRank = null;//大小
      Suit aSuit = null;//花色
      boolean faceUp = false;//扑克牌是否正面朝上
      boolean isRed = false;
 //返回这张牌是否朝上
     boolean isFaceUp();
     //设置这张牌的朝向
     void setFaceUp(boolean faceUp);
     //返回是否为红色
     boolean isRed();
     //获得花色
     Suit getSuit();
     Rank getRank();
     String getIDString();


}
