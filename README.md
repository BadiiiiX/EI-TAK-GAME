# Projet Tak - ESIEA

**IMPORTANT** : Ce projet n'est aucunement un projet personnel et a pour but éducatif ; Projet pour l'ESIEA.

## Description 
Tak est une implémentation Java du jeu de société Tak, suivant les règles officielles.


## Contexte général

L'objectif principal de ce projet est de fournir un moteur de jeu Tak avec une architecture claire, modulaire et entièrement testable. Les principes de conception incluent :

- **Modularité** : Chaque responsabilité (plateau, pièces, joueurs, état, règles, orchestration) vit dans son propre package.
- **Interfaces + Implémentations** : On définit toujours d'abord une interface (ou classe abstraite) puis une implémentation concrète unique.
- **Deep copy & immuabilité partielle** : Pour gérer l'undo et éviter les fuites de référence, on clone explicitement le plateau et l'état de la partie.
- **Factories au démarrage** : Un "GameFactory" assemble plateau, joueurs et moteur de règles pour produire un "GameImpl" prêt à jouer.
- **Tests unitaires JUnit 5** : Chaque composant de base est couvert par un test dédié.

## Structure du projet

```
    src
    ├─ main
    │   └─ java
    │       └─ fr
    │           └─ esiea
    │               └─ mali
    │                   └─ core
    │                       ├─ model
    │                       └─ service
    │                           └─ factory
     └─ test
         └─ java
             └─ fr
                 └─ esiea
                     └─ mali
                         └─ (tests core)
    ├─ docs
    │    └─ (Javadoc générée)
    ├─ uml
    │    └─ yuml.txt
    └─ README.md
```

## Informations
- Le projet n'est pas fini et est toujours en cours de développement.
- Le diagramme de classe **à jour** est disponible via le fichier `uml/yuml.txt`; généré via [yUML](https://yuml.me/diagram/scruffy/class/draw)

## Licence

[TODO -> setup license]
