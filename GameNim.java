import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;


public class GameNim extends Game {
		
	char marks[] = {'O', 'X'}; //'O' for computer, 'X' for human
	
//    int winningCoins[][] = {
//        {8},
//        {7, 8},
//        {6, 7, 8}
//    };
    
    int WinningScore = 10;
    int LosingScore = -10;
    int NeutralScore = 0;    
    static int index = 0;
    static int gamecount = 13;
    
    public GameNim() {
    	currentState = new StateNim();
    }
    
    
    
    
    public boolean isWinState(State state){
    	
    	StateNim tstate = (StateNim) state;
    	
        //player who did the last move
        int previous_player = (state.player==0 ? 1 : 0);  
        char mark = marks[previous_player];
        
//        if (mark == tstate.board[winningCoins[0][0]]){
//                   
//               	return true;
//           }    
//        
//        else if (mark == tstate.board[winningCoins[1][0]]
//                && mark == tstate.board[winningCoins[1][1]]) {
//                   
//               	return true;
//           }        
//        
//        else if (mark == tstate.board[winningCoins[2][0]]
//             && mark == tstate.board[winningCoins[2][1]]
//             && mark == tstate.board[winningCoins[2][2]]) {
//                
//            	return true;
//        }
        
        
        if(tstate.count == 1) {
        	return true;
        } else {
            return false;	
        }
    }
    
    
    
    
    public boolean isStuckState(State state) {
    	
        if (isWinState(state)) 
            return false;
        
        StateNim tstate = (StateNim) state;
        
        for (int i=1; i<=13; i++) 
            if ( tstate.board[i] == '$') 
                return false;
        
        return true;
    }
	
	
    
    public boolean InRange(State state) {
    
    	StateNim tstate = (StateNim) state;
    	
    	if(tstate.count < 0) {
    		return true;
    	} else {
    		return false;
    	}	
    }
    
   
    
    
	public Set<State> getSuccessors(State state)
    {
		if(isWinState(state) || isStuckState(state))
			return null;
		
		Set<State> successors = new HashSet<State>();
        StateNim tstate = (StateNim) state;
        
        StateNim successor_state;
        
        char mark = 'O';
        if (tstate.player == 1) //human
            mark = 'X';
        
        
        //AI takes away i coins away up to 3
       for(int i = 1; i <= 3; i++) {
    	   
           successor_state = new StateNim(tstate);
           successor_state.count = successor_state.count - i;
           successor_state.player = (state.player==0 ? 1 : 0);  
           InRange(successor_state);
           successors.add(successor_state); 
           
       }
    		
        
        
//        for (int i = 1; i <= 9; i++) {
//            if (tstate.board[i] == ' ') {
//                successor_state = new StateNim(tstate);
//                successor_state.board[i] = mark;
//                successor_state.player = (state.player==0 ? 1 : 0); 
//                
//                successors.add(successor_state);
//            }
//        }
    
        return successors;
    }	
    
	
	
	
	
	
    public double eval(State state) 
    {   
    	if(isWinState(state)) {
    		//player who made last move
    		int previous_player = (state.player==0 ? 1 : 0);
    	
	    	if (previous_player==0) //computer wins
	            return WinningScore;
	    	else //human wins
	            return LosingScore;
    	}

        return NeutralScore;
    }
    
    
    
    public static void main(String[] args) throws Exception {
        
        System.out.println("!!! WELCOME TO NIM !!!\n");    	
    	
        System.out.println("Instructions:");
        System.out.println("Imagine a pile of coins.");
        System.out.println("There are 13 coins.");
        System.out.println("Grab 1 to 3 coins on your turn.");
        System.out.println("Try not to take the last coin to win.\n");

        Game game = new GameNim(); 
        Search search = new Search(game);
        int depth = 8;
      
        //needed to get human's move
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        while (true) {
        	
        	
        	
        	StateNim 	nextState = null;
        	
            switch ( game.currentState.player ) {
              case 1: //Human
                  
            	  System.out.println(game.currentState);
            	  
            	  //get human's move
//                  System.out.print("There are " + gamecount + " coins remaining.");
                  System.out.print("How many coins will you take?> ");
                  int pos = Integer.parseInt( in.readLine() );
                  
                  while(!(pos<4 && pos>0)) {
                      System.out.print("You can only take 1 to 3 Max. How many coins will you take?> ");
                      pos = Integer.parseInt( in.readLine() );
                  }
            	             
                  nextState = new StateNim((StateNim)game.currentState);
                  nextState.player = 1;
                                    
                  for(int i = 0; i < pos; i++) {
                	  nextState.board[index] = 'X';
                	  gamecount--;
                	  index++;
                  }
                  
            	  nextState.count = nextState.count - pos;            
                  
            	  
                  System.out.println("Human: \n" + nextState);
                  break;
                  
                  
              case 0: //Computer
            	  
            	  nextState = (StateNim)search.bestSuccessorState(depth);
            	  int diff = gamecount - nextState.count;
            	  System.out.println("Computer took " + diff + " coins" );
            	  
            	  for(int i = 0; i < diff; i++) {
                	  nextState.board[index] = 'O';
                	  gamecount--;
                	  index++;
            	  }
            	         	  
            	  
            	  nextState.player = 0;
            	  System.out.println("Computer: \n" + nextState);
                  break;
            }
            
            
            
                       
            game.currentState = nextState;
            //change player
            game.currentState.player = (game.currentState.player==0 ? 1 : 0);
            
            //Who wins?
            if ( game.isWinState(game.currentState) ) {
            
            	if (game.currentState.player == 1) //i.e. last move was by the computer
            		System.out.println("Computer wins!");
            	else
            		System.out.println("You win!");
            	
            	break;
            }
            
            if ( game.isStuckState(game.currentState) ) { 
            	System.out.println("Cat's game!");
            	break;
            }
        }
    }
	
	
}