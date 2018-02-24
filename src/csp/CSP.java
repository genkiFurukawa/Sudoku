package csp;

import java.util.ArrayDeque;
import java.util.Deque;

public interface CSP {
	@SuppressWarnings("rawtypes")
	Deque stack = new ArrayDeque();

	void solve();

	void backTrackingSearch();
	
}