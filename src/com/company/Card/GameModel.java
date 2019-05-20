package com.company.Card;
import com.company.GUI.CardPileView;
import com.company.GUI.GameModelListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public final class  GameModel {

    private static final GameModel INSTANCE = new GameModel();
     private  ArrayList<CardStack> card_Stacks ;//存放桌面上的所有牌堆
    private Stack<ArrayList> regret_stack;
    private int fromIndex;
    private DeckStack deck_Stack;//0
    private DisCardStack disCard_Stack;//1
    private TableStack[] table_Stacks;//2-8
    private SuitStack[] suit_Stacks;//9-12
    private static final String SEPARATOR = ";";
    private String level = "low";
    private final List<GameModelListener> aListeners = new ArrayList<GameModelListener>();
    public GameModel()
    {
        init();
    }
    public void init()
    {
        this.regret_stack = new Stack<ArrayList>();
        this.table_Stacks = new  TableStack[7];
        for(int i = 0 ; i < table_Stacks.length ; i++)
        {
            this.table_Stacks[i] = new TableStack();
        }
        this.deck_Stack = new DeckStack();
        this.disCard_Stack = new DisCardStack();
        this.suit_Stacks = new SuitStack[4];
        for(int i = 0 ; i< suit_Stacks.length ; i++)
        {
            this.suit_Stacks[i] = new SuitStack();
        }
        this.card_Stacks = new ArrayList<CardStack>();
        this.card_Stacks.add(this.deck_Stack);//0
        this.card_Stacks.add(this.disCard_Stack);//1
        for(int i = 0 ; i < table_Stacks.length ; i ++)
        {
            this.card_Stacks.add(this.table_Stacks[i]);//2-8
        }
        for(int i = 0 ; i < suit_Stacks.length ; i ++)
        {
            this.card_Stacks.add(this.suit_Stacks[i]);//9-12
        }
         Random random = new Random();
        ArrayList<Card> normal_Rank = new ArrayList();
        Card temp_card = null;
        // 使用normal_Rank暂存52张卡牌的信息
        for( Suit suit : Suit.values() )
        {
            for( Rank rank : Rank.values() )
            {
                temp_card = new Card(rank, suit);
                normal_Rank.add(temp_card);
            }
        }
        int i;
        //随机主七个主牌堆的牌
        int index = 0;
        for( i = 0; i < 7; ++i) {
            for(int j = 0; j <= i; ++j) {
                while (true)
                {
                    index = random.nextInt(normal_Rank.size());
                    temp_card = (Card)normal_Rank.get(index);
                    if( !this.table_Stacks[i].isEmpty() && temp_card.isRed() != this.table_Stacks[i].peek().isRed())
                    {
                        this.table_Stacks[i].init(temp_card);
                        normal_Rank.remove(index);
                        break;
                    }
                    if(this.table_Stacks[i].isEmpty() )
                    {
                        this.table_Stacks[i].init(temp_card);
                        normal_Rank.remove(index);
                        break;
                    }
                }
            }
        }
        //将每个牌堆的顶端翻正
        for(i = 0; i < 7; ++i) {
            this.table_Stacks[i].peek().setFaceUp(true);
        }
        //将剩余牌放入发牌堆
        for(i = 0; i < 24; ++i) {
            index = random.nextInt(normal_Rank.size());
            temp_card = (Card)normal_Rank.get(index);
            this.deck_Stack.init(temp_card);
            normal_Rank.remove(index);
        }
    }
    //获取牌堆
    public CardStack getStack(int index) {
        return this.card_Stacks.get(index);
    }
    //获取实例
    public static GameModel instance()
    {
        return INSTANCE;
    }
    //设置卡牌移动的监听器
    public void addListener(GameModelListener pListener)
    {
       if(pListener != null);
        aListeners.add(pListener);
    }
    //在卡牌移动后，更新桌面
    private void notifyListeners()
    {
        for( GameModelListener listener : aListeners )
        {
            listener.gameStateChanged();
        }
    }
    //返回卡牌的子序列，点击桌面堆的七个牌堆的子序列。
    public CardStack getSubStack(Card pCard, int aIndex)
    {
        TableStack stack = (TableStack) GameModel.instance().getStack(aIndex);
        TableStack temp_stack = stack.getSubStack(pCard,stack);

            return temp_stack;



    }
    //判断 移动是否合法
    public boolean isLegalMove(Card pCard,int aIndex )
    {
        if(aIndex >=1 && aIndex<= 8)
        {
                return canMoveToTableStack(pCard,aIndex);
        }
        else if(aIndex>= 9 && aIndex <=12)
        {
                return canMoveToSuitStack(pCard,aIndex);
        }

        return false;
    }
    //是否能移到桌面堆
    public boolean  canMoveToTableStack(Card pCard,int aIndex)
    {
        if(level == "high")
        {
            if(pCard!=null)
            {
                CardStack temp_stack = getStack(aIndex);
                if( temp_stack.isEmpty() )
                {

                    return pCard.getRank() == Rank.KING;
                }
                else
                {
                    return pCard.getRank().ordinal() == temp_stack.peek().getRank().ordinal()-1 &&
                            pCard.isRed() != temp_stack.peek().isRed();
                }

            }
        }
        else if(level == "low")
        {

            if(pCard!=null)
            {
                CardStack temp_stack = getStack(aIndex);
                if( temp_stack.isEmpty() )
                {

                    return true;
                }
                else
                {
                    return pCard.getRank().ordinal() == temp_stack.peek().getRank().ordinal()-1 &&
                            pCard.isRed() != temp_stack.peek().isRed();
                }

            }
        }

        return false;
    }
    //是否能移到花色堆堆
    public boolean  canMoveToSuitStack(Card pCard,int aIndex)
    {
        assert pCard != null ;
        CardStack temp_stack = getStack(aIndex);
        if(temp_stack.isEmpty())
        {
            return  pCard.getRank() == Rank.ACE ;
        }
        else
        {
            return pCard.getRank().ordinal() == temp_stack.peek().getRank().ordinal()+1 &&
                    pCard.getSuit()==temp_stack.peek().getSuit();
        }


    }

    public void Desc_to_DisCard()
    {
        Card temp_card = this.getStack(0).peek();
        this.getStack(1).init(temp_card);
        this.getStack(0).pop();
        notifyListeners();

    }
    //移动牌堆
        public boolean moveCard(CardStack from,int aIndex)
        { if
                (aIndex>=2 && aIndex<=8)
            {
                TableStack to = (TableStack)this.getStack(aIndex);
                if (!to.isEmpty())
                {
                    this.getStack(aIndex).push(from);
                    pop_from(from);
                    notifyListeners();
                    setRegret_stack();
                        return true;


        } else if (from.isEmpty()) {

            return false;
        } else {

                        this.getStack(aIndex).push(from);
                        pop_from(from);
                    setRegret_stack();
                        notifyListeners();


        }
            }
            else  if(aIndex>=9 && aIndex<= 12)
            {
                SuitStack to = (SuitStack) this.getStack(aIndex);
                if (to.isEmpty())
                {//获取他的下个一卡牌
                    this.getStack(aIndex).push(from);
                    pop_from(from);
                    notifyListeners();
                    setRegret_stack();
                return true;

                 }
                 else
                     {
                         this.getStack(aIndex).push(from);
                         pop_from(from);
                         setRegret_stack();
                         notifyListeners();
                     }
            }
            return true;
        }

    public void pop_from(CardStack to)
    {
        for(int j = 0 ; j < to.size() ; j ++)
            {

                this.getStack(fromIndex).pop(to.peek(j));

            }
            if(!this.getStack(fromIndex).peek().isFaceUp())
       {
           this.getStack(fromIndex).peek().setFaceUp(true);
       }

    }
    //如果发牌区全部发完并且discardstack也没有牌
    public void reset_all()
    {
        init();
        notifyListeners();
    }
    //如果发牌区全部发完  但是discardstack有牌
    public void reset()
    {
        for(int i = 0 ; i < this.getStack(1).size() ; i++)
        {
            this.getStack(0).init(this.getStack(1).peek(i));
        }

           this.getStack(1).clear();

        notifyListeners();
    }
    public CardStack StringToStack(String pString)
    {

       if(pString != null && pString.length() > 0)
       {
           String[] tokens = pString.split(SEPARATOR);
           CardStack aCards = new CardStack();
           for( int i = 0; i < tokens.length; i++ )
           {
               aCards.init(Card.get(tokens[i]));
           }
           for(int i = 0 ; i<aCards.size() ; i ++)
           {
               aCards.peek(i).setFaceUp(true);
           }
           return aCards;
       }
        return null;
    }
    public Card getTop(String result)
    {
        if( result != null && result.length() > 0)
        {
            String[] tokens = result.split(SEPARATOR);
            Card aCards [];
            aCards = new Card[tokens.length];
            for( int i = 0; i < tokens.length; i++ )
            {
                aCards[i] = Card.get(tokens[i]);
            }
            return aCards[0];
        }
        return null;
    }
    public String serialize(Card pCard, int aIndex)
    {

        CardStack temp_stack =  GameModel.instance().getSubStack(pCard, aIndex);
        System.out.println(temp_stack.size());
        String result = "";
        Card temp_card ;
        for(int i = 0 ; i< temp_stack.size() ; i++)
        {
            temp_card = temp_stack.peek(i);
            result += temp_card.getIDString()+SEPARATOR;
        }

        if( result.length() > 0)
        {
            result = result.substring(0, result.length()-1);
        }

        return result;
    }


    public void setFromIndex(int fromIndex) {
        this.fromIndex = fromIndex;
    }

    public void setRegret_stack()
    {
        regret_stack.push(this.card_Stacks);
    }

    public void getRegret_stack()
    {
        this.card_Stacks.clear();
        this.card_Stacks = regret_stack.peek();
        regret_stack.pop();
        notifyListeners();
    }

    public void setLevel(String level)
    {
        this.level = level;
    }

}