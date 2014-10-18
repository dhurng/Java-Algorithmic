/**
 * 
 * @author David Hurng CS 146 Hw#4
 */

public class Exchange {
	private double[][] d;
	private int size;
	private boolean done;

	/**
	 * Constructs the map and size of
	 * 
	 * @param constructor
	 *            will take one integer (>1)
	 */
	public Exchange(int x) {
		size = x;
		done = false;
		if (x > 1) {
			d = new double[x][x];
		}
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < x; j++) {
				d[i][j] = -1;
				if (i == j)
					d[i][j] = 1;
			}
		}
	}

	/**
	 * Sets an exchange between currencies of given rate
	 * 
	 * @param from
	 *            currency
	 * @param to
	 *            currency
	 * @param rate
	 *            which currency will exchange
	 */
	public void setRate(int from, int to, double rate) {
		d[from][to] = rate;
	}

	/**
	 * Checks if an arbitrage exists
	 * 
	 * @return true if there is arbitrage
	 */
	public boolean arbitrageExists() {
		int i = 0, j = 0, k = 0;
		if (done == false) {
			for (k = 0; k < size; k++) {
				for (i = 0; i < size; i++) {
					for (j = 0; j < size; j++) {
						if (d[i][j] < d[i][k] * d[k][j]) {
							d[i][j] = d[i][k] * d[k][j];
						}
					}
				}
			}
		}
		done = true;
		for (i = 0; i < size; i++) {
			for (j = 0; j < size; j++) {
				if (i == j && d[i][j] > 1.0)
					return true;
			}
		}
		return false;
	}

	/**
	 * finds the best exchange rate between two named currencies
	 * 
	 * @param from
	 *            currency
	 * @param to
	 *            currency
	 * @return the best exchange rate
	 */
	public double bestExchangeRate(int from, int to) {
		int i = 0, j = 0, k = 0;
		if (done == false) {
			for (k = 0; k < size; k++) {
				for (i = 0; i < size; i++) {
					for (j = 0; j < size; j++) {
						if (d[i][j] < d[i][k] * d[k][j]) {
							d[i][j] = d[i][k] * d[k][j];
						}
					}
				}
			}
		}
		done = true;
		return d[from][to];
	}
}
