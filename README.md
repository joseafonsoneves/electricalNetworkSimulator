Projet pour le cours d'Informatique de l'ISAE-Supaéro qui consiste à la simulation d'une réseau électrique

# Individual part
DE OLIVEIRA MORENO NEVES, José Afonso

## Profiles

Tout d’abord, j’ai choisi de mettre en œuvre les consommateurs et les producteurs comme les mêmes objets car, du moins à ce stade, ils ne sont caractérisés que par leurs profils de puissance quisont équivalents. Ensuite, pour pouvoir regrouper des profils implementés par différentes classes, j’ai créé une interface que j’ai appelé «Profile». Si, à l’avenir, des fonctionnalités sont ajoutées au simulateur qui nécessitent la distinction entre producteurs et consommateurs, il faudra modifier cette construction. Cependant, l’interface que j’ai créée peut être utilisée comme un attribut des nouveaux producteurs ou consommateurs.

Pour préciser les profils au long de l’année, j’ai utilisé 3 mécanismes. Tout d’abord, j’ai créé la classe «YearVariation» qui permet de définir un jour de début et un jour de fin d’opération. Deuxièmement, j’ai créé la classe «WeekVariation» qui permet de définir les jours de la semaine où le profil fonctionnera. Troisièmement, j’ai créé l’interface «Parameter» qui permet de créer des paramètres pour chaque profildont la valeur varie tout au long de l’année. Pour l’instant, j’ai créé deux types de paramètres, constantset sinusoïdaux.

Pour les variations dans le même jour, j’ai considéré deux types de profiles : les profiles qui durent toute la journée et les profiles qui durent seulement une partie de la journée et qui peuvent se répétir. Les premiers ont une implémentation simple et je présente un exemple dans la classe «DayConstantProfile». Les seconds peuvent en fait représenter les premiers si la durée appropriée est définie et pour eux, je présente trois types différents, constants, linéaires et quadratiques. Pour représenter le fait qu’ils commencent à un certain instant, ont une certaine durée et peuvent être périodiques, j’ai créé la classe «Square» et les ai fait hériter de la classe abstraite «SquaredProfile» qui a un attribut «Square» et les attributs des différentes variations annuelles.

D’autre part, j’ai également défini une classe qui implémente un groupe de profils pour pouvoir représenter différents appareils ou profils composés de différents profils que j’ai nommé «ProfilesGroup».

## Outils de visualisation des résultats

Jusqu’à présent, j’ai créé deux types d’outils de visualisation de données, une classe pour écrire les données dans un fichier .csv appelée «DataToCSV» et une classe pour afficher des graphiques de puissance, d’énergie et de puissance perdue appelée «DataToPlots». J’ai essayé de rendre simple la création de graphiques de nouvelles quantités en écrivant cette classe. J’ai également défini une interface appelée «DataOutputs» pour les outils de visualisation afin qu’il soit simple d’en ajouter d’autres.

## Simulateur

J’ai également créé un simulateur que j’ai appelé «City», qui reçoit des «ProfileGroups» de pro-ducteurs et de consommateurs et des «DataOutputs» et simule la production et la consommation. Il envoie également les résultats à travers la classe «Résultats», que j’ai définie pour les outils de visualisation, crée un dossier et un fichier dédié à la simulation et applique les outilsaux résultats.

# Group part

## Extension 1 : réseau de distribution électrique
TEXIER, Aurélien

## Extension 2 : paramétrage de la simulation par fichier
PIGAMO, Antoine

La première ligne correspond au nom de la ville.

Ensuite il s'agit soit de la description d'un producer ou d'un consumer

Soit un produceur (ou un consumer) on a : 
[producer][DayConstantProfile][Puissance][Nom][Y=Year Variation][Début][Durée]
[producer][DayConstantProfile][Puissance][Nom][W=Week Variation][Ensemble des jours];
[producer][DayConstantProfile][Puissance][Nom][N=No Variation];

[producer][DayConstantSquaredProfile][Instant où démarre le carré][Durée du carré][Nombre de répétitions du carré][Période entre chaque carré][Puissance][Nom][Y=Year Variation][Début][Durée]
[producer][DayConstantSquaredProfile][Instant où démarre le carré][Durée du carré][Nombre de répétitions du carré][Période entre chaque carré][Puissance][Nom][W=Week Variation][Ensemble des jours];
[producer][DayConstantSquaredProfile][Instant où démarre le carré][Durée du carré][Nombre de répétitions du carré][Période entre chaque carré][Puissance][Nom][N=No Variation];

[producer][DayLinearSquaredProfile][Instant où démarre le carré][Durée du carré][Nombre de répétitions du carré][Période entre chaque carré][Puissance gauche][Puissance droite][Nom][Y=Year Variation][Début][Durée]
[producer][DayLinearSquaredProfile][Instant où démarre le carré][Durée du carré][Nombre de répétitions du carré][Période entre chaque carré][Puissance gauche][Puissance droite][Nom][W=Week Variation][Ensemble des jours];
[producer][DayLinearSquaredProfile][Instant où démarre le carré][Durée du carré][Nombre de répétitions du carré][Période entre chaque carré][Puissance gauche][Puissance droite][Nom][N=No Variation];

[producer][DayLinearSquaredProfile][Instant où démarre le carré][Durée du carré][Nombre de répétitions du carré][Période entre chaque carré][Puissance gauche][Puissance centre][Puissance droite][Nom][Y=Year Variation][Début][Durée]
[producer][DayLinearSquaredProfile][Instant où démarre le carré][Durée du carré][Nombre de répétitions du carré][Période entre chaque carré][Puissance gauche][Puissance centre][Puissance droite][Nom][W=Week Variation][Ensemble des jours];
[producer][DayLinearSquaredProfile][Instant où démarre le carré][Durée du carré][Nombre de répétitions du carré][Période entre chaque carré][Puissance gauche][Puissance centre][Puissance droite][Nom][N=No Variation];


De plus on a un fichier qui détaille les positions des villes.
Chaque ville est suivi de sa position en x et en y : [Ville][x][y].

## Extension 3 : interface utilisateur graphique Swing
DE OLIVEIRA MORENO NEVES, José Afonso

Cette extension consiste à ajouter une interface utilisateur graphique qui permette de sélectionner des producteurs ou consommateurs dont on souhaite visualiser la courbe de production ou consommation électrique. Les courbes de production ou consommation seront affichées au moyen de la bibliothèque Ptplot. L'interphace graphique est basée sur un "Plot" à la gauche et une "Toolbar" vertical à la droite. Dans cette "Toolbar", chaque button permet d'accéder à un dialogue. Dans chacun de ces dialogues, l'utilisateur peut choisir un nouveau fichier de configuration, choisir un nouveau ensemble de profiles pour montrer leurs courbes, changer le type de simulation ou calculer les pertes associés à la transmission d'énergie.
Pour créer l'interface, il faut seulement ajouter
```
    UserInterface ui = new UserInterface();
    ui.show();
```
et importer la classe avec
```
    import userInterface.UserInterface;
```
Un exemple de l'interface est présenté dans la figure au-dessous.
![Exemple de l'interface](/images/interfaceExample.png)

## Extension 4 : construction de modèles
BERNARD, Rémi

## Intégration

Nous avons réussi à intégrer les extensions 2 et 3 et les extensions 3 et 4. 

Pour faire l'intégration entre l'extension 3 et 4 nous, n'avons pas fait rien parce que les modèles créés à l'extension 4 implémentent tous l'interface Profile.

Pour faire l'intégration entre l'extension 3 et 2, un paquetage appelé integration a été créé avec deux classes qui extendent les classes UserInterface et Controller pour que il soit possible d'avoir des buttons pour ouvrir un nouveau fichier et calculer les pertes.
