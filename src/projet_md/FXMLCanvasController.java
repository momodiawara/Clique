/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet_md;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.scene.layout.Pane;
import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Hadjebar
 */
public class FXMLCanvasController implements Initializable {

    @FXML
    private Group canvasGroup;
    @FXML
    private JFXToggleButton addNodeButton, addEdgeButton;
    @FXML
    private JFXButton resetButton, btnPlay, btnPause;
    @FXML
    private Pane viewer;
    @FXML
    private AnchorPane description;
    @FXML
    private Label text1, text2, text3;
    
    List<NodeFX> nodeDisplay = new ArrayList<>(); // liste des noeuds S
    List<EdgeFX> edgeDisplay = new ArrayList<>(); // liste de aretes A
    
    boolean addNode = true, addEdge = false, playing = false, started = false;
    int nbNodes = 0; //nombre de noeuds
    NodeFX selectedNode = null;
    Thread t;
    
    /* -------------------------------------------------- */
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addEdgeButton.setDisable(true);
        addNodeButton.setDisable(true);
    }    
    
    /**
     * Adds an edge between two selected nodes. Handles events for mouse clicks
     * on a node.
     */
    EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            NodeFX nodeDestination = (NodeFX) mouseEvent.getSource();
            if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (selectedNode != null) {
                    if (addEdge && !edgeExists(selectedNode, nodeDestination) && nodeDestination != selectedNode ) {
                        System.out.println("Adding Edge");
                        /* Graphiquement */
                        EdgeFX edgeGraph = new EdgeFX(selectedNode, nodeDestination);
                        
                        canvasGroup.getChildren().add(edgeGraph);
                        
                        //Ajouter les aretes
                        edgeDisplay.add(edgeGraph);
                        
                    }
                    if (addNode ||  addEdge) {
                        selectedNode.isSelected = false;
                        FillTransition ft1 = new FillTransition(Duration.millis(300), selectedNode, Color.web("#ff4444", 1), Color.web("#007E33", 1));
                        ft1.play();
                    }
                    selectedNode = null;
                    return;
                }
                
                FillTransition ft = new FillTransition(Duration.millis(300), nodeDestination, Color.web("#007E33", 1), Color.web("#ff4444", 1));
                ft.play();
                nodeDestination.isSelected = true;
                selectedNode = nodeDestination;
            }
        }
    };
    
    @FXML
    public void ResetHandle(ActionEvent event) {
        System.out.println("reset");
        
        nbNodes = 0;
        canvasGroup.getChildren().clear();
        canvasGroup.getChildren().addAll(viewer);
        selectedNode = null;
        nodeDisplay = new ArrayList<NodeFX>();
        edgeDisplay = new ArrayList<EdgeFX>();
        addNode = true;
        addEdge = false;
        addEdgeButton.setDisable(true);
        addNodeButton.setDisable(false);
        
        btnPlay.setVisible(true);
        btnPause.setVisible(false);
        description.setVisible(false);
        
        
        playing = false;
        started = false;
    }
    
    @FXML
    public void AddNodeHandle(ActionEvent event) {
        System.out.println("add Node");
        addNode = true;
        addEdge = false;
        addNodeButton.setSelected(true);
        addEdgeButton.setSelected(false);
        selectedNode = null;
    }
    
    @FXML
    public void AddEdgeHandle(ActionEvent event) {
        System.out.println("add Edge");
        addNode = false;
        addEdge = true;
        addNodeButton.setSelected(false);
        addEdgeButton.setSelected(true);
    }
    
    @FXML
    public void PauseHandle(ActionEvent event) {
        System.out.println("pause");
        btnPlay.setVisible(true);
        btnPause.setVisible(false);
        playing = false;
    }
    
    @FXML
    public void PlayHandle(ActionEvent event) {
        System.out.println("Play");
        if(nbNodes == 0) return;
        if(!started){
            Graph graph = new Graph(nodeDisplay, edgeDisplay);
            t = new Thread() {
                private volatile boolean stp = false;
                @Override
                public void run() {
                    GraphCompletFinder.TrouverCliqueMaximale(graph);
                }
                
                public void _stop(){
                    
                }
            };
            t.start();
            started = true;
        }
        playing = true;
        btnPlay.setVisible(false);
        btnPause.setVisible(true);
    }
    
    @FXML
    public void handle(MouseEvent ev) {
        if (addNode) {
            if (nbNodes == 1) {
                addNodeButton.setDisable(false);
            }
            if (nbNodes == 2) {
                addEdgeButton.setDisable(false);
                AddNodeHandle(null);
            }
            
            if (ev.getSource().equals(canvasGroup)) {
                if (ev.getEventType() == MouseEvent.MOUSE_RELEASED && ev.getButton() == MouseButton.PRIMARY) {
                    nbNodes++;
                    
                    NodeFX circle = new NodeFX(ev.getX(), ev.getY(), 1.2, String.valueOf(nbNodes));
                    canvasGroup.getChildren().add(circle);
                    
                    circle.setOnMousePressed(mouseHandler);
                    circle.setOnMouseReleased(mouseHandler);
                    circle.setOnMouseDragged(mouseHandler);
                    circle.setOnMouseExited(mouseHandler);
                    
                    ScaleTransition tr = new ScaleTransition(Duration.millis(100), circle);
                    tr.setByX(10f);
                    tr.setByY(10f);
                    tr.setInterpolator(Interpolator.EASE_OUT);
                    tr.play();
                }
            }
        }
    }
 
/* -------------------------------------------------------------------- */
    /* classe local pour gérer les sommets */
    public class NodeFX extends Circle{
        Sommet sommet;
        Point point;
        Label id;
        boolean isSelected = false;

        public NodeFX(double x, double y, double rad, String name) {
            super(x, y, rad, Color.web("#007E33", 1) );
            point = new Point((int) x, (int) y);
            
            // Ajouter le label
            id = new Label(name);
            canvasGroup.getChildren().add(id);
            id.setLayoutX(x - 18);
            id.setLayoutY(y - 18);
            this.setOpacity(0.5);
            this.setBlendMode(BlendMode.MULTIPLY);
            this.setId("node"); 
            
            this.setStyle("z-index: 10;");
            
            nodeDisplay.add(this);
            System.out.println("ADDing: " + nodeDisplay.size());
        }
        
        public void changeColor(Color c){
            this.setFill(c);
        }

    }
/* ------------------------------------------------------------------- */
       
    /* classe local pour gérer les aretes */
    public class EdgeFX extends Line {
        NodeFX node1, node2;

        public EdgeFX(NodeFX node1, NodeFX node2) {
            super(
                edgeX1(node1.point.x, node1.point.y ,node2.point.x, node2.point.y, 13),
                edgeY1(node1.point.x, node1.point.y ,node2.point.x, node2.point.y, 13),
                edgeX2(node1.point.x, node1.point.y ,node2.point.x, node2.point.y, 13),
                edgeY2(node1.point.x, node1.point.y ,node2.point.x, node2.point.y, 13)
                );
            //super(node1.point.x, node1.point.y ,node2.point.x, node2.point.y);
            this.node1 = node1;
            this.node2 = node2;
        }
    }
    
    private static int edgeX1(int x1, int y1, int x2, int y2, double radius){
        int vx = x2 - x1;
        int vy = y2 - y1;
        double len = Math.sqrt(vx * vx + vy * vy);
        return x1 + (int)Math.round(vx * radius / len);
    }
    
    private static int edgeX2(int x1, int y1, int x2, int y2, double radius){
        int vx = x2 - x1;
        int vy = y2 - y1;
        double len = Math.sqrt(vx * vx + vy * vy);
        return x2 - (int)Math.round(vx * radius / len);
    }
    
    private static int edgeY1(int x1, int y1, int x2, int y2, double radius){
        int vx = x2 - x1;
        int vy = y2 - y1;
        double len = Math.sqrt(vx * vx + vy * vy);
        return y1 + (int)Math.round(vy * radius / len);
    }
    
    private static int edgeY2(int x1, int y1, int x2, int y2, double radius){
        int vx = x2 - x1;
        int vy = y2 - y1;
        double len = Math.sqrt(vx * vx + vy * vy);
        return y2 - (int)Math.round(vy * radius / len);
    }
    
/* ---------------------------------------------------------------------- */
    
    boolean edgeExists(NodeFX u, NodeFX v) {
        for (EdgeFX e : edgeDisplay) {
            if ((e.node1 == u && e.node2 == v) || (e.node1 == v && e.node2 == u)) {
                return true;    
            }
        }   
        return false;
    }
    
    /* ------------- Coloriage --------------- */
    public void colorierRouge(Sommet s){
        for(NodeFX n:nodeDisplay){
            if(n.sommet == s){
                n.changeColor(Color.RED);
            }
        }
    }
    
    public void colorierVert(Sommet s){
        for(NodeFX n:nodeDisplay){
            if(n.sommet == s){
                n.changeColor(Color.GREEN);
            }
        }
    }
    public void colorierJaune(Sommet s){
        for(NodeFX n:nodeDisplay){
            if(n.sommet == s){
                n.changeColor(Color.YELLOW);
            }
        }
    }
    
    public void colorierGris(Sommet s){
        for(NodeFX n:nodeDisplay){
            if(n.sommet == s){
                n.changeColor(Color.GRAY);
            }
        }
    }
    
    public void SetUIText(int id, String text){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                description.setVisible(true);
                Label[] l = new Label[]{ text1, text2, text3};
                l[id].setText(text);
            }
        });
    }
    
    public void HideUIText(){
        description.setVisible(false);
    }
}