package com.tech.and.solve.mudanza.service.domain.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Caso {

	private List<Integer> elementosPorTransportar;
	private int cantidadViajes = 0;
	private int cantidadCasos;

	public Caso(List<Integer> elementosPorTransportar, int cantidadCasos) {
		super();
		this.elementosPorTransportar = elementosPorTransportar;
		this.cantidadCasos = cantidadCasos;
		this.calcularViajes();
	}

	public List<Integer> getElementosPorTransportar() {
		return elementosPorTransportar;
	}

	public int getCantidadViajes() {
		return cantidadViajes;
	}
	
	public int getCantidadCasos() {
		return cantidadCasos;
	}
	
	public void calcularViajes() {
		// cuando existe un solo elemento por defecto es un solo viaje
		List<Integer> elementos = new ArrayList<>(this.elementosPorTransportar);
		if (elementos.size() == 1) {
			this.cantidadViajes = 1;
		} else {
			elementos.sort(Comparator.naturalOrder());
			int indiceFinal = 0;
			while (true) {
				// permite finalizar el ciclo
				if (elementos.isEmpty() || elementos.size() == 1) {
					break;
				}

				indiceFinal = elementos.size() - 1;
				int elementoFinal = elementos.get(indiceFinal);
				elementos.remove(indiceFinal);
				// cuando el elemento pesa igual o mas de 50 kilos se cuenta un viaje con ese
				// unico elemento
				if (elementoFinal >= 50) {
					this.cantidadViajes++;
				} else {
					int multiplicadorPeso = 1;
					// se verifica cuantos elementos van por debajo del mas pesado
					for (Iterator<Integer> iterator = elementos.iterator(); iterator.hasNext();) {
						iterator.next();
						iterator.remove();
						multiplicadorPeso++;
						if (elementoFinal * multiplicadorPeso >= 50) {
							this.cantidadViajes++;
							break;
						}
					}
				}
			}
		}
	}

}
