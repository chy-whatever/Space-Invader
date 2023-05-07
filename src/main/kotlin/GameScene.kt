import javafx.animation.AnimationTimer
import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.media.MediaPlayer
import javafx.scene.paint.Color
import javafx.scene.text.Font
import java.io.File
import java.util.*



class GameScene {
    //final is to check whether it is final round
    var final = false
    //the speed inceased by elimate alien
    var increase_speed = 0.0
    //num of alien you killed
    var num_des = 0
    //score
    var score = 0
    //check whether you finish the game
    var gameover = false
    //remain ships
    var lives = 3
    // current level
    var level = 1
    //check whether the place is available to get a random i
    var check_value = false
    //the speed of alien go down
    var downspeed = 5
    //the speed of enemy bullet
    var bulletspeed = 2.0

    var flag_right = true
    var flag_down= false
    var timer: AnimationTimer ? = null
    var bool = false
    var count = 0
    var point_score_label = Label(this.score.toString())
    var point_level_label = Label(this.level.toString())
    var point_lives_label = Label(this.lives.toString())
    var newgroup = Group()

    var last_hitted = System.currentTimeMillis()
    var player_shoot_timing = System.currentTimeMillis()
    var enemy_shoot_timing = System.currentTimeMillis()
    var X_MAX = 850.0
    var X_MIN = 350.0

    var root = Group()

    var gameScreen: Scene = Scene(root, Color.BLACK)

    /* Player */
    var play = Player()
    val  playerBullets: MutableList<PlayerBullet> = mutableListOf()
    var playerspeed = 20.0

    //enemy
    var enemiesGroup  = Group()
    val enemyBullets: MutableList<EnemyBullet> = mutableListOf()
    val enemies = Array<Array<Enemy?>>(5) { arrayOfNulls<Enemy>(10) }
    var gameend = GameEnd(false, score, final)
    var enemyspeed = 2.0


    //initial some value
    fun sat(){
        score = 0
        final = false
        increase_speed = 0.0
        enemyspeed = 2.0
        num_des = 0
        bool = false
        lives = 3
        gameover = false
        flag_right = true
        flag_down = false
        bulletspeed = 1.0
    }

    fun startGame() {
        sat()
        val score_label = Label("Score")
        val lives_label = Label("Lives")
        val level_label = Label("Level")

        point_score_label = Label(score.toString())
        point_score_label.font = Font("Verdana", 25.0)
        point_lives_label = Label(lives.toString())
        point_lives_label.font = Font("Verdana", 25.0)
        point_level_label = Label(level.toString())
        point_level_label.font = Font("Verdana", 25.0)
        point_score_label.textFill= Color.WHITE
        point_lives_label.textFill= Color.WHITE
        point_level_label.textFill= Color.WHITE
        point_score_label.layoutX = 100.0
        point_score_label.layoutY = 5.0
        point_level_label.layoutX = 600.0
        point_level_label.layoutY = 5.0
        point_lives_label.layoutX = 400.0
        point_lives_label.layoutY = 5.0

        score_label.font = Font("Verdana", 25.0)
        lives_label.font = Font("Verdana", 25.0)
        level_label.font = Font("Verdana", 25.0)

        score_label.textFill= Color.WHITE
        lives_label.textFill= Color.WHITE
        level_label.textFill= Color.WHITE

        lives_label.setLayoutX(300.0)
        lives_label.setLayoutY(5.0)
        score_label.setLayoutX(5.0)
        score_label.setLayoutY(5.0)
        level_label.setLayoutX(500.0)
        level_label.setLayoutY(5.0)
        newgroup = Group(score_label,lives_label,level_label, point_score_label, point_lives_label, point_level_label)
        root.children.add(newgroup)
        play.makePlayer()
        play.playerView.setLayoutX(play.get_X())
        root.children.add(play.getPlayer())




        createEnemiesGroup()
        root.children.add(enemiesGroup)
        startGameTimer()
        gameScreen.onKeyPressed = EventHandler { keyEvent: KeyEvent ->

            if (keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.LEFT) {

                play.moveLeft()
            } else if (keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT) {

                play.moveRight()
            } else if (keyEvent.getCode() == KeyCode.SPACE) {

                playerShoot()
            } else if (keyEvent.getCode() == KeyCode.Q) {
                System.exit(0);
            }


            if (keyEvent.code == KeyCode.ENTER && gameover && level < 3 && num_des == 50) {
                level++
                reset()
                startGame()
            }

            if (keyEvent.code == KeyCode.ENTER && gameover && level == 3 && num_des == 50) {

                reset()
                startGame()
            }
            if (keyEvent.code == KeyCode.ENTER && gameover && num_des != 50) {
                reset()
                startGame()
            }


        }

    }

    fun reset(){
        enemiesGroup.children.clear()
        root.getChildren().remove(gameend.getGameOver())
        root.getChildren().clear()
    }


    fun getScene(): Scene {
        return this.gameScreen
    }

    fun playerShoot() {
        val now = System.currentTimeMillis()
        if (now - 500.0 > player_shoot_timing) {
            val musicFile = "${System.getProperty("user.dir")}/src/main/resources/sounds/shoot.wav" // For example
            val sound = javafx.scene.media.Media(File(musicFile).toURI().toString())
            val mediaPlayer = MediaPlayer(sound)
            mediaPlayer.play()
            player_shoot_timing = now
            val playerBullet = PlayerBullet()
            playerBullet.makePlayerBullet()
            playerBullet.setPosition(play.get_X())
            playerBullets.add(playerBullet)
            root.children.add(playerBullet.getPlayerBullet())
        }
    }

    fun startGameTimer() {

        timer = object : AnimationTimer() {
            override fun handle(now: Long) {
                movePlayerShots()
                moveEnemies()
                enemiesShootAction()
                moveEnemiesShots()
                checkIfEnemyHitPlayer()
                checkIfPlayerHitEnemy()
                //finish the game with level3
                if(num_des == 50 && level == 3){
                    gameover = true
                    final = true
                    gameend = GameEnd(true, score, final)
                    root.children.add(gameend.getGameOver())
                    timer?.stop()
                }
                if(num_des == 50){
                    gameover = true
                    final = false
                    gameend = GameEnd(true, score, final)
                    root.children.add(gameend.getGameOver())
                    timer?.stop()
                }
                if(lives == 0){
                    final = false
                    gameover = true
                    gameend = GameEnd(false, score, final)
                    root.children.add(gameend.getGameOver())
                    timer?.stop()
                }
            }
        }

        timer?.start()
    }

    private fun moveEnemies() {

        var left_boundary = 0.0
        var right_boundary = 0.0
        var down_boundary = 0.0

        for (j in 0..9) {
            for (i in 0..4) {
                if (enemies[i][j]!!.alive) {
                    left_boundary = enemies[i][j]!!.getX()
                    break
                }
            }
        }
        for (j in 9 downTo 0) {
            for (i in 0..4) {
                if (enemies[i][j]!!.alive) {
                    right_boundary = enemies[i][j]!!.getX()
                    break
                }
            }
        }
        for (i in 4 downTo 0) {
            for (j in 0..9) {
                if (enemies[i][j]!!.alive) {
                    down_boundary = enemies[i][j]!!.getY()
                    break
                }
            }
        }

        if(down_boundary >= 500){
            lives--
        }
        if (flag_right && right_boundary >= X_MAX) {

            flag_right = false
            flag_down = true
        } else if (!flag_right && left_boundary  <= X_MIN) {
            flag_right = true
            flag_down = true
        }

        //Move Enemies RIGHT , LEFT , or DOWN

        //Move Enemies down 1 Row
        if (flag_right) {        //MOVE RIGHT
            //Update enemy positions
            for (i in 0..4) {
                for (j in 0..9) {
                    enemies[i][j]!!.move(enemyspeed + level + increase_speed, 0.0)
                }
            }
        } else {
            //Update enemy positions
            for (i in 0..4) {
                for (j in 0..9) {
                    enemies[i][j]!!.move(-enemyspeed - level - increase_speed, 0.0)
                }
            }
        }
        if(flag_down){
            for (i in 0..4) {
                for (j in 0..9) {
                    enemies[i][j]!!.move(0.0, downspeed.toDouble())
                }
            }
            flag_down= false
        }
    }




    private fun checkIfEnemyHitPlayer() {
        val now = System.currentTimeMillis()


        if (now - 1000 > last_hitted) {
            for (bullet in enemyBullets) {

                if (objectsCollide(bullet.getEnemyBulletView()!!, play.getPlayer()!!)) {
                    val musicFile = "${System.getProperty("user.dir")}/src/main/resources/sounds/explosion.wav"
                    val sound = javafx.scene.media.Media(File(musicFile).toURI().toString())
                    val mediaPlayer = MediaPlayer(sound)
                    mediaPlayer.play()
                    last_hitted = System.currentTimeMillis()
                    lives--
                    point_lives_label.text = lives.toString()
                    val rand = Random()
                    var i = rand.nextInt(700)
                    //check whether there is bullet in this place
                    while(check_value){
                        for(index in enemyBullets) {
                            if(index.my_Y == 750.0 && (index.my_X == i.toDouble())) {
                                i = rand.nextInt(700)
                            }
                        }
                    }
                    play.X = i.toDouble()
                    play.playerView!!.setLayoutX(i.toDouble())
                    root.getChildren().remove(bullet.getEnemyBulletView())
                    break
                }
            }
        }
    }

    private fun checkIfPlayerHitEnemy() {
        for (i in 0..4) {
            for (j in 0..9) {
                for (bullet in playerBullets) {
                    val enemy: Node? = enemies[i][j]!!.getEnemy()
                    if (enemies[i][j]!!.alive) {
                        if (objectsCollide(bullet.getPlayerBullet()!!, enemy!!)) {
                            val musicFile = "${System.getProperty("user.dir")}/src/main/resources/sounds/invaderkilled.wav"
                            val sound = javafx.scene.media.Media(File(musicFile).toURI().toString())
                            val mediaPlayer = MediaPlayer(sound)
                            mediaPlayer.play()
                            score +=10
                            num_des++
                            point_score_label.text = score.toString()
                            increase_speed= increase_speed + 0.15
                            playerBullets.remove(bullet)
                            root.getChildren().remove(bullet.getPlayerBullet())
                            enemies[i][j]!!.alive = false
                            enemiesGroup.children.remove(enemy)
                            break
                        }
                    }
                }
            }
        }
    }



    fun objectsCollide(first: Node, second: Node): Boolean {
        return first.boundsInParent.intersects(second.boundsInParent)
    }

    private fun createEnemiesGroup() {

        for (i in 0..4) {
            for (j in 0..9) {

                var enemy = Enemy()
                if (i == 0) {
                    enemy.makeEnemy(1)
                }
                if (i == 1 || i == 2) {
                    enemy.makeEnemy(2)
                }
                if(i == 3 || i == 4){
                    enemy.makeEnemy(3)
                }
                enemies[i][j] = enemy
                enemy.positionEnemy(j * 55 + 190, i * 55 + 90)
                enemiesGroup.getChildren().add(enemy.getEnemy())
            }
        }
    }


    private fun enemiesShootAction() {

        val now = System.currentTimeMillis()

        if ((now - 500.0 > enemy_shoot_timing) && (count < level)) {

            enemy_shoot_timing = now

            while (true) {
                val rand = Random()
                val i = rand.nextInt(5)
                val j= rand.nextInt(10)
                if (enemies[i][j]!!.alive) {

                    var index = enemies[i][j]!!.getEnemyID()
                    val enemyBullet = EnemyBullet()
                    enemyBullet.makeEnemyBullet(index)
                    enemyBullets.add(enemyBullet)
                    val posX = enemies[i][j]!!.getX()
                    val posY = enemies[i][j]!!.getY()
                    enemyBullet.setPosition(posX + 17, posY + 25)
                    root.children.add(enemyBullet.getEnemyBulletView())
                    count++
                    break
                }
            }
        }
    }

    private fun moveEnemiesShots() {

            for (i in enemyBullets.indices) {
                   val bullet = enemyBullets[i]
                if (bullet.my_Y >= 750.0 && bullet.mycount == 0) {
                    bullet.moveDown(bulletspeed * level)
                    count--
                    bullet.mycount++

                    root.getChildren().remove(bullet.getEnemyBulletView())
                } else {
                    bullet.moveDown(bulletspeed * level)
                }
            }

    }

    fun movePlayerShots() {
        for (i in playerBullets) {
            if (i.Y == 100.0) {
                i.moveUp(playerspeed)
                root.getChildren().remove(i.getPlayerBullet())
            } else {
                i.moveUp(playerspeed)
            }
        }
    }




}