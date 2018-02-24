package sudoku;

import java.util.ArrayList;

public class Cell {
	private int value;
	private ArrayList<Integer> domain;

	public Cell() {
		this.value = 0;
		this.domain = new ArrayList<Integer>();
		this.initializeDomain();
	}

	public Cell(int _value) {
		this.value = _value;
		this.domain = new ArrayList<Integer>();
	}

	public Cell(int _value, ArrayList<Integer> _domain) {
		this.value = _value;
		this.domain = _domain;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int _value) {
		this.value = _value;
	}

	public ArrayList<Integer> getDomain() {
		return this.domain;
	}

	/**
	 * 領域の初期化
	 */
	private void initializeDomain() {
		for (int i = 0; i < 9; i++) {
			this.domain.add(i + 1);
		}
	}

	/**
	 * 指定した値を領域から削除
	 * 
	 * @param _value
	 */
	public void deleteValueFromDomain(int _value) {
		ArrayList<Integer> removeList = new ArrayList<Integer>();
		for (int value : domain) {
			if (value == _value) {
				removeList.add(_value);
			}
		}
		this.domain.removeAll(removeList);
	}

	/**
	 * 領域の値をコピー
	 * 
	 * @return　
	 */
	public ArrayList<Integer> copyDomain() {
		ArrayList<Integer> newDomain = new ArrayList<Integer>();
		for (int domainValue : domain) {
			newDomain.add(domainValue);
		}
		return newDomain;
	}
}
