package example

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import tornadofx.App
import tornadofx.View

/**
 * Created by ron on 6-4-16.
 */

class UI : App() {
    override val primaryView = MainView::class

}

class MainView : View() {
    override val root: BorderPane by fxml()

    @FXML lateinit var headerText: Label
    @FXML lateinit var topBox: VBox

    init {
        headerText.text = "dit werkt niet"
    }
}
