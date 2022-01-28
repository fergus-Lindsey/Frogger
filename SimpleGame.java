/*ICS4U 
 *Lindsey Ferguson
 *the game I made is frogger (me and siovan kinda worked together on this? sorta partners)
 *my game includes:
 *a frog (non animated)
 *animated turtles, logs, cars and other obstacles
 *2 levels (on the second level everthing gets faster)
 *score,and a somewhat working timer
 *
 *ISSUES:
 *not collding properly with logs and turtles
 *not sinking in water due to collison issues
 **/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*;
import java.util.ArrayList;

public class SimpleGame extends JFrame implements ActionListener{
	Timer myTimer;   
	GamePanel game;
		
    public SimpleGame() {
		super("Frogger");//this creates and labels the frogger window
		setSize(516,630);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myTimer = new Timer(35, this);
		game = new GamePanel(this);
		add(game);
		setResizable(false);
		setVisible(true);
    }
    
	public void start(){
		myTimer.start();//starts the timers
	}

	public void actionPerformed(ActionEvent evt){
		game.move();//moves
		game.repaint();//repaints
	}
	
    public static void main(String[] arguments) {
		SimpleGame frame = new SimpleGame();//creates the simplegame
    }
}

class GamePanel extends JPanel{
	private boolean []keys; //involves the keys (the only ones i used were the arrow keys and the enter key)
	private Image back,Mediumlog,frogFinish,Smalllog,frogLivesPic,Largelog,car1Pic,car2Pic,car3Pic,car4Pic,car5Pic,top,titlePics,scoreBar,endPic,youwin,press;
	//this lables all the images and classifes them as Images
	private SimpleGame mainFrame;
	private static ArrayList<log> midLog = new ArrayList<log>();//creates a list where alll my logs are stores (since i had three types of logs i had to have 3 lists)
	private static ArrayList<log> topLog = new ArrayList<log>();
	private static ArrayList<log> bottomLog = new ArrayList<log>();
	private Frog frog; //creates m frog(calling from my frog class at the bottom)
	private disapearingturtles dturt1,dturt2,dturt21,dturt11,dturt31;//since i created separete classes for all my turtles and there were 2 different types, these ones sink 
	private floatingturtles fturt1,fturt2,fturt3,fturt4,fturt5,fturt6,fturt11,fturt21,fturt31,fturt41,fturt51;//these are the floating turtles they dont sink so they also required a new class
	private static Car[][] allCars = new Car[5][4];//a list for all my cars
	private static ArrayList<Integer> yPositions = new ArrayList<Integer>();
	private static boolean[] finishedFrog = new boolean[]{false, false, false, false, false};//this boolean allows me to check to see if they frog has completed all 
	private int score=0,lives=3,time = 420,turnCount = 0;//this is where i create my vairbles for score, lives and for my timer
	private String page = "title";//these allow me to create separete pages 
	int frameCnt = 0;//this is so i can use fonts 
	Font fontLocal=null,fontSys=null;
	
	public GamePanel(SimpleGame m){
		mainFrame = m;
		frog = new Frog(300,400); //where i set my frog
		keys = new boolean[KeyEvent.KEY_LAST+1];
		press = new ImageIcon("photos/PressEnter0.png").getImage();//this is where i load all my images 
		scoreBar = new ImageIcon("photos/score.png").getImage();//background and decoration photos
		youwin = new ImageIcon("photos/youWin.png").getImage();
		endPic = new ImageIcon("photos/end.png").getImage();
		titlePics = new ImageIcon("photos/froggertitle.png").getImage();
		back = new ImageIcon("photos/frogg.gif").getImage();
		top = new ImageIcon("photos/ACTtop.png").getImage();
		Mediumlog = new ImageIcon("photos/mediumLog.png").getImage();
		Smalllog = new ImageIcon("photos/smallLog.png").getImage();//log pictures
		Largelog = new ImageIcon("photos/largeLog.png").getImage();
		frogLivesPic = new ImageIcon("photos/Froglife.png").getImage();//frog lives pictires
		car1Pic = new ImageIcon("photos/car/car1.png").getImage();
		car2Pic = new ImageIcon("photos/car/car2.png").getImage();
		car3Pic = new ImageIcon("photos/car/car3.png").getImage();//the car pics
		car4Pic = new ImageIcon("photos/car/car4.png").getImage();
		car5Pic = new ImageIcon("photos/car/car5.png").getImage();	 
		frogFinish = new ImageIcon("photos/frog.png").getImage();//finihs frog pic	
		dturt1 =  new disapearingturtles(530,140,"turt",6);//disapearing turtles this creates tghe new turtles at thier own coords, 
		dturt2 =  new disapearingturtles(570,140,"turt",6);
		dturt11 =  new disapearingturtles(500,250,"turt",6);//disapearing turtles 
		dturt21 =  new disapearingturtles(540,250,"turt",6);
		dturt31 =  new disapearingturtles(580,250,"turt",6);
		fturt1 =  new floatingturtles(690,140,"floating",2);//floating turtles, this creates tghe new turtles at thier own coords, 
		fturt2 =  new floatingturtles(730,140,"floating",2);
		fturt3 =  new floatingturtles(830,140,"floating",2);
		fturt4 =  new floatingturtles(870,140,"floating",2);
		fturt5 =  new floatingturtles(970,140,"floating",2);
		fturt6 =  new floatingturtles(1010,140,"floating",2);
		fturt11 =  new floatingturtles(680,250,"floating",2);
		fturt21 =  new floatingturtles(720,250,"floating",2);
		fturt31 =  new floatingturtles(760,250,"floating",2);
		fturt31 =  new floatingturtles(860,250,"floating",2);
		fturt41 =  new floatingturtles(900,250,"floating",2);
		fturt51 =  new floatingturtles(940,250,"floating",2);
        addKeyListener(new moveListener());
    	for(int i=0; i<4; i++){//there are 4 log in each row, along with cars
    		topLog.add(new log(-130+randint(450,400)*i,110,Mediumlog));//this sets the logs, each have a different x, and y values 
       		midLog.add(new log(-200+randint(440,400)*i,180,Largelog));
       		bottomLog.add(new log(-100+randint(440,400)*i,220,Smalllog));
       		allCars[0][i] = new Car(356+randint(100,300)*i,463,car1Pic,-1);//this sets each row of cars as a new value, using the ranomizer to make ranomd x vals
        	allCars[1][i] = new Car(-20-randint(100,300)*i,434,car2Pic,1);
        	allCars[2][i] = new Car(356+randint(100,300)*i,402,car3Pic,-1);
        	allCars[3][i] = new Car(-20-randint(100,300)*i,360,car4Pic,2);//the -1,1,2 etc. are the direction and speed they are going
        	allCars[4][i] = new Car(356+randint(100,300)*i,336,car5Pic,-2);
		}
		String fName = "Rushing Nightshade DEMO.ttf";//this is where i load and start my font
    	InputStream is = GamePanel.class.getResourceAsStream(fName);
    	try{
    		fontLocal = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(32f);
    	}
    	catch(IOException ex){
    		System.out.println(ex);	
    	}
    	catch(FontFormatException ex){
    		System.out.println(ex);	
    	}
    	fontSys = new Font("Comic Sans MS",Font.PLAIN,15);
	}
	public static int randint(int low, int high){
      return (int)(Math.random()*(high-low+1)+low);
    }//this gets a random number for when the cars appear

    public void addNotify(){
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }
	
	public void move(){
		if(page == "1"||page =="title"){
			dturt1.move(-2);//this moves the turtles, both floating and disapearing
			dturt2.move(-2);
			fturt1.move(-2);
			fturt2.move(-2);
			fturt3.move(-2);
			fturt4.move(-2);
			fturt5.move(-2);
			fturt6.move(-2);
			dturt11.move(-2);
			dturt21.move(-2);
			dturt31.move(-2);
			fturt11.move(-2);
			fturt21.move(-2);
			fturt31.move(-2);
			fturt41.move(-2);
			fturt51.move(-2);
			ArrayList<Boolean> frogSittingOn = new ArrayList<Boolean>();
			for(int i = 0;i<3;i++){ //this moves the logs at thier own speed
					midLog.get(i).move(3);
					topLog.get(i).move(2);
					bottomLog.get(i).move(1);
				}
			for(int i=0; i<3; i++){//this is where he would collide with the logs, there were issues with this though
				if(midLog.get(i).frogTouch(frog) == true){
					//System.out.println("mid log collide");
					frog.setX(midLog.get(i).getLogX()+midLog.get(i).getLogSpeed());
				}
		      	if(topLog.get(i).frogTouch(frog) == true){      		
					//System.out.println("top log collide");
					frog.setX(topLog.get(i).getLogX()+topLog.get(i).getLogSpeed());
							}
		       	if(bottomLog.get(i).frogTouch(frog) == true){
					//System.out.println("bottom log collide");
					frog.setX(bottomLog.get(i).getLogX()+bottomLog.get(i).getLogSpeed());
						}
			}
			if(fturt1.frogTouch(frog) == true){ //this allows fro him to collide with the turtles, making his x thier x 
				frog.setX(fturt1.getturtX());	//this makes him stick to the turtles, so it appears hes sticking onto them, following them down the river
			}
			if(fturt2.frogTouch(frog) == true){
				frog.setX(fturt2.getturtX());
			}
			if(fturt3.frogTouch(frog) == true){
				frog.setX(fturt3.getturtX());
			}
			if(fturt4.frogTouch(frog) == true){
				frog.setX(fturt4.getturtX());
			}
			if(fturt5.frogTouch(frog) == true){
				frog.setX(fturt5.getturtX());
			}
			if(fturt6.frogTouch(frog) == true){
				frog.setX(fturt6.getturtX());
			}
			if(fturt11.frogTouch(frog) == true){
				frog.setX(fturt11.getturtX());
			}
			if(fturt21.frogTouch(frog) == true){
				frog.setX(fturt21.getturtX());
			}
			if(fturt31.frogTouch(frog) == true){
				frog.setX(fturt31.getturtX());
			}
			if(fturt41.frogTouch(frog) == true){
				frog.setX(fturt41.getturtX());
			}
			if(fturt51.frogTouch(frog) == true){
				frog.setX(fturt51.getturtX());
			}
			if(dturt1.frogTouch(frog) == true){//diaspearing turtles
				frog.setX(dturt1.getturtX());
			}
			if(dturt2.frogTouch(frog) == true){
				frog.setX(dturt2.getturtX());
			}
			if(dturt11.frogTouch(frog) == true){
				frog.setX(dturt11.getturtX());
			}
			if(dturt21.frogTouch(frog) == true){
				frog.setX(dturt21.getturtX());
			}
			if(dturt31.frogTouch(frog) == true){
				frog.setX(dturt31.getturtX());
			}
			
			for(int i=0; i<4; i++){
				for(int k=0; k<5; k++){//this moves the cars
						allCars[k][i].move();				
					}
			}
			if(keys[KeyEvent.VK_RIGHT]&&frog.getX()+frog.getPic().getWidth(null)<516){//this gets the user input for the frog 
				frog.move(23,0);//if the right arrow key is pressed then the from moves 23 pixels to teh right
				page = "1"; //also makes teh page go to level 1 (this is mostly for the title page)
			}
			else if(keys[KeyEvent.VK_LEFT]&&frog.getX()+frog.getPic().getWidth(null)>40){
				frog.move(-23,0);//if the left arrow key is pressed then the from moves 23 pixels to teh left
				page = "1";
				}
			else if(keys[KeyEvent.VK_UP]&& frog.getY()>50){
				frog.move(0,-28);//if the up arrow key is pressed then the from moves 23 pixels up
				page = "1";
				if(!yPositions.contains(frog.getY())){
						yPositions.add(frog.getY()); //this allows fro 10 points to be added everytime the frog advances up, oly adding them once,as he cant get more points for re going over paths
						score+=10;
				}
			}
			else if(keys[KeyEvent.VK_DOWN]&& frog.getY()<504){
				frog.move(0,28);//if the down arrow key is pressed then the from moves 23 pixels down
				page = "1";
				}
			else if(keys[KeyEvent.VK_ENTER]){
				page = "1";//for the title page if the enter key is pressed it moves onto the first level
				}
			Rectangle fRect = new Rectangle(frog.getX(),frog.getY(),frog.getPic().getWidth(null),frog.getPic().getHeight(null));//make sthe rectangel to see if the frog intersect with one of the finsihing rog rectangel	
			if(fRect.intersects(new Rectangle (20,60,30,30))){//if froggie gets in one of the slots at the very top then he becomes a finishe dfrog,and needs all 5 to move onto the next level
	    		finishedFrog[0] = true; //it becomes true 
	    		score+=40;//gets 40 points 
	    		yPositions.clear(); //cleasr the x posistion so he gest more points when going back up
	    		backToStart();//gets set to teh start
	    	}
	    	else if(fRect.intersects(new Rectangle (130,60,30,30))){//another slot
	    		finishedFrog[1] = true;
	    		yPositions.clear();
	    		score+=40;
	    		backToStart();
	    	}
	    	else if(fRect.intersects(new Rectangle (240,60,30,30))){//anotehr slot
	    		finishedFrog[2] = true;
	    		yPositions.clear();
	    		score+=40;
	    		backToStart();
	    	}
	    	else if(fRect.intersects(new Rectangle (350,60,30,30))){//anotehr slot
	    		finishedFrog[3] = true;
	    		yPositions.clear();
	    		score+=40;
	    		backToStart();
	    	}
	    	else if(fRect.intersects(new Rectangle (460,60,30,30))){//another slot
	    		finishedFrog[4] = true;
	    		yPositions.clear();
	    		score+=40;
	    		backToStart();
	    	}
	    	if(turnCount%2==0){
				time-=1;//slows down the timer
			}
			if(time==0){ //if the timer hits zero befroe the frog completes a slot he dies and get sent back to the start
				lives-=1;
				backToStart();
			}
		}
		if (page == "2"){ //basiclaly the same as level 1 just the logs,and turtles move alot faster
			dturt1.move(-3);
			dturt2.move(-3);
			fturt1.move(-3);
			fturt2.move(-3);
			fturt3.move(-3);
			fturt4.move(-3);
			fturt5.move(-3);
			fturt6.move(-3);
			dturt11.move(-3);
			dturt21.move(-3);
			dturt31.move(-3);
			fturt11.move(-3);
			fturt21.move(-3);
			fturt31.move(-3);
			fturt41.move(-3);
			fturt51.move(-3);
			ArrayList<Boolean> frogSittingOn = new ArrayList<Boolean>();
			for(int i = 0;i<3;i++){
					midLog.get(i).move(4);
					topLog.get(i).move(3);
					bottomLog.get(i).move(2);
				}
			for(int i=0; i<3; i++){
				if(midLog.get(i).frogTouch(frog) == true){
					System.out.println("mid log collide");
					frog.setX((frog.getX()+(frog.getX()-midLog.get(i).getLogX())+midLog.get(i).getLogSpeed()));
				}
		      	if(topLog.get(i).frogTouch(frog) == true){      
		      		System.out.println("top log collide");
					frog.setX((frog.getX()+(frog.getX()-topLog.get(i).getLogX())+topLog.get(i).getLogSpeed()));		
					//frog.setX(topLog.get(i).getLogSpeed());
				}
		       	if(bottomLog.get(i).frogTouch(frog) == true){
		       		System.out.println("bottom log collide");
					frog.setX((frog.getX()+(frog.getX()-bottomLog.get(i).getLogX())+bottomLog.get(i).getLogSpeed()));
							//frog.setX(bottomLog.get(i).getLogSpeed());
					}
				}
			if(fturt1.frogTouch(frog) == true){ //this allows fro him to collide with the turtles, making his x thier x, same as level 1
				frog.setX(fturt1.getturtX());
			}
			if(fturt2.frogTouch(frog) == true){
				frog.setX(fturt2.getturtX());
			}
			if(fturt3.frogTouch(frog) == true){
				frog.setX(fturt3.getturtX());
			}
			if(fturt4.frogTouch(frog) == true){
				frog.setX(fturt4.getturtX());
			}
			if(fturt5.frogTouch(frog) == true){
				frog.setX(fturt5.getturtX());
			}
			if(fturt6.frogTouch(frog) == true){
				frog.setX(fturt6.getturtX());
			}
			if(fturt11.frogTouch(frog) == true){
				frog.setX(fturt11.getturtX());
			}
			if(fturt21.frogTouch(frog) == true){
				frog.setX(fturt21.getturtX());
			}
			if(fturt31.frogTouch(frog) == true){
				frog.setX(fturt31.getturtX());
			}
			if(fturt41.frogTouch(frog) == true){
				frog.setX(fturt41.getturtX());
			}
			if(fturt51.frogTouch(frog) == true){
				frog.setX(fturt51.getturtX());
			}
			
			if(dturt1.frogTouch(frog) == true){//diaspearing turtles
				frog.setX(dturt1.getturtX());
			}
			if(dturt2.frogTouch(frog) == true){
				frog.setX(dturt2.getturtX());
			}
			if(dturt11.frogTouch(frog) == true){
				frog.setX(dturt11.getturtX());
			}
			if(dturt21.frogTouch(frog) == true){
				frog.setX(dturt21.getturtX());
			}
			if(dturt31.frogTouch(frog) == true){
				frog.setX(dturt31.getturtX());
			}
			
			for(int i=0; i<4; i++){
				for(int k=0; k<5; k++){
						allCars[k][i].move();				
					}
			}
			if(keys[KeyEvent.VK_RIGHT]&&frog.getX()+frog.getPic().getWidth(null)<516){
				frog.move(23,0);
			}
			else if(keys[KeyEvent.VK_LEFT]&&frog.getX()+frog.getPic().getWidth(null)>40){
				frog.move(-23,0);
				}
			else if(keys[KeyEvent.VK_UP]&& frog.getY()>50){
				frog.move(0,-28);
				if(!yPositions.contains(frog.getY())){
						yPositions.add(frog.getY());
						score+=10;
				}
			}
			else if(keys[KeyEvent.VK_DOWN]&& frog.getY()<504){
				frog.move(0,28);
				}
		
			Rectangle fRect = new Rectangle(frog.getX(),frog.getY(),frog.getPic().getWidth(null),frog.getPic().getHeight(null));	
			if(fRect.intersects(new Rectangle (20,60,30,30))){//if froggie gets in one of the slots at the 
	    		finishedFrog[0] = true;
	    		score+=40;
	    		yPositions.clear();
	    		backToStart();
	    	}
	    	else if(fRect.intersects(new Rectangle (130,60,30,30))){//
	    		finishedFrog[1] = true;
	    		yPositions.clear();
	    		score+=40;
	    		backToStart();
	    	}
	    	else if(fRect.intersects(new Rectangle (240,60,30,30))){
	    		finishedFrog[2] = true;
	    		yPositions.clear();
	    		score+=40;
	    		backToStart();
	    	}
	    	else if(fRect.intersects(new Rectangle (350,60,30,30))){
	    		finishedFrog[3] = true;
	    		yPositions.clear();
	    		score+=40;
	    		backToStart();
	    	}
	    	else if(fRect.intersects(new Rectangle (460,60,30,30))){
	    		finishedFrog[4] = true;
	    		yPositions.clear();
	    		score+=40;
	    		backToStart();
	    	}
	    	if(turnCount%2==0){
				time-=1;
			}
			if(time==0){
				lives-=1;
				backToStart();
				time = 420;
			}
		}
	}	
		
    class moveListener implements KeyListener{//gets when the keys are pressed and released
	    public void keyTyped(KeyEvent e) {}
	    public void keyPressed(KeyEvent e) {
	        keys[e.getKeyCode()] = true;
	    }
	    public void keyReleased(KeyEvent e) {
	        keys[e.getKeyCode()] = false;
	    }
    }    

	public void paint(Graphics g){ //this prints out all the garphic for the game
		//System.out.println(page);
	   	if (page == "title"){ //if the page is title, it prints out the basic layout with out and moving obstacles
	   			g.setColor(new Color(0,0,0));  
		        g.fillRect(0,0,getWidth(),getHeight());
		        g.drawImage(back,0,40,null);
		        g.drawImage(titlePics,75,120,null);//adds the photos
		        g.drawImage(scoreBar,15,5,null);
		        g.setColor(Color.WHITE);  
				g.setFont(fontSys);//this prints out the score and level, using the font i loaded earlier
	    		g.drawString(""+score,30,35);
	    		g.drawString("Level: "+page,430,20);
	    		g.drawImage(press,60,400, null);
	    	}  
	   	if(page =="1"){ //if pages = 1 this means its level 1, the cars, logs,cars, and the frog are allprinted here
	   			g.setColor(new Color(0,0,0));  
		        g.fillRect(0,0,getWidth(),getHeight());
		        g.drawImage(back,0,40,null);
		        g.setColor(new Color(3,3,71));  
		        g.fillRect(0,40,516,206);
		        g.drawImage(top,0,40,null);
	   			g.drawImage(scoreBar,15,5,null);//draws all the decortaions,score and level
		        g.setColor(Color.WHITE);  
				g.setFont(fontSys);
	    		g.drawString(""+score,30,35);
	    		g.drawString("Level: "+page,430,20);
		        g.drawImage(scoreBar,15,5,null);
		     	g.setColor(new Color(82, 92, 95));//time bar
	        	g.fillRect(80,570,time,15);
	        	Rectangle fRect = new Rectangle(frog.getX(),frog.getY(),frog.getPic().getWidth(null),frog.getPic().getHeight(null));
	        	if (fRect.intersects(new Rectangle (130,60,30,30))){
	        		lives-=1;
					backToStart();
	        	}
				dturt1.paint(g);
		    	dturt2.paint(g);
		    	fturt1.paint(g);
		    	fturt2.paint(g);//painst all the turtles,becuase im dumb and didnt addd them to a list
		    	fturt3.paint(g);
		    	fturt4.paint(g);
		    	fturt5.paint(g);
		    	fturt6.paint(g);
		    	dturt11.paint(g);
		    	dturt21.paint(g);
		    	dturt31.paint(g);
		    	fturt11.paint(g);
		    	fturt21.paint(g);
		    	fturt31.paint(g);
		    	fturt41.paint(g);
		    	fturt51.paint(g);
		    	for(int i=0; i<3; i++){ //this paints out all the logs as i have 3 dif types
		        	midLog.get(i).paint(g);
		        	topLog.get(i).paint(g);
		        	bottomLog.get(i).paint(g);
		    	}
		    	 for(int i=0; i<4; i++){
		        	for(int k=0; k<5; k++){
		        		allCars[k][i].paint(g);//this paints out all the cars and checks for collisons
		        		if(allCars[k][i].frogTouch(frog) == true){//this is where the car colllison is checked, if the frog has collided with a car
		        			lives-=1;							  // hes gets a life subtracted and gets sent back to the start
							backToStart();
		        		}
		        		else{
		        			frog.paint(g); //paints the frog
		        		}
					}
		    	}
		    	for(int z=0;z<5;z++){
	    			if(finishedFrog[z]==true){//if frog has reached the end, draw the frog image to show completion
	    				g.drawImage(frogFinish,20+(110*z),60, null); 
	    		}
	    	}
			for(int i = 0;i<lives;i++){
	    		g.drawImage(frogLivesPic,20+(i*20),570, null);  //this prints out the frogs lifes
	    	}
	    	
	    	if (finishedFrog[0] && finishedFrog[1] && finishedFrog[2] && finishedFrog[3] && finishedFrog[4]){
	    		page = "2"; //if the frog has completed all finish frogs then he goes onto the naext level, page 2
	    		System.out.println("win next level");
	    		}
	   		}
	   	if(page == "2"){//same as level 1 
	   		for(int z=0;z<5;z++){
	    			if(finishedFrog[z]=false){//this resets the completed frogs from the first level, allowing him to re do them
	    			}
	   		}
		    	g.setColor(new Color(0,0,0));  
		        g.fillRect(0,0,getWidth(),getHeight());
		        g.drawImage(back,0,40,null);
		        g.drawImage(scoreBar,15,5,null);
		        g.setColor(new Color(255,255,255));
		        g.drawString(""+score,30,35);
	    		g.drawString("Level: "+page,430,20);
		        g.setColor(new Color(3,3,71));  
		        g.fillRect(0,40,516,206);
		        g.drawImage(top,0,40,null);
		       
		     	g.setColor(new Color(82, 92, 95));//time bar
	        	g.fillRect(80,570,time,15);			
				dturt1.paint(g);
		    	dturt2.paint(g);
		    	fturt1.paint(g);
		    	fturt2.paint(g);
		    	fturt3.paint(g);
		    	fturt4.paint(g);
		    	fturt5.paint(g);
		    	fturt6.paint(g);
		    	dturt11.paint(g);
		    	dturt21.paint(g);
		    	dturt31.paint(g);
		    	fturt11.paint(g);
		    	fturt21.paint(g);
		    	fturt31.paint(g);
		    	fturt41.paint(g);
		    	fturt51.paint(g);
		    	for(int i=0; i<3; i++){
		        	midLog.get(i).paint(g);
		        	topLog.get(i).paint(g);
		        	bottomLog.get(i).paint(g);
		    	}
		    	 for(int i=0; i<4; i++){
		        	for(int k=0; k<5; k++){
		        		allCars[k][i].paint(g);
		        		if(allCars[k][i].frogTouch(frog) == true){
		        			lives-=1;
		        			
							backToStart();
		        		}
		        		else{
		        			frog.paint(g);
		        		}
					}
		    	}
		    	for(int z=0;z<5;z++){
	    			if(finishedFrog[z]==true){//if frog has reached the end, draw the frog image to signify that he has been there already
	    				g.drawImage(frogFinish,20+(110*z),60, null); 
	    		}
	    	}
	   	}
			for(int i = 0;i<lives;i++){
	    		g.drawImage(frogLivesPic,20+(i*20),570, null); 
	    	}   
    	if (finishedFrog[0] && finishedFrog[1] && finishedFrog[2] && finishedFrog[3] && finishedFrog[4]){
    		g.setColor(new Color(0,0,0));  
		    g.fillRect(0,0,getWidth(),getHeight());//if all frogs are in frog finsih for level 2 then you win
		    g.drawImage(youwin,90,40,null);
    		}	
	   	}
	   	public void backToStart(){//when the frog gets to the end or dies, he goes back to the start
	   		frog = new Frog(300,500);//this is hes starting position
	   		time = 420;//the timer is also re-set if the frog dies
	    	if(lives==0){//if frogBoy has no lives left the game ends
	    		endGame();
	    	}
	    }
	
	    public void endGame(){//game over, the game automatically ends itself
	    	System.out.println("OVER");
	    	page = "exit"; 
	    	System.exit(0);
	    	}
		}
	  
class Frog { //this is the frog class
   	private int frogx,frogy,dir;
  	private Image frogpic,right,left,down;//this is where i load to pictures for each direction the frog go
  	public static final int LEFT = 3, RIGHT = 4,UP = 1, DOWN = 0;//these are the direction the frog can go
  	
  	public Frog(int logx, int logy){
		this.frogx = frogx;
		this.frogy = frogy;
		frogx = 240; 
		frogy = 520;	
		dir = UP; 
		frogpic=new ImageIcon("photos/FrogJump0.png").getImage();//this is where i load the frogs images
		right=new ImageIcon("photos/right.png").getImage();
		left=new ImageIcon("photos/left.png").getImage();
		down=new ImageIcon("photos/down.png").getImage();
		}

	public void move(int dx,int dy){
		frogx += dx; //this shows the directions the frog goes 
		frogy += dy;
		if(dx>0){
			dir = RIGHT;//if the frog moves in a positive way then he is moving right
		}
		if(dx<0){
			dir = LEFT;//if the frog moves in a positive way then he is moving left
		}
		if(dy>0){
			dir = DOWN;//if the frog moves in a positive way then he is moving down
		}
		if(dy<0){
			dir = UP;//if the frog moves in a positive way then he is moving up
		}
    }
    
    public int getX(){ //allows me to access its x vaible from outside the class
		return frogx;
	}
	
	public void setX(int x){//allows me to access its x vaible from outside the class
		frogx = x;
	}
	
	public int getY(){//allows me to access its y vaible from outside the class
		return frogy;
	}
	
	public void setY(int y){//allows me to access its y vaible from outside the class
		frogy = y;
	}
	
	public Image getPic(){//allows me to access its picture from outside the class,usually to get its dimsions
		return frogpic;
	}
	
    public void paint(Graphics g){ //this is hwere the frog is drawn depending on its direction
    	if (dir == UP){
			g.drawImage(frogpic,frogx, frogy, null);
    	} 
    	else if (dir == DOWN){
			g.drawImage(down,frogx, frogy, null);
    	} 
    	else if (dir == RIGHT){
			g.drawImage(right,frogx, frogy, null);
	    	} 
    	else if (dir == LEFT){
			g.drawImage(left,frogx, frogy, null);
	    	}
    }
}

class log {
   	private int oglogx,logx,logy,speed;//this class allowed me to create all the logs, and with the photos 
  	private Image Largelog;
  	
  	public log(int logx, int logy, Image Largelog){
		this.logx = logx;
		this.logy = logy;//each log has a different x & y position
		this.Largelog = Largelog; //every log also has a pictures
		this.speed = speed;
		oglogx = logx;
  	}
  	
	public void move(int speed){
	 	logx +=speed;
    	if(oglogx <= -10 && logx > 516){
			logx = oglogx; //this makes the logs disapear after going out of this range, allowing the logs to cycle through 
			}
    	}
    public void paint(Graphics g){
		  g.drawImage(Largelog,logx,logy,null);//thi sjust paints the logs
		}
	public int getLogX(){ //getting the x and y varible, so when in trying to get the frog to sit on the logs he can by usingthier x and y
		return logx;
	}
	public int getLogY(){
		return logy;
	}
	public int getLogSpeed(){ //also getting thier speed
		return speed;
	}
		
	public boolean frogSits(Frog frog){
		int frogx = frog.getX();
		int frogy = frog.getY();//if a frog is sitting on a log, then its set as true (and supposd to make sure if hes in the water he dies)
		int frogWidth = frog.getPic().getWidth(null);
		int frogHeight = frog.getPic().getHeight(null);

		Rectangle fRect = new Rectangle(frogx, frogy, frogWidth, frogHeight);
		Rectangle lRect = new Rectangle(logx-5, logy-3, Largelog.getWidth(null)+10, Largelog.getHeight(null)+6);
		if(lRect.contains(fRect) || fRect.contains(lRect)){
			return true;
		}
		return false;
	}
	
	public boolean frogTouch(Frog frog){//same thing as teh frog sits but this just sees if hes touching it
		int frogx = frog.getX();
		int frogy = frog.getY();
		int frogWidth = frog.getPic().getWidth(null);
		int frogHeight = frog.getPic().getHeight(null);
		Rectangle fRect = new Rectangle(frogx, frogy, frogWidth, frogHeight);
		Rectangle cRect = new Rectangle(logx, logy, Largelog.getWidth(null), Largelog.getHeight(null));
		if(cRect.intersects(fRect) || fRect.intersects(cRect)){
			return true;
		}
		return false;
		}	
	}
    
class Car{
	private int origCarX, carX, carY, speed;
	private Image carPic;
	
	public Car(int carX, int carY, Image carPic,int speed){
		this.carX = carX;
		this.carY = carY;//each car starts at a different x & y position
		this.carPic = carPic;
		this.speed = speed;//each car also has its own speed and phtoto
		origCarX = carX;
	}
	
	public void move(){
		carX+=speed;
		if((origCarX > 516 && carX < -10)){
			carX = origCarX+GamePanel.randint(100,300); //this makes sure the cars get offscreen before resetting,
		}
		else if(origCarX <= -10 && carX > 516){
			carX = origCarX-GamePanel.randint(100,300);//this makes sure both car directions are coverd
		}
	}
	
	public void paint(Graphics g){
		g.drawImage(carPic,carX,carY, null);//draws the car
	}
	
	public boolean frogTouch(Frog frog){ //sees if a car touchs the frog if the frog does he dies
		int frogx = frog.getX();
		int frogy = frog.getY();
		int frogWidth = frog.getPic().getWidth(null);//gets height and width
		int frogHeight = frog.getPic().getHeight(null);
	
		Rectangle fRect = new Rectangle(frogx, frogy, frogWidth, frogHeight);
		Rectangle cRect = new Rectangle(carX, carY, carPic.getWidth(null), carPic.getHeight(null));//checks to see if the 2 rectangles intersect
		if(cRect.intersects(fRect) || fRect.intersects(cRect)){
			return true;//returns true if they do intersect
		}
		return false;
		}	
}

class disapearingturtles{ //thses turtles sink 
	private int turtlex,turtley,ogturtlex;
	private Image[]pics; //a picture array allows for animation as per frame it cycles through the photos
	private int dir, frame, delay;
	public static final int WAIT = 8;//make sthe animation faster or slower
	
	public disapearingturtles(int turtlex,int turtley,String name,int n){
		this.turtlex = turtlex;
		this.turtley = turtley;
		ogturtlex = turtlex;
		frame = 0;
		delay = 0;
		
		pics = new Image[n];
		for(int i = 0; i<n; i++){
			pics[i] = new ImageIcon(name+"/"+name+i+".png").getImage();//gets all the pictures froma file
		}
	}
	public int getturtX(){
		return turtlex;//allows me to access it outside of the class
	}
	public void move(int x){//this makes teh turtles move automatically 
		turtlex +=x;
		delay += 1;
		if(delay % WAIT == 0){
			frame = (frame + 1) % pics.length; //this is wher ethe pictures get cycled through by the frame, this if loop also slows it down so it dosent go to fast
		}
		if(turtlex < -30 ){
			turtlex += 550; //make sthem re-appear aftr going off screen, looks like a continous loop
			}
		if(turtlex > 520){
			turtlex -= 550;
			}
	}
	
		public boolean frogSits(Frog frog){//this (like the log) sees if a frog is sitting on them 
		int frogx = frog.getX();
		int frogy = frog.getY();
		int frogWidth = frog.getPic().getWidth(null); //gets the frogs positiona nd size to see if it would intersect
		int frogHeight = frog.getPic().getHeight(null);
		Rectangle fRect = new Rectangle(frogx, frogy, frogWidth, frogHeight); 
		Rectangle lRect = new Rectangle(turtlex, turtley, pics[frame].getWidth(null), pics[frame].getHeight(null));//makes the rectnagles to check and see if they intersect
		if(lRect.contains(fRect) || fRect.contains(lRect)){
			return true;//if a frog is sitting on it its not in the water and wont die
		}
		return false;
	}
	
	public boolean frogTouch(Frog frog){ //also like the log sees if the frog intersects with the turtle
		int frogx = frog.getX();
		int frogy = frog.getY();
		int frogWidth = frog.getPic().getWidth(null);//gets the frogs positiona nd size to see if it would intersect
		int frogHeight = frog.getPic().getHeight(null);
		Rectangle fRect = new Rectangle(frogx, frogy, frogWidth, frogHeight);
		Rectangle cRect = new Rectangle(turtlex, turtley,  pics[frame].getWidth(null),  pics[frame].getHeight(null));	//makes the rectangle to see if they intersect
		if(cRect.intersects(fRect) || fRect.intersects(cRect)){
			return true;//it will return true if the frog is touching teh turtle
		}
		return false;
		}
	
	public void paint(Graphics g){//this draws the animation
		int w = pics[frame].getWidth(null);
		int h = pics[frame].getHeight(null);
		g.drawImage(pics[frame], turtlex + w, turtley, -w, h, null);
	}		
}

class floatingturtles{//these turtles  float
	private int turtlex,turtley,ogturtlex;
	private Image[]pics;//a picture array allows for animation as per frame it cycles through the photos
	private int dir, frame, delay;
	public static final int WAIT = 10;//make sthe animation faster or slower
	public floatingturtles(int turtlex,int turtley,String name,int n){
		this.turtlex = turtlex;
		this.turtley = turtley;
		ogturtlex = turtlex;
		frame = 0;
		delay = 0;
		pics = new Image[n];
		for(int i = 0; i<n; i++){ //gets the pictire from the file
			pics[i] = new ImageIcon(name+"/"+name+i+".png").getImage();
		}
	}
	
	public void move(int x){
		turtlex +=x;
		delay += 1;
		if(delay % WAIT == 0){//this is wher ethe pictures get cycled through by the frame, this if loop also slows it down so it dosent go to fast
			frame = (frame + 1) % pics.length;
		}
		if(turtlex < -30 ){
			turtlex += 550; //this make sthe turtles re-appear after they go off screen so it looks like a continous loop
			}
		if(turtlex > 520){
			turtlex -= 550;
			}
	}
	public int getturtX(){
		return turtlex;//allows me to access it outside of the loop
	}
	public void paint(Graphics g){ //this draws the animation if the turtle
		int w = pics[frame].getWidth(null);
		int h = pics[frame].getHeight(null);
		g.drawImage(pics[frame], turtlex + w, turtley, -w, h, null);
	}
	
	public boolean frogSits(Frog frog){
		int frogx = frog.getX();
		int frogy = frog.getY();
		int frogWidth = frog.getPic().getWidth(null);//gets the frogs info
		int frogHeight = frog.getPic().getHeight(null);
		Rectangle fRect = new Rectangle(frogx, frogy, frogWidth, frogHeight);
		Rectangle lRect = new Rectangle(turtlex, turtley, pics[frame].getWidth(null), pics[frame].getHeight(null));//draws the rectangles to see if they intersect
		if(lRect.contains(fRect) || fRect.contains(lRect)){//if they do inersect that means the frog is sitting on a turtle, going with it
			return true;
		}
		return false;
	}
	
	public boolean frogTouch(Frog frog){//this meathod checks to see if the frog collides with the floating turtle
		int frogx = frog.getX();
		int frogy = frog.getY();
		int frogWidth = frog.getPic().getWidth(null);//gets the frogs info
		int frogHeight = frog.getPic().getHeight(null);
		Rectangle fRect = new Rectangle(frogx, frogy, frogWidth, frogHeight);//draws the rectangles to see if they intersect
		Rectangle cRect = new Rectangle(turtlex, turtley,  pics[frame].getWidth(null),  pics[frame].getHeight(null));	
		if(cRect.intersects(fRect) || fRect.intersects(cRect)){ //if they do intersect that means the frog is on a turtle
			return true;
		}
		return false;
	}			
}
