package algorithms;
import org.gamelink.game.Cram;
import org.gamelink.game.Algo;
import java.util.Random;
/* Team Members: Jaina Patel 100523188
                 Sagar Desai 100490491 */
                 
public class Crammers extends Algo{ // Replace TeamName
    private static String teamName = "Crammers"; // Replace TeamName

    public static String getTeamName(){
        return teamName;
    }

    public static void main(String[] args){
        Cram game = new Cram(false);
        game.startGame(Crammers.class); // Replace TeamName
    }

    public static String algorithm(Cram game){

    Random r = new Random();

    int y,x,y1,x1;
    boolean check;
    String s1,s2,s3,s4, move = "";

    	do{
    		int [][] board = game.getBoard();
    		y = (int)(Math.random()*5);
    		x = (int)(Math.random()*5);

        if (y == 0 && x == 0 ){

                if (board [y][x]== 0 && board [y+1][x]== 0){
                  y1 = y+1;
                  x1 = x;

                  s1 = String.valueOf(y);
                  s2 = String.valueOf(x);
                  s3 = String.valueOf(y1);
                  s4 = String.valueOf(x1);

                  move = s1 + s2 + s3 + s4;
                  check = true;
                //	return (move);
                  break;
                }

                else if (board [y][x]== 0 && board [y][x+1]== 0){
                  y1 = y;
                  x1 = x+1;

                  s1 = String.valueOf(y);
                  s2 = String.valueOf(x);
                  s3 = String.valueOf(y1);
                  s4 = String.valueOf(x1);

                  move = s1 + s2 + s3 + s4;
                  check = true;
                //	return (move);
                  break;
                }
                else{
                  check = false;
                }

        }
        else if (y == 4 && x == 0){
              if (board [y][x]== 0 && board [y-1][x]==0){
                y1 = y-1;
                x1 = x;

                s1 = String.valueOf(y);
                s2 = String.valueOf(x);
                s3 = String.valueOf(y1);
                s4 = String.valueOf(x1);

                move = s1 + s2 + s3 + s4;
                check = true;
              //	return (move);
                break;
              }

              else if (board [y][x]== 0 && board [y][x+1]== 0){
                y1 = y;
                x1 = x+1;

                s1 = String.valueOf(y);
                s2 = String.valueOf(x);
                s3 = String.valueOf(y1);
                s4 = String.valueOf(x1);

                move = s1 + s2 + s3 + s4;
                check = true;
              //	return (move);
                break;
              }
              else{
                check = false;
              }

        }
        else if (y == 0 && x == 4){

                if (board [y][x]== 0 && board [y+1][x]== 0){
                  y1 = y+1;
                  x1 = x;

                  s1 = String.valueOf(y);
                  s2 = String.valueOf(x);
                  s3 = String.valueOf(y1);
                  s4 = String.valueOf(x1);

                  move = s1 + s2 + s3 + s4;
                  check = true;
                //	return (move);
                  break;
                }
                else if (board [y][x]== 0 && board [y][x-1]==0){
                  y1 = y;
                  x1 = x-1;

                  s1 = String.valueOf(y);
                  s2 = String.valueOf(x);
                  s3 = String.valueOf(y1);
                  s4 = String.valueOf(x1);
                  move = s1 + s2 + s3 + s4;
                  check = true;
                //	return (move);
                  break;
                }

                else{
                  check = false;
                }

        }
        else if (y == 4 && x == 4){
                if (board [y][x]== 0 && board [y-1][x]==0){
                  y1 = y-1;
                  x1 = x;

                  s1 = String.valueOf(y);
                  s2 = String.valueOf(x);
                  s3 = String.valueOf(y1);
                  s4 = String.valueOf(x1);

                  move = s1 + s2 + s3 + s4;
                  check = true;
                //	return (move);
                  break;
                }

                else if (board [y][x]== 0 && board [y][x-1]==0){
                  y1 = y;
                  x1 = x-1;

                  s1 = String.valueOf(y);
                  s2 = String.valueOf(x);
                  s3 = String.valueOf(y1);
                  s4 = String.valueOf(x1);
                  move = s1 + s2 + s3 + s4;
                  check = true;
                //	return (move);
                  break;
                }

                else{
                  check = false;
                }
        }
        else if (y == 0 && (x == 1 || x == 2 || x == 3)){

                if (board [y][x]== 0 && board [y+1][x]== 0){
                  y1 = y+1;
                  x1 = x;

                  s1 = String.valueOf(y);
                  s2 = String.valueOf(x);
                  s3 = String.valueOf(y1);
                  s4 = String.valueOf(x1);

                  move = s1 + s2 + s3 + s4;
                  check = true;
                //	return (move);
                  break;
                }
                else if (board [y][x]== 0 && board [y][x-1]==0){
                  y1 = y;
                  x1 = x-1;

                  s1 = String.valueOf(y);
                  s2 = String.valueOf(x);
                  s3 = String.valueOf(y1);
                  s4 = String.valueOf(x1);
                  move = s1 + s2 + s3 + s4;
                  check = true;
                //	return (move);
                  break;
                }
                else if (board [y][x]== 0 && board [y][x+1]== 0){
                  y1 = y;
                  x1 = x+1;

                  s1 = String.valueOf(y);
                  s2 = String.valueOf(x);
                  s3 = String.valueOf(y1);
                  s4 = String.valueOf(x1);

                  move = s1 + s2 + s3 + s4;
                  check = true;
                //	return (move);
                  break;
                }
                else{
                  check = false;
                }

        }
        else if (y == 4 && (x == 1 || x == 2 || x == 3)){
                if (board [y][x]== 0 && board [y-1][x]==0){
                  y1 = y-1;
                  x1 = x;

                  s1 = String.valueOf(y);
                  s2 = String.valueOf(x);
                  s3 = String.valueOf(y1);
                  s4 = String.valueOf(x1);

                  move = s1 + s2 + s3 + s4;
                  check = true;
                //	return (move);
                  break;
                }
                else if (board [y][x]== 0 && board [y][x-1]==0){
                  y1 = y;
                  x1 = x-1;

                  s1 = String.valueOf(y);
                  s2 = String.valueOf(x);
                  s3 = String.valueOf(y1);
                  s4 = String.valueOf(x1);
                  move = s1 + s2 + s3 + s4;
                  check = true;
                //	return (move);
                  break;
                }
                else if (board [y][x]== 0 && board [y][x+1]== 0){
                  y1 = y;
                  x1 = x+1;

                  s1 = String.valueOf(y);
                  s2 = String.valueOf(x);
                  s3 = String.valueOf(y1);
                  s4 = String.valueOf(x1);

                  move = s1 + s2 + s3 + s4;
                  check = true;
                //	return (move);
                  break;
                }
                else{
                  check = false;
                }

        }
        else if ((y == 1 || y == 2 || y == 3) && x == 0){
                if (board [y][x]== 0 && board [y-1][x]==0){
                  y1 = y-1;
                  x1 = x;

                  s1 = String.valueOf(y);
                  s2 = String.valueOf(x);
                  s3 = String.valueOf(y1);
                  s4 = String.valueOf(x1);

                  move = s1 + s2 + s3 + s4;
                  check = true;
                //	return (move);
                  break;
                }
                else if (board [y][x]== 0 && board [y+1][x]== 0){
                  y1 = y+1;
                  x1 = x;

                  s1 = String.valueOf(y);
                  s2 = String.valueOf(x);
                  s3 = String.valueOf(y1);
                  s4 = String.valueOf(x1);

                  move = s1 + s2 + s3 + s4;
                  check = true;
                //	return (move);
                  break;
                }
                else if (board [y][x]== 0 && board [y][x+1]== 0){
                  y1 = y;
                  x1 = x+1;

                  s1 = String.valueOf(y);
                  s2 = String.valueOf(x);
                  s3 = String.valueOf(y1);
                  s4 = String.valueOf(x1);

                  move = s1 + s2 + s3 + s4;
                  check = true;
                //	return (move);
                  break;
                }
                else{
                  check = false;
                }

        }
        else if ((y == 1 || y == 2 || y == 3) && x == 4){
                if (board [y][x]== 0 && board [y-1][x]==0){
                  y1 = y-1;
                  x1 = x;

                  s1 = String.valueOf(y);
                  s2 = String.valueOf(x);
                  s3 = String.valueOf(y1);
                  s4 = String.valueOf(x1);

                  move = s1 + s2 + s3 + s4;
                  check = true;
                //	return (move);
                  break;
                }
                else if (board [y][x]== 0 && board [y+1][x]== 0){
                  y1 = y+1;
                  x1 = x;

                  s1 = String.valueOf(y);
                  s2 = String.valueOf(x);
                  s3 = String.valueOf(y1);
                  s4 = String.valueOf(x1);

                  move = s1 + s2 + s3 + s4;
                  check = true;
                //	return (move);
                  break;
                }
                else if (board [y][x]== 0 && board [y][x-1]==0){
                  y1 = y;
                  x1 = x-1;

                  s1 = String.valueOf(y);
                  s2 = String.valueOf(x);
                  s3 = String.valueOf(y1);
                  s4 = String.valueOf(x1);
                  move = s1 + s2 + s3 + s4;
                  check = true;
                //	return (move);
                  break;
                }
                else{
                  check = false;
                }

        }
        else{
                  if (board [y][x]== 0 && board [y-1][x]==0){
                    y1 = y-1;
                    x1 = x;

                    s1 = String.valueOf(y);
                    s2 = String.valueOf(x);
                    s3 = String.valueOf(y1);
                    s4 = String.valueOf(x1);

                    move = s1 + s2 + s3 + s4;
                    check = true;
                  //	return (move);
                    break;
                  }
                  else if (board [y][x]== 0 && board [y+1][x]== 0){
                    y1 = y+1;
                    x1 = x;

                    s1 = String.valueOf(y);
                    s2 = String.valueOf(x);
                    s3 = String.valueOf(y1);
                    s4 = String.valueOf(x1);

                    move = s1 + s2 + s3 + s4;
                    check = true;
                  //	return (move);
                    break;
                  }
                  else if (board [y][x]== 0 && board [y][x-1]==0){
                    y1 = y;
                    x1 = x-1;

                    s1 = String.valueOf(y);
                    s2 = String.valueOf(x);
                    s3 = String.valueOf(y1);
                    s4 = String.valueOf(x1);
                    move = s1 + s2 + s3 + s4;
                    check = true;
                  //	return (move);
                    break;
                  }
                  else if (board [y][x]== 0 && board [y][x+1]== 0){
                    y1 = y;
                    x1 = x+1;

                    s1 = String.valueOf(y);
                    s2 = String.valueOf(x);
                    s3 = String.valueOf(y1);
                    s4 = String.valueOf(x1);

                    move = s1 + s2 + s3 + s4;
                    check = true;
                  //	return (move);
                    break;
                  }
                  else{
                    check = false;
                  }

        }

    	}
    	while (check == false);

    	return (move);


    }
}
