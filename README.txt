# Projet Labyrinthe A*

## Université Paris-Cité

Auteurs du sujet : LOMÉNIE Nicolas, MAHÉ Gael, élaboré avec LOBRY Sylvain.

## Introduction

Ce projet est un mini-projet d'algorithmie où nous utilisons l'algorithme A* pour résoudre des labyrinthes. L'objectif est de trouver le chemin le plus court du point de départ (D) à la sortie (S) tout en évitant les obstacles (murs `#` et feux `F`).

## Prérequis

Ce projet a été développé en Java. Pour l'exécuter, vous aurez besoin de :
- Java JDK 11 ou supérieur.
- Un environnement capable d'exécuter des scripts Java.

## Fonctionnement

Le programme prend en entrée :
1. Le nombre de labyrinthes à créer.
2. La largeur et la hauteur de chaque labyrinthe.
3. La représentation du labyrinthe, où :
   - `.` représente un espace libre.
   - `D` représente le point de départ.
   - `S` représente la sortie.
   - `F` représente le feu.
   - `#` représente un mur.

### Exemple d'entrée :

```text
2
4 5
....D
.....
.....
F...S
3 4
###D
####
S..F

Chaque labyrinthe résolu présente un résultat : 'Y' signifiant que le prisonnier peut s'échapper du labyrinthe, et 'N' signifiant qu'il ne peut pas échapper en raison des murs ou du feu bloquant tous les chemins possibles. Les résultats sont déterminés après l'application de l'algorithme A* basé sur la configuration initiale du labyrinthe.

Pour exécuter le projet, naviguez dans le répertoire contenant les fichiers et lancez la commande suivante dans votre terminal :
java -jar nom_du_jar.jar
Remplacez nom_du_jar.jar par le nom de votre fichier exécutable Java.


## Contact ##
- Auteur : KADIC
- Email : anais.kadic@etu.u-paris.fr
   
