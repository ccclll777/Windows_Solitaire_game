package com.company.GUI;

import com.company.Card.GameModel;
import com.company.Card.Suit;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Solitaire_GUI extends Application {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 600;
    private static final int MARGIN_OUTER = 15;
    private static final String TITLE = "Solitaire";
    private DeckPileView aDeckView = new DeckPileView();// 待用堆
    private Restart restart = new Restart();
    private Mode_selection mode_selection = new Mode_selection();
    private DiscardPileView aDiscardPileView = new DiscardPileView();//初始化四个花色的弃牌堆数组
    private SuitPileView[] aSuitStacks = new SuitPileView[Suit.values().length];//花色堆
    private TablePileView[] aStacks = new TablePileView[TablePile.values().length]; //初始化玩牌区的数
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage pPrimaryStage)//启动函数
    {
        pPrimaryStage.setTitle(TITLE );//设定标题
        GridPane grid = new GridPane();//布局方式
        grid.setStyle("-fx-background-color: write;");
        grid.setHgap(MARGIN_OUTER);
        grid.setVgap(MARGIN_OUTER);
        grid.setPadding(new Insets(MARGIN_OUTER));//设置边界为10
        grid.add(aDeckView, 1, 0);//将节点水平放置
        grid.add(aDiscardPileView, 2, 0);//将节点竖直放置
        grid.add(restart,8,9);//重新开始游戏
        grid.add(mode_selection,7,9);
//        grid.add(regret_game,7,9);//悔牌

        for( SuitPile index : SuitPile.values() )
        {
            aSuitStacks[index.ordinal()] = new SuitPileView(index);
            grid.add(aSuitStacks[index.ordinal()], 4+index.ordinal(), 0);
        }
        for( TablePile index : TablePile.values() )
        {
            aStacks[index.ordinal()] = new TablePileView(index);
            grid.add(aStacks[index.ordinal()], index.ordinal()+1, 1);

        }
        if(GameModel.instance().getStack(9).size() == 13 &&GameModel.instance().getStack(10).size() == 13&&GameModel.instance().getStack(11).size() == 13&&GameModel.instance().getStack(12).size() == 13)
        {
            Text t = new Text();
            t.setText("游戏通关成功!");
            t.setFont(Font.font ("Verdana"));
            t.setFill(Color.BLACK);

            grid.add(t,4,9);
        }
        pPrimaryStage.setResizable(false);
        pPrimaryStage.setScene(new Scene(grid, WIDTH, HEIGHT));
        pPrimaryStage.show();
    }
}
