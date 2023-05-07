import javafx.scene.image.Image
import javafx.scene.image.ImageView

class Enemy {
    var enemyView: ImageView? = null
    var alive = true
    private var enemyID = 0
    var myX = 0.0
    var myY = 0.0

    var count = 0;
    fun makeEnemy(id: Int) {
    if(id == 1) {
        enemyView = ImageView(Image("${System.getProperty("user.dir")}/src/main/resources/images/enemy1.png"))
    }else if(id == 2 ){
        enemyView = ImageView(Image("${System.getProperty("user.dir")}/src/main/resources/images/enemy2.png"))
    }else {
        enemyView = ImageView(Image("${System.getProperty("user.dir")}/src/main/resources/images/enemy3.png"))
    }
        enemyView!!.fitHeight = 50.0
        enemyView!!.fitWidth = 50.0
        enemyID = id
    }

    fun positionEnemy(X: Int, Y: Int) {
        myX = X.toDouble()
        myY = Y.toDouble()
        enemyView!!.x = X.toDouble()
        enemyView!!.y = Y.toDouble()
    }

    fun getEnemy(): ImageView? {
        return enemyView
    }

    fun getEnemyID(): Int {
        return enemyID
    }


    fun move(X: Double, Y: Double) {
        myX += X
        myY += Y
        enemyView!!.x = myX
        enemyView!!.y = myY
    }

    fun getX(): Double {
        return myX
    }

    fun getY(): Double {
        return myY
    }
}