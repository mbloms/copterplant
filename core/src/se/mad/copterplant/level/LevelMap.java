package se.mad.copterplant.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.Stack;

import se.mad.copterplant.actor.Path.PathObject;
import se.mad.copterplant.level.BinaryCollection.BinaryArrayList;
import se.mad.copterplant.level.BinaryCollection.BinaryArrayMatrix;
import se.mad.copterplant.util.FileUtil;

import com.badlogic.gdx.math.Vector2;

/**
 * Sry för svenska David.
 * Kodskelett för spelplanen.
 * 
 * Be careful to note the difference in standards when referring to an element
 * in a matrix and referring to a position in a coordinate system. 
 * @author Mikael Blomstrand
 *
 */
public class LevelMap extends BinaryArrayMatrix{
	int width;
	int height;
	Level currentLevel;
		
	public LevelMap(int width, int height, Level currLevel){
		super(height, width);
		
		this.width = width;
		this.height = height;
		this.currentLevel = currLevel;
		
		parseString(currentLevel.filepath);
	}

	//TODO: Fix comment
	public boolean isFilled(int x, int y){
		try{
			return getBoolean(y, x);
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Fills the block with the given position.
	 * Returns true if the block was successfully filled. False if it failed.
	 * @param x
	 * @param y
	 * @return true if block was filled.
	 */
	public boolean fillBlock(int x, int y){
		try{
			setTrue(y, x);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return isFilled(x,y);
	}
	
	public static BinaryArrayList[] allTrueMatrix(int x, int y){
		BinaryArrayList[] matrix = new BinaryArrayList[y];
		for (int i = 0; i < matrix.length; i++) {
			matrix[i] = new BinaryArrayList(x);
			matrix[i].setAllTrue();
		}
		return matrix;
	}
	
	private class intVector{
		public int x;
		public int y;
		
		public intVector(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
	
	/**
	 * Fills the unfilled area touched by the ball.
	 * @param x The balls x coordinate.
	 * @param y The balls y coordinate.
	 */
	public void areaFill(int[] x, int[] y){
		BinaryArrayList[] matrix = allTrueMatrix(width, height);
		
		Stack<intVector> stack = new Stack<intVector>();
		
		/*
		 * Fyll rad med utgångspunkt i P.
		 * Lägg första tomma blocket över och under raden i stacken.
		 * För alla fyllda block som följs av ett tomt block: lägg det tomma blocket i stacken. (Ovan och under strecket)
		 */
		for(int i = 0;i < x.length;i++){
			stack.push(new intVector(x[i], y[i]));
		}
		int first;
		int last;
		int row;
		intVector current;
		
		while(!stack.isEmpty()){
			current = stack.pop();
			row = current.y;
			
			first = rows[row].lastTrueBitBefore(current.x)+1;
			last = rows[row].firstTrueBitAfter(current.x)-1;
			
			matrix[row].setFalse(first, last); // sätter allt där bollen är
			
			int j;
			int upper=row+1;
			int downer=row-1;
			
			if(upper<height){
				j = rows[upper].firstFalseBitAfter(first-1);
				
				while(j <= last && j!=-1){
					if(matrix[upper].getBoolean(j)){
						stack.push(new intVector(j, upper));
					}
					j = rows[upper].firstTrueBitAfter(j);
					j = rows[upper].firstFalseBitAfter(j);
				}
			}
			
			if(0<=downer){
				j = rows[downer].firstFalseBitAfter(first-1);
				while(j <= last && j!=-1){
					if(matrix[downer].getBoolean(j)){
						stack.push(new intVector(j, downer));
					}
					j = rows[downer].firstTrueBitAfter(j);
					if(j==-1){
						break;
					}
					j = rows[downer].firstFalseBitAfter(j);
				}
			}
			
		}
		rows = matrix;
		currentLevel.getVisualMap().updateBoundingBoxes();
	}

	/**
	 * Fills the path between corners in the input arrays.
	 * @param x An array with the x coordinates of the points where a turn was made.
	 * @param y An array with the y coordinates of the points where a turn was made.
	 */
	/*
	public void fillTrack(int[] x, int[] y){
*/

	public void fillTrack(LinkedList<PathObject> path){
		for (PathObject p:path) {
			Vector2 pos = p.getPos();
			
			Vector2 gridPosNew = VisualMap.ScreenToLevelVector2(pos.cpy());
			
			if (currentLevel.getVisualMap().getLevelBounds().contains(pos)) {
				fillBlock((int)gridPosNew.x, (int)gridPosNew.y);
			}
		}
		currentLevel.getVisualMap().updateBoundingBoxes();
	}

	public void parseString(String filepath){
		
	    StringReader reader = new StringReader(FileUtil.readFile(filepath));
	    BufferedReader br = new BufferedReader(reader);
	    String line;
	    int col = 0;
	    int row = height-1;
	    try {
			while((line=br.readLine())!=null)
			{
				for (String mark:line.split("")){
					//System.out.println("Mark:" + mark);
					if (mark.equals("#")) {
						fillBlock(col, row);
					}
					col++;
				}
				col = 0;
				row--;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Calculates how much of the map is filled.
	 * @return A double with the quote "filled area" divided by "total area".
	 */
	public double percentageFilled(){
		float filled = 0;
		for(int x = 0;x < width;x++){
			for(int y = 0;y < height;y++){
				if(isFilled(x, y)){
					filled++;
				}
			}
		}
		return filled/(width*height)*100;
	}
}
