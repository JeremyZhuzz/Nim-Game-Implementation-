
public class StateNim extends State {
	
	
    public char board[] = new char [13];
    
    int count = 13;
    
    public StateNim() {
    	
        for (int i=0; i<13; i++) 
            board[i] = '$';
        
        player = 1;
        this.count = count;
    }
    
    public StateNim(StateNim state) {
    	
        for(int i=0; i<13; i++)
            this.board[i] = state.board[i]; 
        
        player = state.player;
        this.count = state.count;
    }
    
    public String toString() {
    	
    	String ret = "|";
    		
    		for(int j=0; j<13; j++) {
    			ret += board[j] + " | ";
    		}
    		
    		ret += "\n----------------------------------------------------\n";
    	return ret +"\n" + this.count + " coins remaining";
    }
	
	
}