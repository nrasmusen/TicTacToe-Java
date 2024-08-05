import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton playButton;
    private JButton quitButton; 
    private JButton resetButton;
    private JLabel titleLabel;
    private JPanel landingPanel;
    private JPanel gamePanel;
    private JButton[][] gameButtons = new JButton[3][3];
    private JButton quitGameButton;
    int rand1;
    int rand2;

    public TicTacToe() {
        setTitle("Tic Tac Toe");

        //Landing Screen
        landingPanel = new JPanel(new FlowLayout());

        titleLabel = new JLabel("Tic Tac Toe");
        landingPanel.add(titleLabel);

        playButton = new JButton("Play");
        playButton.addActionListener(this);
        landingPanel.add(playButton);

        quitButton = new JButton("Quit");
        quitButton.addActionListener(this);
        landingPanel.add(quitButton);        

        //Game Screen
        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameButtons[i][j] = new JButton();
                gameButtons[i][j].addActionListener(this);
                gridPanel.add(gameButtons[i][j]);
            }
        }
        gamePanel.add(gridPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        quitGameButton = new JButton("Quit Game");
        quitGameButton.addActionListener(this);
        buttonPanel.add(quitGameButton);

        resetButton = new JButton("Restart");
        resetButton.addActionListener(this);
        buttonPanel.add(resetButton);

        gamePanel.add(buttonPanel, BorderLayout.SOUTH);

        gamePanel.setVisible(false);

        this.setLayout(new CardLayout());
        this.add(landingPanel, "Landing");
        this.add(gamePanel, "Game");


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            // Switch to the game screen
            ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Game");
        } 
        else if (e.getSource() == quitButton || e.getSource() == quitGameButton) {
            System.exit(0);
        } 
        else if(e.getSource() == resetButton){
            resetGame();
        }
        else {
            // Player's turn
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (e.getSource() == gameButtons[i][j]) {
                        gameButtons[i][j].setText("X");
                        gameButtons[i][j].setEnabled(false);
                        winner(checkWin());
                        
                        boolean availableSquare = false;
                        for (int k = 0; k < 3; k++) {
                            for (int l = 0; l < 3; l++) {
                                if (gameButtons[k][l].isEnabled()) {
                                    availableSquare = true;
                                    break;
                                }
                            }
                            if (availableSquare) {
                                break;
                            }
                        }
                        
                        if (!availableSquare) {
                            return;
                        }

                        // AI's turn
                        boolean moveMade = false;
                        while (!moveMade) {
                            rand1 = (int) (Math.random() * 3);
                            rand2 = (int) (Math.random() * 3);
                            if (gameButtons[rand1][rand2].isEnabled()) {
                                gameButtons[rand1][rand2].setText("O");
                                gameButtons[rand1][rand2].setEnabled(false);
                                moveMade = true;
                                winner(checkWin());
                            }
                        }
                    }
                }
            }
        }

    }

    public void resetGame(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameButtons[i][j].setText("");
                gameButtons[i][j].setEnabled(true);
            }
        }
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
    
    public int checkWin(){
        //Rows
        for(int i=0; i<3; i++){
            if(gameButtons[i][0].getText().equals(gameButtons[i][1].getText()) && gameButtons[i][1].getText() == gameButtons[i][2].getText()){
                if(gameButtons[i][0].getText().equals("X")){
                    return 1;
                }
                else if (gameButtons[i][0].getText().equals("O")) {
                    return 2;
                }
            }
        }
        //Columns
        for (int j = 0; j < 3; j++) {
            if (gameButtons[0][j].getText() == gameButtons[1][j].getText() && gameButtons[1][j].getText() == gameButtons[2][j].getText()) {
                if (gameButtons[0][j].getText().equals("X")) {
                    return 1;
                }
                else if (gameButtons[0][j].getText().equals("O")) {
                    return 2;
                }
            }
        }
        //Diagonals
        if(gameButtons[0][0].getText() == gameButtons[1][1].getText() && gameButtons[1][1].getText() == gameButtons[2][2].getText()){
            if (gameButtons[1][1].getText().equals("X")) {
                return 1;
            } else if (gameButtons[1][1].getText().equals("O")) {
                return 2;
            }
        }
        if(gameButtons[0][2].getText() == gameButtons[1][1].getText() && gameButtons[1][1].getText() == gameButtons[2][0].getText()){
            if (gameButtons[1][1].getText().equals("X")) {
                return 1;
            } else if (gameButtons[1][1].getText().equals("O")) {
                return 2;
            }
        }

        return 0;
    }

    public void winner(int w){
        if(w == 1){
            JOptionPane.showMessageDialog(this, "You Won!!!");
            disableButtons();
        }
        else if(w == 2){
            JOptionPane.showMessageDialog(this, "You Lost :(");
            disableButtons();
        }
    }
    
    public void disableButtons(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameButtons[i][j].setEnabled(false);
            }
        }
    }
}