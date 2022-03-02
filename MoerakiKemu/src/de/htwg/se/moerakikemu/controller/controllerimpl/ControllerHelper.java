package de.htwg.se.moerakikemu.controller.controllerimpl;

public class ControllerHelper {
	private int x,y, maxLength;
	private int[] squareArray;
	private boolean finished;
	private Square square;
	
	public ControllerHelper (final int xCoordinates, final int yCoordinates, final int maxLength){
		this.x = xCoordinates;
		this.y = yCoordinates;
		this.maxLength = maxLength;
		this.finished = false;
		this.squareArray = new int[17];
		this.square = new Square();
	}

	public void testSquare(){
		square.test();
	}
	
	public int[] getSquareArray(){
		int[] returnArray = new int[squareArray.length];
		System.arraycopy(squareArray, 0, returnArray, 0, squareArray.length);
		return returnArray;
	}
	
	public void resetSquareTest(){
		square.resetSquare();
	}
	
	private class Square {
		private SquareState squareState;
		
		Square(){
			squareState = new EdgeSquare();
		}
		
		void setSquare(SquareState s){
			squareState = s;
		}
		
		void resetSquare(){
			setSquare(new EdgeSquare());
		}
		
		void test(){
			while(!finished){
				squareState.test(this);
			}
		}
	}
	
	interface SquareState{
		void test(Square square);
	}
	
	private class EdgeSquare implements SquareState{
		@Override
		public void test(Square square){
			if(x == 0 && y == 0){
				squareArray[0] = 1;
				setArray(1, x, y, x + 1, y + 1);
				finished = true;
			} else if(x == 0 && y == maxLength){
				squareArray[0] = 1;
				setArray(1, x, y, x + 1, y - 1);
				finished = true;
			} else if(x == maxLength && y == 0){
				squareArray[0] = 1;
				setArray(1, x, y, x - 1, y + 1);
				finished = true;
			} else if(x == maxLength && y == maxLength){
				squareArray[0] = 1;
				setArray(1, x, y, x - 1, y - 1);
				finished = true;
			}else {
				square.setSquare(new BorderSquare());
			}
		}
	}
	
	private class BorderSquare implements SquareState{
		
		BorderSquare(){
			test(square);
		}

		@Override
		public void test(Square square){
			if(x == 0){
				squareArray[0] = 2;
				setArray(1, x, y, x + 1, y + 1);
				setArray(5, x, y, x + 1, y + 1);
				finished = true;
			} else if(y == 0){
				squareArray[0] = 2;
				setArray(1, x, y, x + 1, y + 1);
				setArray(5, x, y, x - 1, y + 1);
				finished = true;
			} else if(x == maxLength){
				squareArray[0] = 2;
				setArray(1, x, y, x - 1, y + 1);
				setArray(5, x, y, x - 1, y - 1);
				finished = true;
			} else if(y == maxLength){
				squareArray[0] = 2;
				setArray(1, x, y, x - 1, y - 1);
				setArray(5, x, y, x + 1, y - 1);
				finished = true;
			} else {
				square.setSquare(new InnerSquare());
			}
		}
	}
	
	private class InnerSquare implements SquareState{
		InnerSquare(){
			test(square);
		}

		@Override
		public void test(Square square){
			if(x < maxLength && y < maxLength) {
				squareArray[0] = 4;
				setArray(1, x, y, x + 1, y - 1);
				setArray(5, x, y, x - 1, y - 1);
				setArray(9, x, y, x + 1, y + 1);
				setArray(13, x, y, x - 1, y + 1);
				finished = true;
			}
		}
	}

	
	private void setArray(final int start, final int edgeOne, final int edgeTwo,
						  final int edgeThree, final int edgeFour){
		squareArray[start] = edgeOne;
		squareArray[start + 1] = edgeTwo;
		squareArray[start + 2] = edgeThree;
		squareArray[start + 3] = edgeFour;
	}
}
