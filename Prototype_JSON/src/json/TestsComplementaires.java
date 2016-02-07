package json;

public class TestsComplementaires {
	/**
	 * Displays the results of a bunch of tests where the input is not correct.
	 */
	public static void main(String[] args) {
		System.out.println("TESTS DE FROMJSON()");
		
		
		
		System.out.println("\n\n------------\n\n");
		
		
		
		System.out.println("Avec un fichier introuvable :");
		System.out.println(Test.testFromJson("test/fichierIntrouvable.json"));
		System.out.println("Observation : Le test s'arr�te avant d'atteindre le gson.");
		
		
		
		System.out.println("\n\n------------\n\n");
		
		
		
		System.out.println("Avec un fichier vide :");
		System.out.println(Test.testFromJson("test/fichierVide.json"));
		System.out.println("Observation : gson.fromJson() renvoie null.");
		
		
		
		System.out.println("\n\n------------\n\n");
		
		
		
		System.out.println("Avec un fichier qui ne contient pas tous les champs");
		System.out.println(Test.testFromJson("test/fichierIncomplet.json"));
		System.out.println("Observation : les champs non renseign�s sont initialis�s � leur valeur\n"
				+ "par d�faut (0 pour les nombres, false pour les boolean, null pour les objets, ...)");
		System.out.println("Attention aux objets initialis�s � null. Par exemple, il y aurait\n"
				+ "NullPointerException si j'essayais de cr�er un objet Livraison � cette �tape.");
		
		
		
		System.out.println("\n\n------------\n\n");
		
		
		
		System.out.println("Avec un fichier contenant des champs qui n'apparaissent pas dans la classe � parser");
		System.out.println(Test.testFromJson("test/fichierTropComplet.json"));
		System.out.println("Observation : les champs renseign�s non-pr�sents dans la classe sont ignor�s.");
		
		
		
		System.out.println("\n\n------------\n\n");
		
		
		
		System.out.println("Selon les deux tests pr�c�dents, on conclut que si l'on essaie de parser\n"
				+ "un objet d'apr�s une json string qui correspond � une classe diff�rente, nous\n"
				+ "avons un r�sultat, mais il est inexacte : seuls les champs avec le m�me nom\n"
				+ "sont initialis�s. Il faut faire attention � �a !");
		
		
		
		System.out.println("\n\n------------\n\n");
		
		
		
		System.out.println("Avec un fichier qui a les bons champs, mais les mauvais types de donn�es dans les champs");
		try {
			System.out.println(Test.testFromJson("test/fichierAvecMauvaisTypes.json"));
		} catch(com.google.gson.JsonSyntaxException e) {
			//e.printStackTrace();
			System.out.println("Observation : le test lance une exception.");
		}
		
		
		
		System.out.println("\n\n------------\n\n");
		
		
		
		System.out.println("Avec un fichier mal format� :");
		try {
			System.out.println(Test.testFromJson("test/fichierMalFormate.json"));
		} catch(com.google.gson.JsonSyntaxException e) {
			//e.printStackTrace();
			System.out.println("Observation : le test lance une exception.");
		}
	}
}
