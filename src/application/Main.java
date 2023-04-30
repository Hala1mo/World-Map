package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Main extends Application {
	ArrayList<vertex> v = new ArrayList<>();
	HashMap<vertex, Double> distance = new HashMap<>();
	HashMap<String, vertex> name = new HashMap<>();
	PriorityQueue<edges> q = new PriorityQueue<>();
	ArrayList<vertex> vButton = new ArrayList<>();
	// Create a HashSet to keep track of visited nodes.
	Set<vertex> visited = new HashSet<>();
	List<List<edges>> adj_list;
	Circle marker;
	double dis;
	String path = "";
	TextField tt2;
	ComboBox<String> c = new ComboBox<String>();
	ComboBox<String> c2 = new ComboBox<String>();
	// Group g=new Group();
	Pane pp = new Pane();
	int num = 0;
	double imageWidth = 0;
	double imageHeight = 0;
	Line line;

	@Override
	public void start(Stage primaryStage) {
		try {

			File myObj = new File("C:\\Users\\user\\Desktop\\java\\cities.txt");
			if (myObj.length() != 0) {

				try {
					Scanner myReader = new Scanner(myObj);
					while (myReader.hasNext()) {
						String data = myReader.nextLine();
						// System.out.println(data);

						String[] tokens = data.split(":");
						
						if(tokens.length<=1) {
							
						}
						else if (tokens.length > 2) {
							vertex ver = new vertex(tokens[0], Double.parseDouble(tokens[1]),
									Double.parseDouble(tokens[2]), num++);
							v.add(ver);
							name.put(tokens[0], ver);
						}

						else if (tokens.length == 2) {

							for (int i = 0; i < v.size(); i++) {
								if (v.get(i).getName().compareToIgnoreCase(tokens[0]) == 0) {
									for (int j = 0; j < v.size(); j++) {
									
										if (v.get(j).getName().compareToIgnoreCase(tokens[1]) == 0) {
										
											v.get(i).e.add(new edges(v.get(i), v.get(j), Distance(v.get(i), v.get(j))));
										
										System.out.println(v.get(i).getName()+" To "+v.get(j).getName()+":"+ Distance(v.get(i), v.get(j)));
										
										}

									}
								}
							}
						}

					}
					myReader.close();
				} catch (FileNotFoundException e) {
					System.out.println("An error occurred.");
					e.printStackTrace();
				}
			}

			
			for (int i = 0; i < v.size(); i++) {
				System.out.println(v.get(i).ttoString());
			}

			BorderPane root = new BorderPane();
			 root.setStyle("-fx-background-color:LIGHTBLUE");

			ImageView a2 = new ImageView("test1.jpg");
			a2.setFitHeight(800);
			a2.setFitWidth(1200);
			Button b2 = new Button(null, a2);
			b2.setAlignment(Pos.CENTER_LEFT);
			pp.getChildren().add(b2);

			Circle[] button = new Circle[v.size()];

			HashMap<Circle, String> ss = new HashMap<>();
			for (int k = 0; k < v.size(); k++) {
				double latitude = v.get(k).getLatitude();
				double longitude = v.get(k).getLongitude();
				imageWidth = a2.getFitWidth();
				imageHeight = a2.getFitHeight();

				double y = (imageHeight - (latitude + 90) / 180 * imageHeight);
				double x = (longitude + 180) / 360 * imageWidth - 25;

				button[k] = new Circle(x, y, 3.5, Color.BLACK);

				pp.getChildren().addAll(button[k]);
				ss.put(button[k], v.get(k).getName());

			}
   

			for (Circle buttonn : button) {
				int index = Arrays.asList(button).indexOf(buttonn);

				buttonn.setOnMouseClicked(event -> {
					System.out.println("Button " + index + " clicked");

					String country = ss.get(buttonn);
				
					vertex v = name.get(country);
					vButton.add(v);
					
					 if (vButton.size() == 1) {
						c.setValue(country);
						}
					 else {
						 c2.setValue(country);
							}
						

				});
			}
			
			root.setCenter(pp);

			

			c.setMaxSize(200, 100);
			c.setStyle("-fx-font-size: 15pt;");
			
			List<String> items = new ArrayList<>();
			for (int i = 0; i <v.size(); i++) {
			    items.add(v.get(i).getName());
			}
			Collections.sort(items);
			
			for (String item : items) {
			    c.getItems().add(item);
			}
			
			
			
			Label l1 = new Label("Source");
			l1.setFont(Font.font(17));
			l1.setFont(Font.font(null, FontWeight.BOLD, l1.getFont().getSize()));
			Label l2 = new Label("Target");
			l2.setFont(Font.font(17));
			l2.setFont(Font.font(null, FontWeight.BOLD, l2.getFont().getSize()));
			
			c2.setMaxSize(200, 100);
			c2.setStyle("-fx-font-size: 15pt;");
			
			for (String item : items) {
			    c2.getItems().add(item);
			}

			Button b = new Button("Run");
			b.setFont(Font.font(15));
			b.setFont(Font.font(null, FontWeight.BOLD, b.getFont().getSize()));
			b.setMaxSize(100, 100);
			HBox h2 = new HBox();
			Label l3 = new Label("Path:");
			l3.setFont(Font.font(17));
			l3.setFont(Font.font(null, FontWeight.BOLD, l3.getFont().getSize()));
			TextArea tt = new TextArea();
			tt.setMaxSize(250, 250);
			tt.setFont(Font.font(20));
			h2.getChildren().addAll(l3, tt);

			Label l4 = new Label("Distance:");
			l4.setFont(Font.font(17));
			l4.setFont(Font.font(null, FontWeight.BOLD, l4.getFont().getSize()));
			 tt2 = new TextField();
			tt2.setFont(Font.font(20));
			
			Button b3 = new Button("Clear");
			b3.setFont(Font.font(15));
			b3.setFont(Font.font(null, FontWeight.BOLD, b3.getFont().getSize()));
			b3.setMaxSize(100, 100);
			
			
			GridPane g = new GridPane();
			g.setPadding(new Insets(5,5,5,5));
			// g.setHgap(2);
			g.setVgap(10);
			
			Label ll=new Label("World Map");
			ll.setFont(Font.font(25));
			ll.setAlignment(Pos.CENTER);
			ll.setFont(Font.font("Arial",FontWeight.BOLD, FontPosture.ITALIC,25));
			g.add(ll, 1, 0);
			g.add(l1, 0, 1);
			g.add(c, 1, 1);

			g.add(l2, 0, 2);
			g.add(c2, 1, 2);

			g.add(b, 1, 3);

			g.add(l3, 0, 4);
			g.add(tt, 1, 4);

			g.add(l4, 0, 5);
			g.add(tt2, 1, 5);
			g.add(b3,1,6);
			
		

			g.setAlignment(Pos.CENTER_LEFT);
			root.setRight(g);
		
			
			
			b3.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					
					c.setValue(null);
					c2.setValue(null);
					tt.setText("");
					tt2.setText("");
					pp.getChildren().clear();
					
					ImageView a2 = new ImageView("test1.jpg");
					a2.setFitHeight(800);
					a2.setFitWidth(1200);
					Button b2 = new Button(null, a2);
					b2.setAlignment(Pos.CENTER_LEFT);
					pp.getChildren().add(b2);

					Circle[] button = new Circle[v.size()];

					HashMap<Circle, String> ss = new HashMap<>();
					for (int k = 0; k < v.size(); k++) {
						double latitude = v.get(k).getLatitude();
						double longitude = v.get(k).getLongitude();
						imageWidth = a2.getFitWidth();
						imageHeight = a2.getFitHeight();

						double y = (imageHeight - (latitude + 90) / 180 * imageHeight);
						double x = (longitude + 180) / 360 * imageWidth - 25;

						button[k] = new Circle(x, y, 3.5, Color.BLACK);

						pp.getChildren().addAll(button[k]);
						ss.put(button[k], v.get(k).getName());

					}
					
					
					//root.getChildren().add(pp);
				}
			});

			b.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub

					//System.out.println(vButton.size());
					vertex v1 = null;
					vertex v2 = null;
					String s1 = c.getValue();
					String s2 = c2.getValue();

					if (s1 != null && s2 != null) {
						v1 = name.get(s1);
						v2 = name.get(s2);
					} else if (vButton.size() == 2) {
						v1 = vButton.get(0);
						c.setValue(v1.getName());
						v2 = vButton.get(1);
						c2.setValue(v2.getName());
					}
					if (v1 != null && v2 != null) {
						Dijekstra(v1, v2);
						tt2.setText(String.valueOf(v2.distance));
						// String pp=path.substring(0,path.lastIndexOf("-->"));
						tt.setText(path);
					}

				}
			});

			Scene scene = new Scene(root, 1550, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void Dijekstra(vertex Source, vertex Destination) {
		Source.distance = 0;
		
		if(Source==Destination) {
			return;
		}
		
		PriorityQueue<vertex> pq = new PriorityQueue<>(new Comparator<vertex>() {
			@Override
			public int compare(vertex v1, vertex v2) {
				return Double.compare(v1.distance, v2.distance);
			}
		});
		
		pq.add(Source);

		while (!pq.isEmpty()) {
			vertex u = pq.poll();
			
			u.visited=true;
			
			if(u.name.equals(Destination.getName())) {
				break;
			}
			for (edges e : u.getE()) {
				vertex v = e.d;
				if(!v.visited) {
				double weight = e.weight;
				double distanceThroughU = u.distance + weight;
				if (distanceThroughU < v.distance ) {
					v.distance = distanceThroughU;
					v.previous = u;
					pq.add(v);
		}
			}
		}
			
		}

		List<vertex> p = new ArrayList<>();
		for (vertex v = Destination; v != null; v = v.previous) {
			p.add(v);
		}
		Collections.reverse(p);
		if(p.size()>1) {
		for (int i = 0; i < p.size(); i++) {
			
			path = path + p.get(i).getName() + "\n";
		}
		}
		if(p.size()<=1) {
			
			path="No path";
		}
		for (int i = 0; i < p.size() - 1; i++) {
			vertex u = p.get(i);
			vertex v = p.get(i + 1);

			//Line line = new Line();
			line = new Line();
			line.setFill(Color.RED);

			line.setStartX(Xvalue(u.longitude));
			line.setStartY(Yvalue(u.latitude));
			line.setEndX(Xvalue(v.longitude));
			line.setEndY(Yvalue(v.latitude));

			// Add the line to the scene graph
			pp.getChildren().add(line);
		}

	}

	public double Distance(vertex a, vertex b) {

		final int EARTH_RADIUS = 6371; // in km
		double lat1Rad = Math.toRadians(a.getLatitude());
		double lat2Rad = Math.toRadians(b.getLatitude());
		double deltaLat = Math.toRadians(b.getLatitude() - a.getLatitude());
		double deltaLon = Math.toRadians(b.getLongitude() - a.getLongitude());

		double dis = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
				+ Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(dis), Math.sqrt(1 - dis));

		return EARTH_RADIUS * c;

	}

	public class Graph {
		final List<LinkedList<edges>> adjacencyList = null;

		private final int numVertices;

		Graph(int numVertices) {

			this.numVertices = numVertices;

		}

		public int getNumVertices() {
			return numVertices;
		}

		public List<LinkedList<edges>> getAdjacencyList() {
			return adjacencyList;
		}
	}

	public double Xvalue(double longitude) {

		double r = ((longitude + 180) / 360 * imageWidth) - 25;
		System.out.println(r);
		return r;
	}

	public double Yvalue(double latitude) {
		double r = (imageHeight - (latitude + 90) / 180 * imageHeight);
		return r;

	}

}
