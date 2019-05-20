package com.company.Card;

import javafx.scene.image.Image;

public class CardImages {
    //获取卡牌对应的照片

    public Image getCard(Card pCard)
    {
        if(pCard != null)
        {
            Image image = new Image(getClass().getResourceAsStream("../resources/"+pCard.getSuit()+pCard.getRank()+".gif"));
            return image;
        }

        return null;


    }

    public Image getBack()
    {
        Image image = new Image(getClass().getResourceAsStream("../resources/back.jpg"));
        return image;
    }


}
