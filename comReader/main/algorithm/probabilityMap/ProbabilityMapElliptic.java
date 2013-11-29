/*
 * File: ProbabilityMapElliptic.java
 * Date				Author				Changes
 * 28 Nov 2013		Tommy Griese		create version 1.0
 */
package algorithm.probabilityMap;

import java.util.ArrayList;

import algorithm.helper.PointProbabilityMap;

/**
 * The class ProbabilityMapElliptic represents a probability map created with an combination of 
 * the path loss formula and an elliptical approach.
 * <br>
 * (this class is inherited by class {@link algorithm.probabilityMap.ProbabilityMap}).
 * 
 * @version 1.0 28 Nov 2013
 * @author Tommy Griese
 * @see algorithm.helper.PointProbabilityMap
 */
public class ProbabilityMapElliptic extends ProbabilityMap {
	
	/** The signal propagation constant, also named propagation exponent. */
	private double n;
	
	/** The received signal strength at a distance of one meter. */
	private double a;
	
	/** The length of the half-axis in direction x */
	private double lengthHalfAxisX = 3.0;
	
	/** The length of the half-axis in direction y */
	private double lengthHalfAxisY = 1.0;
	
	private static final double PRPAGATION_CONSTANT = 10.0;
	
	/**
	 * Instantiates a new ProbabilityMapElliptic.
	 *
	 * @param n the signal propagation constant
	 * @param a the received signal strength at a distance of one meter
	 * @param lengthHalfAxisX the length of the half-axis in direction x
	 * @param lengthHalfAxisY the length of the half-axis in direction y
	 */
	public ProbabilityMapElliptic(double n, double a, double lengthHalfAxisX, double lengthHalfAxisY) {
		this.n = n;
		this.a = a;
		
		this.lengthHalfAxisX = lengthHalfAxisX;
		this.lengthHalfAxisY = lengthHalfAxisY;
	}
	
	/**
	 * Creates a new probability map based on the given parameters.
	 *
	 * @param xFrom the start value for the probability map in x
	 * @param xTo the end value for the probability map in x
	 * @param yFrom the start value for the probability map in y
	 * @param yTo the end value for the probability map in y
	 * @param granularity the granularity for the probability map
	 * @return the new probability map
	 */
	@Override
	public ArrayList<PointProbabilityMap> getProbabilityMap(double xFrom, double xTo, 
			   												double yFrom, double yTo,
			   												double granularity) {
		
		ArrayList<PointProbabilityMap> pMap = new ArrayList<PointProbabilityMap>();
		
		for (double i = xFrom; i <= xTo; i += granularity) { // x-axis
			for (double j = yFrom; j <= yTo; j += granularity) { // y-axis
				double distance = 0;
				if (i == 0 && j == 0) {
					distance = granularity;
				} else {
					distance = Math.sqrt(i * i + j * j);
				}
				pMap.add(new PointProbabilityMap(i, j, distanceToRSSI(distance)));
			}
		}
		return pMap;
	}
	
	/**
	 * Sets the signal propagation constant.
	 *
	 * @param n the new signal propagation constant
	 */
	public void setN(int n) {
		this.n = n;
	}
	
	/**
	 * Sets the received signal strength at a distance of one meter.
	 *
	 * @param a the new received signal strength at a distance of one meter
	 */
	public void setA(int a) {
		this.a = a;
	}
	
	/**
	 * Gets the signal propagation constant.
	 *
	 * @return the signal propagation constant
	 */
	public double getN() {
		return this.n;
	}

	/**
	 * Gets the received signal strength at a distance of one meter.
	 *
	 * @return the received signal strength at a distance of one meter
	 */
	public double getA() {
		return this.a;
	}
	
	/**
	 * Calculates the rssi value based on the given distance with the help of the
	 * path loss formula.
	 *
	 * @param distance the distance
	 * @return the rssi value
	 */
	public double distanceToRSSI(double distance) {
		double rssi = -(ProbabilityMapElliptic.PRPAGATION_CONSTANT * this.n * Math.log10(distance) + this.a);
		return rssi;
	}
}
