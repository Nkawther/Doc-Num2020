package doc.num.projet;

public class Tthread extends Thread{
	
	public int param;
	
	public Tthread(int param) {
		this.param = param;
	}
	//pause
	public static void Wait(long milli) {
		System.out.println("pause de "+milli+" ms");
		try {
			Thread.sleep(milli);
		}
		catch(InterruptedException x) {
			//ignorer
		}
	}
	
	public void run() {
	     while (true) {

		     try {
		    	// MyXMLHandler handler = new MyXMLHandler();
		    	 wait(60);
		     } catch (InterruptedException e) {
		     	e.printStackTrace();
		     }
	     }
	}
	
	public static void main(String[] args) {
		Tthread tt = new Tthread(1);
		tt.start();
		for(int i = 0; i<1000; i++) {
			
		}

	}
}
    
