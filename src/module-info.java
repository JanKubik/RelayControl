module RelayControl {
	requires javafx.controls;
	requires javafx.fxml;
	requires com.fazecast.jSerialComm;
	requires java.desktop;

	opens application to javafx.graphics, javafx.fxml;
}
