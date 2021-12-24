package application;

import java.io.IOException;
import java.io.PrintWriter;
//import java.lang.invoke.BoundMethodHandle.Specializer.Factory;
import java.net.URL;
import java.util.ResourceBundle;

import com.fazecast.jSerialComm.SerialPort;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
//import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class control implements Initializable {

	static SerialPort chosenPort;
	public ImageView imageWiev;
	// private Stage stage;
	// private Scene scene;

	@FXML
	Button btnConnect;
	@FXML
	ImageView coding, wiring;

	@FXML
	ImageView SW1ON, SW2ON, SW3ON, SW4ON, SW5ON, SW6ON, SW7ON, SW8ON;

	@FXML
	ImageView SW1OFF, SW2OFF, SW3OFF, SW4OFF, SW5OFF, SW6OFF, SW7OFF, SW8OFF;

	@FXML
	ImageView B1OFF, B2OFF, B3OFF, B4OFF, B5OFF, B6OFF, B7OFF, B8OFF;

	@FXML
	ImageView B1ON, B2ON, B3ON, B4ON, B5ON, B6ON, B7ON, B8ON;
	
	@FXML
	ImageView wire12, wire34, wire56, wire78;
	
	@FXML
	TextField lbl_R1_timer, lbl_R2_timer,lbl_R3_timer,lbl_R4_timer,lbl_R5_timer,lbl_R6_timer,lbl_R7_timer,lbl_R8_timer;

	@FXML
	TextField lbl_R12_timer, lbl_R34_timer,lbl_R56_timer,lbl_R78_timer;
	
	@FXML
	ComboBox<String> comPortList = new ComboBox<>();
	@FXML
	Button delayBtn_12, delayBtn_34, delayBtn_56, delayBtn_78;	

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		SerialPort[] portNames = SerialPort.getCommPorts();
		for (SerialPort portName : portNames) {
			comPortList.getItems().addAll(portName.getSystemPortName());
		
		}
	reseting();
	
	}

	/* 
	// in the same window
	public void switchToWiring() throws IOException {		
		 Parent root = FXMLLoader.load(getClass().getResource("wiring.fxml")); 
		 stage = (Stage) wiring.getScene().getWindow();
		 scene = new Scene(root);
		 stage.setScene(scene);
		 stage.show();
	}
	 */
	// in the external window
	public void switchToWiring() throws IOException {
		 FXMLLoader fxmlLoader = new FXMLLoader();
		 fxmlLoader.setLocation(getClass().getResource("wiring.fxml"));
		 Scene scene = new Scene(fxmlLoader.load());
		 Stage stage = new Stage();
		 stage.setTitle("New Window");
		 stage.setScene(scene);
		 stage.show();
	}
		 

	/*// in the same window
	 * public void switchToCoding() throws IOException { Parent root =
	 * FXMLLoader.load(getClass().getResource("codes.fxml")); stage = (Stage)
	 * coding.getScene().getWindow(); scene = new Scene(root);
	 * stage.setScene(scene); stage.show();
	 * 
	 * }
	 */
	
	// in the external window
	public void switchToCoding() throws IOException {
		 FXMLLoader fxmlLoader = new FXMLLoader();
		 fxmlLoader.setLocation(getClass().getResource("codes.fxml"));
		 Scene scene = new Scene(fxmlLoader.load());
		 Stage stage = new Stage();
		 stage.setTitle("New Window");
		 stage.setScene(scene);
		 stage.show();
	}
	
	

	public void connect(ActionEvent e) {
		if (btnConnect.getText().equals("Connect")) {
			// try to connect
			chosenPort = SerialPort.getCommPort(comPortList.getValue());
			System.out.println("chosenPort No" + comPortList.getValue());
			chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
			if (chosenPort.openPort()) {
				btnConnect.setText("Disconnect");
				comPortList.setDisable(true);
				
			}
		} else {
			// disconnect
			chosenPort.closePort();
			comPortList.setDisable(false);
			btnConnect.setText("Connect");
		}
		
		
	}

	public void switch1() {

		System.out.println("Switch1 juste pressed");
		
		if (SW1ON.isVisible()) {
			SW1ON.setVisible(false);
			B1ON.setVisible(true);
			B1OFF.setVisible(false);
			lbl_R1_timer.setDisable(true);

			Thread thread1 = new Thread() {
				@Override
				public void run() {
					// waiting after connection - bootLoader can finish
					try {
						Thread.sleep(100);
					} catch (Exception e) {
					}

					PrintWriter output = new PrintWriter(chosenPort.getOutputStream());

					// sent to the arduino
					do {
						output.print('b');
						output.flush();

						try {
												
							Thread.sleep(Integer.parseInt(lbl_R1_timer.getText()));
						} catch (Exception e) {
							try {
								lbl_R1_timer.setText("1000");
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

							output.print('a');
							output.flush();
							try {
								Thread.sleep( Integer.parseInt(lbl_R1_timer.getText())) ;
							} catch (Exception e) {
							}
							
					} while (!SW1ON.isVisible());
				}
			};

			thread1.start();

		} else {
			SW1ON.setVisible(true);
			B1ON.setVisible(false);
			B1OFF.setVisible(true);
			lbl_R1_timer.setDisable(false);
			lbl_R12_timer.setDisable(false);
			}

	}

	public void switch2() {

		System.out.println("Switch2 juste pressed");
		if (SW2ON.isVisible()) {
			SW2ON.setVisible(false);
			B2ON.setVisible(true);
			B2OFF.setVisible(false);
			lbl_R2_timer.setDisable(true);

			Thread thread2 = new Thread() {
				@Override
				public void run() {
					// waiting after connection - bootLoader can finish
					try {
						Thread.sleep(100);
					} catch (Exception e) {
					}

					PrintWriter output = new PrintWriter(chosenPort.getOutputStream());

					// sent to the arduino
					do {
						output.print('d');
						output.flush();

						try {
							Thread.sleep(Integer.parseInt(lbl_R2_timer.getText()));
						} catch (Exception e) {
							try {
								lbl_R2_timer.setText("1000");
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
										
							output.print('c');
							output.flush();
							try {
								Thread.sleep(Integer.parseInt(lbl_R2_timer.getText()));
							} catch (Exception e) {
							}
							
					} while (!SW2ON.isVisible());
				}
			};

			thread2.start();

		} else {
			SW2ON.setVisible(true);
			B2ON.setVisible(false);
			B2OFF.setVisible(true);
			lbl_R2_timer.setDisable(false);

		}
	}

	public void switch3() {

		System.out.println("Switch3 juste pressed");
		if (SW3ON.isVisible()) {
			SW3ON.setVisible(false);
			B3ON.setVisible(true);
			B3OFF.setVisible(false);
			lbl_R3_timer.setDisable(true);

			Thread thread3 = new Thread() {
				@Override
				public void run() {
					// waiting after connection - bootLoader can finish
					try {
						Thread.sleep(100);
					} catch (Exception e) {
					}

					PrintWriter output = new PrintWriter(chosenPort.getOutputStream());

					// sent to the arduino
					do {
						output.print('f');
						output.flush();

						try {
							Thread.sleep(Integer.parseInt(lbl_R3_timer.getText()));
						} catch (Exception e) {
							try {
								lbl_R3_timer.setText("1000");
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

						output.print('e');
						output.flush();
						try {
							Thread.sleep(Integer.parseInt(lbl_R3_timer.getText()));
						} catch (Exception e) {
						}

						//
					} while (!SW3ON.isVisible());
				}
			};

			thread3.start();

		} else {
			SW3ON.setVisible(true);
			B3ON.setVisible(false);
			B3OFF.setVisible(true);
			lbl_R3_timer.setDisable(false);
		}
	}

	public void switch4() {

		System.out.println("Switch4 juste pressed");
		if (SW4ON.isVisible()) {
			SW4ON.setVisible(false);
			B4ON.setVisible(true);
			B4OFF.setVisible(false);
			lbl_R4_timer.setDisable(true);

			Thread thread4 = new Thread() {
				@Override
				public void run() {
					// waiting after connection - bootLoader can finish
					try {
						Thread.sleep(100);
					} catch (Exception e) {
					}

					PrintWriter output = new PrintWriter(chosenPort.getOutputStream());

					// sent to the arduino
					do {
						output.print('h');
						output.flush();

						try {
							Thread.sleep(Integer.parseInt(lbl_R4_timer.getText()));
						} catch (Exception e) {
							try {
								lbl_R4_timer.setText("1000");
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

						output.print('g');
						output.flush();
						try {
							Thread.sleep(Integer.parseInt(lbl_R4_timer.getText()));
						} catch (Exception e) {
						}

						//
					} while (!SW4ON.isVisible());
				}
			};

			thread4.start();

		} else {
			SW4ON.setVisible(true);
			B4ON.setVisible(false);
			B4OFF.setVisible(true);
			lbl_R4_timer.setDisable(false);
		}
	}

	public void switch5() {

		System.out.println("Switch5 juste pressed");
		if (SW5ON.isVisible()) {
			SW5ON.setVisible(false);
			B5ON.setVisible(true);
			B5OFF.setVisible(false);
			lbl_R5_timer.setDisable(true);

			Thread thread5 = new Thread() {
				@Override
				public void run() {
					// waiting after connection - bootLoader can finish
					try {
						Thread.sleep(100);
					} catch (Exception e) {
					}

					PrintWriter output = new PrintWriter(chosenPort.getOutputStream());

					// sent to the arduino
					do {
						output.print('j');
						output.flush();

						try {
							Thread.sleep(Integer.parseInt(lbl_R5_timer.getText()));
						} catch (Exception e) {
							try {
								lbl_R5_timer.setText("1000");
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

						output.print('i');
						output.flush();
						try {
							Thread.sleep(Integer.parseInt(lbl_R5_timer.getText()));
						} catch (Exception e) {
						}

						//
					} while (!SW5ON.isVisible());
				}
			};

			thread5.start();

		} else {
			SW5ON.setVisible(true);
			B5ON.setVisible(false);
			B5OFF.setVisible(true);
			lbl_R5_timer.setDisable(false);
		}

	}

	public void switch6() {

		System.out.println("Switch6 juste pressed");
		if (SW6ON.isVisible()) {
			SW6ON.setVisible(false);
			B6ON.setVisible(true);
			B6OFF.setVisible(false);
			lbl_R6_timer.setDisable(true);

			Thread thread6 = new Thread() {
				@Override
				public void run() {
					// waiting after connection - bootLoader can finish
					try {
						Thread.sleep(100);
					} catch (Exception e) {
					}

					PrintWriter output = new PrintWriter(chosenPort.getOutputStream());

					// sent to the arduino
					do {
						output.print('l');
						output.flush();

						try {
							Thread.sleep(Integer.parseInt(lbl_R6_timer.getText()));
						} catch (Exception e) {
							try {
								lbl_R6_timer.setText("1000");
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

						output.print('k');
						output.flush();
						try {
							Thread.sleep(Integer.parseInt(lbl_R6_timer.getText()));
						} catch (Exception e) {
						}

						//
					} while (!SW6ON.isVisible());
				}
			};

			thread6.start();

		} else {
			SW6ON.setVisible(true);
			B6ON.setVisible(false);
			B6OFF.setVisible(true);
			lbl_R6_timer.setDisable(false);
		}
	}

	public void switch7() {

		System.out.println("Switch7 juste pressed");
		if (SW7ON.isVisible()) {
			SW7ON.setVisible(false);
			B7ON.setVisible(true);
			B7OFF.setVisible(false);
			lbl_R7_timer.setDisable(true);

			Thread thread7 = new Thread() {
				@Override
				public void run() {
					// waiting after connection - bootLoader can finish
					try {
						Thread.sleep(100);
					} catch (Exception e) {
					}

					PrintWriter output = new PrintWriter(chosenPort.getOutputStream());

					// sent to the arduino
					do {
						output.print('n');
						output.flush();

						try {
							Thread.sleep(Integer.parseInt(lbl_R7_timer.getText()));
						} catch (Exception e) {
							try {
								lbl_R7_timer.setText("1000");
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

						output.print('m');
						output.flush();
						try {
							Thread.sleep(Integer.parseInt(lbl_R7_timer.getText()));
						} catch (Exception e) {
						}

						//
					} while (!SW7ON.isVisible());
				}
			};

			thread7.start();

		} else {
			SW7ON.setVisible(true);
			B7ON.setVisible(false);
			B7OFF.setVisible(true);
			lbl_R7_timer.setDisable(false);
		}
	}

	public void switch8() {

		System.out.println("Switch8 juste pressed");
		if (SW8ON.isVisible()) {
			SW8ON.setVisible(false);
			B8ON.setVisible(true);
			B8OFF.setVisible(false);
			lbl_R8_timer.setDisable(true);

			Thread thread8 = new Thread() {
				@Override
				public void run() {
					// waiting after connection - bootLoader can finish
					try {
						Thread.sleep(100);
					} catch (Exception e) {
					}

					PrintWriter output = new PrintWriter(chosenPort.getOutputStream());

					// sent to the arduino
					do {
						output.print('p');
						output.flush();

						try {
							Thread.sleep(Integer.parseInt(lbl_R8_timer.getText()));
						} catch (Exception e) {
							try {
								lbl_R8_timer.setText("1000");
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

						output.print('o');
						output.flush();
						try {
							Thread.sleep(Integer.parseInt(lbl_R8_timer.getText()));
						} catch (Exception e) {
						}

						//
					} while (!SW8ON.isVisible());
				}
			};

			thread8.start();

		} else {
			SW8ON.setVisible(true);
			B8ON.setVisible(false);
			B8OFF.setVisible(true);
			lbl_R8_timer.setDisable(false);
		}
	}
	
	public void delaydR1R2() { 
		System.out.println("Switch R1 R2 juste pressed");
		
		if (delayBtn_12.getText().equals("Start R1 + R2") && !lbl_R12_timer.getText().isBlank()) {
			delayBtn_12.setText("STOP");
			delayBtn_12.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
			wire12.setVisible(true);
			B1ON.setVisible(true);
			B2ON.setVisible(true);
			B1OFF.setVisible(false);
			B2OFF.setVisible(false);
			lbl_R1_timer.setDisable(true);
			lbl_R2_timer.setDisable(true);
			SW1ON.setDisable(true);
			SW2ON.setDisable(true);
			lbl_R12_timer.setDisable(true);
			

			Thread thread12 = new Thread() {
				@Override
				public void run() {
					// waiting after connection - bootLoader can finish
					try {
						Thread.sleep(50);
					} catch (Exception e) {
					}

					PrintWriter output = new PrintWriter(chosenPort.getOutputStream());

					// sent to the arduino
					do {
						output.print('b');
						output.flush();

						try {
												
							Thread.sleep(Integer.parseInt(lbl_R1_timer.getText()));
						} catch (Exception e) {
							try {
								lbl_R1_timer.setText("1000");
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
							
							output.print('a');
							output.flush();
							try {
								Thread.sleep(Integer.parseInt(lbl_R12_timer.getText()) );
							} catch (Exception e) {
								try {
									lbl_R12_timer.setText("1000");
									Thread.sleep(1000);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
							output.print('d');
							output.flush();
							try {
								Thread.sleep(Integer.parseInt(lbl_R2_timer.getText()) );
							} catch (Exception e) {
								try {
									lbl_R2_timer.setText("1000");
									Thread.sleep(1000);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
							output.print('c');
							output.flush();
							try {
								Thread.sleep(Integer.parseInt(lbl_R12_timer.getText()) );
							} catch (Exception e) {
								try {
									lbl_R12_timer.setText("1000");
									Thread.sleep(1000);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
					} while (delayBtn_12.getText().equals("STOP"));
				}
			};

			thread12.start();

		} else {
			delayBtn_12.setText("Start R1 + R2");
			delayBtn_12.setStyle("-fx-text-fill: green; -fx-font-size: 14px;");
			wire12.setVisible(false);
			SW1ON.setDisable(false);
			SW2ON.setDisable(false);
			B1ON.setVisible(false);
			B2ON.setVisible(false);
			B1OFF.setVisible(true);
			B2OFF.setVisible(true);
			lbl_R1_timer.setDisable(false);
			lbl_R2_timer.setDisable(false);
			lbl_R12_timer.setDisable(false);
			}
	}
	
	public void delaydR3R4() {
System.out.println("Switch R1 R2 juste pressed");
		
		if (delayBtn_34.getText().equals("Start R1 + R2") && !lbl_R34_timer.getText().isBlank()) {
			delayBtn_34.setText("STOP");
			delayBtn_34.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
			wire34.setVisible(true);
			B3ON.setVisible(true);
			B4ON.setVisible(true);
			B3OFF.setVisible(false);
			B4OFF.setVisible(false);
			lbl_R3_timer.setDisable(true);
			lbl_R4_timer.setDisable(true);
			SW3ON.setDisable(true);
			SW4ON.setDisable(true);
			lbl_R34_timer.setDisable(true);
			

			Thread thread34 = new Thread() {
				@Override
				public void run() {
					// waiting after connection - bootLoader can finish
					try {
						Thread.sleep(50);
					} catch (Exception e) {
					}

					PrintWriter output = new PrintWriter(chosenPort.getOutputStream());

					// sent to the arduino
					do {
						output.print('f');
						output.flush();

						try {
												
							Thread.sleep(Integer.parseInt(lbl_R3_timer.getText()));
						} catch (Exception e) {
							try {
								lbl_R3_timer.setText("1000");
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
							
							output.print('e');
							output.flush();
							try {
								Thread.sleep(Integer.parseInt(lbl_R34_timer.getText()) );
							} catch (Exception e) {
								try {
									lbl_R34_timer.setText("1000");
									Thread.sleep(1000);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
							output.print('h');
							output.flush();
							try {
								Thread.sleep(Integer.parseInt(lbl_R4_timer.getText()) );
							} catch (Exception e) {
								try {
									lbl_R4_timer.setText("1000");
									Thread.sleep(1000);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
							output.print('g');
							output.flush();
							try {
								Thread.sleep(Integer.parseInt(lbl_R34_timer.getText()) );
							} catch (Exception e) {
								try {
									lbl_R34_timer.setText("1000");
									Thread.sleep(1000);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
					} while (delayBtn_34.getText().equals("STOP"));
				}
			};

			thread34.start();

		} else {
			delayBtn_34.setText("Start R1 + R2");
			delayBtn_34.setStyle("-fx-text-fill: green; -fx-font-size: 14px;");
			wire34.setVisible(false);
			SW3ON.setDisable(false);
			SW4ON.setDisable(false);
			B3ON.setVisible(false);
			B4ON.setVisible(false);
			B3OFF.setVisible(true);
			B4OFF.setVisible(true);
			lbl_R3_timer.setDisable(false);
			lbl_R4_timer.setDisable(false);
			lbl_R34_timer.setDisable(false);
			}
	}
	
	public void delaydR5R6() {
System.out.println("Switch R1 R2 juste pressed");
		
		if (delayBtn_56.getText().equals("Start R1 + R2") && !lbl_R56_timer.getText().isBlank()) {
			delayBtn_56.setText("STOP");
			delayBtn_56.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
			wire56.setVisible(true);
			B5ON.setVisible(true);
			B6ON.setVisible(true);
			B5OFF.setVisible(false);
			B6OFF.setVisible(false);
			lbl_R5_timer.setDisable(true);
			lbl_R6_timer.setDisable(true);
			SW5ON.setDisable(true);
			SW6ON.setDisable(true);
			lbl_R56_timer.setDisable(true);
			

			Thread thread56 = new Thread() {
				@Override
				public void run() {
					// waiting after connection - bootLoader can finish
					try {
						Thread.sleep(50);
					} catch (Exception e) {
					}

					PrintWriter output = new PrintWriter(chosenPort.getOutputStream());

					// sent to the arduino
					do {
						output.print('j');
						output.flush();

						try {
												
							Thread.sleep(Integer.parseInt(lbl_R5_timer.getText()));
						} catch (Exception e) {
							try {
								lbl_R5_timer.setText("1000");
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
							
							output.print('i');
							output.flush();
							try {
								Thread.sleep(Integer.parseInt(lbl_R56_timer.getText()) );
							} catch (Exception e) {
								try {
									lbl_R56_timer.setText("1000");
									Thread.sleep(1000);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
							output.print('l');
							output.flush();
							try {
								Thread.sleep(Integer.parseInt(lbl_R6_timer.getText()) );
							} catch (Exception e) {
								try {
									lbl_R6_timer.setText("1000");
									Thread.sleep(1000);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
							output.print('k');
							output.flush();
							try {
								Thread.sleep(Integer.parseInt(lbl_R56_timer.getText()) );
							} catch (Exception e) {
								try {
									lbl_R56_timer.setText("1000");
									Thread.sleep(1000);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
					} while (delayBtn_56.getText().equals("STOP"));
				}
			};

			thread56.start();

		} else {
			delayBtn_56.setText("Start R1 + R2");
			delayBtn_56.setStyle("-fx-text-fill: green; -fx-font-size: 14px;");
			wire56.setVisible(false);
			SW5ON.setDisable(false);
			SW6ON.setDisable(false);
			B5ON.setVisible(false);
			B6ON.setVisible(false);
			B5OFF.setVisible(true);
			B6OFF.setVisible(true);
			lbl_R5_timer.setDisable(false);
			lbl_R6_timer.setDisable(false);
			lbl_R56_timer.setDisable(false);
			}
	}
	
	public void delaydR7R8() { 
System.out.println("Switch R1 R2 juste pressed");
		
		if (delayBtn_78.getText().equals("Start R1 + R2") && !lbl_R78_timer.getText().isBlank()) {
			delayBtn_78.setText("STOP");
			delayBtn_78.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
			wire78.setVisible(true);
			B7ON.setVisible(true);
			B8ON.setVisible(true);
			B7OFF.setVisible(false);
			B8OFF.setVisible(false);
			lbl_R7_timer.setDisable(true);
			lbl_R8_timer.setDisable(true);
			SW7ON.setDisable(true);
			SW8ON.setDisable(true);
			lbl_R78_timer.setDisable(true);
			

			Thread thread78 = new Thread() {
				@Override
				public void run() {
					// waiting after connection - bootLoader can finish
					try {
						Thread.sleep(50);
					} catch (Exception e) {
					}

					PrintWriter output = new PrintWriter(chosenPort.getOutputStream());

					// sent to the arduino
					do {
						output.print('n');
						output.flush();

						try {
												
							Thread.sleep(Integer.parseInt(lbl_R7_timer.getText()));
						} catch (Exception e) {
							try {
								lbl_R7_timer.setText("1000");
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
							
							output.print('m');
							output.flush();
							try {
								Thread.sleep(Integer.parseInt(lbl_R78_timer.getText()) );
							} catch (Exception e) {
								try {
									lbl_R78_timer.setText("1000");
									Thread.sleep(1000);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
							output.print('p');
							output.flush();
							try {
								Thread.sleep(Integer.parseInt(lbl_R8_timer.getText()) );
							} catch (Exception e) {
								try {
									lbl_R8_timer.setText("1000");
									Thread.sleep(1000);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
							output.print('o');
							output.flush();
							try {
								Thread.sleep(Integer.parseInt(lbl_R78_timer.getText()) );
							} catch (Exception e) {
								try {
									lbl_R78_timer.setText("1000");
									Thread.sleep(1000);
								} catch (InterruptedException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
					} while (delayBtn_56.getText().equals("STOP"));
				}
			};

			thread78.start();

		} else {
			delayBtn_78.setText("Start R1 + R2");
			delayBtn_78.setStyle("-fx-text-fill: green; -fx-font-size: 14px;");
			wire78.setVisible(false);
			SW7ON.setDisable(false);
			SW8ON.setDisable(false);
			B7ON.setVisible(false);
			B8ON.setVisible(false);
			B7OFF.setVisible(true);
			B8OFF.setVisible(true);
			lbl_R7_timer.setDisable(false);
			lbl_R8_timer.setDisable(false);
			lbl_R78_timer.setDisable(false);
			}
	}
	
	
	
	private void reseting() {
		SW1ON.setVisible(true);
		SW2ON.setVisible(true);
		SW3ON.setVisible(true);
		SW4ON.setVisible(true);
		SW5ON.setVisible(true);
		SW6ON.setVisible(true);
		SW7ON.setVisible(true);
		SW8ON.setVisible(true);
		
		B1OFF.setVisible(true);
		B2OFF.setVisible(true);
		B3OFF.setVisible(true);
		B4OFF.setVisible(true);
		B5OFF.setVisible(true);
		B6OFF.setVisible(true);
		B7OFF.setVisible(true);
		B8OFF.setVisible(true);
		lbl_R1_timer.setText("1000");
		lbl_R2_timer.setText("1000");
		lbl_R3_timer.setText("1000");
		lbl_R4_timer.setText("1000");
		lbl_R5_timer.setText("1000");
		lbl_R6_timer.setText("1000");
		lbl_R7_timer.setText("1000");
		lbl_R8_timer.setText("1000");
		
		wire12.setVisible(false);
		wire34.setVisible(false);
		wire56.setVisible(false);
		wire78.setVisible(false);	
	}
	


}
