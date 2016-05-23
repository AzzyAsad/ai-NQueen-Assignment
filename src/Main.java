import java.util.Random;


public class Main {
	public static void main(String[] args) {
		//Editing code for the first time
		PuzzleSolver p = new PuzzleSolver(8);
	}
}



class PuzzleSolver {
	int[][] initial_state;
	int[][] final_state;
	int num;
	int n;
	MyQueue myQueue;
	MyQueue bfsQueue;

	public PuzzleSolver(int no) {
		double f1 = Math.sqrt(no + 1);
		f1 = Math.floor(f1);
		double f2 = Math.sqrt(no + 1);
		if (f1 == f2) {
			no = (int) Math.sqrt(no + 1);
			n = no;
			int settingInitialState = 0;
			int settingFinalState = 1;
			initial_state = new int[no][no];
			final_state = new int[no][no];
			num = no * no;
			final int[] randomInts = new Random().ints(0, num).distinct().limit(num).toArray();
			for (int a = 0; a < no; a++) {
				for (int b = 0; b < no; b++) {
					initial_state[a][b] = randomInts[settingInitialState++];
				}
			}
			for (int a = 0; a < no; a++) {
				for (int b = 0; b < no; b++) {
					final_state[a][b] = settingFinalState++;
				}
			}
			final_state[no - 1][no - 1] = 0;
			myQueue = new MyQueue();
			bfsQueue = new MyQueue();
			myQueue.enQueue(initial_state);
			bfsQueue.enQueue(initial_state);
			shuffling();

		} else {
			System.out.println("Puzzle Cannot Be Created For This Number!");
		}
	}

	public int[] findPositionOfZero(int[][] ele) {
		for (int a = 0; a < n; a++) {
			for (int b = 0; b < n; b++) {
				if (ele[a][b] == 0) {
					int[] t = new int[2];
					t[0] = a;
					t[1] = b;
					return t;
				}
			}
		}
		return null;
	}

	public boolean goalChecker() {
		int counter = 0;
		int[][] check = myQueue.peek();
		for (int r = 0; r < check.length; r++) {
			for (int t = 0; t < check.length; t++) {
				if (check[r][t] != counter++) {
					return false;
				}
			}
		}
		return true;
	}

	public void shuffling() {
		while (!goalChecker()) {
			int[] t = new int[2];
			int[][] e = myQueue.peek();
			int[][] tempArrayUp = new int[n][n];
			int[][] tempArrayRight = new int[n][n];
			int[][] tempArrayDown = new int[n][n];
			int[][] tempArrayLeft = new int[n][n];
			for (int g = 0; g < n; g++) {
				for (int h = 0; h < n; h++) {
					tempArrayUp[g][h] = e[g][h];
					tempArrayRight[g][h] = e[g][h];
					tempArrayDown[g][h] = e[g][h];
					tempArrayLeft[g][h] = e[g][h];
				}
			}
			for (int a = 0; a < n; a++) {
				for (int b = 0; b < n; b++) {
					System.out.print(e[a][b]);
				}
				System.out.println();
			}
			System.out.println();
			t = findPositionOfZero(e);
			int a = t[0];
			int b = t[1];
			if (a - 1 >= 0) {
				int temp;
				temp = tempArrayUp[a - 1][b];
				tempArrayUp[a - 1][b] = tempArrayUp[a][b];
				tempArrayUp[a][b] = temp;
				for (int x = 0; x < n; x++) {
					for (int y = 0; y < n; y++) {
						System.out.print(tempArrayUp[x][y]);
					}
					System.out.println();
				}
				System.out.println();
				myQueue.enQueue(tempArrayUp);
				bfsQueue.enQueue(tempArrayUp);

			}
			if (b + 1 < n) {
				int temp;
				temp = tempArrayRight[a][b + 1];
				tempArrayRight[a][b + 1] = tempArrayRight[a][b];
				tempArrayRight[a][b] = temp;
				for (int x = 0; x < n; x++) {
					for (int y = 0; y < n; y++) {
						System.out.print(tempArrayRight[x][y]);
					}
					System.out.println();
				}
				System.out.println();
				myQueue.enQueue(tempArrayRight);
				bfsQueue.enQueue(tempArrayRight);

			}
			if (a + 1 < n) {
				int temp;
				temp = tempArrayDown[a + 1][b];
				tempArrayDown[a + 1][b] = tempArrayDown[a][b];
				tempArrayDown[a][b] = temp;
				for (int x = 0; x < n; x++) {
					for (int y = 0; y < n; y++) {
						System.out.print(tempArrayDown[x][y]);
					}
					System.out.println();
				}
				System.out.println();
				myQueue.enQueue(tempArrayDown);
				bfsQueue.enQueue(tempArrayDown);

			}
			if (b - 1 >= 0) {
				int temp;
				temp = tempArrayLeft[a][b - 1];
				tempArrayLeft[a][b - 1] = tempArrayLeft[a][b];
				tempArrayLeft[a][b] = temp;
				for (int x = 0; x < n; x++) {
					for (int y = 0; y < n; y++) {
						System.out.print(tempArrayLeft[x][y]);
					}
					System.out.println();
				}
				System.out.println();
				myQueue.enQueue(tempArrayLeft);
				bfsQueue.enQueue(tempArrayLeft);

			}
			myQueue.deQueue();
		}
	}

	public int manHattanHeuristic() {
		int q = 0;
		int w = 0;
		int sum1 = 0;
		int sum2 = 0;
		int total = 0;

		for (int i = 0; i < initial_state.length; i++) {
			for (int j = 0; j < initial_state.length; j++) {
				int var = initial_state[i][j];
				if (var != 0) {
					for (int a = 0; a < n; a++) {
						for (int b = 0; b < n; b++) {
							if (final_state[a][b] == var) {
								q = a;
								w = b;
							}
						}
					}
					sum1 = i - q;
					sum2 = j - w;
					if (sum1 < 0) {
						sum1 = sum1 * -1;
					}
					if (sum2 < 0) {
						sum2 = sum2 * -1;
					}
					total = total + sum1 + sum2;
				}
			}
		}
		return total;
	}
}



class MyQueue {
	private Node head;
	private Node tail;
	private int counter = 0;

	public void enQueue(int[][] e) {
		Node nNode = new Node();
		nNode.element = e;

		if (isEmpty()) {
			head = nNode;
			tail = nNode;
			counter++;
		} else {
			tail.next = nNode;
			tail = nNode;
			counter++;
		}
	}

	public int[][] deQueue() {
		if (!isEmpty()) {
			int[][] temp = head.element;
			head = head.next;
			counter--;
			return temp;
		} else {
			System.out.println("Queue Empty");
			return null;
		}
	}

	public int[][] peek() {
		if (!isEmpty())
			return head.element;
		else {
			System.out.println("Queue Empty");
			return null;
		}
	}

	public boolean isEmpty() {
		return head == null;
	}

}



class Node {
	int[][] element;
	Node next;

}
