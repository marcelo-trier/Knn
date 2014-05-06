package br.knn.util;

public enum KNNTypes {
	KNN3(3), KNN5(5), KNN7(7), KNN9(9), KNN11(11);

	int valor = 0;
	KNNTypes( int vlr ) {
		valor = vlr;
	}

	public KNNTypes getType( String str ) {
		for( KNNTypes t : KNNTypes.values() ) {
			if( str == t.name() ) {
				return t;
			}
		}
		return null;
	}

}
