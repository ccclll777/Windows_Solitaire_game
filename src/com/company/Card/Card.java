package com.company.Card;
/*
定义牌的一些属性，包括花色、数值、是否正面朝上等。
               定义常量表示花色的角标，定义两个数组分别维护牌的花色和数值。

 */
public class Card implements Card_Interface{
    //变量声明为私有，通过共有的方法改变

    private  final Rank aRank;//大小
    private  final Suit aSuit;//花色
    private boolean faceUp = false;//扑克牌是否正面朝上
    private boolean isRed ;
    //方法为共有的
    //52张牌的初始化
      private static final Card[][] CARDS = new Card[Suit.values().length][];
      // 初始化每一张牌的花色和大小，生成一个编号，以便可以通过编号获取相对应的卡牌
    static
    {
        for( Suit suit : Suit.values() )
        {
            CARDS[suit.ordinal()] = new Card[Rank.values().length];
            for( Rank rank : Rank.values() )
            {
                CARDS[suit.ordinal()][rank.ordinal()] = new Card(rank, suit);
            }
        }
    }
    //构造方法
    public Card(Rank pRank, Suit pSuit)
    {
        aRank = pRank;
        aSuit = pSuit;
        if(pSuit == Suit.HEARTS || pSuit == Suit.DIAMONDS)
        {
            isRed = true;
        }
        else
        {
            isRed = false;
        }

    }
    //获取这张牌的编号
    public String getIDString()
    {
        return Integer.toString(getSuit().ordinal() * Rank.values().length + getRank().ordinal());
    }

    //返回这张牌是否朝上
    public boolean isFaceUp() {
        return faceUp;
    }
    //设置这张牌的朝向
    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }
    //返回是否为红色
    public boolean isRed() {
        return isRed;
    }
    //获得花色
    public Suit getSuit() {
        return this.aSuit;
    }
    //根据编号获取对应的牌
    public static Card get(String pId)
    { if(pId != null)
        {
            int id = Integer.parseInt(pId);
            return get(Rank.values()[id % Rank.values().length],
                    Suit.values()[id / Rank.values().length]);
        }

            return null; }
    //根据花色和大小获取对应的牌
    public static Card get(Rank pRank, Suit pSuit)
    {
        if( pRank != null && pSuit != null)
        {
            return CARDS[pSuit.ordinal()][pRank.ordinal()];
        }

            return null;
    }
    //获取牌的大小
    public Rank getRank() {
        return this.aRank;
        }


}
