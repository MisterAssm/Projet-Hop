# Hop! - Projet de Jeu en Java

**Hop!** est un projet universitaire développé en Java. Il s'agit d'un jeu où le joueur incarne un personnage (Axel) qui
saute de bloc en bloc.

---

## Table des matières

- [Introduction](#introduction)
- [Fonctionnalités supplémentaires](#fonctionnalités-supplémentaires)
- [Utilisation](#utilisation)
- [Structure du projet](#structure-du-projet)
- [Contributeurs](#contributeurs)
- [Licence](#licence)

---

## Introduction

Le jeu initialement proposé représentait un personnage qui s'échappe d'un volcan. Le jeu a été modifié pour le rendre
dans le style de Doodle Jump.

L'objectif principal reste le même : accumuler le maximum de points en sautant le plus haut possible.

---

## Fonctionnalités supplémentaires

- **Fluidité** : Chaque action est géré à la même fréquence que le jeu initial mais le rendu des images se fait toutes
  les ms.
- **Éléments dynamiques** : Blocs bleu qui se déplacent de gauche à droite.
- **Interface utilisateur** :
    - Écran d'accueil.
    - Écran de statistiques (score et niveau).
    - Écran de fin de partie.
- **Gestion des scores (permanent)** : Enregistrement du meilleur score dans un fichier.
- **Graphiques personnalisés** : Intégration d'images, de polices et de son spécifiques.

---

## Utilisation
> build : Java 21 (class file version 65.0)

```bash
java -jar Hop.jar
```

- Lancer le programme démarre le jeu avec l'écran d'accueil.
- Jouer seul : les touches directionnelles permettent de contrôler Axel
- Jouer en multijoueur
  - Le deuxième personnage se joue avec les touches ZQSD (respectivement Haut - Gauche - Bas - Droite)
  - La règle est la même : le score enregistré et le joueur le plus haut. Le premier qui tombe met automatiquement fin à la partie

## Structure du projet

```
TP-Projet-Hop/
├── src/
│   ├── main/java/fr/hop/
│   │   ├── Hop.java            # Contrôleur principal du jeu.
│   │   ├── Main.java           # Point d'entrée du programme.
│   │   ├── entities/           # Classes des entités du jeu (Axel, Block).
│   │   ├── game/               # Logique du terrain de jeu.
│   │   ├── inputs/             # Gestion des entrées utilisateur.
│   │   ├── ui/                 # Interface utilisateur (panels, boutons).
│   │   └── utilities/          # Utilitaires (images, scores, etc.).
│   └── main/resources/
│       ├── images/             # Images des personnages et blocs.
│       ├── fonts/              # Fichiers de police personnalisés.
│       └── sounds/             # Sons présent dans le jeu.
└── README.md                   # Documentation du projet.
```

> **Intelligence artificielle** : L'IA a été utilisé pour la génération de méthodes utilitaires (CoordonateUtilities et ImageUtilities) ainsi que pour la rédaction de ce fichier README.

## Contributeurs

* [Assim Zemouchi](mailto:assim.zemouchi@etu-upsaclay.fr?subject=[Github]%20Projet%20Hop) 
* [Anaïs Hidouche](mailto:ouarda.hidouche@etu-upsaclay.fr?subject=[Github]%20Projet%20Hop)


## Licence

Ce projet est sous **licence Unlicense**. Cela signifie que vous pouvez l'utiliser, le modifier et le redistribuer librement, sans aucune restriction.