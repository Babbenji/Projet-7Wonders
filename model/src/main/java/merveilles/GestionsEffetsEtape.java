package merveilles;

import interfaces.type.IJoueur;

public class GestionsEffetsEtape {

    public void appliquerEffetMerveille(IJoueur joueur) {

        if (joueur.getMerveille().getCite().equals("Olympia"))
        {
            if (joueur.getMerveille().getEtape() == 1)
            {
                joueur.addPtsVictoire(3);
            }
            else if (joueur.getMerveille().getEtape() == 2)
            {
                //TODO première carte gratuite à chaque âge
            }
            else if (joueur.getMerveille().getEtape() == 3)
            {
                joueur.addPtsVictoire(7);
            }
        }
        if (joueur.getMerveille().getCite().equals("Gizah"))
        {
            if (joueur.getMerveille().getEtape() == 1)
            {
                joueur.addPtsVictoire(3);
            }
            else if (joueur.getMerveille().getEtape() == 2)
            {
                joueur.addPtsVictoire(5);
            }
            else if (joueur.getMerveille().getEtape() == 3)
            {
                joueur.addPtsVictoire(7);
            }
        }
        if (joueur.getMerveille().getCite().equals("Alexandria"))
        {
            if (joueur.getMerveille().getEtape() == 1)
            {
                joueur.addPtsVictoire(3);
            }
            else if (joueur.getMerveille().getEtape() == 2)
            {
                joueur.augmenterRessource("BoisOuBriquesOuPierresOuMinerais",1);
            }
            else if (joueur.getMerveille().getEtape() == 3)
            {
                joueur.addPtsVictoire(7);
            }
        }
        if (joueur.getMerveille().getCite().equals("Halikarnassos"))
        {
            if (joueur.getMerveille().getEtape() == 1)
            {
                joueur.addPtsVictoire(3);
            }
            else if (joueur.getMerveille().getEtape() == 2)
            {
                //TODO Batiment gratuit parmi les cartes defaussées
            }
            else if (joueur.getMerveille().getEtape() == 3)
            {
                joueur.addPtsVictoire(7);
            }
        }
        if (joueur.getMerveille().getCite().equals("Babylon"))
        {
            if (joueur.getMerveille().getEtape() == 1)
            {
                joueur.addPtsVictoire(3);
            }
            else if (joueur.getMerveille().getEtape() == 2)
            {
                joueur.addSymboleUniversel(1);
            }
            else if (joueur.getMerveille().getEtape() == 3)
            {
                joueur.addPtsVictoire(7);
            }
        }
        if (joueur.getMerveille().getCite().equals("Rhodos"))
        {
            if (joueur.getMerveille().getEtape() == 1)
            {
                joueur.addPtsVictoire(3);
            }
            else if (joueur.getMerveille().getEtape() == 2)
            {
                joueur.addPuissanceMilitaire(2);
            }
            else if (joueur.getMerveille().getEtape() == 3)
            {
                joueur.addPtsVictoire(7);
            }
        }
        if (joueur.getMerveille().getCite().equals("Ephesos"))
        {
            if (joueur.getMerveille().getEtape() == 1)
            {
                joueur.addPtsVictoire(3);
            }
            else if (joueur.getMerveille().getEtape() == 2)
            {
                joueur.addPieces(9);
            }
            else if (joueur.getMerveille().getEtape() == 3)
            {
                joueur.addPtsVictoire(7);
            }
        }

    }
}
