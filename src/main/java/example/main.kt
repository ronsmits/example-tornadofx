package example

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import javafx.scene.text.TextFlow
import rss.RSSFeed
import rss.RSSItem
import tornadofx.App
import tornadofx.View
import tornadofx.plusAssign

/**
 * Created by ron on 6-4-16.
 */

class UI : App() {
    override val primaryView = MainView::class

}

class MainView : View() {
    override val root: BorderPane by fxml()

    @FXML lateinit var headerText: Label
    val otherText: Label by fxid()

    val center: VBox by fxid()
    init {
        headerText.text = "dit werkt niet"
        otherText.text = "nu werkt het wel"

        with (center) {
            this += TableView<RSSItem>().apply {

                column<RSSItem, String>("Title", "title")
                column<RSSItem, TextFlow>("description")
                background {
                    RSSFeed().getFeed("http://www.jazzpodcast.nl/feed/").allItems
                } ui {
                    items = FXCollections.observableArrayList(it)
                }
            }
        }
    }
}

fun <S, T> TableView<S>.column(propertyName: String): TableColumn<S, T> {
    val column = TableColumn<S, T>(propertyName)
    column.cellValueFactory = PropertyValueFactory<S, T>(propertyName)
    columns.add(column)
    return column
}

fun <S, T> TableView<S>.column(title: String, propertyName: String): TableColumn<S, T> {
    val column = TableColumn<S, T>(title)
    column.cellValueFactory = PropertyValueFactory<S, T>(propertyName)
    columns.add(column)
    return column
}

fun <S, T> TableView<S>.column(title: String, valueProvider: PropertyValueFactory<S, T>): TableColumn<S, T> {
    val column = TableColumn<S, T>(title)
    column.cellValueFactory = valueProvider
    columns.add(column)
    return column
}
