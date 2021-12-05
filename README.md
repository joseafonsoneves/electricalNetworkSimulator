Projet pour le cours d'Informatique de l'ISAE-Supaéro qui consiste à la simulation d'une réseau électrique.Vou pouvez accéder au notre projet sur le lien https://github.com/joseafonsoneves/electricalNetworkSimulator/

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

Cette extension consiste à prendre en compte la notion de perte énergétique lié à la distance et de donner une dimension spatiale (et donc de réalité) dans notre projet. Chaque ville dispose maintenant d'une position x et y. Les villes sont répertorié dans une nouvelle classe Map qui contient un ensemble de ville et d'un tableau booléen (0 et 1) de connectivité entre les villes représentant les routes de la carte. Les nombreuses méthodes implémentées permettent de créer les chemins entre les villes et son producteur; de calculer ensuite la distance et enfin d'en déduire les pertes liées à la distance. Les pertes sont ensuite affichées dans un format csv.

## Extension 2 : paramétrage de la simulation par fichier
PIGAMO, Antoine

Cette extension consiste à créer une ou plusieurs villes grâce à un fichier texte. Il suffit d'importer la classe (import extension2.CSVRead) et d'utiliser la méthode read("chemin d'accès au fichier") ou readSeveralCities("chemin d'accès au fichier"). Elle permet aussi de lire des matrices de connexion entre les villes d'ajouter des positions venant d'un fichier texte à des villes. (respectivement readMatrix et addPositions).
Vous pouvez regarder le pdf Notice_Extension_2 qui explique l'utilisation des méthodes et les différents fichiers de simulations.

La description du format des fichiers textes est décrite plus bas.

La première ligne correspond au nom de la ville.

Ensuite il s'agit soit de la description d'un producer ou d'un consumer
Chaque élément doit être séparer par un point virgule : ";"

Pour les profils DayConstant :

[producer][DayConstantProfile][Puissance][Nom][Y=Year Variation][Début][Durée];

[producer][DayConstantProfile][Puissance][Nom][W=Week Variation][Ensemble des jours];

[producer][DayConstantProfile][Puissance][Nom][N=No Variation];

Pour les profils DayConstantSquared :

[producer][DayConstantSquaredProfile][Instant où démarre le carré][Durée du carré][Nombre de répétitions du carré][Période entre chaque carré][Puissance][Nom][Y=Year Variation][Début][Durée];

[producer][DayConstantSquaredProfile][Instant où démarre le carré][Durée du carré][Nombre de répétitions du carré][Période entre chaque carré][Puissance][Nom][W=Week Variation][Ensemble des jours];

[producer][DayConstantSquaredProfile][Instant où démarre le carré][Durée du carré][Nombre de répétitions du carré][Période entre chaque carré][Puissance][Nom][N=No Variation];

Pour les profils DayLinearSquared :

[producer][DayLinearSquaredProfile][Instant où démarre le carré][Durée du carré][Nombre de répétitions du carré][Période entre chaque carré][Puissance gauche][Puissance droite][Nom][Y=Year Variation][Début][Durée];

[producer][DayLinearSquaredProfile][Instant où démarre le carré][Durée du carré][Nombre de répétitions du carré][Période entre chaque carré][Puissance gauche][Puissance droite][Nom][W=Week Variation][Ensemble des jours];

[producer][DayLinearSquaredProfile][Instant où démarre le carré][Durée du carré][Nombre de répétitions du carré][Période entre chaque carré][Puissance gauche][Puissance droite][Nom][N=No Variation];

Pour les profils DayQuadraticSquared :

[producer][DayQuadraticSquaredProfile][Instant où démarre le carré][Durée du carré][Nombre de répétitions du carré][Période entre chaque carré][Puissance gauche][Puissance centre][Puissance droite][Nom][Y=Year Variation][Début][Durée];

[producer][DayQuadraticSquaredProfile][Instant où démarre le carré][Durée du carré][Nombre de répétitions du carré][Période entre chaque carré][Puissance gauche][Puissance centre][Puissance droite][Nom][W=Week Variation][Ensemble des jours];

[producer][DayQuadraticSquaredProfile][Instant où démarre le carré][Durée du carré][Nombre de répétitions du carré][Période entre chaque carré][Puissance gauche][Puissance centre][Puissance droite][Nom][N=No Variation];

Pour les nouveaux profils de l'extension 4 :

[producer][WhiteNoise][mu][sigma][Début (minute)][Fin (minute)][ID][Y=Year Variation][Début][Durée][W=Week Variation][Ensemble des jours];

[producer][Sinusoid][Amplitude][Fréquence][Phase][Début (minute)][Fin (minute)][ID][Y=Year Variation][Début][Durée][W=Week Variation][Ensemble des jours];

Lorsque qu'on a plusieurs villes dans un fichier texte, les villes sont séparés par une ligne "--"

De plus on a un fichier qui détaille les positions des villes et leur matrice de connexion.
Chaque ville est suivi de sa position en x et en y : [Ville][x][y].
Le fichier est ensuite séparé par la ligne : "--", pour indiquer le début de la matrice de connexion.
La matrice de connexion est composé uniquement de 0 et de 1. Ils sont séparés par ";".

## Extension 3 : interface utilisateur graphique Swing
DE OLIVEIRA MORENO NEVES, José Afonso

Cette extension consiste à ajouter une interface utilisateur graphique qui permette de sélectionner des producteurs ou consommateurs dont on souhaite visualiser la courbe de production ou consommation électrique. Les courbes de production ou consommation seront affichées au moyen de la bibliothèque Ptplot. L'interphace graphique est basée sur un "Plot" à la gauche et une "Toolbar" vertical à la droite. Dans cette "Toolbar", chaque button permet d'accéder à un dialogue. Dans chacun de ces dialogues, l'utilisateur peut choisir un nouveau ensemble de profiles pour montrer leurs courbes ou changer le type de simulation. J'ai vraiment essayé de suivre le paradigme MVC comme vous pouvez voir dans la division des fichiers et dans chaque fichier.
Pour créer l'interface, il faut seulement ajouter
```
    UIModel uiModel = new UIModel();
    UserInterface ui = new UserInterface();
    Controller controller = new Controller(ui, uiModel);
    ui.setController(controller);
    ui.show();
```
et importer la classe avec
```
    import extension3.Controller;
    import extension3.UIModel;
    import extension3.UserInterface;
```
Un exemple de l'interface est présenté dans la figure au-dessous.
![Exemple de l'interface avant intégration](/images/interfaceExample.png)

## Extension 4 : construction de modèles
BERNARD, Rémi

J'ai commencé par créer tous les profils différents demandés : sinus, linéaire, constant (une variante du linaire), bruit blanc gaussien, rectangulaire. Ils sont tous paramétrables via leurs constructeurs. Ils sont tous des sous-classes de la super-Class Model qui contient les bases pour les modeles , la plage de variations , les minutes de début et de fin , leurs noms et leurs fonction associé.

La super-classe Model implémente l'interface Profil afin de permettre l'intégration plus facilement via les méthodes getDayPower et getYearPower.

Pour permettre de combiner ces modèles la classe ModelComposer Permet à l'aide d'une liste d'opération et de modèles de les combiner. Pour les opérations "simples" (multiplication, adition , min ,max,....) il suffit grace à la librairie Bifunction "d'écrire" les ligne d'opérations dans la classe Operations qui est un paramètre de ModelComposer.On peut par exemple utiliser toutes les opérations de la librairie java.util.Math, la seul opération non disponible est la division de deux modèles.

Pour appliquer d'autre opérations plus compliquées, d'autres classes rentre eu jeux. La classe Accumulate permet un écrêtage d'un modèle avec stockage d’Energie (paramétrable). La classe Delayer qui permet de renvoyer un modèle avec un retard (decalage temporel). La classe CompositionWithLinear qui permet elle de composer un modèle Linéaire avec d'autres fonctions pour changer les parametres d'un modele lineaire en fonctions des jours de l'année par exemple.

Les deux executables SimComplex1 et SimComplex2 fournissent des exemples.

SimComplex1 contient une test des modeles de base et l'application de Delay et de Accumulate sur un modèle. SimComplex2 contient un test de minimum de 3 modèles et de somme de 3 modèles pour tester les opérations. Il contient également un modèle complexe utilisant la compostion et les opérations afin de fournir un des exemples demandé , un modèle de production solaire avec composition pour le modèle du soleil et un modèle d'ennuagement.

Le modèle du soleil est linéaire en fonction des périodes de l'année (se couche plus tot et se leve plus tard en hiver , il se decale toute l'année). Le modèle des nuages est représenté par un bruit blanc gaussien qui affecte la production pouvant la faire diminuer.

Dans chacun de ces executables tous les exemples sont commentés sauf un seul. il suffit d'un décommenter un seul a la fois pour les tester.

## Intégration

Nous avons réussi à intégrer les extensions 1, 2 et 3 et les extensions 3 et 4.

Pour faire l'intégration entre l'extension 3 et 4 nous, n'avons pas fait rien parce que les modèles créés à l'extension 4 implémentent tous l'interface Profile.

Pour faire l'intégration entre les extensions 1, 2 et 3, un paquetage appelé integration a été créé avec quatre classes qui extendent les classes UserInterface, DataChooserIntegration, Controller et UIModel pour que il soit possible d'avoir des buttons pour ouvrir un nouveau fichier, calculer les pertes et permettre à l'utilisateur de les montrer dans le Plot.
L'interface s'appele maintenant comme
```
    UserInterfaceIntegration ui = new UserInterfaceIntegration();
    ui.show();
```
et l'importe se fait avec
```
    import integration.UserInterfaceIntegration;
```
Trois exemples de l'interface sont présentés dans les figures au-dessous.
![Exemple de l'interface avant l'intégration](/images/interfaceIntegrationExample.png)
![Exemple de choix de profiles ou pertes](/images/interfaceIntegrationProfilesChoice.png)
![Exemple de choix de type de simulation](/images/interfaceIntegrationTypeChoice.png)