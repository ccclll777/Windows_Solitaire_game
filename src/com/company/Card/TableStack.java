package com.company.Card;

//桌面堆
public class TableStack extends CardStack {


    public int  getCardIndex(Rank pRank, Suit pSuit) {

        if(!this.isEmpty())
        {//从顶部开始往下看 正面的卡牌，如果这个卡牌是正面的 并且他是我们要找的那张，就可以选择他
            for(int i = this.size() -1 ; i >= 0 ; i--)
            {

                    if(this.peek(i).getRank() == pRank && this.peek(i).getSuit() == pSuit)
                        return i;
            }
        }

        return -1;
    }
    public TableStack getSubStack(Card pCard, TableStack stack)
    {
        if(pCard != null)
        {
            int index = stack.getCardIndex(pCard.getRank(),pCard.getSuit());
            TableStack temp_stack = new TableStack();
            for(int i = index ; i< stack.size() ; i++)
            {
                temp_stack.init(stack.peek(i));

            }
            return temp_stack;

        }
        return null;
    }



}
