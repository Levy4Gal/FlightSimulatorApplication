package com.example.frontend.windowController;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class FleetOverviewController implements Initializable {

    @FXML
    private ImageView refreshBtn;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;

    @FXML
    private PieChart myPie;

    @FXML
    private BarChart myBar;

    @FXML
    private BarChart myBar2;


    @FXML
    private LineChart lineC;

    @FXML
    private ImageView img1;

    @FXML
    private ImageView airp;

    @FXML
    private Label lbl;

    private int[][] coordinates;
    private final int NORMAL = 10;

    private Timer timer = new Timer();


    int current_i = 75, current_j = 0;
    int prev_i = 0, prev_j = 0;
    double angle = 0;


    public FleetOverviewController() {


        TimerTask getAirplanePos = new TimerTask() {
            @Override
            public void run() {
                airp.setLayoutY(current_j);
                airp.setLayoutX(current_i);
                airp.setRotate(angle);
            }
        };
        timer.schedule(getAirplanePos, 1000L, 1000L);


//        String imagePath=Paths.get("").toAbsolutePath().toString()+"\\Frontend\\src\\main\\resources\\icons\\planesmap.gif";
//        imagePath= imagePath.replace("\\","/");
//
//        System.out.println("path "+imagePath);
//        File f=new File(imagePath);
//        if(f.exists())
//        {
//
//            img1 = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
//        }else {
//
//            System.out.println("file not exist "+imagePath);
//        }


        //System.out.println(getClass().getResource("planesmap.gif").toExternalForm());
//        javafx.scene.image.Image image = new javafx.scene.image.Image(getClass().getResource("planesmap.gif").toExternalForm());
//        ImageView iv = new ImageView(image);


    }

    public void setInitPlaneLocation(int i, int j) {
        current_j = j;
        current_i = i;
        airp.setLayoutY(current_j);
        airp.setLayoutX(current_i);
        airp.setRotate(angle);
    }
    //Features:

    // redirecting to "Monitoring" tab for additional information about specific airplane
    public void doubleClick() {
    }

    // refreshing all airplanes locations presented in the map
    public void refreshMap() {
    }

    // manual refresh button of all the graphs
    public void refreshButton() {

    }

    // presenting a single plane information by clicking it once
    public void singlePlaneInfo(String name, int direction, int altitude, int velocity) {
        // name of the plane
        // flight direction (in degrees)
        // altitude (kilo feet - kft)
        // speed (knots - kn)
    }


    //zoom in / out?


    // changing and presenting plane icon direction towards its location
    public void direction(int longitude, int latitude) {
        prev_i = current_i;
        prev_j = current_j;
        current_i = longitude;
        current_j = latitude;

        int delta_x = Math.abs(current_j - prev_j);
        int delta_y = Math.abs(current_i - prev_i);
        angle = Math.toDegrees(Math.atan(delta_y) / (delta_x)) - 180.0;
    }


    //Charts:

    // active planes compared to inactive planes
    public void activePlanes(int avg) {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                new PieChart.Data("Test 1", 50),
                new PieChart.Data("Test 2", 30),
                new PieChart.Data("Test 3", 2)
        );
        myPie.setData(data);
    }

    // sorted accumulated nautical miles for individual plane since the beginning of the month
    public void singleSortedMiles(HashMap<Integer, List<Integer>> airplaneList) {
        HashMap<Integer, Integer> sums = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> e : airplaneList.entrySet()) {
            int sum = e.getValue().stream().mapToInt(a -> a).sum();
            sums.put(e.getKey(), sum);
        }

        Map<Integer, Integer> sorted = sums
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                LinkedHashMap::new));

        var data = new XYChart.Series<String, Number>();
//        Random r=new Random();
//      for(int i=1;i<=30;i++) {
//          data.getData().add(new XYChart.Data<>(i+"", r.nextInt(50)));
//      }

        for (Map.Entry<Integer, Integer> e : sorted.entrySet()) {
            data.getData().add(new XYChart.Data<>(e.getKey() + "", e.getValue()));
        }

        myBar.getData().addAll(data);
    }

    // presents average sorted nautical miles of all the fleet for every month since the beginning of the year
    public void multipleSortedMiles(HashMap<Integer, List<Integer>> airplaneList) {
        HashMap<Integer, Integer> sums = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> e : airplaneList.entrySet()) {
            int sum = e.getValue().stream().mapToInt(a -> a).sum();
            sums.put(e.getKey(), sum);
        }

        Map<Integer, Integer> sorted = sums
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                LinkedHashMap::new));

        var data = new XYChart.Series<String, Number>();
//        Random r=new Random();
//      for(int i=1;i<=30;i++) {
//          data.getData().add(new XYChart.Data<>(i+"", r.nextInt(50)));
//      }

        for (Map.Entry<Integer, Integer> e : sorted.entrySet()) {
            data.getData().add(new XYChart.Data<>(e.getKey() + "", e.getValue()));
        }
        myBar2.getData().addAll(data);
    }

    // presents the fleet size relative to time
    public void lineChart(HashMap<Integer, List<Integer>> airplaneList) {
        var data = new XYChart.Series<String, Number>();


        for (Map.Entry<Integer, List<Integer>> e : airplaneList.entrySet()) {
            int sum = e.getValue().stream().mapToInt(a -> a).sum();
            data.getData().add(new LineChart.Data<>(e.getKey() + "", sum));
        }
        lineC.getData().add(data);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        activePlanes(0);

        HashMap<Integer, List<Integer>> test = new HashMap<>();
        test.put(5, Arrays.asList(1, 2, 3));
        test.put(8, Arrays.asList(9, 9, 9));
        test.put(7, Arrays.asList(1, 1, 1));


        singleSortedMiles(test);

        HashMap<Integer, List<Integer>> test2 = new HashMap<>();
        test2.put(4, Arrays.asList(1, 5, 3));
        test2.put(1, Arrays.asList(77, 9, 9));
        test2.put(6, Arrays.asList(1, 88, 1));


        multipleSortedMiles(test2);

        lineChart(test);

//        String imagePath=Paths.get("").toAbsolutePath().toString()+"\\Frontend\\src\\main\\resources\\icons\\planesmap.gif";
//        imagePath= imagePath.replace("\\","/");
//
//        System.out.println("path "+imagePath);
//        File f=new File(imagePath);
//        if(f.exists())
//        {
//
//            img1 = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
//        }else {
//
//            System.out.println("file not exist "+imagePath);
//        }
        System.out.println("w: " + img1.getFitWidth()+ " h: " + img1.getFitHeight());
        double h = img1.getFitHeight() * NORMAL;//1000 0
        double w = img1.getFitWidth() * NORMAL;//500 0
        coordinates = new int[(int) w][(int) h];


//        img1.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent e) {
//                if(e.isSecondaryButtonDown()){
//                    cm.show(new AnchorPane(),e.getScreenX(),e.getScreenY());
//                }
//            }
//        });

//        img1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                System.out.println("test");
//            }
//        });
    }
public void clickPlane(MouseEvent e){
    System.out.println("test");
    lbl.setVisible(true);
    lbl.setLayoutX(airp.getLayoutX());
    lbl.setLayoutY(airp.getLayoutY());
//    cm.show(new AnchorPane(),e.getScreenX(),e.getScreenY());
}
}
