package sudoku;

import java.util.ArrayList;

import csp.State;

public class Board extends State {
	static final int NUMBER_ROWS = 9;
	static final int NUMBER_COLUMNS = 9;
	static final int NUMBER_VALUE_OF_DOMAIN = 9;
	static final int NUMBER_CELLS = NUMBER_ROWS * NUMBER_COLUMNS;

	private Cell[][] board;

	public Board() {
		board = new Cell[NUMBER_ROWS][NUMBER_COLUMNS];
		for (int i = 0; i < NUMBER_ROWS; i++) {
			for (int j = 0; j < NUMBER_COLUMNS; j++) {
				board[i][j] = new Cell();
			}
		}
	}

	/**
	 * 指定した場所のCellに値を格納
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public void setValueOfCell(int row, int column, int value) {
		board[row][column].setValue(value);
	}

	public Cell getCell(int row, int column) {
		return board[row][column];
	}

	/**
	 * 制約を満たさないものを領域の集合から削除
	 */
	public void deleteIlligalValueFromDomains() {
		for (int i = 0; i < NUMBER_ROWS; i++) {
			for (int j = 0; j < NUMBER_COLUMNS; j++) {
				if (board[i][j].getValue() == 0) {
					for (int k = 0; k < NUMBER_VALUE_OF_DOMAIN; k++) {
						int value = k + 1;
						if (this.checkConstraint(i, j, value)) {
							this.board[i][j].deleteValueFromDomain(value);
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * @return 値が確定している変数の数
	 */
	public int countAssignedVariables() {
		int assignedVariables = 0;
		for (int i = 0; i < NUMBER_ROWS; i++) {
			for (int j = 0; j < NUMBER_COLUMNS; j++) {
				if (board[i][j].getValue() != 0) {
					assignedVariables++;
				}
			}
		}
		return assignedVariables;
	}

	/**
	 * 指定したCellの値が制約に違反していればtrueを返す
	 * 
	 * @param row
	 *            行の番号
	 * @param column
	 *            列の番号
	 * @param value
	 *            変数の値
	 * @return 制約に違反しているかどうか
	 */
	public boolean checkConstraint(int row, int column, int value) {
		return (this.checkConstraintOfRow(row, value)
				|| this.checkConstraintOfColumn(column, value) || this
					.checkConstraintOfBlock(row, column, value));
	}

	/**
	 * 行に関して制約チェック
	 * 
	 * @param row
	 * @param value
	 * @return
	 */
	private boolean checkConstraintOfRow(int row, int value) {
		for (int j = 0; j < NUMBER_COLUMNS; j++) {
			if (board[row][j].getValue() == value) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 列に関して制約チェック
	 * 
	 * @param column
	 * @param value
	 * @return
	 */
	private boolean checkConstraintOfColumn(int column, int value) {
		for (int i = 0; i < NUMBER_ROWS; i++) {
			if (board[i][column].getValue() == value) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ブロックに関して制約チェック
	 * 
	 * @param row
	 * @param column
	 * @param value
	 * @return
	 */
	private boolean checkConstraintOfBlock(int row, int column, int value) {
		//
		int blockRow = row - row % 3;
		int blockColumn = column - column % 3;
		for (int i = blockRow; i < blockRow + 3; i++) {
			for (int j = blockColumn; j < blockColumn + 3; j++) {
				if (board[i][j].getValue() == value) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * MRVヒューリスティック 領域の大きさが一番小さいCellの場所を返す
	 * 
	 * @return Cellの場所
	 */
	public int[] selectMinimumRemainingValue() {
		int[] numberOfCell = new int[2];
		int rowNumber = 0;
		int columnNumber = 0;
		int numberOfDomain = 9;
		for (int i = 0; i < NUMBER_ROWS; i++) {
			for (int j = 0; j < NUMBER_COLUMNS; j++) {
				if (board[i][j].getValue() == 0) {
					if (board[i][j].getDomain().size() < numberOfDomain) {
						rowNumber = i;
						columnNumber = j;
						numberOfDomain = board[i][j].getDomain().size();
					}
				}
			}
		}
		numberOfCell[0] = rowNumber;
		numberOfCell[1] = columnNumber;
		return numberOfCell;
	}

	/**
	 * ボードのコピーを作成
	 * 
	 * @param originalBoard
	 */
	public void copyBoard(Board originalBoard) {
		for (int i = 0; i < NUMBER_ROWS; i++) {
			for (int j = 0; j < NUMBER_COLUMNS; j++) {
				int value = originalBoard.getCell(i, j).getValue();
				ArrayList<Integer> domain = originalBoard.getCell(i, j)
						.copyDomain();
				board[i][j] = new Cell(value, domain);
			}
		}
	}

	/**
	 * ボードの状況を出力
	 */
	public void show() {
		for (int i = 0; i < NUMBER_ROWS; i++) {
			for (int j = 0; j < NUMBER_COLUMNS; j++) {
				System.out.print(board[i][j].getValue() + " ");
			}
			System.out.println();
		}
	}

	/**
	 * 領域の数を出力(デバグ用)
	 */
	public void showDomain() {
		for (int i = 0; i < NUMBER_ROWS; i++) {
			for (int j = 0; j < NUMBER_COLUMNS; j++) {
				if (board[i][j].getValue() == 0) {
					System.out.print(board[i][j].getDomain().size() + " ");
				} else {
					System.out.print("N" + " ");
				}
			}
			System.out.println();
		}
	}

	@Override
	public boolean isGoal() {
		// TODO 自動生成されたメソッド・スタブ
		return (this.countAssignedVariables() == NUMBER_CELLS);
	}

	@Override
	public boolean isFail() {
		// TODO 自動生成されたメソッド・スタブ
		for (int i = 0; i < NUMBER_ROWS; i++) {
			for (int j = 0; j < NUMBER_COLUMNS; j++) {
				if (this.board[i][j].getValue() == 0) {
					if (this.board[i][j].getDomain().size() == 0) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void doIfGoal() {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("-result-");
		this.show();
	}

	@Override
	public ArrayList<Board> generateNextState() {
		ArrayList<Board> nextStates = new ArrayList<Board>();

		int[] cell = new int[2];
		cell = this.selectMinimumRemainingValue();
		ArrayList<Integer> domain = this.getCell(cell[0], cell[1]).getDomain();

		for (int value : domain) {
			Board nextBoard = new Board();
			nextBoard.copyBoard(this);
			nextBoard.setValueOfCell(cell[0], cell[1], value);
			nextBoard.deleteIlligalValueFromDomains();
			nextStates.add(nextBoard);
		}
		return nextStates;
	}

}
