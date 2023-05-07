import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.text.Font
import javafx.stage.Stage

class Main : Application() {
    override fun start(stage: Stage) {


        stage.title = "Space Invaders"
        stage.width = 1280.0
        stage.height = 800.0
        stage.isResizable = false



        val logo = Image("images/logo.png")
        val logoView = ImageView(logo)
        logoView.setFitHeight(170.0)
        logoView.setFitWidth(400.0)
        logoView.setLayoutX(400.0)
        logoView.setLayoutY(110.0)


        val instructions = Label("Instructions")
        val startGame_label = Label("ENTER - Start Game")
        val move_label = Label("A or <, D or > - Move ship left or right")
        val fire_label = Label("SPACE - Fire!")
        val q_label = Label("Q - Quit Game")
        val levels_label = Label("1 or 2 or 3 - Start Game at Specific Level")
        val copyRight_label = Label("Implemented by Haiyao Chen (20822211) for CS 349, University of Waterloo, S22")
        instructions.setLayoutX(500.0)
        instructions.setLayoutY(340.0)
        instructions.font = Font("Verdana", 26.0)
        copyRight_label.setLayoutY(720.0)
        copyRight_label.setLayoutX(370.0)
        copyRight_label.font = Font("Verdana", 15.0)
        instructions.style = "-fx-font-weight: bold"
        startGame_label.setLayoutY(500.0)
        startGame_label.setLayoutX(520.0)
        startGame_label.font = Font("Verdana", 20.0)
        levels_label.setLayoutX(440.0)
        levels_label.setLayoutY(600.0)
        levels_label.font = Font("Verdana", 20.0)
        move_label.setLayoutX(450.0)
        move_label.setLayoutY(525.0)
        move_label.font = Font("Verdana", 20.0)
        q_label.setLayoutX(540.0)
        q_label.setLayoutY(575.0)
        q_label.font = Font("Verdana", 20.0)
        fire_label.setLayoutY(550.0)
        fire_label.setLayoutX(540.0)
        fire_label.font = Font("Verdana", 20.0)






        val root = Group(logoView, instructions, startGame_label, move_label, fire_label, q_label, levels_label,
            copyRight_label)
        var titleScreen = Scene(root)


        val game = GameScene()
        val gameScreen: Scene = game.getScene()



        titleScreen.onKeyPressed = EventHandler { keyEvent: KeyEvent ->
            if (keyEvent.code == KeyCode.ENTER || keyEvent.code == KeyCode.DIGIT1) {               //Level 1
                game.level = 1;
                game.startGame()
                stage.setScene(gameScreen)

            } else if (keyEvent.code == KeyCode.DIGIT2) {                                                //Level 2
                game.level =2;
                game.startGame()
                stage.setScene(gameScreen)
            } else if (keyEvent.code == KeyCode.DIGIT3) {                                                  //Level 3
                game.level =3;
                game.startGame()
                stage.setScene(gameScreen)
            } else if (keyEvent.code == KeyCode.Q) {                                                      //Quit Game
                System.exit(0)
            }
        }

        stage.scene = titleScreen
        stage.show()
    }
}