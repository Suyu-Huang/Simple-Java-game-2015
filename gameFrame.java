package amazingGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

import game.MyTetris;

import java.awt.image.*;
import java.util.Map;
import java.awt.Toolkit;

public class gameFrame extends JFrame{
	JTextArea textArea;
	JScrollPane scrollPane;
	
	public gameFrame(){
		setSize(560,470);
		
		gamePanel panel=new gamePanel();
		addKeyListener(panel);
		panel.setBackground(Color.white);
		
		
		
		add(panel);
		
	}
}



class gamePanel extends JPanel implements KeyListener{
	
	Hero hero=new Hero(11,6);
	Enemy e1=new Enemy(5, 6);
	Enemy e2=new Enemy(5, 2);
	
	JTextArea textArea;
	JScrollPane scrollPane;
	
	public static int[][] map=new int[15][10];
	int i,j;
	private int score=0;
	private int X,Y,M1,N1,M2,N2;
	private int earth=0;   //携带量
	private int dir; //方向
	
	Image imgHero=new ImageIcon("/Users/huangsuyu/Downloads/Q版3D-图主角38人+魔物108个)-人物-爱给网/Doll_李逍遥.png").getImage();
	Image imgEnemy1=new ImageIcon("//Users/huangsuyu/Documents/workspace/mygame/RPG_MARKER_XP-原人物Q版动画-爱给网/side_m/m_12.png").getImage();
	Image imgEnemy2=new ImageIcon("/Users/huangsuyu/Documents/workspace/mygame/RPG_MARKER_XP-原人物Q版动画-爱给网/side_m/m_46.png").getImage();
	
	
	
	public gamePanel(){
		
		
		
		drawWall();
		repaint();
		
		//获取英雄苏渝的位置
		X=hero.getX();
		Y=hero.getY();
		
		//获取敌人的位置
		M1=e1.getM();
		N1=e1.getN();
		
		M2=e2.getM();
		N2=e2.getN();
		
		Timer t=new Timer(500, new TimeListener());
		t.start();
		
		Timer t2=new Timer(3000, new EnemyTime());
		t2.start();
			
		
		
	}
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_RIGHT&&e.isShiftDown()){
			hero.digBrick(1);
			
			return;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT&&e.isShiftDown()){
			hero.digBrick(-1);
			return;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT&&e.isAltDown()){
			hero.throwBrick(1);;
			return;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT&&e.isAltDown()){
			hero.throwBrick(-1);
			
			return;
		}
		
		switch (e.getKeyCode()) {
		
		case KeyEvent.VK_UP:
			
			hero.up();
			break;
		case KeyEvent.VK_DOWN:
			hero.down();
			break;
		case KeyEvent.VK_RIGHT:
			hero.right();
			break;
		case KeyEvent.VK_LEFT:
			hero.left();
			break;
		case KeyEvent.VK_A:
			hero.ladder();
		default:
			break;
		}
		
		
	}
	
	public void keyReleased(KeyEvent e){
		
	}
	
	public void keyTyped(KeyEvent e){
		
	}
	
	
	
	class TimeListener implements ActionListener {
		
		public TimeListener() {
			
		}
		public void actionPerformed(ActionEvent e) {
			
			
			X=hero.getX();
			Y=hero.getY();
			
			M1=e1.getM();
			N1=e1.getN();
			
			M2=e2.getM();
			N2=e2.getN();
			
			if(X==12&&Y==4){
				JOptionPane.showMessageDialog(null, "第一关通过！");
			}
			
			if((X==M1&&Y==N1)||(X==M2&&Y==N2)){
				JOptionPane.showMessageDialog(null, "英雄苏渝差点死亡");
				drawWall();
				hero.setX(11);
				hero.setY(6);
				e1.alive=true;
				e2.alive=true;
			}
			repaint();
		}
	}
	
	class EnemyTime implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e1.alive)
				e1.move();
			
			if (e2.alive) 
				e2.move();
		}
	}
	
	public void drawWall(){
		//画出砖块1
		for(i=1;i<13;i++){
			map[i][8]=1;
			map[i][7]=1;
			if(i==3||i==10){
				map[i][6]=4;
				map[i][6]=4;
			}
			else{
				map[i][6]=0;
			}
			if(i==9||i==8||i==2){
				map[i][5]=0;
			}
			else{
				map[i][5]=1;
			}
			map[i][4]=0;
			if(i<8&&i!=1){
				map[i][3]=1;
			}
			else{
				map[i][3]=0;
			}
			map[i][2]=0;
			if(i==5||i==7||i==8){
				map[i][1]=1;
			}
			else{
				map[i][1]=0;
			}
			map[i][0]=0;
		}
		
		//画出围墙2
		for(j=0;j<9;j++){
			map[0][j]=2;
			map[13][j]=2;
		}
		
		//画出通关符号5
		map[12][4]=5;
	}
	
	
	
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//遍历所有数据模型
		for(i=0;i<14;i++){
			for(j=0;j<9;j++){
				//数据模型为1画出砖块
				if(map[i][j]==1){
					Image imgBlock=new ImageIcon("/Users/huangsuyu/Documents/workspace/mygame/027ab0419cbb454b8d7d0568c770d421.png").getImage();
					g.drawImage(imgBlock, i*40, j*50, 40, 50, null);
				}
				//数据模型为2画出围墙
				if(map[i][j]==2){
					Image imgBorder=new ImageIcon("/Users/huangsuyu/Documents/workspace/mygame/下载.jpeg").getImage();
					g.drawImage(imgBorder, i*40, j*50, 40, 50, null);
				}
				//数据模型为3画出梯子
				if(map[i][j]==3){
					Image imgLadder=new ImageIcon("/Users/huangsuyu/Documents/workspace/mygame/images.png").getImage();
					g.drawImage(imgLadder, i*40, j*50, 40, 100, null);
				}
				//数据模型为4画出石块
				if(map[i][j]==4){
					Image imgStone=new ImageIcon("/Users/huangsuyu/Documents/workspace/mygame/石头.jpeg").getImage();
					g.drawImage(imgStone, i*40+4,j*50+20 , 32, 50, null);
				}
				//数据模型为5画出通关标示符
				if(map[i][j]==5){
					Image imgGame=new ImageIcon("/Users/huangsuyu/Documents/workspace/mygame/f2a74e3005694560b1efb4ee868ef9b1.png").getImage();
					g.drawImage(imgGame, i*40, j*50, 40, 50, null);
				}
			}
		}
		
		g.drawImage(imgHero, X*40, Y*50,40,50, null);
		if(e1.setAlive())
			g.drawImage(imgEnemy1, M1*40+4, N1*50+10,32,40, null);
		if(e2.setAlive())
			g.drawImage(imgEnemy2, M2*40+4, N2*50+10,32,40, null);
		
	}	
	
	
	
	
	
	class Hero {
		
		private int x,y;
		
		public Hero(int x,int y){
			this.x=x;
			this.y=y;
			
		}
		
		
		
		public boolean check(int x,int y){
			if(map[x][y]==1||map[x][y]==2||map[x][y]==4||map[x][y+1]==0){
				return false; 
			}
			else return true;
		}
		
		public void up(){
			if(map[x][y-1]==3){
				y=y-2;
				System.out.println("英雄苏渝爬楼梯了！");
			}
			
			repaint();
		}
		
		public void down(){
			if(map[x][y+1]==3){
				y=y+2;
				System.out.println("英雄苏渝又爬下来了！");
			}
		
			repaint();
		}
		
		public void right(){
			
			if(check(x+1, y)){
				x=x+1;
				System.out.println("英雄苏渝试探性的向右移动了一小步！");
			}
			
			repaint();
		}
		
		public void left(){
			
			
			if(check(x-1, y)){
				setX(x-1);;
				while(map[x][y+1]==0){
					y=y+1;
				}
			}
			System.out.println("英雄苏渝谨慎地向左迈出了一步");
			repaint();
		}
		
		
		
		
		public void digBrick(int dir){
			
			//携带量为零，且方向面对的草地存在，才能挖坑
			if(earth==0&&map[x+dir][y+1]==1&&map[x+dir][y]!=4){
				map[x+dir][y+1]=0;
				earth=1;
				System.out.println("英雄苏渝挖了一个土坑！");
				repaint();
			}
			else if(earth==0&&map[x+dir][y]==4){
				map[x+dir][y]=0;
				earth=4;
				System.out.println("英雄苏渝挖起一块巨石收入囊中！");
				repaint();
			}
			
			
		}
		
		public void throwBrick(int dir){
			int m=1;
			//携带量为1，且判断方向面对处的草地
			if(earth==1){
				if(map[x+dir][y+1]==1){
					map[x+dir][y]=1;
				}
				else if(map[x+dir][y+1]==0){
					while(map[x+dir][y+m]==0){
						m++;
					}
					map[x+dir][y+m-1]=1;
				}
				earth=0;
				System.out.println("英雄苏渝填满了一个土坑，他正在布下的局！");
				repaint();
			}
			
			if(earth==4){
				if(map[x+dir][y+1]==1){
					map[x+dir][y]=4;
				}
				else if(map[x+dir][y+1]==0){
					while(map[x+dir][y+m]==0){
						m++;
					}
					map[x+dir][y+m-1]=4;
				}
				earth=0;
				System.out.println("英雄苏渝凶狠地丢出一块巨石！");
				repaint();
			}
		}
		
		public void ladder(){     //创建梯子
			//判断梯子创建的合法性
			if(map[x-1][y]==0 && map[x-1][y-1]==0 && (map[x-2][y-1]==1 || map[x-2][y-1]==2) && (map[x][y-1]==1 || map[x][y-1]==2)){                  //左侧模型为空 && 上侧为空 && （上侧左右模型为空||围墙存在）
				map[x-1][y-1]=3;
			}
			if(map[x+1][y]==0 && map[x+1][y-1]==0 && (map[x+2][y-1]==1 || map[x+2][y-1]==2) && (map[x][y-1]==1 || map[x][y-1]==2)){                  //左侧模型为空 && 上侧为空 && （上侧左右模型为空||围墙存在）
				map[x+1][y-1]=3;
			}
			System.out.println("英雄苏渝搭建了一个梯子！");
			
		}
		
		public int getX(){
			
			return x;
		}
		
		public int getY(){
			return y;
		}
		
		public void setX(int x) {
			this.x=x;
		}
		
		public void setY(int y) {
			this.y=y;
		}
	}
	
	class Enemy{
		private int m,n;
		private int down=1;
		private int dir=-1;
		private boolean alive=true;
		
		public Enemy(int m,int n){
			this.m=m;
			this.n=n;
		}
		
		//判断移动条件
		public boolean moveable(int m,int n){
			if(map[m][n]==1||map[m][n]==2||map[m][n]==4||(map[m][n+1]==0&&(map[m+dir][n+1]==2||map[m+dir][n+1]==0))){
				return false; 
			}
			else return true;
		}
		
		public void move(){
			if(hero.getY()==n&&moveable(m+dir, n)){
				if(hero.getX()>m){
					dir=1;
				}
				else if(hero.getX()<m){
					dir=-1;
					
				}
				m=m+dir;	
				while(map[m][n+down]==0){
					down++;
				}
				n=down+n-1;
			}
			
			else if(hero.getY()==n&&!moveable(m+dir, n)){
				
			}
			
			else {
				if(moveable(m+dir, n)){
					m=m+dir;
					while(map[m][n+down]==0){
						down++;
					}
					n=down+n-1;
					down=1;
				}
				else{
					
					if(dir==1){
						dir=-1;
						if(moveable(m+dir, n)){
							m=m+dir;
							while(map[m][n+down]==0){
								down++;
							}
							n=down+n-1;
							down=1;
						}
					}
					else {
						dir=1;
						if(moveable(m+dir, n)){
							m=m+dir;
							while(map[m][n+down]==0){
								down++;
							}
							n=down+n-1;
							down=1;
						}
					}
				}
				
			}
		}
		
		public int getM(){
			return m;
		}
		
		public int getN(){
			return n;
		}
		
		public void setM(int m){
			this.m=m;
		}
		
		public void setN(int n){
			this.n=n;
		}
		
		public boolean setAlive(){
			if(map[m][n]==1||map[m][n]==4){
				alive=false;
				
			}
			return alive;
		}
		
	}
	
}




class gameTest{
	public static void main(String[] args){
		
				JFrame frame=new gameFrame();
				frame.requestFocus();
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				frame.setResizable(false);
		
	}
}