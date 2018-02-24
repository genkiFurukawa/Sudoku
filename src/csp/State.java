package csp;

import java.util.ArrayList;

public abstract class State {
	/**
	 * 終了状態か判定.終了状態であればtrueを返す.
	 * 
	 * @return boolean
	 */
	public abstract boolean isGoal();

	/**
	 * これ以上展開できないかどうか判断.これ以上展開できない状態であればtrueを返す.
	 * 
	 * @return
	 */
	public abstract boolean isFail();

	/**
	 * 終了状態になったときの動作
	 */
	public abstract void doIfGoal();

	/**
	 * 次の状態を生成
	 * 
	 * @return 次の状態の集合
	 */
	@SuppressWarnings("rawtypes")
	public abstract ArrayList generateNextState();

	/**
	 * 状態を表示
	 */
	public abstract void show();

}

