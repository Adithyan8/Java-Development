import java.util.*;

class Player
{
    String name;
    char symbol;

    public Player(String name, char symbol)
    {
        this.name=name;
        this.symbol = symbol;
    }
    String getName()
    {
        return this.name;
    }

    char getSymbol()
    {
        return this.symbol;
    }

    void getPlayerDetails()
    {
        System.out.println("Name : " + this.name);
        System.out.println("Symbol : " + this.symbol);
    }
}

class Board
{
    char[][] board;
    int[] currentLevel;
    int rows,cols;
    int freeCols; //Number of columns in which symbol can be dropped

    public Board()
    {
        rows=2;
        cols=2;
        freeCols = cols;

        board = new char[rows][cols];
        for(int row=0;row<rows;row++)
            Arrays.fill(board[row],' ');

        currentLevel = new int[cols];
        Arrays.fill(currentLevel,rows);
    }

    char[][] getBoard()
    {
        return this.board;
    }

    void setBoard(Player player, int row, int col)
    {
        this.board[row][col] = player.getSymbol();
    }

    int getFreeCols()
    {
        return this.freeCols;
    }

    void decrementFreeCols()
    {
        this.freeCols--;
    }

    int[] getCurrentLevel()
    {
        return this.currentLevel;
    }

    void decrementCurrentLevel(int col)
    {
        this.currentLevel[col]--;
    }

    int makeMove(Player currentPlayer)
    {
        int chosenCol=-1;
        System.out.println(currentPlayer.getName() + "'s turn : ");
        while(true)
        {
            System.out.println("Enter the column : ");
            Scanner scan = new Scanner(System.in);
            chosenCol = scan.nextInt();

            if(chosenCol < 0 || chosenCol >= this.cols)
            {
                System.out.println("Column number is invalid. Choose another column!!");
                continue;
            }

            if(this.getCurrentLevel()[chosenCol] == 0)
            {
                System.out.println("This column is full. Choose another column!!");
            }
            else
            {
                this.decrementCurrentLevel(chosenCol);
                if(this.getCurrentLevel()[chosenCol] == 0)  this.decrementFreeCols();
                this.setBoard(currentPlayer,this.getCurrentLevel()[chosenCol],chosenCol);
                break;
            }
        }
        return chosenCol;
    }

    boolean didPlayerWin(Player player,int row, int col)
    {
        int countOfSameSymbols,currRow,currCol;

        //left diagonal
        countOfSameSymbols=0;

        for(currRow = row-1,currCol = col-1;currRow>=0 && currCol >=0;currRow--,currCol--)
        {
            if(this.getBoard()[currRow][currCol] == player.getSymbol())
                countOfSameSymbols++;
            else
                break;

            if(countOfSameSymbols == 3) return true;
        }

        for(currRow = row+1,currCol = col+1;currRow<this.rows && currCol<this.cols ;currRow++,currCol++)
        {
            if(this.getBoard()[currRow][currCol] == player.getSymbol())
                countOfSameSymbols++;
            else
                break;

            if(countOfSameSymbols == 3) return true;
        }

        //right diagonal
        countOfSameSymbols=0;

        for(currRow = row+1,currCol = col-1;currRow < this.rows && currCol >= 0;currRow++,currCol--)
        {
            if(this.getBoard()[currRow][currCol] == player.getSymbol())
                countOfSameSymbols++;
            else
                break;

            if(countOfSameSymbols == 3) return true;
        }

        for(currRow = row-1,currCol = col+1;currRow >= 0 && currCol<this.cols ;currRow--,currCol++)
        {
            if(this.getBoard()[currRow][currCol] == player.getSymbol())
                countOfSameSymbols++;
            else
                break;

            if(countOfSameSymbols == 3) return true;
        }

        //horizontal
        countOfSameSymbols=0;

        for(currRow = row ,currCol = col-1; currCol >= 0; currCol--)
        {
            if(this.getBoard()[currRow][currCol] == player.getSymbol())
                countOfSameSymbols++;
            else
                break;

            if(countOfSameSymbols == 3) return true;
        }

        for(currRow = row,currCol = col+1; currCol<this.cols ; currCol++)
        {
            if(this.getBoard()[currRow][currCol] == player.getSymbol())
                countOfSameSymbols++;
            else
                break;

            if(countOfSameSymbols == 3) return true;
        }

        //vertical
        countOfSameSymbols=0;

        for(currRow = row-1 ,currCol = col; currRow >= 0; currRow--)
        {
            if(this.getBoard()[currRow][currCol] == player.getSymbol())
                countOfSameSymbols++;
            else
                break;

            if(countOfSameSymbols == 3) return true;
        }

        for(currRow = row+1 ,currCol = col; currRow<this.rows ; currRow++)
        {
            if(this.getBoard()[currRow][currCol] == player.getSymbol())
                countOfSameSymbols++;
            else
                break;

            if(countOfSameSymbols == 3) return true;
        }

        return false;
    }

    void displayBoard()
    {
        for(int col=0;col<this.cols;col++)
        {
            System.out.print(" | "+ col);
        }
        System.out.println(" |");
        for(int col=0;col<this.cols;col++)
        {
            System.out.print("----");
        }
        System.out.println("----");
        for(int row=0;row<this.rows;row++)
        {
            for(int col=0;col<this.cols;col++)
            {
                System.out.print(" | "+this.board[row][col]);
            }
            System.out.println(" |");

            for(int col=0;col<this.cols;col++)
            {
                System.out.print("----");
            }
            System.out.println("----");
        }
    }
}

public class Connect4
{
    static void game(Board board, Player player1, Player player2)
    {
        int winningPlayer = 0;
        Player currentPlayer = player1;
        System.out.println("------------------------------------------");
        System.out.println("Welcome to CONNECT4");
        System.out.println("----------------------");
        System.out.println("Player 1 details : ");
        player1.getPlayerDetails();

        System.out.println("----------------------");
        System.out.println("Player 2 details : ");
        player2.getPlayerDetails();

        System.out.println("----------------------------------------");
        board.displayBoard();
        while(winningPlayer == 0)
        {
            System.out.println("----------------------------------------");

            int col = board.makeMove(currentPlayer);
            int row = board.getCurrentLevel()[col];
            //Display the board after the move
            board.displayBoard();

            //CHeck if player won with current move
            if(board.didPlayerWin(currentPlayer,row,col))
            {
                System.out.println("CONGRATULATIONS " + currentPlayer.getName() + " !!!!");
                System.out.println("YOU WON!!!");
                return;
            }

            //Checking if board is full
            if(board.getFreeCols() == 0)
            {
                System.out.println("No more possible moves!!!");
                System.out.println("RESULT : DRAW");
                return;
            }

            //Switching players
            currentPlayer = currentPlayer == player1 ? player2 : player1;
        }
    }

    public static void main(String[] args) {
        Board board = new Board();
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the name of Player 1 : ");
        String name = scan.nextLine();
        Player player1 = new Player(name,'X');
        System.out.println("Enter the name of Player 2 : ");
        name = scan.nextLine();
        Player player2 = new Player(name,'O');

        game(board,player1,player2);
    }
}