import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font

class GameEnd (boolean: Boolean, score: Int, final :Boolean) {
    private var gameOver = StackPane()
    var my = Group(gameOver)

    init {
        if(final && boolean){
            val table = Rectangle(390.0, 240.0, Color.WHITE)
            table.arcHeight = 50.0
            table.arcWidth = 50.0
            val display_Score = Label("You win and finish the level3. Score : $score")
            val display_Quit = Label(" press Q to quit")
            display_Score.font = Font("Verdana", 16.0)
            display_Quit.font = Font("Verdana", 16.0)
            display_Score.textFill = Color.BLACK
            display_Quit.textFill = Color.BLACK
            val instructions = VBox(10.0)
            instructions.children.add(display_Quit)
            instructions.children.add(display_Score)
            instructions.alignment = Pos.CENTER
            gameOver = StackPane(table, instructions)
            gameOver.layoutX = 300.0
            gameOver.layoutY = 250.0
        }else {
            /* Style Back Ground */
            val table = Rectangle(390.0, 240.0, Color.WHITE)
            table.arcHeight = 50.0
            table.arcWidth = 100.0

            /* Create Labels */
            val display_Result = Label("GAME OVER")
            var display_Score = Label("Score")
            if (boolean) {
                display_Score = Label("You win. Score : $score")
            } else {
                display_Score = Label("You lose. Score : $score")
            }
            var display_Next = Label("press Enter to continue")
            if (boolean) {
                 display_Next = Label("press Enter to continue")
            } else {
                display_Next = Label("press Enter to restart")
            }


            val display_Quit = Label("if you want to quit or you at level3, press Q")


            display_Result.font = Font("Verdana", 30.0)
            display_Score.font = Font("Verdana", 16.0)
            display_Next.font = Font("Verdana", 16.0)
            display_Quit.font = Font("Verdana", 16.0)
            display_Result.textFill = Color.BLACK
            display_Score.textFill = Color.BLACK
            display_Next.textFill = Color.BLACK
            display_Quit.textFill = Color.BLACK
            display_Result.style = "-fx-font-weight: bold"
            val instructions = VBox(10.0)
            instructions.children.add(display_Result)
            instructions.children.add(display_Score)
            instructions.children.add(display_Next)
            instructions.children.add(display_Quit)
            instructions.alignment = Pos.CENTER
            gameOver = StackPane(table, instructions)
            gameOver.layoutX = 300.0
            gameOver.layoutY = 250.0
        }

    }

    fun getGameOver(): StackPane {
        return gameOver
    }
}