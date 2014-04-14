package br.knn;

import java.awt.Color;


public enum Classe {
	MONTANHA(1),
	CEU(2),
	MAR(3),
	AREIA(4);

	public final static Color Cores[] = {
		Color.ORANGE,
		Color.BLUE,
		Color.GREEN,
		Color.RED
	};
	
	public int valor;
	Classe( int vlr ) {
		valor = vlr;
	}
	
	public final static int PONTOSX[][] = { // ordem: montanha, ceu, mar, areia
		{21, 17, 28, 59, 29, 37, 148, 113, 60, 93, 117, 147, 250, 213, 197, 229, 271, 390, 423, 333, 287, 259, 210, 199, 161, 111, 78, 61, 75, 74 },
		{145, 167, 169, 185, 179, 197, 218, 236, 219, 528, 536, 518, 516, 529, 528, 531, 530, 530, 531, 531, 531, 531, 533, 530, 515, 478, 453, 409, 336, 274 },
		{530, 515, 496, 480, 488, 463, 509, 530, 397, 330, 365, 406, 484, 521, 424, 467, 349, 264, 228, 192, 215, 236, 252, 240, 264, 280, 309, 274, 330, 361 },
		{47, 40, 21, 106, 130, 136, 148, 173, 332, 365, 316, 234, 154, 155, 238, 185, 68, 110, 110, 109, 66, 34, 111, 138, 182, 184, 223, 130, 273, 288 }
	};
	
	public final static int PONTOSY[][] = { // ordem: montanha, ceu, mar, areia
		{13, 69, 121, 129, 149, 206, 204, 139, 96, 38, 41, 70, 164, 43, 85, 96, 143, 163, 163, 175, 175, 199, 166, 121, 148, 89, 122, 34, 214, 194 }, 
		{3, 4, 13, 7, 25, 28, 22, 28, 8, 7, 23, 30, 13, 36, 49, 65, 79, 87, 101, 120, 131, 140, 154, 170, 178, 174, 150, 140, 132, 95 },
		{188, 187, 186, 185, 197, 197, 197, 204, 195, 196, 204, 215, 216, 238, 230, 241, 237, 228, 236, 245, 268, 288, 271, 248, 246, 274, 315, 299, 343, 362 },
		{252, 269, 251, 265, 281, 267, 283, 298, 374, 389, 381, 344, 319, 370, 378, 393, 311, 371, 318, 294, 282, 283, 274, 341, 349, 369, 386, 394, 357, 367 }
	};
}