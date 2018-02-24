package csp;

import java.util.ArrayList;
import java.util.Deque;

public class Node {
	private State state;
	private Node parent;
	private ArrayList<Node> children;

	public Node(State _state) {
		state = _state;
		parent = null;
		children = new ArrayList<Node>();
	}

	public Node(State _state, Node _parent) {
		state = _state;
		parent = _parent;
		children = new ArrayList<Node>();
	}

	public State getState() {
		return this.state;
	}

	public Node getParent() {
		return parent;
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	/**
	 * スタックにノードを入れるためのメソッド
	 * 
	 * @param stack
	 */
	public void start(Deque<Node> stack) {
		if (this.state.isGoal()) {
			state.doIfGoal();
			System.exit(1);
		} else if (this.state.isFail()) {
		} else {
			this.expandNode(stack);
		}
	}

	/**
	 * スタックに入れるためのノードを生成
	 * 
	 * @param stack
	 */
	private void expandNode(Deque<Node> stack) {
		@SuppressWarnings("unchecked")
		ArrayList<State> nextStates = this.state.generateNextState();
		for (State nextState : nextStates) {
			Node child = new Node(nextState, this);
			this.children.add(child);
			stack.addFirst(child);
		}
	}
}
