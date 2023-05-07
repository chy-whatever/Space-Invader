import javafx.scene.image.Image
import javafx.scene.image.ImageView

class EnemyBullet {
    var enemyView: ImageView? = null
    var mycount = 0
    var my_X = 0.0
    var my_Y = 0.0

    fun makeEnemyBullet(id: Int) {
        if(id == 1) {
            enemyView = ImageView(Image("${System.getProperty("user.dir")}/src/main/resources/images/bullet1.png"))
        } else if(id == 2){
            enemyView = ImageView(Image("${System.getProperty("user.dir")}/src/main/resources/images/bullet2.png"))
        }else {
            enemyView = ImageView(Image("${System.getProperty("user.dir")}/src/main/resources/images/bullet3.png"))

        }
        enemyView!!.fitHeight = 30.0
        enemyView!!.fitWidth = 20.0
    }

    fun getEnemyBulletView(): ImageView? {
        return enemyView
    }

    fun setPosition(X: Double, Y: Double) {
        my_X = X
        my_Y = Y
        enemyView!!.layoutX = my_X
        enemyView!!.layoutY = my_Y
    }

    fun moveDown(Y: Double) {
        my_Y += Y
        enemyView!!.layoutX = my_X
        enemyView!!.layoutY = my_Y
    }
}