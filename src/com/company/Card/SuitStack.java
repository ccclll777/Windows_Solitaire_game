package com.company.Card;

public class SuitStack  extends CardStack{


    @Override
    public void push(CardStack pStack, int index) {
        super.push(pStack, index);
        if (!this.isEmpty()) {
            this.peek().setFaceUp(true);
        }
    }










}
