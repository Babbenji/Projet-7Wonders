# 7 Wonders - Groupe 5

🚨 Pour un meilleur rendu du README (y compris la gestion des images), ouvrez le sur IntelliJ une fois le projet cloné, le markdown y est mieux géré) 🚨

Bonjour, nous sommes le groupe 5, composé de :
+ Matthieu BLOND
+ Timothé DURAND
+ Julien LIETARD
+ Aziz BEN JAZIA

Voici notre travail réalisé dans le cadre de L'UE "Projet informatique" en cette année scolaire 2021-2022 de M1 MIAGE à l'Université d'Orléans.

Le sujet, vous le connaissez : c'est le jeu de société 7 Wonders en version digitale.
Il est réalisé en Java (+ Java FX pour la partie graphique) avec Maven, RMI (Remote Method Invocation) et MongoDB pour la base de données.

Comme convenu, c'est une version jouable à 4 joueurs, ni plus ni moins.

Voici les instructions et autres informations utiles dont vous aurez besoin pour exécuter notre projet et éventuellement y toucher :

## Première étape : Cloner notre repo GIT

La première étape consiste à cloner note repo git. Pour ce faire :

1. Copier le lien de clonage provenant du menu "cloner" de la barre de navigation gauche de notre repo sur Bitbucket
2. Dans IntelliJ IDEA, suivre le chemin ***File>New>Project from version control*** et coller l'URL précédemment copiée dans le champ correspondant
3. Spécifier l'emplacement souhaité puis cliquer sur ***CLONE***

## Deuxième étape : Lancement du serveur RMI

Il vous faudra en premier lieu exécuter le fichier RunServer.java à l'emplacement suivant :

***rmiService/src/main/java/app/RunServer.java***

Le terminal devrait afficher de nombreux Logs INFO en rouge.

## Troisième étape : Lancement des clients JFX
Si vous ne l'avez pas déjà fait et si l'option n'est pas paramétrée par défaut, il vous faudra autoriser les instances multiples de Main.java situé à l'emplacement suivant :

***clientJfx/src/main/java/application/Main.java***

<img src="/images/Capture1.PNG"/>
<img src="/images/Capture2.PNG"/>
<img src="/images/Capture3.PNG"/>

À partir de là, il vous faudra exécuter 4 instances de ce même fichier et de placer, dans l'idéal, les 4 fenêtres dans les 4 coins de votre écran.

Pour l'inscription et la connexion, il n'y a pas de secret.

Si toutefois vous avez besoin d'identifiants, en voici 4 :

|Nom d'utilisateur | Mot de passe |
|:----------------:|:------------:|
|      mblond      |   password   |
|     tdurand      |   password   |
|     jlietard     |   password   |
|    abenjazia     |   password   |

## Informations supplémentaires

La base de données est sur serveur distant et est accessible [ici](https://account.mongodb.com/account/login) avec les identifiants suivants

|                Mail                |   Mot de passe   |
|:----------------------------------:|:----------------:|
| accesbddmongo.professeur@gmail.com | jeSuisUnProf123! |

<img src="/images/Capture4.PNG"/>
