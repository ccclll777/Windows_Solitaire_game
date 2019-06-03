package com.company.GUI;

import com.company.Card.CardImages;
import com.company.Card.GameModel;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Mode_selection extends HBox {

    private static final String BUTTON_STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 5, 5, 5, 5;";
    private static final String BUTTON_STYLE_PRESSED = "-fx-background-color: transparent; -fx-padding: 6 4 4 6;";
    private static final int IMAGE_NEW_LINE_WIDTH = 10;
    private static final int IMAGE_FONT_SIZE = 15;
//    private static CardImages cardImages = new CardImages();
    private String level = "low";

    Mode_selection()
    {

        final Button button = new Button();
        button.setGraphic(createNewGameImage());
        button.setStyle(BUTTON_STYLE_NORMAL);

        button.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent pEvent)
            {
                ((Button)pEvent.getSource()).setStyle(BUTTON_STYLE_PRESSED);
                if(level == "low")
                {
                    level = "high";
                    GameModel.instance().setLevel(level);
                    button.setGraphic(createNewGameImage());
                }
                else if(level == "high")
                {
                    level = "low";
                    GameModel.instance().setLevel(level);
                    button.setGraphic(createNewGameImage());
                }


            }
        });

        button.setOnMouseReleased(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent pEvent)
            {
                ((Button)pEvent.getSource()).setStyle(BUTTON_STYLE_NORMAL);

            }
        });

        getChildren().add(button);
    }

    private Canvas createNewGameImage()
    {

        double width =  CardImages.getBack().getWidth();
        double height =  CardImages.getBack().getHeight();
        Canvas canvas = new Canvas( width, height );
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setFill(Color.DARKKHAKI);
        context.setLineWidth(IMAGE_NEW_LINE_WIDTH);
        context.strokeOval(width/4, height/2-width/4 + IMAGE_FONT_SIZE, width/2, width/2);
        context.setTextAlign(TextAlignment.CENTER);
        context.setTextBaseline(VPos.CENTER);
        context.setFill(Color.DARKKHAKI);
        context.setFont(Font.font(Font.getDefault().getName(), IMAGE_FONT_SIZE));
        if(level == "low")
        {
            context.fillText("low", Math.round(width/2), IMAGE_FONT_SIZE);
        }
        else if(level == "high")
        {
            context.fillText("high", Math.round(width/2), IMAGE_FONT_SIZE);
        }

        context.setTextAlign(TextAlignment.CENTER);
        return canvas;
    }
}
