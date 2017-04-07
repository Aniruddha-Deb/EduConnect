package com.sensei.poc.polymorphism.frame;

public abstract class Frame<T extends Frame> {
	
	protected int weightInGrams = -1;
	
	public T withWeight( int weightInGrams ) {
		this.weightInGrams = weightInGrams;
		return (T)this;
	}

}
