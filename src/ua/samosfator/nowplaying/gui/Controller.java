package ua.samosfator.nowplaying.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ua.samosfator.nowplaying.Config;
import ua.samosfator.nowplaying.Daemon;
import ua.samosfator.nowplaying.Song;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;

public class Controller implements Initializable {

    @FXML
    private Button getVkButton;
    @FXML
    private Button startBroadcastingButton;
    @FXML
    private Button resetSettingsButton;
    @FXML
    private TextField accessTokenTextField;
    @FXML
    private TextField lastfmTextField;
    @FXML
    private Label refreshRateLabel;
    @FXML
    private Slider refreshRateSlider;

    @FXML
    private TableView<Song> songTable;
    @FXML
    private TableColumn<Song, String> timeColumn;
    @FXML
    private TableColumn<Song, String> songTitleColumn;

    public static ObservableList<Song> songList = FXCollections.observableArrayList();

    @FXML
    private void getVkButton() throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI(Config.getAccessTokenURL()));
    }

    @FXML
    private void saveToken() {
        String fieldText = accessTokenTextField.getText();
        if (fieldText.contains("http://api.vk.com/blank.html#access_token=")) {
            Config.saveVKPref(fieldText);
        }
    }

    @FXML
    private void setLastfmUser() {
        String username = lastfmTextField.getText();
        Config.saveLastfmUser(username);
    }

    @FXML
    private void startBroadcasting() {
        if (startBroadcastingButton.getText().contains("Start")) {
            Daemon.start();
            startBroadcastingButton.setText("Stop broadcasting");
            toggleDisableButtons();
        } else {
            Daemon.stop();
            startBroadcastingButton.setText("Start broadcasting");
            toggleDisableButtons();
        }
    }

    @FXML
    private void clearPreferences() throws BackingStoreException {
        Config.preferences.clear();
        accessTokenTextField.clear();
        lastfmTextField.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTextFields();
        initTable();
        refreshRateSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            refreshRateLabel.setText("Refresh rate: " + newValue.intValue() + "s");
            Config.REFRESH_RATE = newValue.intValue() * 1000;
        });
    }

    private void initTextFields() {
        String savedToken = Config.preferences.get("access_token", "");

        if (savedToken.length() > 80)
            accessTokenTextField.setText(Config.getCompleteAccessTokenURL());

        lastfmTextField.setText(Config.preferences.get("username", ""));
    }

    private void initTable() {
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        songTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        songTable.setItems(songList);
    }

    private void toggleDisableButtons() {
        boolean disabled = refreshRateSlider.isDisabled();
        getVkButton.setDisable(!disabled);
        resetSettingsButton.setDisable(!disabled);
        accessTokenTextField.setDisable(!disabled);
        refreshRateLabel.setDisable(!disabled);
        refreshRateSlider.setDisable(!disabled);
        lastfmTextField.setDisable(!disabled);
    }
}
