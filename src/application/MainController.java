package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.beans.binding.*;
import javafx.beans.property.SimpleStringProperty;
// set the class of border button
public class MainController {
  private 	boolean flagist=false,flag_arrow=false;     //flag variables flag for button events
  @FXML
  Label La,count1,count2;
  @FXML
  GridPane Gp;
  private static int botarray[][]=new int[12][12],marked[][]=new int[12][12];  
  private static int turn=0;
  
  static int bot1=4,bot2=4;    //initial number of bots
  private static ImageView cur;//  bot of first object
  
  private Button srcbot,destbot;
	private String HOVERED_BUTTON_STYLE="-fx-background-color:silver;";
	private String STANDARD_BUTTON_STYLE="";
	private Button buttonsInMatrix[][]=new Button[12][12];
   
	public void initialState(ActionEvent ae)
	{
		La.setText("Welcome to amazon");
		turn=0;
		flagist=false;
		flag_arrow=false;
		bot1=4;bot2=4;
		count1.setText(" " +bot1);
		  count2.setText(" " +bot2);
		cur=null;
		for(int i=0;i<12;i++)
		{
			for(int j=0;j<12;j++)
			{
				marked[i][j]=0;
				botarray[i][j]=0;
			}
		}
		
		botarray[1][4]=2;
		botarray[1][7]=2;
		botarray[4][1]=2;
		botarray[4][10]=2;
		//2 means bot 1 and 3 means bot 2
		botarray[7][1]=3;
		botarray[7][10]=3;
		botarray[10][4]=3;
		botarray[10][7]=3;
		
		BotsRed imgred[]=new BotsRed[4];
		for(int i=0;i<4;i++)
		{
			imgred[i]=new BotsRed();
			imgred[i].i.setFitWidth(50.0);
			imgred[i].i.setFitHeight(50.0);
		}

		
		BotsBlue imgblue[]=new BotsBlue[4];
		for(int i=0;i<4;i++)
		{
			imgblue[i]=new BotsBlue();
			imgblue[i].i.setFitWidth(50.0);
			imgblue[i].i.setFitHeight(50.0);
		}
		int count1=0,count2=0;
		for(Node n:Gp.getChildren())
		{ 
			Button by=null;
		try{
			by=(Button)n;}
			catch(Exception e)
			{ 
				
				continue;
			}
			
			if(by!=null)
			{ 
				Integer r=0,c=0;
			 c=GridPane.getColumnIndex((Node)by);
			 r=GridPane.getRowIndex((Node)by);
			 if(c!=null&&r!=null)
			 { buttonsInMatrix[r][c]=by;
				
			if((r.intValue()==1&&c.intValue()==4)||(r.intValue()==1&&c.intValue()==7)||(r.intValue()==4&&c.intValue()==1)||(r.intValue()==4&&c.intValue()==10))       //red image 
			{
				
			  by.setGraphic(imgred[count1++].i);
			}
			else	if((r.intValue()==7&&c.intValue()==1)||(r.intValue()==7&&c.intValue()==10)||(r.intValue()==10&&c.intValue()==4)||(r.intValue()==10&&c.intValue()==7))       //red image 
			{
				
			  by.setGraphic(imgblue[count2++].i);
			}
			else 
				by.setGraphic(null);				
		}}
		}
		
	}
	
	 private void changeBackgroundOnHoverUsingBinding(Node node) {
		    node.styleProperty().bind(
		      Bindings
		        .when(node.hoverProperty())
		          .then(
		            new SimpleStringProperty(HOVERED_BUTTON_STYLE)
		          )
		          .otherwise(
		            new SimpleStringProperty(STANDARD_BUTTON_STYLE)
		          )
		    );
		  }

	
   
	 @FXML
		public void processcells(ActionEvent ae)
		{
			Button btn=(Button)ae.getSource();
			 ImageView img=(ImageView)((Button)ae.getSource()).getGraphic();
			 int column=GridPane.getColumnIndex(btn).intValue();
			 int row =GridPane.getRowIndex(btn).intValue();
			
			 if(img !=null)
			 {
					 if(turn%2==0 && botarray[row][column]==2)
					 {
					 //bot1 turn
						 update();
						if(bot1!=0) //checkfor gameover
						{ 
							if(!flagist)
							 {
								 flagist=true;
								 flag_arrow=false;
								 srcbot=btn;
								 cur=img;
								 
							 }
							else
							{
								if(flag_arrow)
								{
									calldialogbox("Choose Another destination to fire arrow");
								}
								else
								{
									calldialogbox("choose anotheer destination to move your bot");
								}
							}
					 }
						else
							{La.setText("You have fucked yourself");}}
						else if (turn%2!=0 && botarray[row][column]==3)
					 {
					 //bot2 turn
							update();
						 if(bot2!=0) //checkfor gameover
							{ 
								if(!flagist)
								 {
									 flagist=true;
									 flag_arrow=false;
									 srcbot=btn;
									 cur=img;
									 
								 }
								else
									{La.setText("You have fucked yourself");}}
						 else
							{
								if(flag_arrow)
								{
									calldialogbox("Choose Another destination to fire arrow");
								}
								else
								{
									calldialogbox("choose anotheer destination to move your bot");
								}
							}
						 }
					 else{
						 if(!flagist)
						 calldialogbox("Choose a proper bot");
						 else
						 {
							 if(!flag_arrow)
								 calldialogbox("choose anotheer destination to move your bot");
						 else
							 calldialogbox("choose a proper destination to fire arrow");
						 }
					     }
			 }
			 else
			 {
				 if(flagist&&!flag_arrow)
				 {
					 if(checkpath(srcbot,btn))               // for a valid path 
					 {
						 flag_arrow=true;
						 destbot=btn;
						 srcbot.setGraphic(null);
						 int r=GridPane.getRowIndex(btn);
						 int c=GridPane.getColumnIndex(btn);
						 botarray[r][c]=0;
						 if(turn%2==0)
						 {
						 botarray[row][column]=2;
						 }
						 else
						 {
						 botarray[row][column]=3;
						 }
						 btn.setGraphic(cur);
						 update();
					 }
					 else
					 {
						 calldialogbox("Not a valid path");
						 flagist=false;
						 flag_arrow=false;
						 srcbot=null;
						 cur=null;
					 }
				 }
				 else
				 {
					 if(flagist&&flag_arrow)
					 {
						 if(checkpath(destbot,btn))
						 {
							 flag_arrow=false;
							 flagist=false;
							 turn++;
							 btn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("arrow.png"))));
							 botarray[row][column]=-1;
							 update();
						 }
						 else{
							 calldialogbox("Select a proper path to fire an arrow ");
						 }
					 }
				 }
			 }
			 }
   
	 boolean checktarget(Button b)
	 {
		 int column=GridPane.getColumnIndex(b);
		 int row =GridPane.getRowIndex(b);
		 if(b.getGraphic()==null&&(column>0&&column<11)&&(row>0&&row<11))
		 {
			 return true;
		 }
		 else 
			 return false;
	 }

	 Button getButton(int i,int j)
	 {
		 return buttonsInMatrix[i][j];
	 }
	 public void calldialogbox(String msg)  // function for setting up error message
	 {
	  
		 La.setText(msg);
	 }
	 boolean checkpath(Button a ,Button b)
	 {
		 int p1=GridPane.getRowIndex(a);
		 int p2=GridPane.getColumnIndex(a);
		 int np1=GridPane.getRowIndex(b);
		 int np2=GridPane.getColumnIndex(b);
		 int i=np1;
		 int j=np2;
		 if(i>p1&&j>p2)
		 {
			 int k,l;
		   boolean fl=false;
			for(  k = p1+1, l = p2+1 ; k <= np1 && l <= np2 ;k++,l++)
			{ 
				//check that no arrow or no other amazon will be there
				if(!checktarget(getButton(k,l)))  //make a get button function
				{
					fl=true;
					break;
				}
				
			}
			if(fl)
			{
				calldialogbox("Invalid move    "+k+"   "+l);//create a function for dialog box	
				return false;
			}
			else if(k!=np1+1 || l!=np2+1)
			{
				calldialogbox("Invalid move   "+k +"   "+l);//create a function for dialog box	
				return false;	
			}
			else{
				return true;	
			}
	       }
		 else if(i==p1 && j>p2)
			{
				int k,l;
				boolean fl=false;
				for( k=p1,l=p2+1;l<=np2;l++)
				{
					//check that no arrow or no other amazon will be there
					if(!checktarget(getButton(k,l)))
					{
						fl=true;
						break;
					}
					
				}
					if(fl)
					{
						calldialogbox("Invalid move");
						return false;
					}
						else if(k!=p1 || l!=np2+1)
					{
							calldialogbox("Invalid move");
							return false;
						
					}
				
					else{
						calldialogbox("move successful  "+k+"   "+l);
						return true ;
						
					}
			}
		 else if(i<p1 && j>p2)
			{
				int k,l;
				boolean fl=false;
				for(k=p1-1,l=p2+1;k>=np1&&l<=np2;k--,l++)
				{
					//check that no arrow or no other amazon will be there
					if(!checktarget(getButton(k,l)))
					{
						fl=true;
						break;
					}
					
				}
					if(fl==true)
					{
						calldialogbox("Invalid move");
						return false;
					}
						else if(k!=np1-1 || l!=np2+1)
					{
							calldialogbox("Invalid move");
							return false;
						
					}
				
					else{
						return true;
						
					}
			}
		 else if(i<p1 && j==p2)
			{
				int k,l;
				boolean fl=false;
				for( k=p1-1,l=p2;k>=np1;k--)
				{
					//check that no arrow or no other amazon will be there
					if(!checktarget(getButton(k,l)))
					{
						fl=true;
						break;
					}
					
				}
					if(fl==true)
					{
						calldialogbox("Invalid move");
						return false;
					}
						else if(k!=np1-1 || l!=p2)
					{
							calldialogbox("Invalid move");
							return false;
						
					}
				
					else{
						return true;
						
					}
			}
			
			else if(i<p1 && j<p2)
			{
				int f=0,k,l;
				for(k=p1-1,l=p2-1;k>=np1&&l>=np2;k--,l--)
				{
					//check that no arrow or no other amazon will be there
					if(!checktarget(getButton(k,l)))
					{
						f=1;
						break;
					}
					
				}
					if(f==1)
					{
						calldialogbox("Invalid move");
						return false;
					}
						else if(k!=np1-1 || l!=np2-1)
					{
							calldialogbox("Invalid move");
							return false;
						
					}
				
					else{
						return true;
						
					}
			}
			else if(i==p1 && j<p2)
			{
				int k,l;
				boolean fl=false;
				for(k=p1,l=p2-1;l>=np2;l--)
				{
					//check that no arrow or no other amazon will be there
					if(!checktarget(getButton(k,l)))
					{
						fl=true;
						break;
					}
					
				}
					if(fl==true)
					{
						calldialogbox("Invalid move");
						return false;
					}
					else if(k!=p1 || l!=np2-1)
					{
						
						calldialogbox("Invalid move");
						return false;
					}
				
					else{
						return true;
						
					}
			}
			else if(i>p1 && j<p2)
			{
				int k,l;
				boolean fl=false;
				for(k=p1+1,l=p2-1;k<=np1&&l>=np2;k++,l--)
				{
					//check that no arrow or no other amazon will be there
					if(!checktarget(getButton(k,l)))
					{
						fl=true;
						break;
					}
					
				}
					if(fl)
					{
						calldialogbox("Invalid move");
						return false;
					}
						else if(k!=np1+1 || l!=np2-1)
					{
							calldialogbox("Invalid move");
							return false;
						
					}
				
					else{
						return true;
						
					}
			}
			else if(i>p1 && j==p2)
			{
				int k,l;
				boolean fl=false;
				for(k=p1+1,l=p2;k<=np1;k++)
				{
					//check that no arrow or no other amazon will be there
					if(!checktarget(getButton(k,l)))
					{
						fl=true;
						break;
					}
					
				}
					if(fl)
					{
						calldialogbox("Invalid move");
						return false;
					}
					else if(k!=np1+1 || l!=p2)
					{
						calldialogbox("Invalid move");
						return false;
					}
					else{
						return true;	
					}
			}
			else{
				calldialogbox("Sorry Something went wrong");
				return false;
			}
}
   void  update()                      // return values 0-no game is still on 1-player1 2-player2
    {
	   for(int i=0;i<12;i++)
	   {
		   for(int j=0;j<12;j++)
		   {
			   if(botarray[i][j]==2)
			   {
				   if(checkalldirection(buttonsInMatrix[i][j])&&marked[i][j]==1)  //  two ifs are used as because only two combination are used
				   {
					   bot1++;
					   marked[i][j]=0;
				   }
				   if(!checkalldirection(buttonsInMatrix[i][j])&& marked[i][j]!=1)
				   {
					  bot1--;
				   }
			   }
			   if(botarray[i][j]==3)
			   {
				   if(checkalldirection(buttonsInMatrix[i][j])&&marked[i][j]==1)  //  two ifs are used as because only two combination are used
				   {
					   bot2++;
					   marked[i][j]=0;
				   }
				   if(!checkalldirection(buttonsInMatrix[i][j])&& marked[i][j]!=1)
				   {
					  bot2--;
				   }
			   }
			   
	   }
	   }
	   count1.setText(" " +bot1);
	   count2.setText(" " +bot2);
    	
    }
	boolean checkalldirection(Button b1)	
	{
		int p2=GridPane.getColumnIndex(b1).intValue();
		int p1=GridPane.getRowIndex(b1).intValue();
		if((getButton(p1+1,p2+1).getGraphic()!=null||getButton(p1+1,p2+1).getStyleClass().equals("Cl1"))&&(getButton(p1+1,p2).getGraphic()!=null||getButton(p1+1,p2).getStyleClass().equals("Id1"))&&(getButton(p1-1,p2).getGraphic()!=null||getButton(p1-1,p2).getStyleClass().equals("Id1"))&&(getButton(p1,p2+1).getGraphic()!=null||getButton(p1,p2+1).getStyleClass().equals("Cl1"))&&(getButton(p1-1,p2-1).getGraphic()!=null||getButton(p1-1,p2-1).getStyleClass().equals("Id1"))&&(getButton(p1,p2-1).getGraphic()!=null||getButton(p1,p2-1).getStyleClass().equals("Id1"))&&(getButton(p1-1,p2+1).getGraphic()!=null||getButton(p1-1,p2+1).getStyleClass().equals("Cl1"))&&(getButton(p1+1,p2-1).getGraphic()!=null||getButton(p1+1,p2-1).getStyleClass().equals("Cl1")))
				{
			return false;
				}
		
		return true;
	}
		 
		 
		 
		 
		 
		 
	 
	@FXML
	public void processcellsm(MouseEvent ae)
	{
		changeBackgroundOnHoverUsingBinding((Button)ae.getSource());
	}
}
