import javafx.scene.image.Image
import javafx.scene.image.ImageView

class PlayerBullet {
    var playerBulletView: ImageView? = null
    var Y = 700.0
    var X = 18.0

    fun makePlayerBullet() {
        val playerBullet = Image("${System.getProperty("user.dir")}/src/main/resources/images/player_bullet.png")
        playerBulletView = ImageView(playerBullet)
        playerBulletView!!.setFitHeight(20.0)
        playerBulletView!!.setFitWidth(5.0)
    }


    fun setPosition(position_X: Double) {
        X = position_X
        playerBulletView!!.setLayoutX(X)
        playerBulletView!!.setLayoutY(Y)
    }

    fun moveUp(position_Y: Double) {
        Y -= position_Y
        playerBulletView!!.setLayoutX(X)
        playerBulletView!!.setLayoutY(Y)
    }

    fun getPlayerBullet(): ImageView? {
        return playerBulletView
    }
}