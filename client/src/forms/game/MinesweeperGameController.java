package forms.game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MinesweeperGameController implements Initializable {
    private Random random = new Random();

    private static int TILE_SIZE=35;
    private static int TILE_AMOUNT_X ;
    private static int TILE_AMOUNT_Y;
    private static int TOTAL_BOMBS ;

    private int remainingBombs;
    private int unopenedClearTiles;

    private Tile[][] grid;
    private GridPane gridPane;

    private boolean firstClick = true;
    private boolean gameOver = false;
    public static final int EASY_TILE_AMOUNT_X = 9;
    public static final int EASY_TILE_AMOUNT_Y = 9;
    public static final int EASY_TOTAL_BOMBS = 10;

    public static final int MEDIUM_TILE_AMOUNT_X = 16;
    public static final int MEDIUM_TILE_AMOUNT_Y = 16;
    public static final int MEDIUM_TOTAL_BOMBS = 40;

    public void setEasyDifficulty() {
        setTileAmountX(EASY_TILE_AMOUNT_X);
        setTileAmountY(EASY_TILE_AMOUNT_Y);
        setTotalBombs(EASY_TOTAL_BOMBS);
    }

    public void setMediumDifficulty() {
        setTileAmountX(MEDIUM_TILE_AMOUNT_X);
        setTileAmountY(MEDIUM_TILE_AMOUNT_Y);
        setTotalBombs(MEDIUM_TOTAL_BOMBS);
    }

    @FXML
    private BorderPane root;
    @FXML
    private Text bombAmountDisplay;


    @FXML
    public void newGameEasy() {
        setEasyDifficulty();
        newGame();
    }

    @FXML
    public void newGameMedium() {
        setMediumDifficulty();
        newGame();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        root.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.N == event.getCode()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm");
                alert.setHeaderText("New game?");
                alert.setContentText("Are you sure you want to start a new game?");

                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent()) {
                    if (result.get() == ButtonType.OK) {
                        newGame();
                    } else {
                        event.consume();
                    }
                }
            }
        });
    }

    public static void setTileSize(int tileSize) {
        TILE_SIZE = tileSize;
    }

    public static void setTileAmountX(int tileAmountX) {
        TILE_AMOUNT_X = tileAmountX;
    }

    public static void setTileAmountY(int tileAmountY) {
        TILE_AMOUNT_Y = tileAmountY;
    }

    public static void setTotalBombs(int totalBombs) {
        TOTAL_BOMBS = totalBombs;
    }

    public static int getTileSize() {
        return TILE_SIZE;
    }

    public static int getTileAmountX() {
        return TILE_AMOUNT_X;
    }

    public static int getTileAmountY() {
        return TILE_AMOUNT_Y;
    }

    public static int getTotalBombs() {
        return TOTAL_BOMBS;
    }

    private void updateBombAmountDisplay() {
        bombAmountDisplay.setText(String.valueOf(this.remainingBombs));
    }

    private class Tile extends StackPane {
        private int xPos;
        private int yPos;

        private boolean hasBomb;
        private int nearbyBombs;
        private boolean isOpen = false;
        private boolean isFlagged = false;
        private boolean hovered = false;

        private static Paint UNOPENED_BG = Color.WHITE;
        private static Paint UNOPENED_STROKE =  Color.rgb(229, 231, 233);
        private static Paint OPENED_BG = Color.rgb(234, 237, 237);
        private static Paint OPENED_STROKE = Color.rgb(229, 231, 233);
        private final Rectangle rectangle = new Rectangle(
                TILE_SIZE - 2.0, TILE_SIZE - 2.0);
        private final Text text = new Text();
        private ImageView imageView = new ImageView();

        public Tile(int xPos, int yPos) {
            this.xPos = xPos;
            this.yPos = yPos;

            imageView.preserveRatioProperty();
            imageView.setFitHeight(TILE_SIZE - (TILE_SIZE * 0.20));
            imageView.setFitWidth(TILE_SIZE - (TILE_SIZE * 0.20));

            rectangle.setFill(UNOPENED_BG);
            rectangle.setStroke(UNOPENED_STROKE);


            Font font = Font.font("Verdana", FontWeight.BOLD, 24);
            text.setFont(font);
            this.getChildren().addAll(rectangle, text, imageView);


            this.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    this.open();
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    this.flag();
                    if (this.isOpen) {
                        openNeighbours(this);
                    }
                }
            });

            this.hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    rectangle.setFill(OPENED_BG);
                    hovered = true;
                } else if (oldValue) {
                    if (!isOpen) {
                        rectangle.setFill(UNOPENED_BG);
                    }
                    hovered = false;
                }
            });
        }

        private void updateNearbyBombs() {

            long bombs = getNeighbours(this).stream().filter(t -> t.hasBomb).count();
            this.nearbyBombs = (int) bombs;
        }


        private void updateText() {
            if (hasBomb || this.nearbyBombs > 0) {
                this.text.setText(hasBomb ? "" : String.valueOf(this.nearbyBombs));
            }

            Color color;
            if (hasBomb) {
                color = Color.WHITE;
                this.setImage("/images/Bomb@3x.png");
            } else {
                color = switch (nearbyBombs) {
                    case 1 -> Color.rgb(133, 193, 233 );
                    case 2 -> Color.rgb(88, 214, 141 );
                    case 3 -> Color.rgb(236, 112, 99);
                    case 4 -> Color.rgb(40, 116, 166);
                    default -> Color.SILVER;
                };
            }

            text.setFill(color);
        }


        private void open() {
            if (firstClick) {
                placeBombs(this);
                getAllTiles().forEach(Tile::updateNearbyBombs);
                updateBombAmountDisplay();
                firstClick = false;
            }

            if (!isOpen && !isFlagged && !gameOver) {
                revealTile();

                if (hasBomb) {
                    gameOver = true;
                    displayGameOverAlert();
                    newGame();
                } else {
                    unopenedClearTiles--;
                    if (unopenedClearTiles == 0) {
                        flagAllBombs();
                        displayWinAlert();
                        gameOver = true;
                    }
                }
                if (nearbyBombs == 0 && !hasBomb) {
                    getNeighbours(this).forEach(Tile::open);
                }
            } else if (!isOpen && gameOver) {
                if (hasBomb && !isFlagged) {
                    this.revealTile();
                    this.setImage("images/Bomb@3x.png");
                } else if (!hasBomb && isFlagged) {
                    this.setImage("images/NoBomb@3x.png");
                }
            }
        }

        private void displayGameOverAlert() {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("Game Over!");
            alert.setContentText("Sorry, you clicked on a bomb. Game over!");
            alert.showAndWait();
        }

        private void setImage(String imagePath) {
            Image image = new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream(imagePath)));
            imageView.setImage(image);
        }

        private void removeImage() {
            setImage("images/Transparent.png");
        }

        private void revealTile() {
            this.isOpen = true;
            text.setVisible(true);
            rectangle.setFill(OPENED_BG);
            rectangle.setStroke(OPENED_STROKE);
            this.updateText();
        }

        private void flag() {
            if (!isOpen && !gameOver) {
                if (isFlagged) {
                    this.isFlagged = false;
                    this.removeImage();
                    remainingBombs++;
                    updateBombAmountDisplay();
                } else {
                    this.isFlagged = true;
                    this.setImage("/images/Flag@3x.png");
                    remainingBombs--;
                    updateBombAmountDisplay();
                }
            }
        }

        private void flagAllBombs() {
            for (int y = 0; y < TILE_AMOUNT_Y; y++) {
                for (int x = 0; x < TILE_AMOUNT_X; x++) {
                    Tile tile = grid[x][y];

                    if (tile.hasBomb && !tile.isFlagged) {
                        tile.flag();
                    }
                }
            }
        }
    }

    private void createContent() {

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setFocusTraversable(true);
        root.setCenter(vBox);

        grid = new Tile[TILE_AMOUNT_X][TILE_AMOUNT_Y];

        gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        gridPane.setAlignment(Pos.CENTER);
        vBox.getChildren().add(gridPane);


        for (int y = 0; y < TILE_AMOUNT_Y; y++) {
            for (int x = 0; x < TILE_AMOUNT_X; x++) {
                Tile tile = new Tile(x, y);

                grid[x][y] = tile;
                gridPane.add(tile, x, y);
            }
        }
        updateBombAmountDisplay();
    }


    private List<Tile> getNeighbours(Tile tile) {
        List<Tile> neighbours = new ArrayList<>();

        int[] points = new int[]{
                //  x   x   x
                //  x   *   x
                //  x   x   x

                -1, -1, // top left
                -1, 0,  // top center
                -1, 1,  // top right
                0, -1,  // middle left
                0, 1,   // middle right
                1, -1,  // bottom left
                1, 0,   // bottom center
                1, 1    // bottom right
        };

        for (int i = 0; i < points.length; i = i + 2) {
            int dx = points[i];
            int dy = points[i + 1];

            int newX = tile.xPos + dx;
            int newY = tile.yPos + dy;

            if (newX >= 0 && newX < TILE_AMOUNT_X
                    && newY >= 0 && newY < TILE_AMOUNT_Y) {
                neighbours.add(grid[newX][newY]);
            }
        }

        return neighbours;
    }

    private void placeBombs(Tile startingTile) {
        List<Tile> allTiles = getAllTiles();

        List<Tile> possibleBombTiles = allTiles;
        possibleBombTiles.removeAll(getNeighbours(startingTile));
        possibleBombTiles.remove(startingTile);

        while (remainingBombs < TOTAL_BOMBS) {
            int randomInt = random.nextInt(possibleBombTiles.size());
            Tile tile = possibleBombTiles.get(randomInt);

            if (!tile.hasBomb) {
                tile.hasBomb = true;
                remainingBombs++;
            }
        }
    }

    private List<Tile> getAllTiles() {
        List<Tile> allTiles = new ArrayList<>();
        for (int y = 0; y < TILE_AMOUNT_Y; y++) {
            for (int x = 0; x < TILE_AMOUNT_X; x++) {
                Tile tile = grid[x][y];
                allTiles.add(tile);
            }
        }

        return allTiles;
    }

    private void openNeighbours(Tile tile) {
        List<Tile> neighbours = getNeighbours(tile);
        if (neighbours.stream().filter(t -> t.isFlagged).count() == tile.nearbyBombs) {
            neighbours.forEach(Tile::open);
        }
    }

    @FXML
    public void newGame() {
        Pane center = (Pane) root.getCenter();
        root.getChildren().remove(center);

        gameOver = false;
        firstClick = true;

        remainingBombs = 0;
        unopenedClearTiles = (TILE_AMOUNT_X * TILE_AMOUNT_Y) - TOTAL_BOMBS;

        createContent();
    }

    private void displayWinAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Win");
        alert.setHeaderText("Win!");
        alert.setContentText("Congratulations, you have won the game. ");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }



}