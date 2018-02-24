package sudoku;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import csp.CSP;
import csp.Node;

public class Sudoku implements CSP {

	private Deque<Node> stack;

	public Sudoku() {
		stack = new ArrayDeque<Node>();
	}

	/**
	 * ファイルから数独の初期状態を生成
	 * 
	 * @param fileName
	 * @param board
	 */
	private void loadFromFile(String fileName, Board board) {
		try {
			File file = new File(fileName);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String str = br.readLine();
			int column = 0;
			while (str != null) {
				String[] strAry = str.split(" ");
				for (int j = 0; j < strAry.length; j++) {
					int value = Integer.parseInt(strAry[j]);
					if (value != 0) {
						board.setValueOfCell(column, j, value);
					}
				}
				str = br.readLine();
				column++;
			}
			br.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * スタックに最初に入れるノードを生成
	 */
	private void setRootNodeToStack() {
		Board rootBoard = new Board();
		this.loadFromFile("sudoku5.txt", rootBoard);
		rootBoard.deleteIlligalValueFromDomains();
		Node rootNode = new Node(rootBoard);

		for (Board board : rootBoard.generateNextState()) {
			System.out.println();
			rootNode.getChildren().add(new Node(board, rootNode));
		}
		this.stack.addFirst(rootNode);
	}

	/**
	 * バックトラッキングサーチ
	 */
	public void backTrackingSearch() {
		this.setRootNodeToStack();
		while (!this.stack.isEmpty()) {
			this.stack.removeFirst().start(stack);
		}
	}

	/**
	 * 数独を解くメソッド
	 */
	public void solve() {
		this.backTrackingSearch();
	}

}