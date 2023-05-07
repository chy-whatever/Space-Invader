import javafx.scene.image.Image
import javafx.scene.image.ImageView

class Player {

        var playerView = ImageView(Image("${System.getProperty("user.dir")}/src/main/resources/images/player.png"))
        var X = 370.0
        var X_MAX = 1220.0
        var X_MIN = 0.0

        fun makePlayer() {
            playerView.setFitHeight(40.0)
            playerView.setFitWidth(40.0)
            playerView.setLayoutX(370.0)
            playerView.setLayoutY(700.0)
        }

        fun getPlayer(): ImageView? {
            return this.playerView
        }

        fun moveRight() {
            if (X + 10 < X_MAX) {
                X += 10.0
                this.playerView!!.setLayoutX(X)
            }
        }

        fun moveLeft() {
            if (X - 10 > X_MIN) {
                X -= 10.0
                playerView!!.setLayoutX(X)
            }
        }

        fun get_X(): Double {
            return X
        }



}